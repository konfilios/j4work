package org.j4work.spring5.web.jsonauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;

/**
 * .
 */
public class JsonLogin
{
    static private final Log logger = LogFactory.getLog(JsonLogin.class);

    static public HttpSecurity configure(HttpSecurity http, String loginUri)
        throws Exception
    {
        if (logger.isDebugEnabled())
            logger.debug("Configuring JSON login on " + loginUri);

        JsonLoginDtoProducer loginDtoProducer = (req, resp, auth) -> auth.getPrincipal();
        // @formatter:off
        return http
            // Unauthorized - Prompt for login
            .exceptionHandling()
                .authenticationEntryPoint((req, resp, e)->resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage()))
//                .accessDeniedHandler((req, resp, e)->resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: " + e.getMessage()))
                .and()
            // Logging in
            .apply(new JsonLoginConfigurer<>())
                .loginPage(loginUri)
                .failureHandler((req, resp, e) -> resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage()))
                .successHandler((req, resp, auth) -> {
                    resp.getWriter().write(new ObjectMapper().writeValueAsString(loginDtoProducer.produce(req, resp, auth)));
                    resp.addHeader("Content-Type", req.getHeader("Content-Type"));
                })
                .and()
            // Logging out
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(loginUri, "DELETE"))
                .logoutSuccessHandler((req, resp, auth) -> resp.setStatus(HttpServletResponse.SC_NO_CONTENT))
                .and()
            // Install filter LogoutFilter
            .addFilterBefore(
                new JsonLoginGetAuthenticationFilter(
                    new AntPathRequestMatcher(loginUri, "GET"),
                    loginDtoProducer
                ),
                LogoutFilter.class
            )
            ;
        // @formatter:on
    }
}
