package com.lnm011223.learning_companion



import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.marginTop


class AntoLineUtil @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) :
    ViewGroup(context, attrs, defStyleAttr) {
    /**
     * 子view左右间距
     */
    var horizontalSpacing = 0

    /**
     * 子view上下行距离
     */
    var verticalSpacing = 0
    private var context: Context? = null

    constructor(context: Context) : this(context, null) {
        this.context = context
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val count = childCount
        for (i in 0 until count) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec)
        }
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        if (widthMode != MeasureSpec.EXACTLY) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                getAutoLinefeedWidth(width), widthMode
            )
        }
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        if (heightMode != MeasureSpec.EXACTLY) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                getAutoLinefeedHeight(width), heightMode
            )
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * 自动换行 计算需要的宽度
     *
     * @param width 可用宽度
     * @return 需要的宽度
     */
    private fun getAutoLinefeedWidth(width: Int): Int {
        var totalWidth = paddingLeft + paddingRight
        for (i in 0 until childCount) {
            if (i > 0) totalWidth += horizontalSpacing
            val child = getChildAt(i)
            val childWidth = child.measuredWidth
            totalWidth += childWidth
            if (totalWidth >= width) {
                totalWidth = width
                break
            }
        }
        return totalWidth
    }

    /**
     * 自动换行 计算需要的高度
     *
     * @param width 可用宽度
     * @return 需要的高度
     */
    private fun getAutoLinefeedHeight(width: Int): Int {

        //一行最大可用宽度
        val lineWidth = width - paddingLeft - paddingRight
        //剩余可用宽度
        var availableLineWidth = lineWidth
        //需要的高度
        var totalHeight = paddingTop + paddingBottom
        var lineChildIndex = 0
        //本行最大高度
        var lineMaxHeight = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            //这个child需要的宽度  如果不是第一位的 那么需要加上间距
            //这里是用来判断需不需要换行
            val needWidth = if (i == 0) childWidth else childWidth + horizontalSpacing
            //如果剩余可用宽度小于需要的长度 那么换行
            if (availableLineWidth < needWidth) {
                totalHeight = totalHeight + lineMaxHeight
                if (i > 0) totalHeight += verticalSpacing
                availableLineWidth = lineWidth
                lineMaxHeight = 0
                lineChildIndex = 0
            }
            //这个child需要的宽度  如果不是第一位的 那么需要加上间距
            val realNeedWidth =
                if (lineChildIndex == 0) childWidth else childWidth + horizontalSpacing
            lineMaxHeight = Math.max(childHeight, lineMaxHeight)
            availableLineWidth = availableLineWidth - realNeedWidth
            lineChildIndex++
        }
        totalHeight = totalHeight + lineMaxHeight
        return totalHeight
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        layout()
    }

    private fun layout() {
        val count = childCount
        var childLeft = paddingLeft
        var childTop = paddingTop
        val lineWidth = measuredWidth - paddingRight - paddingLeft
        var availableLineWidth = lineWidth
        var lineChildIndex = 0
        //一行的最大高度
        var lineMaxHeight = 0
        for (i in 0 until count) {
            val child = getChildAt(i)
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            val needWidth = if (i == 0) childWidth else childWidth + horizontalSpacing
            if (availableLineWidth < needWidth) {
                availableLineWidth = lineWidth
                childTop += lineMaxHeight
                if (i > 0) childTop += verticalSpacing
                lineMaxHeight = 0
                childLeft = paddingLeft
                lineChildIndex = 0
            }
            val realNeedWidth =
                if (lineChildIndex == 0) childWidth else childWidth + horizontalSpacing
            lineMaxHeight = Math.max(lineMaxHeight, childHeight)
            child.layout(
                childLeft + realNeedWidth - childWidth,
                childTop,
                childLeft + realNeedWidth,
                childTop + childHeight
            )
            availableLineWidth -= realNeedWidth
            childLeft += realNeedWidth
            lineChildIndex++
        }
    }

    init {
        if (attrs != null) {
            val array = context.obtainStyledAttributes(
                attrs,
                R.styleable.AntoLineUtil
            )
            horizontalSpacing = array.getDimensionPixelOffset(
                R.styleable.AntoLineUtil_horizontalSpacing, 0
            )
            verticalSpacing = array.getDimensionPixelOffset(
                R.styleable.AntoLineUtil_verticalSpacing, 0
            )
            array.recycle()
            if (horizontalSpacing < 0) horizontalSpacing = 0
            if (verticalSpacing < 0) verticalSpacing = 0
        }
    }


}
