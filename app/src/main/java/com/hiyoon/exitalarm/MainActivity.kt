package com.hiyoon.exitalarm

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.daum.mf.map.api.MapView


class MainActivity : AppCompatActivity() {


    private val PERMISSION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // check pemission
        requestPermissions(
            arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), // 1
            PERMISSION_REQUEST_CODE
        ) // 2

        // set kakao map
        val mapView = MapView(this)
        mapView.setDaumMapApiKey("a007daa36d84faa52404a7a30bb5c643")
        val mapViewContainer = map_view
        mapViewContainer.addView(mapView)
    }

    /**
     * permission check
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {

        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {  // 1
                if (grantResults.isEmpty()) {  // 2
                    throw RuntimeException("Empty permission result")
                }

                // check grant result
                grantResults.forEachIndexed { idx, element ->

                    // kakao map이 delay를 안주면 조회를 못해온다.. 대박...
                    Thread.sleep(1000)

                    // permission check
                    if (element == PackageManager.PERMISSION_GRANTED) {  // 3
                        Toast.makeText(applicationContext, "Permission granted", Toast.LENGTH_LONG)
                    } else {
                        if (shouldShowRequestPermissionRationale(permissions.get(idx))
                        ) { // 4
                            requestPermissions(
                                arrayOf(permissions.get(idx)),
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
