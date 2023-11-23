package com.dkb.codefactory.service

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

    //TODO test post

    @Test
    fun getUrlFromShortUrl() {
        //Arrange
        val fullUrl = "fullUrl"
        val shortUrl = "shortUrl"
        val url = Url(shortUrl, fullUrl)
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
}