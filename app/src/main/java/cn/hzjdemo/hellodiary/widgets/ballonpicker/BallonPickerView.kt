package cn.hzjdemo.hellodiary.widgets.ballonpicker

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * @author:Hzj
 * @date  :2019/10/17/017
 * desc  ：自定义气球选择进度条，源码参考:https://github.com/fairytale110/BalloonPicker
 * record：1.分别绘制选中和未选中的基线，然后绘制触摸块外圆和内圆
 */
class BallonPickerView : ViewGroup {

    private val mPaintOfTempLine= Paint()

    constructor(context: Context?) : super(context) {
        configure()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        configure()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        configure()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        configure()
    }

    /**
     * 初始化
     */
    private fun configure() {
//        mPaintOfTempLine.color="#BDBDBD".toColorInt()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }

}