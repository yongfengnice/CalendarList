package com.suyf.calendarlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.suyf.calendarlist.widget.MonthBean
import com.suyf.calendarlist.widget.MonthView

class DateAdapter(private val monthList: MutableList<MonthBean>) :
    RecyclerView.Adapter<DateAdapter.DateAdapterVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateAdapterVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
        return DateAdapterVH(view)
    }

    override fun getItemCount(): Int {
        return monthList.size
    }

    override fun onBindViewHolder(holder: DateAdapterVH, position: Int) {
        val bean = monthList[position]
        holder.tvTitle.text = bean.title
        holder.mdvMonthView.setDayList(bean.dayList)

        if (holder.mdvMonthView.dayClickListener == null) {
            holder.mdvMonthView.dayClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    dayItemClickListener?.invoke(holder.adapterPosition, position)
                }
        }
    }

    class DateAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        var mdvMonthView: MonthView = itemView.findViewById(R.id.dmv_month)
    }

    var dayItemClickListener: ((Int, Int) -> Unit)? = null
}