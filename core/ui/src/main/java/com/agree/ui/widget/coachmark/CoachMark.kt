package com.agree.ui.widget.coachmark

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.agree.ui.R
import com.agree.ui.UiKitAttrs
import com.telkom.legion.component.utility.enums.ButtonColors
import com.telkom.legion.component.utility.extension.requiredColor

class CoachMark constructor(
    var view: View? = null,
    var title: String? = null,
    var description: String? = null,
    var coachMarkPosition: CoachMarkPosition = CoachMarkPosition.CENTER,
    var isBackground: Boolean = false,
    val scrollView: ViewGroup? = null
) {
    /**
     * radius for circle shape
     */
    var radius = 0

    /**
     * positions for CoachMark
     */
    var positions: List<Int>? = null

    /**
     * Show CoachMark on RecyclerViews
     */
    fun recyclerItem(itemPosition: Int = 0): CoachMark {
        val isRecycler = view is RecyclerView
        if (isRecycler) {
            val recyclerView = view as RecyclerView
            val itemView = recyclerView.findViewHolderForLayoutPosition(itemPosition)?.itemView
            this.view = itemView
            this.isBackground = true // must
        }
        return this
    }

    /**
     * Show CoachMark on View with circle shape
     */
    fun circle(activity: Activity, margin: Int = 20): CoachMark {
        val location = IntArray(2)
        view?.getLocationInWindow(location)

        val width = view?.width ?: 0
        val height = view?.height ?: 0
        val xStart = location[0]
        val yStart = location[1] - CoachMarkDialog.getStatusBarHeight(activity)
        val xEnd: Int = location[0] + width
        val yEnd: Int =
            location[1] + height - CoachMarkDialog.getStatusBarHeight(activity)
        val xCenter = (xStart + xEnd) / 2
        val yCenter = (yStart + yEnd) / 2
        val max = if (width > height) width else height
        val radius: Int = max / 2 + margin
        this.view = activity.findViewById(android.R.id.content)
        this.positions = listOf(xCenter, yCenter)
        this.radius = radius
        this.isBackground = true // must
        return this
    }

    /**
     * Show CoachMark on View with square shape
     */
    fun square(activity: Activity, margin: Int = 20): CoachMark {
        val location = IntArray(2)
        view?.getLocationInWindow(location)

        val width = view?.width ?: 0
        val height = view?.height ?: 0
        val xStart = location[0]
        val yStart = location[1] - CoachMarkDialog.getStatusBarHeight(activity)
        val xEnd: Int = location[0] + width
        val yEnd: Int =
            location[1] + height - CoachMarkDialog.getStatusBarHeight(activity)
        this.view = activity.findViewById(android.R.id.content)
        this.positions = listOf(xStart - margin, yStart - margin, xEnd + margin, yEnd + margin)
        this.isBackground = true // must
        return this
    }

    companion object {
        /**
         * Create a [CoachMarkDialog] with Builder Pattern.
         */
        fun setup(
            activity: FragmentActivity,
            block: Builder.() -> Unit
        ): CoachMarkDialog {
            return Builder(activity).apply(block).build()
        }
    }

    /**
     * Builder for [CoachMarkDialog].
     */
    open class Builder(
        private val fragmentActivity: FragmentActivity
    ) {

        /**
         * Set the [CoachMark]s to be displayed.
         */
        private var coachMarks: MutableList<CoachMark> = mutableListOf()

        /**
         * Set the [CoachMark]s view's to be displayed.
         */
        @LayoutRes
        var layoutView = R.layout.layout_coachmark

        /**
         * Set the [CoachMark]s text title color to be displayed.
         */
        @ColorInt
        var textTitleColor: Int? = fragmentActivity.requiredColor(UiKitAttrs.colorPrimary)

        /**
         * Set the [CoachMark]s text description color to be displayed.
         */
        @ColorInt
        var textDescriptionColor: Int? = fragmentActivity.requiredColor(UiKitAttrs.black_font)

        /**
         * Set the [CoachMark]s shadow color to be displayed.
         */
        @ColorInt
        var shadowColor: Int = Color.parseColor("#B0000000")

        /**
         * Set the [CoachMark]s background color to be displayed.
         */
        @ColorInt
        var backgroundColor: Int? = fragmentActivity.requiredColor(UiKitAttrs.white)

        /**
         * Set the [CoachMark]s button next color to be displayed.
         */
        var buttonNextColor: ButtonColors = ButtonColors.DEFAULT

        /**
         * Set the [CoachMark]s button previous color to be displayed.
         */
        var buttonPreviousColor: ButtonColors = ButtonColors.DEFAULT

        /**
         * Set the [CoachMark]s button finish color to be displayed.
         */
        var buttonFinishColor: ButtonColors = ButtonColors.DEFAULT

        /**
         * Set the [CoachMark]s button skip color to be displayed.
         */
        var buttonSkipColor: ButtonColors = ButtonColors.DEFAULT

        /**
         * Set the [CoachMark]s arrow spacing to be displayed.
         */
        var spacing = 0

        /**
         * Set the [CoachMark]s button previous text to be displayed.
         */
        var buttonPreviousText: String? = "PREV"

        /**
         * Set the [CoachMark]s button next text to be displayed.
         */
        var buttonNextText: String? = "NEXT"

        /**
         * Set the [CoachMark]s button finish text to be displayed.
         */
        var buttonFinishText: String? = "FINISH"

        /**
         * Set the [CoachMark]s button skip text to be displayed.
         */
        var buttonSkipText: String? = "SKIP"

        /**
         * Set the [CoachMark]s cancelable to be displayed.
         */
        var isCancelable = false

        /**
         * Set the [CoachMark]s arrow to be displayed.
         */
        var isArrow = true

        /**
         * Set the [CoachMark]s skip to be displayed.
         */
        var isSkip = false

        /**
         * Set the [CoachMark]s previous to be displayed.
         */
        var isPrevious = false

        /**
         * Set the [CoachMark]s indicator to be displayed.
         */
        var isIndicator = false

        /**
         * Set the [CoachMark]s delimiter indicator to be displayed.
         */
        var delimiterText: String? = "/"

        /**
         * Add [CoachMark]'s to be displayed.
         */
        fun addCoachMark(block: CoachMark.() -> Unit) {
            coachMarks.add(CoachMark().apply(block))
        }

        /**
         * Create a CoachMarkDialog with the arguments supplied to this builder.
         * @return [CoachMarkDialog]
         */
        fun build(): CoachMarkDialog {
            return CoachMarkDialog.newInstance(fragmentActivity, coachMarks, this)
        }
    }

//    open class Builder : Parcelable {
//        private var fragmentActivity: FragmentActivity? = null
//        private var coachMarks: List<CoachMark>? = null
//        var layoutView = R.layout.layout_coachmark
//        var textTitleColor = 0
//        var textDescriptionColor = 0
//        var shadowColor = 0
//        var backgroundColor = 0
//        var spacing = 0
//        var buttonPreviousText: String? = "PREV"
//        var buttonNextText: String? = "NEXT"
//        var buttonFinishText: String? = "FINISH"
//        var buttonSkipText: String? = "SKIP"
//        var isCancelable = false
//        var isArrow = true
//        var isSkip = false
//        var isPrevious = false
//        var isIndicator = false
//        var delimiterText: String? = "/"
//
//        fun coachMark(coachMark: CoachMark): Builder {
//            this.coachMarks = listOf(coachMark)
//            return this
//        }
//
//        fun coachMark(coachMarks: List<CoachMark>): Builder {
//            this.coachMarks = coachMarks
//            return this
//        }
//
//        fun layoutView(@LayoutRes layoutView: Int): Builder {
//            this.layoutView = layoutView
//            return this
//        }
//
//        fun textTitleColor(@ColorRes id: Int): Builder {
//            this.textTitleColor = id
//            return this
//        }
//
//        fun textDescriptionColor(@ColorRes id: Int): Builder {
//            this.textDescriptionColor = id
//            return this
//        }
//
//        fun spacing(@DimenRes id: Int): Builder {
//            this.spacing = id
//            return this
//        }
//
//        fun shadowColor(@ColorRes id: Int): Builder {
//            this.shadowColor = id
//            return this
//        }
//
//        fun backgroundColor(@ColorRes id: Int): Builder {
//            this.backgroundColor = id
//            return this
//        }
//
//        fun arrow(isArrow: Boolean): Builder {
//            this.isArrow = isArrow
//            return this
//        }
//
//        fun skipButton(isSkip: Boolean): Builder {
//            this.isSkip = isSkip
//            return this
//        }
//
//        fun indicator(delimiterText: String? = "/"): Builder {
//            this.isIndicator = true
//            this.delimiterText = delimiterText
//            return this
//        }
//
//        fun previousButton(isButtonPrevious: Boolean): Builder {
//            this.isPrevious = isButtonPrevious
//            return this
//        }
//
//        fun buttonFinishText(buttonFinishText: String): Builder {
//            this.buttonFinishText = buttonFinishText
//            return this
//        }
//
//        fun buttonPreviousText(buttonPreviousText: String): Builder {
//            this.buttonPreviousText = buttonPreviousText
//            return this
//        }
//
//        fun buttonNextText(buttonNextText: String): Builder {
//            this.buttonNextText = buttonNextText
//            return this
//        }
//
//        fun buttonSkipText(buttonSkipText: String): Builder {
//            this.buttonSkipText = buttonSkipText
//            return this
//        }
//
//        fun cancelable(isCancelable: Boolean): Builder {
//            this.isCancelable = isCancelable
//            return this
//        }
//
//        fun build(): CoachMarkDialog {
//            return CoachMarkDialog.newInstance(fragmentActivity, coachMarks, this)
//        }
//
//        override fun describeContents(): Int {
//            return 0
//        }
//
//        override fun writeToParcel(dest: Parcel, flags: Int) {
//            dest.writeInt(layoutView)
//            dest.writeInt(textTitleColor)
//            dest.writeInt(textDescriptionColor)
//            dest.writeInt(shadowColor)
//            dest.writeInt(spacing)
//            dest.writeInt(backgroundColor)
//            dest.writeString(buttonPreviousText)
//            dest.writeString(buttonNextText)
//            dest.writeString(buttonFinishText)
//            dest.writeString(buttonSkipText)
//            dest.writeString(delimiterText)
//            dest.writeByte(if (isCancelable) 1.toByte() else 0.toByte())
//            dest.writeByte(if (isArrow) 1.toByte() else 0.toByte())
//            dest.writeByte(if (isSkip) 1.toByte() else 0.toByte())
//            dest.writeByte(if (isPrevious) 1.toByte() else 0.toByte())
//            dest.writeByte(if (isIndicator) 1.toByte() else 0.toByte())
//        }
//
//        constructor(fragmentActivity: FragmentActivity?) {
//            this.fragmentActivity = fragmentActivity
//        }
//
//        protected constructor(parcel: Parcel) {
//            layoutView = parcel.readInt()
//            textTitleColor = parcel.readInt()
//            textDescriptionColor = parcel.readInt()
//            shadowColor = parcel.readInt()
//            spacing = parcel.readInt()
//            backgroundColor = parcel.readInt()
//            buttonPreviousText = parcel.readString()
//            buttonNextText = parcel.readString()
//            buttonFinishText = parcel.readString()
//            isCancelable = parcel.readByte().toInt() != 0
//            isArrow = parcel.readByte().toInt() != 0
//            buttonSkipText = parcel.readString()
//            delimiterText = parcel.readString()
//            isSkip = parcel.readByte().toInt() != 0
//            isPrevious = parcel.readByte().toInt() != 0
//            isIndicator = parcel.readByte().toInt() != 0
//        }
//
//        companion object CREATOR : Parcelable.Creator<Builder> {
//            override fun createFromParcel(parcel: Parcel): Builder {
//                return Builder(parcel)
//            }
//
//            override fun newArray(size: Int): Array<Builder?> {
//                return arrayOfNulls(size)
//            }
//        }
//    }
}
