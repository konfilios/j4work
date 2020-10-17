package org.j4work.spring5.web.jsonauth;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Produces a response body for GET /login.
 */
public interface JsonLoginDtoProducer {

    Object produce(
        HttpServletRequest request, HttpServletResponse response, Authentication authentication
    );
}
