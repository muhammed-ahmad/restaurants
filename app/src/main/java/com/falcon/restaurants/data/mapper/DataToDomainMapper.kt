package com.falcon.restaurants.data.mapper

interface DataToDomainMapper <S,D> {
    fun toDomain(model: S): D
    fun toDomainList(models: List<S>): List<D>
}