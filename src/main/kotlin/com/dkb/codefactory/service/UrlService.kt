package com.dkb.codefactory.service

import com.dkb.codefactory.dto.FullUrlDto
import com.dkb.codefactory.dto.ShortUrlDto
import com.dkb.codefactory.persistence.repository.UrlRepository
import com.dkb.codefactory.service.exceptions.FullUrlNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UrlService {

    @Autowired
    private lateinit var urlRepository: UrlRepository

    fun getFullUrlFromShortUrl(shortUrl: ShortUrlDto): FullUrlDto {
        val url = urlRepository.findByShortUrl(shortUrl.shortUrl)
            ?: throw FullUrlNotFoundException(shortUrl.shortUrl)
        return FullUrlDto(url.fullUrl)
    }

    fun postFullUrl(fullUrl: String): ShortUrlDto {
        //TODO
        //Here we generate a shortUrl and save the key-pair shortUrl:fullUrl
        // We have to check if the fullUrl already exists
        //If it exists, return its shortUrl. If not, create one and return it
        return ShortUrlDto("")
    }
}