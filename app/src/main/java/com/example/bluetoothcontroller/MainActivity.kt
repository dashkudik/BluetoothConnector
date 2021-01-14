package com.example.bluetoothcontroller

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.io.OutputStream
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val UUID_STRING = "0000111e-0000-1000-8000-00805f9b34fb"
        const val NAME = "Dima"
    }
    private val recyclerAdapter = DeviceListAdapter(::connectWithDevice)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AcceptThread().start()

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
        ConnectThread(device)
    }
}