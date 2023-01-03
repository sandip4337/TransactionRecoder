package com.example.demoapp.Fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.adapter.ListAdapter
import com.example.demoapp.R
import com.example.demoapp.ViewModel.MainViewModel
import com.example.demoapp.data.BalanceSheet
import kotlinx.android.synthetic.main.fragment_my_transaction.*
import kotlinx.android.synthetic.main.fragment_my_transaction.view.*

class MyTransactionFragment : Fragment() {

    lateinit var mainViewModel: MainViewModel
    lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_transaction, container, false)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        adapter = ListAdapter(requireContext(), mainViewModel, true)
        val recyclerView = view.all_transaction
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        view.back.setOnClickListener {
            findNavController().navigate(R.id.action_myTransactionFragment_to_dashboardFragment)
        }

        view.delete_all.setOnClickListener {
            deleteAllUser()
        }

        return view
    }

    private fun getData(
        mainViewModel: MainViewModel,
        allTransaction: RecyclerView,
        txtNoTransaction: TextView,
        adapter: ListAdapter
    ) {
        mainViewModel.transactionInfo.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.setData(it)
                allTransaction.visibility = View.VISIBLE
                txtNoTransaction.visibility = View.GONE
            } else {
                allTransaction.visibility = View.GONE
                txtNoTransaction.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getData(mainViewModel, this.all_transaction, this.txt_no_transaction, adapter)
    }

    private fun deleteAllUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->

            mainViewModel.deleteAllUser()

            val balance = BalanceSheet(1, 0, 0)
            mainViewModel.updateBalanceSheet(balance)

            Toast.makeText(
                requireContext(), "Successfully Removed everything", Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete Everything?")
        builder.setMessage("Are You Sure , You Want To Delete Everything?")
        builder.create().show()
    }
}