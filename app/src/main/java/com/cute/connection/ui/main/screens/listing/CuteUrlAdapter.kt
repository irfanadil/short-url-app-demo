package com.cute.connection.ui.main.screens.listing

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cute.connection.R
import com.cute.connection.extensions.hide
import com.cute.connection.extensions.show
import com.cute.connection.ui.main.model.UrlResultEntity

class CuteUrlAdapter
internal constructor(
    onAdapterClickListener: AdapterClickListener
) : RecyclerView.Adapter<CuteUrlAdapter.HistoryItemViewHolder>() {

    private var classScopedItemClickListener: AdapterClickListener = onAdapterClickListener

    init {
        this.classScopedItemClickListener = onAdapterClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        val holder: HistoryItemViewHolder
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        holder = HistoryItemViewHolder(inflater.inflate(R.layout.inner_item, parent, false))
        return holder

    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {

        val urlItem = differ.currentList[position]
        holder.longUrlTextView.text = urlItem.originalUrl
        holder.cuteUrlTextView.text = urlItem.shortUrl

        holder.copyBtn.setOnClickListener {
            classScopedItemClickListener.copyUrlEvent(position)
        }

        holder.deleteBtn.setOnClickListener {
            classScopedItemClickListener.deleteUrlEvent(position)
        }

        if (urlItem.recycleItemState) {
            holder.copyBtn.hide()
            holder.copiedBtn.show()
        } else {
            holder.copyBtn.show()
            holder.copiedBtn.hide()
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class HistoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var longUrlTextView: TextView = itemView.findViewById(R.id.long_url_textview)
        var deleteBtn: ImageView = itemView.findViewById(R.id.delete_btn)
        var cuteUrlTextView: TextView = itemView.findViewById(R.id.cute_url_textview)
        var copyBtn: AppCompatButton = itemView.findViewById(R.id.copy_btn)
        var copiedBtn: AppCompatButton = itemView.findViewById(R.id.copied_btn)

        override fun onClick(v: View?) {}
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private val differCallback = object : DiffUtil.ItemCallback<UrlResultEntity>() {
        override fun areItemsTheSame(oldItem: UrlResultEntity, newItem: UrlResultEntity): Boolean {
            return oldItem.autoId == newItem.autoId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: UrlResultEntity,
            newItem: UrlResultEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


}