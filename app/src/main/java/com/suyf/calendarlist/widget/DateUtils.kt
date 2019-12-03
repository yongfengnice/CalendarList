package com.suyf.calendarlist.widget

import java.util.*

object DateUtils {
    private val calendar = Calendar.getInstance()
    val todayYear = calendar.get(Calendar.YEAR)
    private val todayMonth = calendar.get(Calendar.MONTH)
    private val todayDay = calendar.get(Calendar.DAY_OF_MONTH)

    fun getMonthStartDay(year: Int, month: Int): Int {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return calendar.get(Calendar.DAY_OF_WEEK) - 1
    }

    fun isToday(year: Int, month: Int, day: Int): Boolean {
        return year == todayYear && month == todayMonth && day == todayDay
    }

    fun getDaysInMonth(month: Int, year: Int): Int {
        return when (month) {
            Calendar.JANUARY, Calendar.MARCH, Calendar.MAY, Calendar.JULY, Calendar.AUGUST, Calendar.OCTOBER, Calendar.DECEMBER -> 31
            Calendar.APRIL, Calendar.JUNE, Calendar.SEPTEMBER, Calendar.NOVEMBER -> 30
            Calendar.FEBRUARY -> if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) 29 else 28
            else -> throw IllegalArgumentException("Invalid Month")
        }
    }
}