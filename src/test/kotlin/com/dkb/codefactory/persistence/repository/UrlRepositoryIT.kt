package com.dkb.codefactory.persistence.repository

import com.dkb.codefactory.persistence.model.Url
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.junit.jupiter.Testcontainers

@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = [MongoDBTestContainerConfig::class])
internal class UrlRepositoryIT {

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @Autowired
    private lateinit var urlRepository: UrlRepository

    @BeforeEach
    fun deleteAll() {
        urlRepository.deleteAll()
    }

    @Test
    fun `save url document`() {
        //Arrange & Act
        val savedUrl = saveUrl()

        // Assert
        val returnedUrls = urlRepository.findAll()
        assertThat(returnedUrls.size).isEqualTo(1)
        assertThat(returnedUrls.first()).isEqualTo(savedUrl)
    }

    @Test
    fun `find url document by shortUrl`() {
        //Arrange
        val savedUrl = saveUrl()

        //Act
        val returnedUrl = urlRepository.findByShortUrl(savedUrl.shortUrl)

        //Assert
        assertThat(returnedUrl).isNotNull
        assertThat(returnedUrl).isEqualTo(savedUrl)
    }

    @Test
    fun `find url document by fullUrl`() {
        //Arrange
        val savedUrl = saveUrl()

        //Act
        val returnedUrl = urlRepository.findByFullUrl(savedUrl.fullUrl)

        //Assert
        assertThat(returnedUrl).isNotNull
        assertThat(returnedUrl).isEqualTo(savedUrl)
    }

    private fun saveUrl(): Url {
        val urlToSave = Url("shortUrl", "fullUrl")
        return mongoTemplate.save(urlToSave)
    }
}