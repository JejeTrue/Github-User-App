package com.jejetrue.githubjj.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jejetrue.githubjj.data.response.ItemsItem
import com.jejetrue.githubjj.databinding.ItemsUsersBinding
import com.jejetrue.githubjj.ui.activity.DetailActivity

class UserAdapter : androidx.recyclerview.widget.ListAdapter<ItemsItem,UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemsUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem){
            val url = user.avatarUrl
            binding.tvUser.text = user.login
            Glide.with(itemView.context)
                .load(url)
                .into(binding.ciUsers)

            itemView.setOnClickListener {
                val  intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
                itemView.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
        val binding = ItemsUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object{
        val DIFF_CALLBACK= object: DiffUtil.ItemCallback<ItemsItem>(){
            override fun areItemsTheSame(oldItem: ItemsItem,
                                         newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }


}