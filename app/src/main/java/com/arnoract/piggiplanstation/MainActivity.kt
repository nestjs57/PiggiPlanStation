package com.arnoract.piggiplanstation

import android.os.Bundle
import com.arnoract.piggiplanstation.base.BaseActivity
import com.arnoract.piggiplanstation.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mViewModel: MainActivityViewModel by viewModel()

    override var layoutResource: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel.getStationNearByLatLong()
    }
}