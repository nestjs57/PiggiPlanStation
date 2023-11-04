package com.arnoract.piggiplanstation.ui.main.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.collectAsState
import com.arnoract.piggiplanstation.R
import com.arnoract.piggiplanstation.base.BaseActivity
import com.arnoract.piggiplanstation.core.setDebounceOnClickListener
import com.arnoract.piggiplanstation.databinding.ActivityDestinationDetailBinding
import com.arnoract.piggiplanstation.ui.main.model.UiType
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DestinationDetailActivity : BaseActivity<ActivityDestinationDetailBinding>() {

    override var layoutResource: Int = R.layout.activity_destination_detail

    private val mViewModel: DestinationDetailViewModel by viewModel {
        parametersOf(intent.getStringExtra(DESTINATION_ID))
    }

    companion object {
        const val DESTINATION_ID = "destination-id"
        const val LOCATION_NAME = "location-name"
        const val DISTANCE_STRING = "distance-string"
        const val RESULT_START_ID = "result-start-id"
    }

    private lateinit var selectStationResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        initView()
        observeViewModel()
        observeResult()
    }

    private fun initView() {
        val locationName = intent.getStringExtra(LOCATION_NAME)
        binding.imvBack.setOnClickListener {
            finish()
        }
        binding.tvTitleToolsBar.text = getString(R.string.go_to_with_text_label, locationName)
        binding.tvStationStart.setDebounceOnClickListener {
            val intent = Intent(this, SelectStationActivity::class.java)
            selectStationResultLauncher.launch(intent)
        }
        binding.composeView.setContent {
            val data = mViewModel.routes.collectAsState()

            binding.tvTitleDescription.text = getString(
                R.string.description_way_with_value_of_station, data.value.size.toString()
            )
            LazyColumn {
                items(data.value.size) {
                    DestinationDetailItem(data.value[it])
                }
                if (data.value.isNotEmpty()) {
                    item {
                        DestinationDetailFooter(
                            intent.getStringExtra(LOCATION_NAME) ?: "-",
                            intent.getStringExtra(DISTANCE_STRING)
                        )
                    }
                }
            }
        }
    }

    private fun observeViewModel() {
        mViewModel.isLoading.observe(this) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.layoutContent.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.layoutContent.visibility = View.VISIBLE
            }
        }
        mViewModel.endLocationCode.observe(this) {
            when (it) {
                UiType.BTS_SKW -> {
                    binding.layoutStationCodeEnd.setBackgroundResource(R.drawable.ic_bg_bts_sukhumvit)
                    binding.tvStationCodeEnd.text = getString(R.string.bts_code_label)
                }

                UiType.BTS_SL -> {
                    binding.layoutStationCodeEnd.setBackgroundResource(R.drawable.ic_bg_bts_srilom)
                    binding.tvStationCodeEnd.text = getString(R.string.bts_code_label)
                }

                UiType.MRT_BLUE -> {
                    binding.layoutStationCodeEnd.setBackgroundResource(R.drawable.ic_bg_mrt_blue_line)
                    binding.tvStationCodeEnd.text = getString(R.string.mrt_code_label)
                }

                UiType.MRT_PURPLE -> {
                    binding.layoutStationCodeEnd.setBackgroundResource(R.drawable.ic_bg_mrt_purple_line)
                    binding.tvStationCodeEnd.text = getString(R.string.mrt_code_label)
                }

                UiType.APL -> {
                    binding.layoutStationCodeEnd.setBackgroundResource(R.drawable.ic_bg_apl)
                    binding.tvStationCodeEnd.text = getString(R.string.apl_code_label)
                }

                UiType.BTS_G -> {
                    binding.layoutStationCodeEnd.setBackgroundResource(R.drawable.ic_bg_bts_gold)
                    binding.tvStationCodeEnd.text = getString(R.string.bts_g_code_label)
                }

                UiType.RED_NORMAL -> {
                    binding.layoutStationCodeEnd.setBackgroundResource(R.drawable.ic_bg_srtet_red)
                    binding.tvStationCodeEnd.text = getString(R.string.srtet_code_label)
                }

                UiType.RED_WEAK -> {
                    binding.layoutStationCodeEnd.setBackgroundResource(R.drawable.ic_bg_srtet_red_ligth)
                    binding.tvStationCodeEnd.text = getString(R.string.srtet_code_label)
                }

                UiType.MRT_YELLOW -> {
                    binding.layoutStationCodeEnd.setBackgroundResource(R.drawable.ic_bg_mrt_yellow)
                    binding.tvStationCodeEnd.text = getString(R.string.mrt_code_label)
                }

                else -> {
                    binding.layoutStationCodeEnd.setBackgroundResource(R.drawable.ic_bg_srtet_red_ligth)
                    binding.tvStationCodeEnd.text = getString(R.string.srtet_code_label)
                }
            }
        }
        mViewModel.startLocationCode.observe(this) {
            binding.layoutStationCodeStart.visibility = View.VISIBLE
            when (it) {
                UiType.BTS_SKW -> {
                    binding.layoutStationCodeStart.setBackgroundResource(R.drawable.ic_bg_bts_sukhumvit)
                    binding.tvStationCodeStart.text = getString(R.string.bts_code_label)
                }

                UiType.BTS_SL -> {
                    binding.layoutStationCodeStart.setBackgroundResource(R.drawable.ic_bg_bts_srilom)
                    binding.tvStationCodeStart.text = getString(R.string.bts_code_label)
                }

                UiType.MRT_BLUE -> {
                    binding.layoutStationCodeStart.setBackgroundResource(R.drawable.ic_bg_mrt_blue_line)
                    binding.tvStationCodeStart.text = getString(R.string.mrt_code_label)
                }

                UiType.MRT_PURPLE -> {
                    binding.layoutStationCodeStart.setBackgroundResource(R.drawable.ic_bg_mrt_purple_line)
                    binding.tvStationCodeStart.text = getString(R.string.mrt_code_label)
                }

                UiType.APL -> {
                    binding.layoutStationCodeStart.setBackgroundResource(R.drawable.ic_bg_apl)
                    binding.tvStationCodeStart.text = getString(R.string.apl_code_label)
                }

                UiType.BTS_G -> {
                    binding.layoutStationCodeStart.setBackgroundResource(R.drawable.ic_bg_bts_gold)
                    binding.tvStationCodeStart.text = getString(R.string.bts_g_code_label)
                }

                UiType.RED_NORMAL -> {
                    binding.layoutStationCodeStart.setBackgroundResource(R.drawable.ic_bg_srtet_red)
                    binding.tvStationCodeStart.text = getString(R.string.srtet_code_label)
                }

                UiType.RED_WEAK -> {
                    binding.layoutStationCodeStart.setBackgroundResource(R.drawable.ic_bg_srtet_red_ligth)
                    binding.tvStationCodeStart.text = getString(R.string.srtet_code_label)
                }

                UiType.MRT_YELLOW -> {
                    binding.layoutStationCodeStart.setBackgroundResource(R.drawable.ic_bg_mrt_yellow)
                    binding.tvStationCodeStart.text = getString(R.string.mrt_code_label)
                }

                else -> {
                    binding.layoutStationCodeStart.setBackgroundResource(R.drawable.ic_bg_srtet_red_ligth)
                    binding.tvStationCodeStart.text = getString(R.string.srtet_code_label)
                }
            }
        }
        mViewModel.endLocationName.observe(this) {
            binding.tvStationEnd.text = it
        }
        mViewModel.startLocationName.observe(this) {
            binding.tvStationStart.text = it
            binding.tvStationStart.setTextColor(this.getColor(R.color.black))
        }
    }

    private fun observeResult() {
        selectStationResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                mViewModel.setStationSelected(data?.getStringExtra(RESULT_START_ID))
            }
        }
    }
}