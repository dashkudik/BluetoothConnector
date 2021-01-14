package com.example.bluetoothcontroller

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class DeviceListAdapter(val onItemClick: (BluetoothDevice) -> Unit):
    ListAdapter<BluetoothDevice, DeviceViewHolder>(object: DiffUtil.ItemCallback<BluetoothDevice>() {
        override fun areItemsTheSame(oldItem: BluetoothDevice, newItem: BluetoothDevice) = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: BluetoothDevice, newItem: BluetoothDevice) = oldItem == newItem
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DeviceViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_device, null, false),
            onItemClick
            )

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) =
        holder.onBind(getItem(position))
}