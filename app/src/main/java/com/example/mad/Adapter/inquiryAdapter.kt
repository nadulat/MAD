package com.example.mad.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.ModelClassInquiry
import com.example.mad.R

class inquiryAdpter(private val empList: ArrayList<ModelClassInquiry>) :
    RecyclerView.Adapter <inquiryAdpter.ViewHolder>() {

    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycleview, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEmp = empList[position]

        holder.tvinquiryId.text = currentEmp.Yourid
        holder.tvinquiry.text = currentEmp.inquiry
    }

    override fun getItemCount(): Int {
        return empList.size
    }

    class ViewHolder(itemView: View, listener: OnItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        val tvinquiryId: TextView = itemView.findViewById(R.id.inquiryId)
        val tvinquiry: TextView = itemView.findViewById(R.id.inquiry2)

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }
}
