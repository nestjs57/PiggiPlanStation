package com.arnoract.piggiplanstation.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.arnoract.piggiplanstation.R

abstract class BaseDialogFragment(
    @LayoutRes
    contentLayoutId: Int
) : DialogFragment(contentLayoutId) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogNoTitle)

        arguments?.let {
            extractExtras(it)
        }
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState)
        }

        setUpInstance()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        observeViewModel()
        if (savedInstanceState == null) {
            init()
        } else {
            restoreView(savedInstanceState)
        }
    }

    /**
     * Extract extras from fragment's arguments
     * called when fragment has arguments set.
     */
    open fun extractExtras(arguments: Bundle) {
        //Optional
    }

    open fun setUpInstance() {
        //Optional
    }

    open fun setUpView() {
        //Optional
    }

    /**
     * Use to observe ViewModel's LiveData.
     */
    open fun observeViewModel() {
        //Optional
    }

    /**
     * Do things when fragment first created.
     */
    open fun init() {
        //Optional
    }

    /**
     * Restore savedInstanceState data.
     * Called when configuration change.
     */
    open fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        //Optional
    }

    /**
     * Restore view's state from savedInstanceState.
     * Called when configuration change.
     */
    open fun restoreView(savedInstanceState: Bundle?) {
        //Optional
    }
}
