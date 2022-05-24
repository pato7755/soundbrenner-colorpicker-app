package com.soundbrenner.colorpicker.ui.main

import android.R.attr.*
import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.soundbrenner.colorpicker.R
import com.soundbrenner.colorpicker.databinding.MainFragmentBinding


class MainFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private var selectedColor: Int = 0
    private val cardOneSelected = true
    private val cardTwoSelected = true
    private val cardThreeSelected = true

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        var bitmap: Bitmap

        binding.imageView.setOnTouchListener { view: View, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) {
                bitmap = viewModel.getBitmapFromView(binding.imageView)

                selectedColor =
                    viewModel.getSelectedColor(bitmap, event.x.toInt(), event.y.toInt())

                updateSelector(selectedColor)
            }
            true
        }
        binding.apply {
            controlOne.setOnClickListener(this@MainFragment)
            controlTwo.setOnClickListener(this@MainFragment)
            controlThree.setOnClickListener(this@MainFragment)
        }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun updateSelector(color: Int) {
        binding.selector.backgroundTintList = ColorStateList.valueOf(color)
    }

    private fun highlightCard(cardOne: Boolean, cardTwo: Boolean, cardThree: Boolean) {
        when {
            cardOne -> {
                binding.apply {
                    view1.backgroundTintList = ColorStateList.valueOf(selectedColor)
                    controlOne.setCardBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray
                        )
                    )
                    controlTwo.setCardBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.dark_gray
                        )
                    )
                    controlThree.setCardBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.dark_gray
                        )
                    )
                }
            }
            cardTwo -> {
                binding.apply {
                    view2.backgroundTintList = ColorStateList.valueOf(selectedColor)
                    controlTwo.setCardBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray
                        )
                    )
                    controlOne.setCardBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.dark_gray
                        )
                    )
                    controlThree.setCardBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.dark_gray
                        )
                    )
                }
            }
            cardThree -> {
                binding.apply {
                    view3.backgroundTintList = ColorStateList.valueOf(selectedColor)
                    controlThree.setCardBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray
                        )
                    )
                    controlOne.setCardBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.dark_gray
                        )
                    )
                    controlTwo.setCardBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.dark_gray
                        )
                    )
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.controlOne.id -> highlightCard(
                cardOneSelected,
                cardTwoSelected.not(),
                cardThreeSelected.not()
            )
            binding.controlTwo.id -> highlightCard(
                cardOneSelected.not(),
                cardTwoSelected,
                cardThreeSelected.not()
            )
            binding.controlThree.id -> highlightCard(
                cardOneSelected.not(),
                cardTwoSelected.not(),
                cardThreeSelected
            )
        }
    }
}