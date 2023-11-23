package com.dkb.codefactory.controller

import com.dkb.codefactory.CodefactoryApplication.Companion.REST_V1_PATH
import com.dkb.codefactory.dto.FullUrlDto
import com.dkb.codefactory.dto.ShortUrlDto
import com.dkb.codefactory.service.UrlService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("${REST_V1_PATH}/url-shortener")
class UrlController(private val urlService: UrlService) {

    @GetMapping
    fun getFullUrl(
        @RequestParam(name = "shortUrl", required = true)
        shortUrl: ShortUrlDto
    ): ResponseEntity<FullUrlDto> {
        val fullUrlDto = urlService.getFullUrlFromShortUrl(shortUrl)
        return ResponseEntity(fullUrlDto, OK)
    }

    @PostMapping
    fun getShortUrl(
        @RequestBody
        fullUrlDto: FullUrlDto
    ): ResponseEntity<ShortUrlDto> {
        val shortUrlDto = urlService.postFullUrl(fullUrlDto.fullUrl)
        return ResponseEntity(shortUrlDto, CREATED)
    }
}