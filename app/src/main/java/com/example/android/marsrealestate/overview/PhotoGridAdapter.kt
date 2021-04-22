package com.example.android.marsrealestate.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsProperty

class PhotoGridAdapter(val clickListener: MarsPropertyListener) : ListAdapter<MarsProperty, PhotoGridAdapter.MarsPropertyViewHolder>(MarsPropertyDiffCallback) {

    /**
     * Create a new ViewHolder object.*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPropertyViewHolder {
        return MarsPropertyViewHolder.from(parent)
    }

    /**
     * Populate the list item with data.
     * */
    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.bind(marsProperty, clickListener)
    }

    /**
     * Define a ViewHolder.*/
    class MarsPropertyViewHolder(var binding: GridViewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Create a new ViewHolder object with the parent parameter.
         */
        companion object {
            fun from(parent: ViewGroup): MarsPropertyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GridViewItemBinding.inflate(layoutInflater, parent, false)
                return MarsPropertyViewHolder(binding)
            }
        }

        /**
         * Create a bind() method in the VH class, which populates the list item with data.
         * Call executePendingBindings() to execute the update immediately. */
        fun bind(marsProperty: MarsProperty, clickListener: MarsPropertyListener) {
            binding.marsProperty = marsProperty
            // Assign the click listener to the binding object
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    /**
     * Define a DiffCallback companion object.
     * */
    companion object MarsPropertyDiffCallback : DiffUtil.ItemCallback<MarsProperty>() {
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem == newItem
        }
    }
}

/**
 * Define a Click listener
 */
class MarsPropertyListener(val clickListener: (marsProperty: MarsProperty) -> Unit) {
    fun onClick(marsProperty: MarsProperty) {
        clickListener(marsProperty)
    }
}
