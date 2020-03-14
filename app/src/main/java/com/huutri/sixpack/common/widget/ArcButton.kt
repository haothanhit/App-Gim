package com.huutri.sixpack.common.widget

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.huutri.sixpack.R

class ArcButton : LinearLayout {
    private var mContext: Context? = null
    /**
     * lấy text sử dụng
     *
     * @return text root View
     */
    var textInRootView: TextView? = null
        private set
    private var mImvIcon: ImageView? = null
    private var mRootView: LinearLayout? = null

    private var llDistance: LinearLayout? = null

    constructor(context: Context) : super(context) {
        mContext = context
        initializeView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context
        initializeView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mContext = context
        initializeView()
    }

    /**
     * init View
     */
    private fun initializeView() {
        val inflater = mContext!!
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.custom_arc_button, this)
        mRootView = view.findViewById(R.id.rootView)
        textInRootView = view.findViewById(R.id.arc_button_text)
        mImvIcon = view.findViewById(R.id.arc_button_icon)
        llDistance = view.findViewById(R.id.llDistance)
    }

    /**
     * Thay đổi text cho button
     *
     * @param stringResourceId string resource ID
     */
    fun setLabel(@StringRes stringResourceId: Int) {
        textInRootView!!.setText(stringResourceId)
    }

    /**
     * Thay đổi text cho button
     *
     * @param stringLabel string
     */
    fun setLabel(stringLabel: String) {
        textInRootView!!.text = stringLabel
    }

    /**
     * Thay đổi icon của button
     *
     * @param icon drawable resource ID
     */
    fun setIcon(@DrawableRes icon: Int) {
        mImvIcon!!.visibility = View.VISIBLE
        Glide.with(context).load(icon).into(mImvIcon!!)
        // mImvIcon!!.setImageResource(icon)
    }

    /**
     * Thay đổi background cho button
     *
     * @param backgroundButton drawable resource ID
     */
    fun setBackgroundButton(@DrawableRes backgroundButton: Int) {
        mRootView!!.setBackgroundResource(backgroundButton)
    }


    /**
     * Thay đổi background cho button green and text color white
     */
    fun setStyleButtonBGWhite() {
     //   mRootView!!.setBackgroundResource(R.drawable.bg_arc_button_white_selector)
        textInRootView!!.setTextColor(resources.getColor(R.color.green))

    }

    /**
     * Thay đổi text color cho button
     *
     * @param textButton drawable resource ID
     */
    fun setTextButton(@ColorRes textButton: Int) {
        textInRootView!!.setTextColor(resources.getColor(textButton))
    }

    /**
     * hide Icon cho button
     *
     */
    fun hideIcon() {
        mImvIcon!!.visibility = View.GONE
    }

    /**
     * show Icon cho button
     *
     */
    fun showIcon() {
        mImvIcon!!.visibility = View.VISIBLE
    }

    /**
     * Thay đổi kích thước của Text trong button
     *
     * @param size text size
     */
    fun setTextSize(size: Float) {
        textInRootView!!.textSize = size
    }

    /**
     * lấy đối tượng sử dụng
     *
     * @return root View
     */
    override fun getRootView(): LinearLayout? {
        return mRootView
    }

    /**
     * Gán giá trị enable
     *
     * @param enable giá trị
     */
    override fun setEnabled(enable: Boolean) {
        mRootView!!.isEnabled = enable
    }


    /**
     * Thay đổi color state list của icon và text.
     *
     * @param colorStateList color của cả icon và text.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setAllColorStateList(@ColorRes colorStateList: Int) {
        mImvIcon!!.imageTintList = ContextCompat.getColorStateList(mContext!!, colorStateList)
        textInRootView!!.setTextColor(ContextCompat.getColorStateList(mContext!!, colorStateList))
    }

    /**
     * Thay đổi size của icon
     *
     * @param width  chiều rộng icon
     * @param height chiều cao icon
     */
    fun setIconSize(width: Int, height: Int) {
        mImvIcon!!.layoutParams.width = width
        mImvIcon!!.layoutParams.height = height
    }

    /**
     * style for btn transparent screen Edit
     */
    fun setStyleScreenEdit(@StringRes stringResourceId: Int, @DrawableRes icon: Int) {
        setLabel(stringResourceId)
        setIcon(icon)
       // setBackgroundButton(R.color.transparent0)
        setIconSize(60, 60)
        setTextSize(14f)
        setDistanceMargins(15, 5, 15, 5)
        setTextButton(R.color.white)
        setPadding(5, 5, 5, 5)
    }

    /**
     * style for btn transparent screen Edit
     */
    fun setStyleScreenColor(@StringRes stringResourceId: Int) {
        setLabel(stringResourceId)
       // setBackgroundButton(R.color.transparent0)
        setIconSize(60, 60)
        setTextSize(14f)
        setDistanceMargins(15, 5, 15, 5)
        setTextButton(R.color.white)
        setPadding(5, 5, 5, 5)
        hideIcon()
    }

    /**
     * style for btn white screen Home
     */
    fun setStyleScreenHome(@StringRes stringResourceId: Int, @DrawableRes icon: Int) {
        setLabel(stringResourceId)
        setIcon(icon)
      //  setBackgroundButton(R.drawable.bg_arc_button_home_selector)
        // setIconSize(100,100)
        //setTextSize(18f)
        //setDistanceMargins(15, 15, 15, 15)
        setTextButton(R.color.white)
        //  setPadding(5,5,5,5)
    }

    /**
     * Thay đổi margin của icon
     *
     * @param left   margin left
     * @param right  margin right
     * @param top    margin top
     * @param bottom margin bottom
     */
    fun setIconMargins(left: Int, top: Int, right: Int, bottom: Int) {
        (mImvIcon!!.layoutParams as LinearLayout.LayoutParams).setMargins(left, top, right, bottom)
    }

    /**
     * Thay đổi distance margin
     *
     * @param left   margin left
     * @param right  margin right
     * @param top    margin top
     * @param bottom margin bottom
     */
    fun setDistanceMargins(left: Int, top: Int, right: Int, bottom: Int) {
        (llDistance!!.layoutParams as LinearLayout.LayoutParams).setMargins(
            left,
            top,
            right,
            bottom
        )
    }

    /**
     * style for btn only icon
     */
    fun setStyleOnlyImage(@DrawableRes icon: Int) {
        //   setLabel(stringResourceId)
        setIcon(icon)
        // setBackgroundButton(R.drawable.bg_arc_button_home_selector)
      //  setBackgroundButton(R.drawable.bg_item_rcv_selector)
        setIconSize(35, 35)
        //setTextSize(18f)
        //setDistanceMargins(15, 15, 15, 15)
        //  setTextButton(R.color.white)
        //  setPadding(5,5,5,5)
    }

    /**
     * style for btn only text
     */
    fun setStyleOnlyText(@StringRes stringResourceId: Int) {
        hideIcon()
        setLabel(stringResourceId)
      //  setBackgroundButton(R.drawable.bg_item_rcv_selector)
        // setIconSize(100,100)
        //setTextSize(18f)
        //setDistanceMargins(15, 15, 15, 15)
     //   setTextButton(R.color.white)
      //  textInRootView!!.setTextColor(resources.getColor(R.color.blue_dark))

        //  setPadding(5,5,5,5)
    }
}