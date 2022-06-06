package com.arnoract.piggiplanstation.core.model

import com.google.gson.annotations.SerializedName

data class SimpleApiResponse(
	@field:SerializedName("success")
	var success: Int = 0,
	@field:SerializedName("error_message", alternate = ["error", "message"])
	var errorMessage: String? = null
)
