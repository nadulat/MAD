package com.example.mad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.Feedback

class FeedbackAdapter(var mList: List<Feedback>) :
    RecyclerView.Adapter<FeedbackAdapter.viewHolder>() {

    private lateinit var mListener : onItemClickListener

    //Setting up onClick listner interface
    interface onItemClickListener{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListener(listner: onItemClickListener){
        mListener = listner
    }

    inner class viewHolder(itemView: View, listner: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val feedback: TextView = itemView.findViewById(R.id.tvFeedback)

        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_feedback, parent, false)
        return viewHolder(view, mListener)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.feedback.text = mList[position].dis
    }


}