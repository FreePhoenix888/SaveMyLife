package com.freephoenix888.savemylife.mappers

interface Mapper<I, O> {
    fun map(input: I): O
}