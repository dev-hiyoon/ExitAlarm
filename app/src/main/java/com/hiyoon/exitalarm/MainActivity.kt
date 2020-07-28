package com.hiyoon.exitalarm

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.daum.mf.map.api.MapView


class MainActivity : AppCompatActivity() {


    private val PERMISSION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set kakao map
        val mapView = MapView(this)
        map_view.addView(mapView)

        // check pemission
        requestPermissions(
            arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), // 1
            PERMISSION_REQUEST_CODE
        ) // 2
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {

        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {  // 1
                if (grantResults.isEmpty()) {  // 2
                    throw RuntimeException("Empty permission result")
                }

                // check grant result
                grantResults.forEach {
                    if (it == PackageManager.PERMISSION_GRANTED) {  // 3
                        Toast.makeText(applicationContext, "Permission granted", Toast.LENGTH_LONG)
                    } else {
                        if (shouldShowRequestPermissionRationale(
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        ) { // 4
                            requestPermissions(
                                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                PERMISSION_REQUEST_CODE
                            )
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "User declined and i can't ask",
                                Toast.LENGTH_LONG
                            )
                        }
                    }
                }
            }
        }
    }
}
