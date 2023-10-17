package com.den.gorobets.giphyseacher.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.den.gorobets.giphyseacher.model.dto.Datum

class ListDiffUtil : DiffUtil.ItemCallback<Datum>() {

    override fun areItemsTheSame(oldItem: Datum, newItem: Datum): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Datum, newItem: Datum): Boolean = oldItem == newItem
}
