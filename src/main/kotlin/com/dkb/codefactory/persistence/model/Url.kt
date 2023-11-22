package com.dkb.codefactory.persistence.model

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "urldocuments")
data class Url(
/*    @Id
    val id: ObjectId = ObjectId(),*/
    val longUrl: String,
    val shortUrl: String
)