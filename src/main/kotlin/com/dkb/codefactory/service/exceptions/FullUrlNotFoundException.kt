package com.dkb.codefactory.service.exceptions

class FullUrlNotFoundException(shortUrl: String) : RuntimeException(message(shortUrl)) {

    companion object {
        fun message(shortUrl: String) =
            "Full URL was not found for shortened URL: '${shortUrl}'"
    }
}