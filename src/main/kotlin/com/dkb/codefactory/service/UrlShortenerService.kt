package com.dkb.codefactory.service

import com.dkb.codefactory.dto.ShortUrlDto
import org.springframework.stereotype.Service

@Service
class UrlShortenerService {

    fun generateShortUrl(fullUrl: String): ShortUrlDto{
        return ShortUrlDto("") //TODO
    }
}