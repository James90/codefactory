package com.dkb.codefactory.persistence.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "urldocuments")
data class Url(
    @Id
    val id: ObjectId = ObjectId(),
    val shortUrl: String,
    val fullUrl: String
) {
    constructor(shortUrl: String, fullUrl: String) : this(ObjectId(), shortUrl, fullUrl)
}