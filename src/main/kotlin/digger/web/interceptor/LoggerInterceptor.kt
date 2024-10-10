/*
 * Digger
 * Copyright (C) 2019-2022 Hildeberto Mendonca
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
package digger.web.interceptor

import digger.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LoggerInterceptor(private val userRepository: UserRepository) : HandlerInterceptor {
    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.userPrincipal == null) {
            return true
        }
        if ("GET" == request.method) {
            logger.info("{} navigates to {}", request.userPrincipal.name, request.requestURI + getParameters(request))
        }
        val session = request.getSession(true)
        if (session.getAttribute("fullName") == null) {
            val user = userRepository.findByUsername(request.userPrincipal.name)
            session.setAttribute("fullName", user.fullName)
        }
        return true
    }

    private fun getParameters(request: HttpServletRequest): String {
        val e: Enumeration<*>? = request.parameterNames
        if (e == null || !e.hasMoreElements()) {
            return ""
        }
        val posted = StringBuilder()
        while (e.hasMoreElements()) {
            posted.append(if (posted.length > 1) "&" else "?")
            val curr = e.nextElement() as String
            posted.append(curr)
            posted.append("=")
            posted.append(request.getParameter(curr))
        }
        return posted.toString()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(LoggerInterceptor::class.java)
    }
}