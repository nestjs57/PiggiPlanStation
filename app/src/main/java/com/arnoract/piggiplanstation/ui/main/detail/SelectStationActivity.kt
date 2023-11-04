package com.arnoract.piggiplanstation.ui.main.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.arnoract.piggiplanstation.R
import com.arnoract.piggiplanstation.base.BaseActivity
import com.arnoract.piggiplanstation.core.setDebounceOnClickListener
import com.arnoract.piggiplanstation.databinding.ActivitySelectStationBinding
import com.arnoract.piggiplanstation.ui.main.adapter.StationAdapter
import com.arnoract.piggiplanstation.ui.main.model.UiStation
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectStationActivity : BaseActivity<ActivitySelectStationBinding>(),
    StationAdapter.StationAdapterListener {

    override var layoutResource: Int = R.layout.activity_select_station

    private val mViewModel: SelectStationViewModel by viewModel()

    private var _mAdapter: StationAdapter? = null
    private val mAdapter
        get() = _mAdapter!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        initView()
        observeViewModel()
    }

    private fun initView() {
        _mAdapter = StationAdapter(this)
        binding.rcvStation.layoutManager = LinearLayoutManager(this)
        binding.rcvStation.adapter = ConcatAdapter(mAdapter)

        binding.imvBack.setDebounceOnClickListener {
            finish()
        }

        binding.edtStation.addTextChangedListener {
            mViewModel.onSearchChanged(binding.edtStation.text?.trim().toString())
        }
    }

    private fun observeViewModel() {
        mViewModel.uiStations.observe(this) {
            it ?: return@observe
            if (it.isEmpty()) {
                binding.tvSearchNotFound.visibility = View.VISIBLE
            } else {
                binding.tvSearchNotFound.visibility = View.GONE
            }
            mAdapter.submitList(it)
        }
    }

    override fun onClickStation(model: UiStation) {
        val intent = Intent()
        intent.putExtra(DestinationDetailActivity.RESULT_START_ID, model.id)
        setResult(RESULT_OK, intent)
        finish()
    }
}