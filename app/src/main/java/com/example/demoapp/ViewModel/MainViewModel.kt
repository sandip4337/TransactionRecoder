package com.example.demoapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.demoapp.LocalDatabase.TransactionDatabase
import com.example.demoapp.Repository.Repository
import com.example.demoapp.data.BalanceSheet
import com.example.demoapp.data.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    val transactionInfo: LiveData<List<Transaction>>
    private val repository: Repository

    init {
        val transactionDao = TransactionDatabase.getDataBase(application).transactionDao()
        repository = Repository(transactionDao)
        transactionInfo = repository.transactionInfo
    }

    fun fetchNTransaction() : List<Transaction> {
       return repository.fetchNTransaction()
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTransaction(transaction)
        }
    }

    fun addBalance(balanceSheet: BalanceSheet) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBalance(balanceSheet)
        }
    }


    fun updateExpense(total_expense: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateExpense(total_expense)
        }
    }

    fun updateIncome(totalIncome: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateIncome(totalIncome)
        }
    }

    fun getTotalIncome(): Int {
        return repository.getTotalIncome()
    }

    fun getTotalExpense(): Int {
        return repository.getTotalExpense()
    }

    fun deleteTransaction(item: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTransaction(item)
        }
    }

    fun deleteAllUser() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUser()
        }
    }

    fun updateBalanceSheet(info: BalanceSheet){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBalanceSheet(info)
        }
    }

}