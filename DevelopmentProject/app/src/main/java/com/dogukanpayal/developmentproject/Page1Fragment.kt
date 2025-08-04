package com.dogukanpayal.developmentproject

import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedStateListDrawable
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat

class Page1Fragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val cb = view.findViewById<AppCompatCheckBox>(R.id.checkBox2)
        cb.buttonDrawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.checkbox_unchecked)

        cb.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val ld = ContextCompat
                    .getDrawable(requireContext(), R.drawable.checkbox_checked_layer)!!
                        as LayerDrawable
                buttonView.buttonDrawable = ld

                val tick = ld.findDrawableByLayerId(R.id.layer_tick)
                if (tick is Animatable) tick.start()
            }
            else {
                buttonView.buttonDrawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.checkbox_unchecked)
            }
        }
    }


}