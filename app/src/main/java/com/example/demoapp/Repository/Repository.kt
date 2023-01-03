package com.example.demoapp.Repository

import androidx.lifecycle.LiveData
import com.example.demoapp.LocalDatabase.TransactionDao
import com.example.demoapp.data.BalanceSheet
import com.example.demoapp.data.Transaction

class Repository(private val transactionDao: TransactionDao) {

    val transactionInfo: LiveData<List<Transaction>> = transactionDao.getTransactionList()

    suspend fun addTransaction(transaction: Transaction) {
        transactionDao.addTransaction(transaction)
    }

    suspend fun addBalance(balanceSheet: BalanceSheet) {
        transactionDao.addBalance(balanceSheet)
    }

    fun updateExpense(totalExpense: Int) {
        transactionDao.updateExpense(totalExpense)
    }

    fun updateIncome(totalIncome: Int) {
        transactionDao.updateIncome(totalIncome)
    }

    fun getTotalIncome(): Int {
        return transactionDao.getTotalMoney()
    }

    fun getTotalExpense(): Int {
        return transactionDao.getExpenseMoney()
    }

    suspend fun deleteTransaction(item: Transaction) {
        transactionDao.deleteTransaction(item)
    }

    suspend fun deleteAllUser(){
        transactionDao.deleteAllTransaction()
    }

    fun fetchNTransaction(): List<Transaction> {
        return transactionDao.getFirstNTransaction()
    }

    suspend fun updateBalanceSheet(info: BalanceSheet) {
        transactionDao.updateBalanceSheet(info)
    }
}