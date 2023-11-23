package com.dkb.codefactory.service

import com.dkb.codefactory.dto.FullUrlDto
import com.dkb.codefactory.dto.ShortUrlDto
import com.dkb.codefactory.persistence.model.Url
import com.dkb.codefactory.persistence.repository.UrlRepository
import com.dkb.codefactory.service.exceptions.FullUrlNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UrlService {

    @Autowired
    private lateinit var urlRepository: UrlRepository

    @Autowired
    private lateinit var urlShortenerService: UrlShortenerService

    fun getFullUrlFromShortUrl(shortUrl: ShortUrlDto): FullUrlDto {
        val url = urlRepository.findByShortUrl(shortUrl.shortUrl)
            ?: throw FullUrlNotFoundException(shortUrl.shortUrl)
        return FullUrlDto(url.fullUrl)
    }

    fun getShortUrlFromFullUrl(fullUrlDto: FullUrlDto): ShortUrlDto {
        val existingUrl = urlRepository.findByFullUrl(fullUrlDto.fullUrl)
        return existingUrl?.let { ShortUrlDto(it.shortUrl) } ?: saveUrl(fullUrlDto)
    }

    private fun saveUrl(fullUrlDto: FullUrlDto): ShortUrlDto {
        val shortUrlDto = getUniqueShortUrl(fullUrlDto)
        val urlToSave = createUrl(shortUrlDto, fullUrlDto)
        val savedUrl = urlRepository.save(urlToSave)
        return ShortUrlDto(savedUrl.shortUrl)
    }

    private fun getUniqueShortUrl(fullUrlDto: FullUrlDto): ShortUrlDto {
        var shortUrlDto: ShortUrlDto
        var returnedUrl: Url?

        do {
            shortUrlDto = urlShortenerService.generateShortUrl(fullUrlDto.fullUrl)
            returnedUrl = urlRepository.findByShortUrl(shortUrlDto.shortUrl)
        } while (returnedUrl != null)

        return shortUrlDto
    }
    private fun createUrl(shortUrl: ShortUrlDto, fullUrlDto: FullUrlDto): Url {
        return Url(shortUrl.shortUrl, fullUrlDto.fullUrl)
    }
}