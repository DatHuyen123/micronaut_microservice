package com.dirox.error

import com.dirox.exceptions.UserBadRequestException
import io.micronaut.context.annotation.Requirements
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.http.server.exceptions.response.ErrorContext
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor
import javax.inject.Singleton

@Produces
@Singleton
@Requirements(
    Requires(classes = [UserBadRequestException::class, ExceptionHandler::class])
)
class UserBadRequestExceptionHandler(private val errorResponseProcessor: ErrorResponseProcessor<Any>) :
    ExceptionHandler<UserBadRequestException, HttpResponse<*>> {

    override fun handle(request: HttpRequest<*>, exception: UserBadRequestException): HttpResponse<*> {
        return errorResponseProcessor.processResponse(
            ErrorContext.builder(request)
                .cause(exception)
                .errorMessage(exception.exMessage)
                .build(), HttpResponse.badRequest<Any>()
        )
    }
}