package com.example.bluetoothcontroller

import android.bluetooth.BluetoothDevice
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_device.view.*

class DeviceViewHolder(view: View, private val onItemClick: (BluetoothDevice) -> Unit):
    RecyclerView.ViewHolder(view) {
    fun onBind(device: BluetoothDevice) {
        with(itemView) {
            tv_device_name.text = device.name
            tv_device_address.text = device.address
            setOnClickListener {
                onItemClick(device)
            }
        }
    }
}