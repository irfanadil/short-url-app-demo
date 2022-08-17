package com.cute.connection.ui.main.screens.listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.cute.connection.R
import com.cute.connection.extensions.gone
import com.cute.connection.ui.main.model.UrlResultEntity
import com.orhanobut.logger.Logger

class CuteUrlAdapter
internal constructor(
    passedUrlList: ArrayList<UrlResultEntity>, onAdapterClickListener: AdapterClickListener
) : RecyclerView.Adapter<CuteUrlAdapter.HistoryItemViewHolder>() {


    private var urlList = ArrayList<UrlResultEntity>()
    private var classScopedItemClickListener: AdapterClickListener = onAdapterClickListener

    init {
        this.urlList = passedUrlList
        this.classScopedItemClickListener = onAdapterClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        val holder: HistoryItemViewHolder
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        holder = HistoryItemViewHolder(inflater.inflate(R.layout.inner_item, parent, false))
        return holder

    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {

        val urlItem = urlList[position]
        holder.longUrlTextView.text = urlItem.originalUrl
        holder.cuteUrlTextView.text = urlItem.shortUrl

        holder.copyBtn.setOnClickListener {
            holder.copyBtn.visibility = View.INVISIBLE
            holder.copiedBtn.visibility = View.VISIBLE
            urlItem.recycleItemState = true
            classScopedItemClickListener.triggerCopyUrlClickEvent(position)
        }

        holder.deleteBtn.setOnClickListener {
            classScopedItemClickListener.triggerDeleteUrlClickEvent(position)
        }

        if(urlItem.recycleItemState){
            holder.copyBtn.performClick()
        }


    }

    override fun getItemCount(): Int = urlList.size

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


}