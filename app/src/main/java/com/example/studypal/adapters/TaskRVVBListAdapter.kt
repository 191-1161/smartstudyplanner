package com.example.studypal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studypal.databinding.ViewLayoutListTaskBinding
import com.example.studypal.databinding.ViewTaskGridLayoutBinding
import com.example.studypal.models.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TaskRVVBListAdapter (
    private val isList: MutableLiveData<Boolean>,
    private val deleteUpdateCallback: (type:String,position: Int, task: Task) -> Unit
):
ListAdapter<Task, RecyclerView.ViewHolder>(DiffCallback()){


    class  ListTaskViewHolder(private val viewLayoutListTaskBinding: ViewLayoutListTaskBinding) :
        RecyclerView.ViewHolder(viewLayoutListTaskBinding.root){
        fun bind(
            task: Task,
            deleteUpdateCallback: (type:String,position: Int, task: Task) -> Unit,
        ){
            viewLayoutListTaskBinding.titleTxt.text = task.title
            viewLayoutListTaskBinding.descTxt.text = task.description

            val dateFormat = SimpleDateFormat ("dd-MMM-yyyy HH:mm:ss a", Locale.getDefault())
            viewLayoutListTaskBinding.dateTxt.text = dateFormat.format(task.date)

            viewLayoutListTaskBinding.deleteImg.setOnClickListener{
                if (adapterPosition != -1) {
                    deleteUpdateCallback("delete",adapterPosition,task)
                }
            }
            viewLayoutListTaskBinding.editImg.setOnClickListener{
                if (adapterPosition != -1) {
                    deleteUpdateCallback("update",adapterPosition,task)
                }
            }
        }
    }

    class  GridTaskViewHolder(private val viewTaskGridLayoutBinding: ViewTaskGridLayoutBinding):
        RecyclerView.ViewHolder(viewTaskGridLayoutBinding.root){
        fun bind(
            task: Task,
            deleteUpdateCallback: (type:String,position: Int, task: Task) -> Unit,
        ){
            viewTaskGridLayoutBinding.titleTxt.text = task.title
            viewTaskGridLayoutBinding.descTxt.text = task.description

            val dateFormat = SimpleDateFormat ("dd-MMM-yyyy HH:mm:ss a", Locale.getDefault())
            viewTaskGridLayoutBinding.dateTxt.text = dateFormat.format(task.date)

            viewTaskGridLayoutBinding.deleteImg.setOnClickListener{
                if (adapterPosition != -1) {
                    deleteUpdateCallback("delete",adapterPosition,task)
                }
            }
            viewTaskGridLayoutBinding.editImg.setOnClickListener{
                if (adapterPosition != -1) {
                    deleteUpdateCallback("update",adapterPosition,task)
                }
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return if (viewType == 1) {// Grid_Item
            GridTaskViewHolder(
                ViewTaskGridLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }else{// List_Item
            ListTaskViewHolder(
                ViewLayoutListTaskBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task = getItem(position)
        if (isList.value!!){
            (holder as ListTaskViewHolder).bind(task,deleteUpdateCallback)
        }else{
            (holder as GridTaskViewHolder).bind(task,deleteUpdateCallback)

        }


    }

    override fun getItemViewType(position: Int): Int {
        return if (isList.value!!){
            0 // List_Item
        } else{
            1 // Grid_Item
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return   oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
          return oldItem == newItem
        }

    }
}