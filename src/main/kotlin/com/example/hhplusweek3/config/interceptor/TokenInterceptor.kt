package com.example.hhplusweek3.config.interceptor

import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenStatus
import com.example.hhplusweek3.service.userQueueToken.UserQueueTokenService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class TokenInterceptor(
    private val userQueueTokenService: UserQueueTokenService
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (isTokenRequired(request).not()) {
            return true
        }

        val token = getToken(request)
        if (isValidToken(token).not()) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("""{"message": "Token is invalid"}""")
            response.contentType = "application/json"
            return false
        }

        // if token is valid, set current user id to request attribute
        val userQueueToken = userQueueTokenService.getByToken(token!!) ?: return false // token is valid, so it should not be null
        request.setAttribute("currentUserQueueToken", userQueueToken)

        return true
    }

    private fun isValidToken(token: String?): Boolean {
        if (token == null) {
            return false
        }
        return userQueueTokenService.getByToken(token)?.status == UserQueueTokenStatus.IN_PROGRESS
    }

    private fun isTokenRequired(request: HttpServletRequest): Boolean {
        // allow all swaggers
        if (request.requestURI.contains("swagger") || request.requestURI.contains("api-docs")) {
            return false
        }

        val nonTokenRequiredList = listOf(
            "/user-queue-tokens" to RequestMethod.POST.name,
            "/users/*/charge" to RequestMethod.POST.name,
            "/users" to RequestMethod.POST.name,
        )
        val requestUri = request.requestURI
        val requestMethod = request.method
        val isTokenRequired = nonTokenRequiredList.none { (uri, method) ->
            requestUri.matches(Regex(uri.replace("*", "[^/]+"))) && requestMethod == method
        }

        return isTokenRequired
    }

    private fun getToken(request: HttpServletRequest): String? {
        return request.getHeader("X-Reservation-Token")
    }
}
