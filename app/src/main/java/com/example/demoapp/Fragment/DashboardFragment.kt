package com.example.demoapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoapp.adapter.ListAdapter
import com.example.demoapp.R
import com.example.demoapp.ViewModel.MainViewModel
import com.example.demoapp.data.BalanceSheet
import com.example.demoapp.data.Transaction
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class DashboardFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ListAdapter
    private var isAllFabVisible: Boolean? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        isAllFabVisible = false

        view.add_fab.setOnClickListener {
            (if (!isAllFabVisible!!) {
                view.expense_fab.show()
                view.income_fab.show()
                add_income_fab.visibility = View.VISIBLE
                add_expense_fab.visibility = View.VISIBLE
                true
            } else {
                view.expense_fab.hide()
                view.income_fab.hide()
                view.add_income_fab.visibility = View.GONE
                view.add_expense_fab.visibility = View.GONE
                false
            }).also { isAllFabVisible = it }
        }

        view.income_fab.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_editIncomeFragment)
        }

        val totalIncome = mainViewModel.getTotalIncome()
        val totalExpense = mainViewModel.getTotalExpense()

        view.expense_fab.setOnClickListener {
            if (totalIncome != 0 || totalIncome <= totalExpense){
                findNavController().navigate(R.id.action_dashboardFragment_to_addExpenceFragment)
            } else {
                Toast.makeText(requireContext(), "You can't add expense, Please add money to expense", Toast.LENGTH_SHORT).show()
            }
        }

        adapter = ListAdapter(requireContext(), mainViewModel, false)
        val recyclerView = view.transaction
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val transaction: List<Transaction> = mainViewModel.fetchNTransaction()

        if (transaction.isNotEmpty()) {
            adapter.setData(transaction)
            view.transaction.visibility = View.VISIBLE
        } else {
            view.txt_no_transaction.visibility = View.VISIBLE
        }

        if (totalIncome == 0){
            val balance = BalanceSheet(0, 0, 0)
            mainViewModel.addBalance(balance)
        }

        view.txt_income.text = totalIncome.toString()


        totalExpense.let {
            view.txt_expense.text = it.toString()
        }

        view.view_all.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_myTransactionFragment)
        }

        if (totalExpense >= totalIncome){
            Toast.makeText(requireContext(), "You have no money, Please add money", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}