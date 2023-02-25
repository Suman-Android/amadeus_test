package com.coforge.amadeus.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.coforge.amadeus.R
import com.coforge.amadeus.models.WeatherItemUiState
import java.util.*


class MyCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        attrs?.let {
            val typedArray =
                context.obtainStyledAttributes(it, R.styleable.MyCustomView)
            // some attr handling stuffs...
            typedArray.recycle()
        }
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        mBinding = MyCustomViewBinding.inflate(inflater)
    }

    fun weatherItemUiState(obj: WeatherItemUiState?) {

    }


}