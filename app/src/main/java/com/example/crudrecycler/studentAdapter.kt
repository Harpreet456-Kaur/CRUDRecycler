package com.o7services.recyclercrud

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudrecycler.databinding.ItemRvLayoutBinding
import com.example.crudrecycler.newInterface
import com.example.crudrecycler.studentModel

class studentAdapter(val studentList:ArrayList<studentModel>,val newInterface: newInterface):RecyclerView.Adapter<studentAdapter.viewHolder>(){
    class viewHolder (val binding:ItemRvLayoutBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding=ItemRvLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(binding)

    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.binding.tvName.text=studentList[position].name
        holder.binding.tvRollNo.text=studentList[position].rollno
        holder.itemView.setOnClickListener {
            newInterface.Edit(position)

        }
    }

    override fun getItemCount(): Int {
        return studentList.size
    }
}