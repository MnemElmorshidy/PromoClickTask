package com.example.promoclicktask.pojo.home

data class HotProductPaidStatu(
    val ProductUserNumber: Int,
    val exp_date: String,
    val id: Int,
    val image: String,
    val name: String,
    val new_price: Int,
    val old_price: Int,
    val rate: Float
)