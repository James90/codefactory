package com.dkb.codefactory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CodefactoryApplication {
    companion object {
        const val REST_BASE_PATH = "/api"
        const val REST_V1_PATH = "$REST_BASE_PATH/v1"
    }
}

fun main(args: Array<String>) {
    runApplication<CodefactoryApplication>(*args)
}
