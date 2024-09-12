package com.example.bank

data class Expenses(val amount: Double, val category: String, val date: String) {
    fun displayDetails() = "Сумма: $amount\nКатегория: $category\nДата: $date\n"
}