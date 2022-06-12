package com.arnoract.piggiplanstation

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.arnoract.piggiplanstation.base.BaseActivity
import com.arnoract.piggiplanstation.databinding.ActivityMainBinding
import com.arnoract.piggiplanstation.ui.main.adapter.StationAdapter
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override var layoutResource: Int = R.layout.activity_main
    private val mViewModel: MainActivityViewModel by viewModel()
    private var _mAdapter: StationAdapter? = null
    private val mAdapter
        get() = _mAdapter!!

    private val searchingProgressDialog: ProgressDialog by lazy {
        ProgressDialog(this, R.style.MyAlertDialogStyle).apply {
            isIndeterminate = true
            setCancelable(false)
            setMessage("กำลังค้นหา...")
        }
    }

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
                    searchingProgressDialog.show()
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
        binding.tvLocationName.setOnClickListener {
            openSearchLocation()
        }
    }

    override fun subscribeLiveData() {
        mViewModel.uiStations.observe(this) {
            mAdapter.submitList(it)
            binding.rcvStation.smoothScrollToPosition(0)
            binding.viewFlipper.displayedChild = if (it.isNullOrEmpty()) 0 else 1
            searchingProgressDialog.dismiss()
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