package com.example.test.Entity

data class Bank(var amount: Int) {
    fun resetAmount() {
        this.amount = 0
    }
}