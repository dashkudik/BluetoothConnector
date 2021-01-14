package com.example.bluetoothcontroller

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val UUID_STRING = "5c47c9c3-0833-4d14-ae28-a4e509fc1e74"
        const val NAME = "Dima"
    }
    private val recyclerAdapter = DeviceListAdapter(::connectWithDevice)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_devices.adapter = recyclerAdapter
        btn_search.setOnClickListener {
            Observable
                .just(BluetoothAdapter.getDefaultAdapter().bondedDevices)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { devices ->
                    recyclerAdapter.submitList(devices.toList())
                }
        }
    }

    private fun connectWithDevice(device: BluetoothDevice) {
        Log.i("HERE", "START")
        AcceptThread(device).start()
    }
}