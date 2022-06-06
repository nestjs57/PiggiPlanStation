package com.arnoract.piggiplanstation.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

fun getDistanceMeter(locationA: Location, locationB: Location): Float {
    return locationA.distanceTo(locationB)
}

fun Context.bitmapDescriptorFromVector(vectorResId: Int): BitmapDescriptor {
    val vectorDrawable = ContextCompat.getDrawable(this, vectorResId)
    vectorDrawable!!.setBounds(
        0,
        0,
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight
    )
    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    vectorDrawable.draw(Canvas(bitmap))
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun updateCameraLatLngZoom(latLng: LatLng?, zoomLevel: Float = 12.0f): CameraUpdate {
    return CameraUpdateFactory.newLatLngZoom(
        LatLng(
            latLng?.latitude ?: 0.0,
            latLng?.longitude ?: 0.0
        ), zoomLevel
    )
}

fun updateCameraLatLngBound(latLng: List<LatLng>, padding: Int = 0): CameraUpdate {
    val builder = LatLngBounds.Builder()
    latLng.forEach { latLong ->
        builder.include(latLong)
    }
    return CameraUpdateFactory.newLatLngBounds(builder.build(), padding)
}