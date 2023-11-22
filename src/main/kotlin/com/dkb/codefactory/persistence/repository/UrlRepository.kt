package com.dkb.codefactory.persistence.repository

import com.dkb.codefactory.persistence.model.Url
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UrlRepository : MongoRepository<Url, ObjectId> {
    fun findByShortUrl(longUrl: String): Url?
}