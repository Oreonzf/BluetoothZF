package com.example.bluetoothzf.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

class BluetoothManager(private val context: Context) {
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var discoveryListener: DiscoveryListener? = null
    private val discoveredDevices = mutableSetOf<BluetoothDevice>() // Usar Set para evitar duplicatas

    init {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
    }

    interface DiscoveryListener {
        fun onDeviceFound(devices: List<BluetoothDevice>)
        fun onDiscoveryStarted()
        fun onDiscoveryFinished()
    }

    fun setDiscoveryListener(listener: DiscoveryListener) {
        this.discoveryListener = listener
    }

    fun startDiscovery() {
        // Verifique se a permissão BLUETOOTH_SCAN foi concedida
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED) {
            discoveredDevices.clear() // Limpa dispositivos anteriores
            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND).apply {
                addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
            }
            context.registerReceiver(bluetoothReceiver, filter)
            bluetoothAdapter?.startDiscovery()
            discoveryListener?.onDiscoveryStarted()
        } else {
            // Notifique o chamador que a permissão é necessária
            discoveryListener?.onDiscoveryFinished()
            Toast.makeText(context, "Permissão BLUETOOTH_SCAN necessária para iniciar descoberta.", Toast.LENGTH_SHORT).show()
        }
    }

    private val bluetoothReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE, BluetoothDevice::class.java)
                    device?.let {
                        if (discoveredDevices.add(it)) { // Adiciona apenas dispositivos novos
                            discoveryListener?.onDeviceFound(discoveredDevices.toList())
                        }
                    }
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    context.unregisterReceiver(this)
                    discoveryListener?.onDiscoveryFinished()
                }
            }
        }
    }
}
