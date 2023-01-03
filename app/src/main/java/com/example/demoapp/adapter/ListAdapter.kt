package com.example.demoapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.Fragment.DashboardFragment
import com.example.demoapp.R
import com.example.demoapp.ViewModel.MainViewModel
import com.example.demoapp.data.Transaction
import kotlinx.android.synthetic.main.transaction_layout.view.*

class ListAdapter(
    private val context: Context,
    private var mainViewModel: MainViewModel,
    private var isDeleteOption: Boolean
) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var transactionList = emptyList<Transaction>()


    fun setData(items: List<Transaction>) {
        this.transactionList = items
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.transaction_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = transactionList[position]
        holder.itemView.transaction_amount.text = item.Amount.toString()
        holder.itemView.date.text = item.Date
        holder.itemView.comment.text = item.Comment
        holder.itemView.incoming.visibility = View.GONE
        holder.itemView.outgoing.visibility = View.GONE
        holder.itemView.delete_transaction.visibility = View.GONE

        if (item.Type == "Income") {
            holder.itemView.incoming.visibility = View.VISIBLE
        }

        if (item.Type == "Expense") {
            holder.itemView.outgoing.visibility = View.VISIBLE
        }

        if (isDeleteOption) {
            holder.itemView.delete_transaction.visibility = View.VISIBLE
        }

        holder.itemView.delete_transaction.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setPositiveButton("Yes") { _, _ ->

                if (item.Type == "Income") {
                    val income: Int = mainViewModel.getTotalIncome()
                    val totalIncome = income - item.Amount
                    mainViewModel.updateIncome(totalIncome)
                }

                if (item.Type == "Expense") {
                    val expense = mainViewModel.getTotalExpense()
                    val totalExpense = expense - item.Amount
                    mainViewModel.updateExpense(totalExpense)
                }

                mainViewModel.deleteTransaction(item)

                Toast.makeText(
                    context, "Successfully Removed", Toast.LENGTH_SHORT
                ).show()
            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.setTitle("Delete ${item.Comment}?")
            builder.setMessage("Are You Sure , You Want To Delete ${item.Comment}?")
            builder.create().show()
        }
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

}