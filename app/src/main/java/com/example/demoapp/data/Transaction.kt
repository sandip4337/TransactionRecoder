package com.example.demoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TransactionTable")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val TransactionId: Int,
    val Amount: Int,
    val Comment: String,
    val Date: String,
    val Type: String
)
