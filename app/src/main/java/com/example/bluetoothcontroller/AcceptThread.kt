package com.example.bluetoothcontroller

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.util.Log
import com.example.bluetoothcontroller.MainActivity.Companion.NAME
import com.example.bluetoothcontroller.MainActivity.Companion.UUID_STRING
import java.io.IOException
import java.util.*

class AcceptThread(private val device: BluetoothDevice): Thread() {
    private val mmServerSocket: BluetoothServerSocket? by lazy(LazyThreadSafetyMode.NONE) {
        BluetoothAdapter
            .getDefaultAdapter()
            .listenUsingInsecureRfcommWithServiceRecord(
                NAME,
                UUID.fromString(UUID_STRING)
            )
    }

    override fun run() {
        Log.i("TEST", "IN RUN ACCEPT THREAD")
        // Keep listening until exception occurs or a socket is returned.
        var shouldLoop = true
        while (shouldLoop) {
            val socket: BluetoothSocket? = try {
                mmServerSocket?.accept()
            } catch (e: IOException) {
                Log.i("TEST", "Socket's accept() method failed", e)
                shouldLoop = false
                null
            }
            socket?.also {
                Log.i("TEST", "ACCESS THREAD - SUCCESS")
                ConnectThread(device)
                mmServerSocket?.close()
                shouldLoop = false
            }
        }
    }

    // Closes the connect socket and causes the thread to finish.
    fun cancel() {
        try {
            mmServerSocket?.close()
        } catch (e: IOException) {
            Log.e("TEST", "Could not close the connect socket", e)
        }
    }
}