package com.arnoract.piggiplanstation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.arnoract.piggiplanstation.base.BaseActivity
import com.arnoract.piggiplanstation.databinding.ActivityMainBinding
import com.arnoract.piggiplanstation.ui.main.adapter.StationAdapter
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {

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

        val window: Window = this.window

// clear FLAG_TRANSLUCENT_STATUS flag:

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

// finally change the color

// finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_color)
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
    }

    override fun subscribeLiveData() {
        mViewModel.uiStations.observe(this) {
            mAdapter.submitList(it)
            binding.rcvStation.smoothScrollToPosition(0)
            binding.viewFlipper.displayedChild = if (it.isNullOrEmpty()) 0 else 1
            dialog?.dismiss()
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
}