package com.dkb.codefactory.service

import com.dkb.codefactory.dto.ShortUrlDto
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.security.SecureRandom

@Service
class UrlShortenerService {

    companion object {
        const val MD5_ALGORITHM = "MD5"
        const val HEX_FORMAT = "%02x"
        const val SHORT_URL_LENGTH = 7
    }

    fun generateShortUrl(fullUrl: String): ShortUrlDto {
        val messageDigest = MessageDigest.getInstance(MD5_ALGORITHM)

        val salt = generateSalt()
        val digestBytes = messageDigest.digest((salt + fullUrl).toByteArray())

        val hexString = digestBytes.joinToString("") { HEX_FORMAT.format(it) }

        return ShortUrlDto(hexString.substring(0, SHORT_URL_LENGTH))
    }

    private fun generateSalt(): String {
        val random = SecureRandom()
        val saltBytes = ByteArray(1)
        random.nextBytes(saltBytes)
        return saltBytes.joinToString("") { HEX_FORMAT.format(it) }
    }
}