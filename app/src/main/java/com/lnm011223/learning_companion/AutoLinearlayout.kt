import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.lnm011223.learning_companion.R

class AutoLineLayout(context: Context, attrs: AttributeSet?) :
    ViewGroup(context, attrs) {
    private val horizontalSpace: Int
    private val verticalSpace: Int
    private var theRealWidth = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //不测算宽度模式，因为不管是wrap_content还是match_parent都按match_parent来算
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var lineWidth = 0
        var lineHeight = 0
        var height = 0
        val paddingStart = paddingStart
        val paddingEnd = paddingEnd
        val size = widthSize - paddingStart - paddingEnd
        theRealWidth = size
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        for (i in 0 until childCount) {
            val view: View = getChildAt(i)
            val childHeight: Int = view.getMeasuredHeight()
            val childWidth: Int = view.getMeasuredWidth()
            val nowWidth = childWidth + lineWidth
            var changeLine = nowWidth > size
            changeLine = changeLine || nowWidth - horizontalSpace > size
            if (changeLine) {
                lineWidth = childWidth + horizontalSpace
                height += lineHeight + verticalSpace //这里的lineHeight其实是上一行的所有view的高度的最大值
                lineHeight = childHeight
            } else {
                lineHeight = Math.max(lineHeight, childHeight)
                lineWidth += childWidth + horizontalSpace
                if (i == 0) height += lineHeight
            }
        }
        height += paddingTop + paddingBottom
        setMeasuredDimension(
            widthSize,
            if (heightMode == MeasureSpec.EXACTLY) heightSize else height
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val paddingStart = paddingStart
        var leftOffset = paddingStart
        var topOffset = paddingTop
        var lineHeight = 0
        for (i in 0 until childCount) {
            val childView: View = getChildAt(i)
            val childWidth: Int = childView.getMeasuredWidth()
            val childHeight: Int = childView.getMeasuredHeight()
            val nowWidth = childWidth + leftOffset
            var changeLine = nowWidth > theRealWidth
            changeLine = changeLine || nowWidth - horizontalSpace > theRealWidth
            if (changeLine) {
                leftOffset = paddingStart
                topOffset += lineHeight + verticalSpace
                childView.layout(
                    leftOffset,
                    topOffset,
                    childWidth + leftOffset,
                    topOffset + childHeight
                )
                leftOffset += childWidth + horizontalSpace
                lineHeight = childHeight
            } else {
                childView.layout(
                    leftOffset,
                    topOffset,
                    childWidth + leftOffset,
                    topOffset + childHeight
                )
                leftOffset += childWidth + horizontalSpace
                lineHeight = Math.max(lineHeight, childHeight)
            }
        }
    }

    init {
        val array: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoLineLayout)
        horizontalSpace =
            Math.ceil(array.getDimension(R.styleable.AutoLineLayout_horizontalSpace, 0f).toDouble())
                .toInt()
        verticalSpace =
            Math.ceil(array.getDimension(R.styleable.AutoLineLayout_verticalSpace, 0f).toDouble())
                .toInt()
        array.recycle()
    }
}