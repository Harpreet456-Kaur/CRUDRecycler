package com.example.crudrecycler

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudrecycler.databinding.ActivityMainBinding
import com.example.crudrecycler.databinding.AddLayoutBinding
import com.example.crudrecycler.databinding.EditLayoutBinding
import com.o7services.recyclercrud.studentAdapter

class MainActivity : AppCompatActivity(), newInterface {
    lateinit var binding: ActivityMainBinding
    var studentList=ArrayList<studentModel>()
    lateinit var studentAdapter: studentAdapter
    lateinit var newInterface:newInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentAdapter = studentAdapter(studentList, this)
        binding.lv.adapter = studentAdapter
        binding.lv.layoutManager = LinearLayoutManager(this)
        binding.floatingBtn.setOnClickListener {
            val dialogBinding = AddLayoutBinding.inflate(layoutInflater)
            val dialog = Dialog(this)
            dialog.setContentView(dialogBinding.root)
            dialogBinding.btnAdd.setOnClickListener {
                if (dialogBinding.etName.text.toString().isEmpty()) {
                    dialogBinding.etName.error = "Enter your name"
                } else if (dialogBinding.etRollNo.text.toString().isEmpty()) {
                    dialogBinding.etRollNo.error = "Enter your Rollno"
                } else {
                    studentList.add(
                        studentModel(
                            dialogBinding.etName.text.toString(),
                            dialogBinding.etRollNo.text.toString()
                        )
                    )
                    dialog.dismiss()
                    studentAdapter.notifyDataSetChanged()
                }
            }
            dialog.show()
        }
    }

    override fun Edit(position: Int) {
        val dialogboxBinding=EditLayoutBinding.inflate(layoutInflater)
        val dialogbox=Dialog(this)
        dialogbox.setContentView(dialogboxBinding.root)
        dialogboxBinding.btnEdit.setOnClickListener {
            if (dialogboxBinding.etName.text.toString().isEmpty()){
                dialogboxBinding.etName.error="enter name"
            }
            else if (dialogboxBinding.etRollNo.text.toString().isEmpty()){
                dialogboxBinding.etRollNo.error="enter rollno"
            }
            else{
                studentList.set(position,studentModel(dialogboxBinding.etName.text.toString(),
                    dialogboxBinding.etRollNo.text.toString()))
                studentAdapter.notifyDataSetChanged()
                dialogbox.dismiss()
            }
        }
        dialogboxBinding.btnDelete.setOnClickListener {
            if (dialogboxBinding.etName.text.toString().isEmpty()) {
                dialogboxBinding.etName.error = "enter name"
            } else if (dialogboxBinding.etRollNo.text.toString().isEmpty()) {
                dialogboxBinding.etRollNo.error = "enter rollno"
            } else {
                studentList.removeAt(position)
                studentAdapter.notifyDataSetChanged()
                dialogbox.dismiss()
            }
        }
            dialogbox.show()
    }
}