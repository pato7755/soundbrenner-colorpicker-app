package com.soundbrenner.colorpicker.ui.main

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
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
    private var pointer = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        var bitmap: Bitmap

        binding.imageView.setOnTouchListener { view: View, event: MotionEvent ->

            if (event.action == MotionEvent.ACTION_DOWN) {

                bitmap = viewModel.getBitmapFromView(binding.imageView)

                selectedColor =
                    viewModel.getSelectedColor(bitmap, event.x.toInt(), event.y.toInt())

                updateSelectorColor(selectedColor)

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

    private fun updateSelectorColor(color: Int) {
        binding.selector.backgroundTintList = ColorStateList.valueOf(color)
        pointer++
        checkPointer(false)
    }

    private fun highlightCard(cardOne: Boolean, cardTwo: Boolean, cardThree: Boolean) {
        when {
            cardOne -> {
                binding.apply {
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
            binding.controlOne.id -> pointer = 1
            binding.controlTwo.id -> pointer = 2
            binding.controlThree.id -> pointer = 3
        }
        checkPointer(true)
    }

    private fun saveColor(view: View?) {
        when (view?.id) {
            binding.controlOne.id -> {
                highlightCard(cardOne = true, cardTwo = false, cardThree = false)
                binding.view1.backgroundTintList = ColorStateList.valueOf(selectedColor)
            }
            binding.controlTwo.id -> {
                highlightCard(cardOne = false, cardTwo = true, cardThree = false)
                binding.view2.backgroundTintList = ColorStateList.valueOf(selectedColor)
            }
            binding.controlThree.id -> {
                highlightCard(cardOne = false, cardTwo = false, cardThree = true)
                binding.view3.backgroundTintList = ColorStateList.valueOf(selectedColor)
            }
        }
    }

    private fun recallColor(view: View?) {
        when (view?.id) {
            binding.controlOne.id -> {
                highlightCard(cardOne = true, cardTwo = false, cardThree = false)
                binding.selector.backgroundTintList = binding.view1.backgroundTintList
            }
            binding.controlTwo.id -> {
                highlightCard(cardOne = false, cardTwo = true, cardThree = false)
                binding.selector.backgroundTintList = binding.view2.backgroundTintList
            }
            binding.controlThree.id -> {
                highlightCard(cardOne = false, cardTwo = false, cardThree = true)
                binding.selector.backgroundTintList = binding.view3.backgroundTintList
            }
        }
    }

    private fun checkPointer(recall: Boolean) {
        when (pointer) {
            1 -> if (recall) recallColor(binding.controlOne) else saveColor(binding.controlOne)
            2 -> if (recall) recallColor(binding.controlTwo) else saveColor(binding.controlTwo)
            3 -> {
                if (recall) recallColor(binding.controlThree) else saveColor(binding.controlThree)
                pointer = 0
            }
        }
    }
}