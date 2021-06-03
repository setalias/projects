package com.example.lettuceapp

interface User {
    fun createProfile() : Boolean
    fun clearField()
    fun isDuplicateUser(email : String) : Int
    // fun validate
}