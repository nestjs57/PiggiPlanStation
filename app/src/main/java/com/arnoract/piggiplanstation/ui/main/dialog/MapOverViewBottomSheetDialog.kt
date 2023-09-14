package com.arnoract.piggiplanstation.ui.main.dialog

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arnoract.piggiplanstation.R
import com.arnoract.piggiplanstation.core.setDebounceOnClickListener
import com.arnoract.piggiplanstation.databinding.BottomDialogMapOverviewBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MapOverViewBottomSheetDialog : BottomSheetDialogFragment(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: BottomDialogMapOverviewBinding

    private lateinit var mMap: GoogleMap
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        mMap.setOnMarkerClickListener(this)
    }

    companion object {
        fun newInstance(

        ) = MapOverViewBottomSheetDialog().apply {

        }
    }

    override fun onStart() {
        super.onStart()

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)

        val screenHeight = displayMetrics.heightPixels
        val targetHeight = (screenHeight * 0.8).toInt()

        dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            ?.let { bottomSheet ->
                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.peekHeight = screenHeight
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                bottomSheet.layoutParams.height = targetHeight
                bottomSheet.requestLayout()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomDialogMapOverviewBinding.inflate(inflater, container, false)

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        val layoutParams = view?.layoutParams
        layoutParams?.height = screenHeight
        view?.layoutParams = layoutParams

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMap()
        initView()
    }

    private fun initView() {
        binding.imvClose.setDebounceOnClickListener {
            dismiss()
        }
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        p0.tag ?: return false
        return false
    }

    private fun setUpMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapLayout) as SupportMapFragment
        mapFragment.getMapAsync(callback)
    }
}