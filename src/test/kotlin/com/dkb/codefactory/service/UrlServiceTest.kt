package com.dkb.codefactory.service

import UrlFixtures.fullUrl
import UrlFixtures.shortUrl
import UrlFixtures.url
import com.dkb.codefactory.dto.FullUrlDto
import com.dkb.codefactory.dto.ShortUrlDto
import com.dkb.codefactory.persistence.model.Url
import com.dkb.codefactory.persistence.repository.UrlRepository
import com.dkb.codefactory.service.exceptions.FullUrlNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ContextConfiguration(classes = [UrlService::class])
@ExtendWith(SpringExtension::class, MockitoExtension::class)
internal class UrlServiceTest {

    @Autowired
    private lateinit var urlService: UrlService

    @MockBean
    private lateinit var urlRepository: UrlRepository

    @MockBean
    private lateinit var urlShortenerService: UrlShortenerService

    @Test
    fun getUrlFromShortUrl() {
        //Arrange
        whenever(urlRepository.findByShortUrl(any())).thenReturn(url)

        //Act
        val returnedUrl = urlService.getFullUrlFromShortUrl(ShortUrlDto(shortUrl))

        //Assert
        assertEquals(FullUrlDto(fullUrl), returnedUrl)
    }

    @Test
    fun `getUrlFromShortUrl throws exception when shortUrl not found`() {
        assertThrows<FullUrlNotFoundException> {
            urlService.getFullUrlFromShortUrl(ShortUrlDto("shortUrl"))
        }
    }

    @Test
    fun `getShortUrlFromFullUrl saves to the database and returns shortUrlDto`() {
        //Arrange
        val shortUrlDto = ShortUrlDto(shortUrl)

        whenever(urlShortenerService.generateShortUrl(any())).thenReturn(shortUrlDto)
        whenever(urlRepository.findByFullUrl(any())).thenReturn(null)
        whenever(urlRepository.save(any<Url>())).thenReturn(url)

        //Act
        val returnedShortUrl = urlService.getShortUrlFromFullUrl(FullUrlDto(fullUrl))

        //Assert
        assertEquals(url.shortUrl, returnedShortUrl.shortUrl)
        verify(urlRepository).save(any<Url>())
        verify(urlRepository).findByFullUrl(any())

    }

    @Test
    fun `getShortUrlFromFullUrl if fullUrl already exists then return its shortUrl`() {
        //Arrange
        whenever(urlRepository.findByFullUrl(any())).thenReturn(url)

        //Act
        val returnedShortUrl = urlService.getShortUrlFromFullUrl(FullUrlDto(fullUrl))

        //Assert
        assertEquals(url.shortUrl, returnedShortUrl.shortUrl)
        verify(urlRepository, never()).save(any())
    }
}