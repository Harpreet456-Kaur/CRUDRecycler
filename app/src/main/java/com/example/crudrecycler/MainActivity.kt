package com.example.crudrecycler

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudrecycler.databinding.ActivityMainBinding
import com.example.crudrecycler.databinding.AddLayoutBinding
import com.example.crudrecycler.databinding.EditLayoutBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), newInterface {
    lateinit var binding: ActivityMainBinding
    val db = Firebase.firestore
    var studentList=ArrayList<studentModel>()
    var showUserList=ArrayList<studentModel>()
    lateinit var studentAdapter: StudentAdapter
    lateinit var newInterface:newInterface
    var StudentModel = studentModel()
    var isUpdated=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentAdapter = StudentAdapter(showUserList, this)
        binding.lv.adapter = studentAdapter
        binding.lv.layoutManager = LinearLayoutManager(this)

        binding.et.doOnTextChanged { text, start, before, count ->
            if ((text ?:"").isEmpty()){
                showUserList.clear()
                showUserList.addAll(studentList)
            }
            else if ((text?:"").length > 2){
                showUserList.clear()
                var list = ArrayList<studentModel>()
                list.addAll(studentList.filter { element-> element.name?.contains((text?:""),true)==true })
                showUserList.addAll(list)
            }
        }

        db.collection("Users").get().addOnSuccessListener {
            for (snapshot in it){
                val userModel = snapshot.toObject(studentModel::class.java)
                userModel.key = snapshot.id
                studentList.add(userModel)
            }
        }

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
                    db.collection("Users")
                        .add(studentModel(dialogBinding.etName.text.toString(),dialogBinding.etRollNo.text.toString()))
                        .addOnSuccessListener {
                            Toast.makeText(this,"Add",Toast.LENGTH_SHORT).show()
                        }
                }
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    override fun Edit(position: Int) {
        val dialogboxBinding=EditLayoutBinding.inflate(layoutInflater)
        val dialogbox=Dialog(this)
        dialogbox.setContentView(dialogboxBinding.root)
        dialogboxBinding.etName.setText(studentList[position].name)
        dialogboxBinding.etRollNo.setText(studentList[position].rollno)

        dialogboxBinding.btnEdit.setOnClickListener {
            if (dialogboxBinding.etName.text.toString().isEmpty()){
                dialogboxBinding.etName.error="enter name"
            }
            else if (dialogboxBinding.etRollNo.text.toString().isEmpty()){
                dialogboxBinding.etRollNo.error="enter rollno"
            }
            else {
                //studentList.set(position,studentModel(dialogboxBinding.etName.text.toString(),
                //   dialogboxBinding.etRollNo.text.toString()))
                //studentAdapter.notifyDataSetChanged()
                var updatestudentModel=StudentModel
                updatestudentModel.name=dialogboxBinding.etName.text.toString()
                updatestudentModel.rollno=dialogboxBinding.etRollNo.text.toString()
                updatestudentModel.key=studentList[position].key
                db.collection("Users")
                    .document(studentList[position].key ?: "")
                    .set(updatestudentModel).addOnSuccessListener {
                        binding.lv.clearFocus()
                        isUpdated = false
                    }
                studentAdapter.notifyDataSetChanged()
                dialogbox.dismiss()
            }
        }
        dialogboxBinding.btnDelete.setOnClickListener {
            if (dialogboxBinding.etName.text.toString().isEmpty()) {
                dialogboxBinding.etName.error = "Enter name"
            } else if (dialogboxBinding.etRollNo.text.toString().isEmpty()) {
                dialogboxBinding.etRollNo.error = "Enter rollno"
            } else {
                StudentModel.key=studentList[position].key
                db.collection("Users")
                    .document(studentList[position].key ?: "")
                    .delete().addOnSuccessListener {
                        binding.lv.clearFocus()
                        isUpdated = false
                    }
                studentList.removeAt(position)
                studentAdapter.notifyDataSetChanged()
                dialogbox.dismiss()
            }
        }
            dialogbox.show()
    }
}