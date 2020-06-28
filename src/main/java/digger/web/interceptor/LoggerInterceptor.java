/*
 * Digger
 * Copyright (C) 2019-2020 Hildeberto Mendonca
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * A full copy of the GNU General Public License is available at:
 * https://github.com/htmfilho/digger/blob/master/LICENSE
 */

package digger.web.interceptor;

import digger.model.User;
import digger.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    private final static Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

    private UserRepository userRepository;

    public LoggerInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getUserPrincipal() == null) {
            return true;
        }

        if("GET".equals(request.getMethod())) {
            logger.info("{} navigates to {}", request.getUserPrincipal().getName() , request.getRequestURI() + getParameters(request));
        }

        HttpSession session = request.getSession(true);
        if (session.getAttribute("fullName") == null) {
            User user = userRepository.findByUsername(request.getUserPrincipal().getName());
            session.setAttribute("fullName", user.getFullName());
        }

        return true;
    }

    private String getParameters(HttpServletRequest request) {
        Enumeration<?> e = request.getParameterNames();
        if (e == null || !e.hasMoreElements()) {
            return "";
        }

        StringBuilder posted = new StringBuilder();
        while (e.hasMoreElements()) {
            posted.append(posted.length() > 1 ? "&" : "?");
            String curr = (String) e.nextElement();
            posted.append(curr);
            posted.append("=");
            posted.append(request.getParameter(curr));
        }
        return posted.toString();
    }
}
