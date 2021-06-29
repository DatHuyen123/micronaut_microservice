package com.microservice.api

import com.microservice.model.Book
import com.microservice.repository.BooksRepository
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/api")
class BookAPI(private val booksRepository: BooksRepository) {

    @Get("/books")
    fun list(): List<Book> {
        return booksRepository.getAllBook()
    }
}