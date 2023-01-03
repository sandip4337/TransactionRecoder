package com.example.demoapp.LocalDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.demoapp.data.BalanceSheet
import com.example.demoapp.data.Transaction

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTransaction(info : Transaction)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBalance(info : BalanceSheet)

    @Delete
    suspend fun deleteTransaction(info: Transaction)

    @Update
    suspend fun updateBalanceSheet(info: BalanceSheet)

    @Query("DELETE FROM TransactionTable")
    suspend fun deleteAllTransaction()

    @Query("SELECT * FROM TransactionTable\n" +
            "LIMIT 3")
    fun getFirstNTransaction() : List<Transaction>

    @Query("SELECT * FROM TransactionTable ORDER BY TransactionId ASC")
    fun getTransactionList(): LiveData<List<Transaction>>

    @Query("SELECT TotalExpense FROM BalanceSheetTable")
    fun getExpenseMoney(): Int

    @Query("SELECT TotalSalary FROM BalanceSheetTable")
    fun getTotalMoney(): Int

    @Query("UPDATE BalanceSheetTable\n" +
            "SET TotalExpense = :totalExpense;")
    fun updateExpense(totalExpense: Int)

    @Query("UPDATE BalanceSheetTable\n" +
            "SET TotalSalary = :totalIncome;")
    fun updateIncome(totalIncome: Int)

}