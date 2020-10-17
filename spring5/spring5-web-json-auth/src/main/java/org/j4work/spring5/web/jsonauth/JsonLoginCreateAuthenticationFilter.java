package org.j4work.spring5.web.jsonauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Create a login using JSON.
 */
public class JsonLoginCreateAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    public JsonLoginCreateAuthenticationFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request, HttpServletResponse response
    ) {
        if (!request.getMethod().equals("PUT")) {
            throw new AuthenticationServiceException(
                "Authentication method not supported: " + request.getMethod());
        }

        // JSON-Decode request body and retrieve LoginDto.
        JsonLoginCreateCmd jsonLoginCreateCmd;
        try {
            jsonLoginCreateCmd = objectMapper.readValue(request.getInputStream(),
                JsonLoginCreateCmd.class);
        } catch (IOException e) {
//            e.printStackTrace();
            Logger.getLogger(JsonLoginCreateAuthenticationFilter.class.getName()).log(Level.SEVERE,
                null, e);
            throw new RuntimeException(e);
//            return null;
        }

        // The rest of the code is copied from the parent class
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
            jsonLoginCreateCmd.username, jsonLoginCreateCmd.password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

}