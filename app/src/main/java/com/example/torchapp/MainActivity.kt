package com.example.torchapp

import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService

class MainActivity : AppCompatActivity() {

    private lateinit var toggleButton: ToggleButton
    private lateinit var cameraManager: CameraManager
    private lateinit var cameraId: String

    //private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //context = this

        toggleButton = ToggleButton(this)

        var isFlash = this.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

        if (!isFlash) {
            displayNoFlashError()
        }
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraId = cameraManager.cameraIdList[0]
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
        toggleButton = findViewById(R.id.toggleButton1)

        toggleButton.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener() { _: CompoundButton, b: Boolean ->
            //@Override
            //fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                switchFlash(b)
            //}
        })

    }

    private fun switchFlash(status: Boolean) {
        try {
            cameraManager.setTorchMode(cameraId, status)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun displayNoFlashError() {
        var error: AlertDialog = AlertDialog.Builder(this).create()
        error.setTitle("Flash Error!")
        error.setMessage("Can't access flash")
        error.setButton(DialogInterface.BUTTON_POSITIVE, "OK", DialogInterface.OnClickListener() { _: DialogInterface, _: Int ->
            //fun onClick(dialogInterface: DialogInterface, i: Int) {
                finish()
            //}
        })
        error.show()
    }
}