package com.example.hhplusweek3.config.interceptor

import com.example.hhplusweek3.domain.userQueueToken.UserQueueToken
import com.example.hhplusweek3.domain.userQueueToken.UserQueueTokenDomain
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TokenInterceptorTest {
    private val userQueueTokenDomain = mockk<UserQueueTokenDomain>()
    private val tokenInterceptor = spyk(TokenInterceptor(userQueueTokenDomain), recordPrivateCalls = true)

    @Test
    fun `preHandle - should return true when token is not required`() {
        // given
        val request = mockk<HttpServletRequest>()
        val response = mockk<HttpServletResponse>()
        val handler = mockk<Any>()

        every { tokenInterceptor["isTokenRequired"](request) } answers { false }

        // when
        val result = tokenInterceptor.preHandle(request, response, handler)

        // then
        assertTrue(result)
        verify(exactly = 1) { tokenInterceptor["isTokenRequired"](request) }
    }

    @Test
    fun `preHandle - should return false when token is invalid`() {
        // given
        val request = mockk<HttpServletRequest>()
        val response = mockk<HttpServletResponse>() {
            every { status = HttpServletResponse.SC_UNAUTHORIZED } answers { Unit }
            every { writer.write("""{"message": "Token is invalid"}""") } answers { Unit }
            every { contentType = "application/json" } answers { Unit }
        }
        val handler = mockk<Any>()

        every { tokenInterceptor["isTokenRequired"](request) } answers { true }
        every { tokenInterceptor["getToken"](request) } answers { "token" }
        every { tokenInterceptor["isValidToken"]("token") } answers { false }

        // when
        val result = tokenInterceptor.preHandle(request, response, handler)

        // then
        assertTrue(!result)
        verify(exactly = 1) { tokenInterceptor["isTokenRequired"](request) }
        verify(exactly = 1) { tokenInterceptor["getToken"](request) }
        verify(exactly = 1) { tokenInterceptor["isValidToken"]("token") }
        verify(exactly = 1) { response.status = HttpServletResponse.SC_UNAUTHORIZED }
        verify(exactly = 1) { response.writer.write("""{"message": "Token is invalid"}""") }
        verify(exactly = 1) { response.contentType = "application/json" }
    }

    @Test
    fun `preHandle - should set currentUser attribute and return true when valid`() {
        // given
        val request = mockk<HttpServletRequest>()
        val response = mockk<HttpServletResponse>()
        val handler = mockk<Any>()
        val userQueueToken = mockk<UserQueueToken>() {
            every { user } answers { mockk() }
        }

        every { tokenInterceptor["isTokenRequired"](request) } answers { true }
        every { tokenInterceptor["getToken"](request) } answers { "token" }
        every { tokenInterceptor["isValidToken"]("token") } answers { true }
        every { userQueueTokenDomain.getUseQueueTokenByToken("token") } answers { userQueueToken }
        every { request.setAttribute("currentUserQueueToken", userQueueToken) } answers { Unit }

        // when
        val result = tokenInterceptor.preHandle(request, response, handler)

        // then
        assertTrue(result)
        verify(exactly = 1) { tokenInterceptor["isTokenRequired"](request) }
        verify(exactly = 1) { tokenInterceptor["getToken"](request) }
        verify(exactly = 1) { tokenInterceptor["isValidToken"]("token") }
        verify(exactly = 1) { userQueueTokenDomain.getUseQueueTokenByToken("token") }
        verify(exactly = 1) { request.setAttribute("currentUserQueueToken", userQueueToken) }
    }
}






















