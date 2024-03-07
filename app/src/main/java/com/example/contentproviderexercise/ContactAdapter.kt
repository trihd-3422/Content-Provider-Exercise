package com.example.contentproviderexercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contentproviderexercise.databinding.ItemListBinding

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private val listData = mutableListOf<Contact>()

    fun setListData(listData: List<Contact>){
        this.listData.clear()
        this.listData.addAll(listData)
    }

    class ContactViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindData(contact: Contact){
            binding.name.text = contact.displayName
            binding.phoneNum.text = contact.phoneNumber
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ContactViewHolder(ItemListBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bindData(listData[position])
    }

}