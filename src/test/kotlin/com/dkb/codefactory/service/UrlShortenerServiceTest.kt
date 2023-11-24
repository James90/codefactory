package com.dkb.codefactory.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ContextConfiguration(classes = [UrlShortenerService::class])
@ExtendWith(SpringExtension::class)
internal class UrlShortenerServiceTest {

    @Autowired
    private lateinit var urlShortenerService: UrlShortenerService

    @Test
    fun `generateShortUrl generates unique ShortUrlDtos`() {
        //Arrange
        val fullUrl1 = "https://www.example.com"
        val fullUrl2 = "https://www.example.org"

        //Act
        val shortUrlDto1 = urlShortenerService.generateShortUrl(fullUrl1)
        val shortUrlDto2 = urlShortenerService.generateShortUrl(fullUrl2)

        //Assert
        assertEquals(7, shortUrlDto1.shortUrl.length)
        assertEquals(7, shortUrlDto2.shortUrl.length)

        assertNotEquals(shortUrlDto1.shortUrl, shortUrlDto2.shortUrl)
    }

    @Test
    fun `generateShortUrl generates different shortUrls for same URL`() {
        //Arrange
        val fullUrl = "https://www.example.org"

        //Act
        val shortUrlDto1 = urlShortenerService.generateShortUrl(fullUrl)
        val shortUrlDto2 = urlShortenerService.generateShortUrl(fullUrl)

        //Assert
        assertNotEquals(shortUrlDto1.shortUrl, shortUrlDto2.shortUrl)
    }
}