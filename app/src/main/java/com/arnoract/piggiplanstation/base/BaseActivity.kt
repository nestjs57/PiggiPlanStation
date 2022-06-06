package com.arnoract.piggiplanstation.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    abstract var layoutResource: Int
    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResource)
        subscribeLiveData()
        initInstance()
    }


    open fun initInstance() {
    }

    open fun onViewClickCheckDouble(view: View?) {
    }

    open fun subscribeLiveData() {
    }
}
