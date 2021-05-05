package com.example.todocleanarchi.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.renderscript.RenderScript
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todocleanarchi.R
import com.example.todocleanarchi.data.models.Priority
import com.example.todocleanarchi.data.models.ToDoData
import com.example.todocleanarchi.data.viewmodel.ToDoViewModel
import com.example.todocleanarchi.fragments.SharedViewModel


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    lateinit var currentTitle: EditText
    lateinit var currentPrioritySpinner: Spinner
    lateinit var currentDescription: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_update, container, false)
        setHasOptionsMenu(true)

        currentTitle = view.findViewById<EditText>(R.id.current_title_et)
        currentTitle.setText(args.currentItem.title)

        currentPrioritySpinner = view.findViewById<Spinner>(R.id.current_priorities_spinner)
        currentPrioritySpinner.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))
        currentPrioritySpinner.onItemSelectedListener = mSharedViewModel.listener

        currentDescription = view.findViewById<EditText>(R.id.current_description_et)
        currentDescription.setText(args.currentItem.description)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _ ->
            mToDoViewModel.deleteItem(args.currentItem)
            Toast.makeText(
                requireContext(),
                "Successfully Removed: ${args.currentItem.title}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){ _, _ ->}
        builder.setTitle("Delete card")
        builder.setMessage("Are you sure you want to delete this?")
        builder.create().show()
    }

    private fun updateItem() {
        val title = currentTitle.text.toString()
        var description = currentDescription.text.toString()
        val getPriority = currentPrioritySpinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if(validation){
            val updateditem = ToDoData(
                args.currentItem.id,
                title,
                mSharedViewModel.parsePriority(getPriority),
                description
            )
            mToDoViewModel.updateData(updateditem)
            Toast.makeText(requireContext(), "Data updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else{
            Toast.makeText(requireContext(), "Error in data", Toast.LENGTH_SHORT).show()
        }

    }


}