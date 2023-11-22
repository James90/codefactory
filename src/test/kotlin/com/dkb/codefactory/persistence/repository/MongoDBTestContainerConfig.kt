package com.dkb.codefactory.persistence.repository

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container

@Configuration
@EnableMongoRepositories(basePackages = ["com.dkb.codefactory.persistence.repository"])
class MongoDBTestContainerConfig {

    companion object {
        private val DEFAULT_PORT = 27017

        @Container
        private val mongoDBContainer = MongoDBContainer("mongo:latest")
            .withExposedPorts(DEFAULT_PORT)

        init {
            mongoDBContainer.start()
            val mappedPort = mongoDBContainer.getMappedPort(DEFAULT_PORT)
            System.setProperty("mongodb.container.port", mappedPort.toString())
        }
    }
}