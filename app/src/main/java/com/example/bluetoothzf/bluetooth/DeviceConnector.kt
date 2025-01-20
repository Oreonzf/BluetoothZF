package com.example.bluetoothzf.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

class DeviceConnector(private val device: BluetoothDevice, private val uuid: UUID, private val context: Context) {
    private var socket: BluetoothSocket? = null
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null
    var connectionListener: ConnectionListener? = null

    interface ConnectionListener {
        fun onConnectionSuccess(device: BluetoothDevice)
        fun onConnectionFailed(device: BluetoothDevice, error: String)
        fun onDataReceived(data: String)
    }

    fun connect() {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
            Thread {
                try {
                    socket = device.createRfcommSocketToServiceRecord(uuid)
                    socket?.connect()
                    connectionListener?.onConnectionSuccess(device)
                    manageConnectedSocket(socket!!)
                } catch (e: IOException) {
                    connectionListener?.onConnectionFailed(device, e.message ?: "Erro desconhecido")
                    e.printStackTrace()
                }
            }.start()
        } else {
            connectionListener?.onConnectionFailed(device, "Permissão BLUETOOTH_CONNECT necessária.")
        }
    }

    private fun manageConnectedSocket(socket: BluetoothSocket) {
        inputStream = socket.inputStream
        outputStream = socket.outputStream
        readFromInputStream()
    }

    private fun readFromInputStream() {
        val buffer = ByteArray(1024)  // Buffer para o fluxo de dados
        var bytes: Int

        while (true) {
            try {
                bytes = inputStream!!.read(buffer)
                val incomingMessage = String(buffer, 0, bytes)
                connectionListener?.onDataReceived(incomingMessage)
            } catch (e: IOException) {
                e.printStackTrace()
                break
            }
        }
    }

    fun writeToOutputStream(message: String) {
        val bytes = message.toByteArray()
        try {
            outputStream?.write(bytes)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
