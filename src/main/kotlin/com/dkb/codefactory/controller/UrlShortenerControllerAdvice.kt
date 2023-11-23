package com.dkb.codefactory.controller

import com.dkb.codefactory.service.exceptions.FullUrlNotFoundException
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class UrlShortenerControllerAdvice {

    @ExceptionHandler(FullUrlNotFoundException::class)
    fun handleFullUrlNotFoundException(e: FullUrlNotFoundException): ResponseEntity<String> {
        return ResponseEntity(e.message, NOT_FOUND)
    }
}