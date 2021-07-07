package com.example.promoclicktask.pojo.details



data class RelatedProduct(
    val Gallary: List<Gallary>,
    val ProductUserNumber: Int,
    val address: String,
    val description: String,
    val email: String,
    val exp_date: String,
    val id: Int,
    val image: String,
    val lat: String,
    val lng: String,
    val mobile: String,
    val name: String,
    val new_price: Int,
    val old_price: Int,
    val rate: Double,
    val review: List<Review>,
    val vendor: String,
    val view: String
)