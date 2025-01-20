package com.example.bluetoothzf.ui.views

import DeviceListAdapter
import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetoothzf.R
import com.example.bluetoothzf.bluetooth.BluetoothManager
import com.example.bluetoothzf.bluetooth.BluetoothScreen
import com.example.bluetoothzf.bluetooth.DeviceConnector
import com.example.bluetoothzf.ui.theme.BluetoothZFTheme


class MainActivity : ComponentActivity() {
    private lateinit var bluetoothManager: BluetoothManager
    private var bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var DeviceListAdapter: DeviceListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        checkAndRequestPermissions()
        setupBluetooth()

        setContent {
            BluetoothZFTheme {
                BluetoothScreen(
                    onEnableBluetooth = { enableBluetooth() },
                    onDiscoverDevices = { startDiscovery() }
                )
            }
        }
    }


    private fun setupBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Este dispositivo não suporta Bluetooth", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        bluetoothManager = BluetoothManager(this).apply {
            setDiscoveryListener(object : BluetoothManager.DiscoveryListener {
                override fun onDeviceFound(devices: List<BluetoothDevice>) {
                    runOnUiThread {
                        DeviceListAdapter.updateDevices(devices) // Atualiza o adapter com a lista de dispositivos
                    }
                }

                override fun onDiscoveryStarted() {
                    Toast.makeText(this@MainActivity, "Iniciando descoberta...", Toast.LENGTH_SHORT).show()
                }

                override fun onDiscoveryFinished() {
                    Toast.makeText(this@MainActivity, "Descoberta concluída!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun requestPermissionsIfNecessary() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.BLUETOOTH_SCAN),
                    REQUEST_CODE_PERMISSIONS
                )
            }
        }
    }


    private fun enableBluetooth() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
            if (bluetoothAdapter?.isEnabled == false) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            } else {
                Toast.makeText(this, "Bluetooth já está ligado!", Toast.LENGTH_SHORT).show()
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.BLUETOOTH_CONNECT),
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun connectToDevice(device: BluetoothDevice) {
        val connector = DeviceConnector(device, MY_UUID, context = baseContext).apply {
            connectionListener = object : DeviceConnector.ConnectionListener {
                @SuppressLint("MissingPermission")
                override fun onConnectionSuccess(device: BluetoothDevice) {
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            "Conectado a: ${device.name ?: "Desconhecido"}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onConnectionFailed(device: BluetoothDevice, error: String) {
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            "Erro ao conectar: $error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onDataReceived(data: String) {
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            "Dados recebidos: $data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        connector.connect()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        DeviceListAdapter = DeviceListAdapter(arrayListOf()) { device ->
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return@DeviceListAdapter
            }
            Toast.makeText(this, "Conectando a: ${device.name ?: "Dispositivo desconhecido"}", Toast.LENGTH_SHORT).show()
            connectToDevice(device) // Chama o método para iniciar a conexão
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DeviceListAdapter
    }


    private fun startDiscovery() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permissão para descobrir dispositivos Bluetooth não concedida.", Toast.LENGTH_SHORT).show()
            return
        }
        bluetoothManager.startDiscovery()
    }

    private val REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.BLUETOOTH_SCAN,
        android.Manifest.permission.BLUETOOTH_CONNECT
    )

    private val REQUEST_CODE_PERMISSIONS = 1001

    private fun checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val missingPermissions = REQUIRED_PERMISSIONS.filter {
                ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
            }
            if (missingPermissions.isNotEmpty()) {
                ActivityCompat.requestPermissions(this, missingPermissions.toTypedArray(), REQUEST_CODE_PERMISSIONS)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableBluetooth()
            } else {
                Toast.makeText(this, "Permissão de Bluetooth necessária para habilitar.", Toast.LENGTH_SHORT).show()
            }
        }
    }



    companion object {
        private const val REQUEST_ENABLE_BT = 1
        private val MY_UUID = java.util.UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }
}
