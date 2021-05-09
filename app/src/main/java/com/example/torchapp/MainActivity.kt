package com.example.torchapp

import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var toggleButton: ToggleButton
    private lateinit var cameraManager: CameraManager
    private lateinit var cameraId: String
    private lateinit var time: EditText

    //private lateinit var context: Context
    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //context = this

        toggleButton = ToggleButton(this)
        time = EditText(this)

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
        time = findViewById(R.id.editTextNumber)

        toggleButton.setOnCheckedChangeListener { _: CompoundButton, switch: Boolean ->
            //@Override
            //fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
            var t = time.text.toString().toLong() * 1000
            countDownTimer(t)
            switchFlash(switch)
            //}
        }

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
        error.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { _: DialogInterface, _: Int ->
            //fun onClick(dialogInterface: DialogInterface, i: Int) {
            finish()
            //}
        }
        error.show()
    }

    private fun countDownTimer(time: Long) {
        object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000)
                millisUntilFinished / 1000
            }

            override fun onFinish() {
                //mTextField.setText("done!")
                switchFlash(false)
            }
        }.start()
    }
}