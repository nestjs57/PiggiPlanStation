package com.arnoract.piggiplanstation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.arnoract.piggiplanstation.base.BaseActivity
import com.arnoract.piggiplanstation.core.setDebounceOnClickListener
import com.arnoract.piggiplanstation.core.toast
import com.arnoract.piggiplanstation.databinding.ActivityMainBinding
import com.arnoract.piggiplanstation.ui.main.adapter.SeeMoreAdapter
import com.arnoract.piggiplanstation.ui.main.adapter.StationAdapter
import com.arnoract.piggiplanstation.ui.main.dialog.FilterBottomSheetDialog
import com.arnoract.piggiplanstation.ui.main.dialog.MapOverViewBottomSheetDialog
import com.arnoract.piggiplanstation.ui.main.model.UiSeeMore
import com.arnoract.piggiplanstation.ui.main.model.UiStation
import com.arnoract.piggiplanstation.ui.main.model.UiType
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>(),
    FilterBottomSheetDialog.FilterBottomSheetDialogListener, SeeMoreAdapter.SeeMoreAdapterListener,
    StationAdapter.StationAdapterListener {

    override var layoutResource: Int = R.layout.activity_main
    private val mViewModel: MainActivityViewModel by viewModel()
    private var _mAdapter: StationAdapter? = null
    private val mAdapter
        get() = _mAdapter!!
    private var _mSeeMoreAdapter: SeeMoreAdapter? = null
    private val mSeeMoreAdapter
        get() = _mSeeMoreAdapter
    private var materialAlertDialogBuilder: MaterialAlertDialogBuilder? = null
    private var dialog: AlertDialog? = null

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
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
                        place.name?.let { mViewModel.setLocationNameSelected(it) }
                    }
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
        loadAds()
    }

    private fun loadAds() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun setupRecyclerView() {
        _mAdapter = StationAdapter(this)
        _mSeeMoreAdapter = SeeMoreAdapter(this)
        binding.rcvStation.layoutManager = LinearLayoutManager(this)
        binding.rcvStation.adapter = ConcatAdapter(mAdapter, mSeeMoreAdapter)
    }

    private fun initView() {
        materialAlertDialogBuilder =
            MaterialAlertDialogBuilder(this@MainActivity).setCancelable(false)
                .setView(R.layout.view_item_loading)
        dialog = materialAlertDialogBuilder?.create()
        binding.tvLocationName.setOnClickListener {
            openSearchLocation()
        }
        binding.tvTitleNearMe.isSelected = true
        binding.tvTitleNearMe.text = "-"
        binding.imvFilter.setDebounceOnClickListener {
            mViewModel.onOpenFilterType()
        }

        binding.imvRelocation.setDebounceOnClickListener {
            mViewModel.onRefreshLocationNearMe()
        }
    }

    override fun subscribeLiveData() {
        mViewModel.stationNearMe.observe(this) {
            val prefixName = when (it.type) {
                UiType.BTS_SKW, UiType.BTS_SL, UiType.BTS_G -> {
                    getString(R.string.bts_code_label)
                }

                UiType.MRT_BLUE, UiType.MRT_YELLOW, UiType.MRT_PURPLE -> {
                    getString(R.string.mrt_code_label)
                }

                UiType.APL -> {
                    getString(R.string.prefix_apl_label)
                }

                UiType.RED_NORMAL, UiType.RED_WEAK -> {
                    getString(R.string.prefix_srtet_label)
                }
            }
            val locationName = "$prefixName ${it.name_th} (${it.distanceStr})"
            binding.tvTitleNearMe.text = locationName
        }
        mViewModel.checkPermissionEvent.observe(this) {
            if (hasLocationPermission()) {
                requestLocation()
            } else {
                requestLocationPermission()
            }
        }
        mViewModel.locationName.observe(this) {
            binding.tvLocationName.text = it
            binding.tvLocationName.setTextColor(this.getColor(R.color.black))
            binding.tvTitle.text = getString(R.string.list_of_nearby_station_label, it)
            binding.tvTitle.setTextColor(this.getColor(R.color.black))
        }
        mViewModel.isShowSeeMore.observe(this) {
            if (it) {
                mSeeMoreAdapter?.submitList(listOf(UiSeeMore(getString(R.string.see_more_label))))
            } else {
                mSeeMoreAdapter?.submitList(null)
            }
        }
        mViewModel.scrollToPosition.observe(this) {
            binding.rcvStation.smoothScrollToPosition(0)
        }
        mViewModel.uiStations.observe(this) {
            mAdapter.submitList(it)
            binding.viewFlipper.displayedChild = if (it.isNullOrEmpty()) 0 else 1
            dialog?.dismiss()
        }
        mViewModel.openDialogFilterType.observe(this) {
            FilterBottomSheetDialog.newInstance(it, this).show(
                supportFragmentManager, FilterBottomSheetDialog::class.java.canonicalName
            )
        }
        mViewModel.onClickStationEvent.observe(this) {
            MapOverViewBottomSheetDialog.newInstance(it).show(
                supportFragmentManager, MapOverViewBottomSheetDialog::class.java.canonicalName
            )
        }
        mViewModel.isEnableFilter.observe(this) {
            binding.imvFilter.apply {
                isClickable = it
                val colorTint = if (it) R.color.opposite_primary else R.color.gray500
                setColorFilter(ContextCompat.getColor(context, colorTint), PorterDuff.Mode.SRC_IN)
            }
        }
        mViewModel.isLoadingLocationNearMe.observe(this) {
            it ?: return@observe
            if (it) {
                binding.viewFlipperRefreshLocation.displayedChild = 1
            } else {
                binding.viewFlipperRefreshLocation.displayedChild = 0
                requestLocationPermission()
            }
        }
    }

    private fun openSearchLocation() {
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.OVERLAY,
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        ).build(this)
        resultLauncher.launch(intent)
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun requestLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocation()
            }
        }
    }

    override fun onConfirmSelectedType(type: FilterBottomSheetDialog.TypeSelected) {
        mViewModel.setDefaultMaxDataSize()
        mViewModel.setFilterType(type)
    }

    override fun onClickedSeeMore() {
        mViewModel.onClickSeeMore()
    }

    override fun onClickStation(data: UiStation) {
        mViewModel.onClickStation(data)
    }
}