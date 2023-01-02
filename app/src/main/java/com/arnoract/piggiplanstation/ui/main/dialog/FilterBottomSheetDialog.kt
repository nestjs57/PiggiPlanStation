package com.arnoract.piggiplanstation.ui.main.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arnoract.piggiplanstation.R
import com.arnoract.piggiplanstation.core.setDebounceOnClickListener
import com.arnoract.piggiplanstation.databinding.BottomDialogFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheetDialog : BottomSheetDialogFragment() {

    lateinit var binding: BottomDialogFilterBinding

    var listener: FilterBottomSheetDialogListener? = null
    var currentSelected: TypeSelected = TypeSelected.NONE
    var type: TypeSelected? = null

    companion object {
        fun newInstance(
            type: TypeSelected?,
            listener: FilterBottomSheetDialogListener
        ) = FilterBottomSheetDialog().apply {
            this.type = type
            this.listener = listener
        }
    }

    interface FilterBottomSheetDialogListener {
        fun onConfirmSelectedType(type: TypeSelected)
    }

    enum class TypeSelected {
        NONE,
        BTS,
        MRT,
        APL,
        SRT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomDialogFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        setState()
        setOnClicked()
    }

    private fun setOnClicked() {
        binding.tvViewOnlyBts.setDebounceOnClickListener {
            setStateViewOnlyBts()
        }
        binding.tvViewOnlyMrt.setDebounceOnClickListener {
            setStateViewOnlyMrt()
        }
        binding.tvViewOnlyApl.setDebounceOnClickListener {
            setStateViewOnlyApl()
        }
        binding.tvViewOnlySrt.setDebounceOnClickListener {
            setStateViewOnlySrt()
        }
        binding.imvClose.setDebounceOnClickListener {
            dismiss()
        }
        binding.tvReset.setDebounceOnClickListener {
            clearSelected()
        }
        binding.btnConfirm.setDebounceOnClickListener {
            listener?.onConfirmSelectedType(currentSelected)
            dismiss()
        }
    }

    private fun setState() {
        when (type) {
            null -> {
                clearSelected()
            }
            TypeSelected.NONE -> {
                clearSelected()
            }
            TypeSelected.BTS -> {
                setStateViewOnlyBts()
            }
            TypeSelected.MRT -> {
                setStateViewOnlyMrt()
            }
            TypeSelected.APL -> {
                setStateViewOnlyApl()
            }
            TypeSelected.SRT -> {
                setStateViewOnlySrt()
            }
        }
    }

    private fun setStateViewOnlyBts() {
        binding.tvViewOnlyBts.setTextColor(requireContext().getColor(R.color.primary))
        binding.tvViewOnlyMrt.setTextColor(requireContext().getColor(R.color.black))
        binding.tvViewOnlyApl.setTextColor(requireContext().getColor(R.color.black))
        binding.tvViewOnlySrt.setTextColor(requireContext().getColor(R.color.black))
        binding.imvViewOnlyBtsSelected.visibility = View.VISIBLE
        binding.imvViewOnlyMrtSelected.visibility = View.GONE
        binding.imvViewOnlyAplSelected.visibility = View.GONE
        binding.imvViewOnlySrtSelected.visibility = View.GONE
        currentSelected = TypeSelected.BTS
    }

    private fun setStateViewOnlyMrt() {
        binding.tvViewOnlyBts.setTextColor(requireContext().getColor(R.color.black))
        binding.tvViewOnlyMrt.setTextColor(requireContext().getColor(R.color.primary))
        binding.tvViewOnlyApl.setTextColor(requireContext().getColor(R.color.black))
        binding.tvViewOnlySrt.setTextColor(requireContext().getColor(R.color.black))
        binding.imvViewOnlyBtsSelected.visibility = View.GONE
        binding.imvViewOnlyMrtSelected.visibility = View.VISIBLE
        binding.imvViewOnlyAplSelected.visibility = View.GONE
        binding.imvViewOnlySrtSelected.visibility = View.GONE
        currentSelected = TypeSelected.MRT
    }

    private fun setStateViewOnlyApl() {
        binding.tvViewOnlyBts.setTextColor(requireContext().getColor(R.color.black))
        binding.tvViewOnlyMrt.setTextColor(requireContext().getColor(R.color.black))
        binding.tvViewOnlyApl.setTextColor(requireContext().getColor(R.color.primary))
        binding.tvViewOnlySrt.setTextColor(requireContext().getColor(R.color.black))
        binding.imvViewOnlyBtsSelected.visibility = View.GONE
        binding.imvViewOnlyMrtSelected.visibility = View.GONE
        binding.imvViewOnlyAplSelected.visibility = View.VISIBLE
        binding.imvViewOnlySrtSelected.visibility = View.GONE
        currentSelected = TypeSelected.APL
    }

    private fun setStateViewOnlySrt() {
        binding.tvViewOnlyBts.setTextColor(requireContext().getColor(R.color.black))
        binding.tvViewOnlyMrt.setTextColor(requireContext().getColor(R.color.black))
        binding.tvViewOnlyApl.setTextColor(requireContext().getColor(R.color.black))
        binding.tvViewOnlySrt.setTextColor(requireContext().getColor(R.color.primary))
        binding.imvViewOnlyBtsSelected.visibility = View.GONE
        binding.imvViewOnlyMrtSelected.visibility = View.GONE
        binding.imvViewOnlyAplSelected.visibility = View.GONE
        binding.imvViewOnlySrtSelected.visibility = View.VISIBLE
        currentSelected = TypeSelected.SRT
    }

    private fun clearSelected() {
        binding.tvViewOnlyBts.setTextColor(requireContext().getColor(R.color.black))
        binding.tvViewOnlyMrt.setTextColor(requireContext().getColor(R.color.black))
        binding.tvViewOnlyApl.setTextColor(requireContext().getColor(R.color.black))
        binding.tvViewOnlySrt.setTextColor(requireContext().getColor(R.color.black))
        binding.imvViewOnlyBtsSelected.visibility = View.GONE
        binding.imvViewOnlyMrtSelected.visibility = View.GONE
        binding.imvViewOnlyAplSelected.visibility = View.GONE
        binding.imvViewOnlySrtSelected.visibility = View.GONE
        currentSelected = TypeSelected.NONE
    }
}