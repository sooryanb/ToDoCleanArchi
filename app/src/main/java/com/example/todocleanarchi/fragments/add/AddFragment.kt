package com.example.todocleanarchi.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todocleanarchi.R
import com.example.todocleanarchi.data.models.Priority
import com.example.todocleanarchi.data.models.ToDoData
import com.example.todocleanarchi.data.viewmodel.ToDoViewModel
import com.example.todocleanarchi.fragments.SharedViewModel
import org.w3c.dom.Text


class AddFragment : Fragment() {

    private  val mToDoViewModel: ToDoViewModel by viewModels()
    private  val mSharedViewModel : SharedViewModel by viewModels()


    lateinit var title: EditText
    lateinit var spinner: Spinner
    lateinit var desc: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add, container, false)

        title = view.findViewById(R.id.title_et)
        spinner = view.findViewById(R.id.priorities_spinner)
        desc = view.findViewById(R.id.description_et)

        spinner.onItemSelectedListener = mSharedViewModel.listener

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.menu_add){
            insertDataToDb()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTitle = title.text.toString()
        val mPriority = spinner.selectedItem.toString()
        val mDescription = desc.text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)
        if(validation){
            val newData = ToDoData(
                0,
                mTitle,
                mSharedViewModel.parsePriority(mPriority),
                mDescription
            )
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully Added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Fill out all fields", Toast.LENGTH_SHORT).show()
        }

    }


}