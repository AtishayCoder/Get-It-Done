package com.example.getitdone.utils

object InputValidator {
    fun checkValidity(input: String?): Boolean = !input?.trim().isNullOrEmpty() && input.length > 1
}