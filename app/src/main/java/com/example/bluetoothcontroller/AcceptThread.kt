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

class AcceptThread(): Thread() {
    private val mmServerSocket: BluetoothServerSocket =
        BluetoothAdapter
            .getDefaultAdapter()
            .listenUsingRfcommWithServiceRecord(NAME, UUID.fromString(UUID_STRING))


    override fun run() {
        // Keep listening until exception occurs or a socket is returned.
        var shouldLoop = true
        while (shouldLoop) {
            val socket: BluetoothSocket? = try {
                Log.i("TEST", "TRYING")
                mmServerSocket.accept()
            } catch (e: Throwable) {
                Log.i("TEST", "Socket's accept() method failed")
                shouldLoop = false
                null
            }
            socket?.also {
                Log.i("TEST", "ACCESS THREAD - SUCCESS")
                mmServerSocket.close()
                shouldLoop = false
            }
        }
    }

    // Closes the connect socket and causes the thread to finish.
    fun cancel() {
        try {
            mmServerSocket.close()
        } catch (e: IOException) {
            Log.e("TEST", "Could not close the connect socket", e)
        }
    }
}