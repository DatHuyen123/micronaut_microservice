package com.dirox.exceptions

class UserBadRequestException(val exMessage: String) : RuntimeException(exMessage)