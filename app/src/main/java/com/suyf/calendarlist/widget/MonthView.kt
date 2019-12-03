package com.suyf.calendarlist.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import com.suyf.calendarlist.R

class MonthView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var mDayList: MutableList<DayBean>? = null
    private var mNumPaint = Paint()
    private var mCellHeight = dip2px(52f)
    private var mCellWidth = (screenWidth() / COLUMN_NUM).toFloat()
    private var todayRadius = dip2px(17f)
    private val textRect = Rect()
    private var textWhiteColor = ContextCompat.getColor(context, R.color.text_white)
    private var textBlackColor = ContextCompat.getColor(context, R.color.text_black)
    private var textGrayColor = ContextCompat.getColor(context, R.color.text_bf)
    private var textBlueColor = ContextCompat.getColor(context, R.color.text_primary)
    private var todayTextSize = sp2px(10f).toFloat()
    private var normalTextSize = sp2px(14f).toFloat()
    var dayClickListener: AdapterView.OnItemClickListener? = null

    companion object {
        private const val COLUMN_NUM = 7
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                onViewClick(event)
            }
        }
        return true
    }

    private fun onViewClick(event: MotionEvent) {
        val dayList = mDayList ?: return
        val col = (event.x / mCellWidth).toInt()
        val row = (event.y / mCellHeight).toInt()
        val position = row * COLUMN_NUM + col
        if (position > -1 && position < dayList.size) {
            dayClickListener?.onItemClick(null, this, position, 0)
        }
    }

    init {
        mNumPaint.isAntiAlias = true
        mNumPaint.textSize = normalTextSize
        mNumPaint.style = Paint.Style.FILL
        mNumPaint.textAlign = Paint.Align.CENTER
        mNumPaint.isFakeBoldText = false
        mNumPaint.getTextBounds("8", 0, 1, textRect)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val dayList = mDayList ?: return super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var rowNum = dayList.size / COLUMN_NUM
        if (dayList.size % COLUMN_NUM != 0) {
            rowNum++
        }
        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            (mCellHeight * rowNum + dip2px(5f)).toInt()
        )
    }

    override fun onDraw(canvas: Canvas) {
        val dayList = mDayList ?: return super.onDraw(canvas)
        for (index in 0 until dayList.size) {
            val x = (index % COLUMN_NUM) * mCellWidth + mCellWidth / 2
            val y = (index / COLUMN_NUM) * mCellHeight + mCellHeight / 2
            val dayBean = dayList[index]
            if (dayBean.isToday) {
                mNumPaint.color = textBlueColor
                mNumPaint.textSize = todayTextSize
                canvas.drawCircle(x, y, todayRadius, mNumPaint)
                canvas.drawText("今天", x, y + textRect.height() + todayRadius, mNumPaint)
                mNumPaint.color = textWhiteColor
                mNumPaint.textSize = normalTextSize
                canvas.drawText("${dayBean.day}", x, y + textRect.height() / 2, mNumPaint)
            } else {
                mNumPaint.color = if (dayBean.isAvailable) textBlackColor else textGrayColor
                mNumPaint.textSize = normalTextSize
                canvas.drawText(
                    if (dayBean.day > 0) "${dayBean.day}" else " ",
                    x,
                    y + textRect.height() / 2,
                    mNumPaint
                )
            }
        }
        super.onDraw(canvas)
    }

    fun setDayList(pDayList: MutableList<DayBean>) {
        mDayList = pDayList
        requestLayout()
    }

    private fun dip2px(dipValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dipValue * scale
    }

    private fun screenWidth(): Int {
        val outMetrics = DisplayMetrics()
        val wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    private fun sp2px(spVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, spVal,
            context.resources.displayMetrics
        ).toInt()
    }

}