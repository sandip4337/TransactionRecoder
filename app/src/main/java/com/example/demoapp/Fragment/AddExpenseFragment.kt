package com.example.demoapp.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.demoapp.R
import com.example.demoapp.ViewModel.MainViewModel
import com.example.demoapp.data.Transaction
import kotlinx.android.synthetic.main.fragment_add_expence.*
import kotlinx.android.synthetic.main.fragment_add_expence.view.*
import kotlinx.android.synthetic.main.fragment_add_expence.view.addbutton
import kotlinx.android.synthetic.main.fragment_add_expence.view.back

class AddExpenseFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var date: String
    private lateinit var sourceType: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_expence, container, false)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        view.back.setOnClickListener {
            findNavController().navigate(R.id.action_addExpenceFragment_to_dashboardFragment)
        }

        view.addExpenseDate.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            val supportFragmentManager = requireActivity().supportFragmentManager
            supportFragmentManager.setFragmentResultListener(
                "REQUEST_KEY",
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == "REQUEST_KEY") {
                    date = bundle.getString("SELECTED_DATE").toString()
                    date_picker_actions.setText(date)
                }
            }
            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }

        val sourceTypes = resources.getStringArray(R.array.SourceType)
        val adapter =
            ArrayAdapter(requireContext(), R.layout.sample_spinner, sourceTypes)
        view.addType.adapter = adapter
        val spinnerPosition: Int = adapter.getPosition("Type")
        view.addType.setSelection(spinnerPosition)
        view.addType.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                sourceType = sourceTypes[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        view.addbutton.setOnClickListener {
            addExpense(date, sourceType)
        }
        return view
    }

    private fun addExpense(date: String, sourceType: String) {
        val comment = addComment.text.toString()
        val amount = Integer.parseInt(addAmount.text.toString())

        if (inputCheck(date, comment, addAmount.text, sourceType)) {

            val transaction = Transaction(0, amount, comment, date, sourceType)
            mainViewModel.addTransaction(transaction)

            val expense = mainViewModel.getTotalExpense()
            val totalExpense = expense + amount
            mainViewModel.updateExpense(totalExpense)

            Toast.makeText(requireContext(), "Successfully Added", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(
        date: String,
        comment: String,
        amount: Editable,
        sourceType: String
    ): Boolean {
        return !(TextUtils.isEmpty(date) && TextUtils.isEmpty(comment) && amount.isEmpty() && sourceType.isEmpty())
    }
}
