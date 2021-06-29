package com.microservice.repository.impl

import com.microservice.model.Book
import com.microservice.repository.BooksRepository

class BooksRepositoryImpl: BooksRepository {

    override fun getAllBook(): List<Book> {
        val book: Book = Book(id = 1 , name = "hello" , page = 1)
        val book2: Book = Book(id = 2 , name = "hello2" , page = 2)
        var books: MutableList<Book> = mutableListOf(book , book2)
        return books
    }

}