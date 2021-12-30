package com.riteshk.archunit.junit5.kotlin.example.util

class NameValidator {

    fun isValidName(name: String): Boolean {
        return !name.isBlank()
    }
}