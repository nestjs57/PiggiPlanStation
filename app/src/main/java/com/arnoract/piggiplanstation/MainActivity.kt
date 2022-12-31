package com.arnoract.piggiplanstation

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.arnoract.piggiplanstation.base.BaseActivity
import com.arnoract.piggiplanstation.core.setDebounceOnClickListener
import com.arnoract.piggiplanstation.databinding.ActivityMainBinding
import com.arnoract.piggiplanstation.ui.main.adapter.StationAdapter
import com.arnoract.piggiplanstation.ui.main.dialog.FilterBottomSheetDialog
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>(),
    FilterBottomSheetDialog.FilterBottomSheetDialogListener {

    override var layoutResource: Int = R.layout.activity_main
    private val mViewModel: MainActivityViewModel by viewModel()
    private var _mAdapter: StationAdapter? = null
    private val mAdapter
        get() = _mAdapter!!
    private var materialAlertDialogBuilder: MaterialAlertDialogBuilder? = null
    private var dialog: AlertDialog? = null

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val place = Autocomplete.getPlaceFromIntent(data)
                    val latLng = place.latLng
                    if (latLng != null) {
                        mViewModel.getStationNearByLatLong(latLng.latitude, latLng.longitude)
                    }
                    binding.tvLocationName.text = place.name
                    binding.tvLocationName.setTextColor(this.getColor(R.color.black))
                    dialog?.show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()
        initView()
    }

    private fun setupRecyclerView() {
        _mAdapter = StationAdapter()
        binding.rcvStation.layoutManager = LinearLayoutManager(this)
        binding.rcvStation.adapter = mAdapter
    }

    private fun initView() {
        materialAlertDialogBuilder = MaterialAlertDialogBuilder(this@MainActivity)
            .setCancelable(false)
            .setView(R.layout.view_item_loading)
        dialog = materialAlertDialogBuilder?.create()
        binding.tvLocationName.setOnClickListener {
            openSearchLocation()
        }
        binding.imvFilter.setDebounceOnClickListener {
            mViewModel.onOpenFilterType()
        }
    }

    override fun subscribeLiveData() {
        mViewModel.uiStations.observe(this) {
            mAdapter.submitList(it)
            binding.rcvStation.smoothScrollToPosition(0)
            binding.viewFlipper.displayedChild = if (it.isNullOrEmpty()) 0 else 1
            dialog?.dismiss()
        }
        mViewModel.openDialogFilterType.observe(this) {
            FilterBottomSheetDialog.newInstance(it, this)
                .show(
                    supportFragmentManager,
                    FilterBottomSheetDialog::class.java.canonicalName
                )
        }
        mViewModel.isEnableFilter.observe(this) {
            binding.imvFilter.apply {
                isClickable = it
                val colorTint = if (it) R.color.opposite_primary else R.color.gray500
                setColorFilter(ContextCompat.getColor(context, colorTint), PorterDuff.Mode.SRC_IN)
            }
        }
    }

    private fun openSearchLocation() {
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.OVERLAY,
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        )
            .build(this)
        resultLauncher.launch(intent)
    }

    override fun onConfirmSelectedType(type: FilterBottomSheetDialog.TypeSelected) {
        mViewModel.setFilterType(type)
    }
}