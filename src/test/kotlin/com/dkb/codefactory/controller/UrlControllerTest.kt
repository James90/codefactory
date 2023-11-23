package com.dkb.codefactory.controller

import UrlFixtures.fullUrl
import UrlFixtures.shortUrl
import com.dkb.codefactory.CodefactoryApplication.Companion.REST_V1_PATH
import com.dkb.codefactory.dto.FullUrlDto
import com.dkb.codefactory.dto.ShortUrlDto
import com.dkb.codefactory.service.UrlService
import com.dkb.codefactory.service.exceptions.FullUrlNotFoundException
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UrlController::class)
@AutoConfigureMockMvc(print = MockMvcPrint.SYSTEM_OUT)
internal class UrlControllerTest {

    companion object {
        const val REST_PATH = "$REST_V1_PATH/url-shortener"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var urlService: UrlService

    @Test
    fun getFullUrl() {
        //Arrange
        val fullUrlDto = FullUrlDto(fullUrl)
        whenever(urlService.getFullUrlFromShortUrl(any())).thenReturn(fullUrlDto)

        //Act & Assert
        performGetFullUrl()
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(content().json(fullUrlDto.toJsonString(), true))
    }

    @Test
    fun postFullUrl() {
        //Arrange
        val shortUrlDto = ShortUrlDto(shortUrl)
        val fullUrlDto = FullUrlDto(fullUrl)
        whenever(urlService.getShortUrlFromFullUrl(any())).thenReturn(shortUrlDto)

        //Act & Assert
        mockMvc.perform(
            post(REST_PATH)
                .content(fullUrlDto.toJsonString())
                .contentType(APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType((APPLICATION_JSON_VALUE)))
            .andExpect(
                content().json(shortUrlDto.toJsonString(), true)
            )
    }

    @Test
    fun `returns not found when URL cannot be found`() {
        val shortUrlDto = ShortUrlDto(shortUrl)
        whenever(urlService.getFullUrlFromShortUrl(any())).thenThrow(FullUrlNotFoundException(shortUrlDto.shortUrl))

        //Act
        performGetFullUrl()
            .andExpect(status().isNotFound)
            .andExpect(content().contentType("text/plain;charset=UTF-8"))
            .andExpect(
                content().string(FullUrlNotFoundException.message(shortUrlDto.shortUrl))
            )
    }

    private fun performGetFullUrl() = mockMvc.perform(
        get(REST_PATH)
            .queryParam(shortUrl, shortUrl)
            .contentType(APPLICATION_JSON_VALUE)
    )

    private fun FullUrlDto.toJsonString(): String {
        return "{\"fullUrl\":\"${this.fullUrl}\"}"
    }

    private fun ShortUrlDto.toJsonString(): String {
        return "{\"shortUrl\":\"${this.shortUrl}\"}"
    }
}