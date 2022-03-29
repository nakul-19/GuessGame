package com.nakul.guess_game.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.nakul.guess_game.R
import com.nakul.guess_game.models.State
import com.nakul.guess_game.models.TileState


/**
 * Created by Nakul
 * on 29,March,2022
 */
class Tile : AppCompatTextView {

    constructor(context: Context, attrs: AttributeSet? = null) : super(
        context, attrs, R.style.TileStyle
    ) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    constructor(context: Context) : super(context, null, R.style.TileStyle) {
        initView(context, null)
    }

    var num: Int? = null
    var state: TileState? = null

    private fun initView(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.Tile, 0, 0)
        try {
            num = ta.getInt(R.styleable.Tile_num, 0)
        } catch (e: Exception) {
        } finally {
            ta.recycle()
        }
    }

    fun animateShowing() {
        val oa1 = ObjectAnimator.ofFloat(this, "scaleX", 1f, 0f)
        val oa2 = ObjectAnimator.ofFloat(this, "scaleX", 0f, 1f)
        oa1.interpolator = DecelerateInterpolator()
        oa2.interpolator = AccelerateDecelerateInterpolator()
        oa1.duration = 100
        oa2.duration = 100
        oa1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                setHidden(false)
                oa2.start()
            }
        })
        oa1.start()
    }

    fun setHidden(value: Boolean) {
        setBackgroundResource(if (value) R.drawable.hidden_tile else R.drawable.shown_tile)
        text = if (value) "" else num.toString()
    }

}

@BindingAdapter("app:bindTo")
fun bind(tile: Tile, value: TileState) {
    tile.num = value.num
    if (tile.state?.state == State.HIDDEN && value.state == State.SHOWN) {
        tile.animateShowing()
        tile.state = value
    } else {
        tile.state = value
        tile.setHidden(value.state == State.HIDDEN)
    }
}