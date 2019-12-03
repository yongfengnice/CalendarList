package com.suyf.calendarlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.suyf.calendarlist.widget.MonthBean
import com.suyf.calendarlist.widget.DateUtils
import com.suyf.calendarlist.widget.DayBean
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val monthList = mutableListOf<MonthBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTestData()
        showMonthList()
    }

    private fun showMonthList() {
        val adapter = DateAdapter(monthList)
        adapter.dayItemClickListener = { month, day ->
            val dayBean = monthList[month].dayList[day]
            Toast.makeText(
                this,
                "${dayBean.year}年${dayBean.month + 1}月${dayBean.day}日",
                Toast.LENGTH_SHORT
            ).show()
        }
        rv_date.adapter = adapter
    }

    private fun initTestData() {
        for (year in DateUtils.todayYear downTo 2018) {
            for (month in 11 downTo 0) {
                initMonthList(year, month)
            }
        }
    }

    private fun initMonthList(year: Int, month: Int) {
        val monthBean = findOrCreateMonthBean(year, month)

        val startDay = DateUtils.getMonthStartDay(year, month)
        for (i in 0 until startDay) {
            monthBean.dayList.add(DayBean(year, month, 0))
        }

        val daysInMonth = DateUtils.getDaysInMonth(month, year)
        for (day in 1..daysInMonth) {
            val dayBean = DayBean(year, month, day, DateUtils.isToday(year, month, day))
            dayBean.isAvailable = day % 3 == 0
            monthBean.dayList.add(dayBean)
        }
    }

    private fun findOrCreateMonthBean(year: Int, month: Int): MonthBean {
        val title = "${year}年${month + 1}月"
        var dateBean = monthList.find { it.title == title }
        if (dateBean == null) {
            dateBean = MonthBean(title)
            monthList.add(0, dateBean)
        }
        return dateBean
    }
}
