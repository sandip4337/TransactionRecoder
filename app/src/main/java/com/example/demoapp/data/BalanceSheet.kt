package com.example.demoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BalanceSheetTable")
data class BalanceSheet(
    @PrimaryKey(autoGenerate = true)
    val Id: Int,
    val TotalSalary: Int,
    val TotalExpense: Int
)
