package com.example.remitconnectapp.data.mapper

interface DomainMapper<E, D> {
    fun mapToDomain(entity: E): D
}