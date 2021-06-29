package com.microservice.repository

import com.microservice.model.Book

interface BooksRepository {
    fun getAllBook(): List<Book>
}