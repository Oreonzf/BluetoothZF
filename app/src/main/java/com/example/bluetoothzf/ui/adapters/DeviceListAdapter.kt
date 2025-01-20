import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetoothzf.R

class DeviceListAdapter(
    private var devices: ArrayList<BluetoothDevice>,
    private val onClick: (BluetoothDevice) -> Unit
) : RecyclerView.Adapter<DeviceListAdapter.DeviceViewHolder>() {

    class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var deviceName: TextView = itemView.findViewById(R.id.device_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.device_list_item, parent, false)
        return DeviceViewHolder(view)
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = devices[position]
        holder.deviceName.text = device.name ?: "Dispositivo desconhecido"
        holder.itemView.setOnClickListener { onClick(device) }
    }

    override fun getItemCount(): Int = devices.size

    fun updateDevices(newDevices: List<BluetoothDevice>) {
        devices.clear()
        devices.addAll(newDevices)
        notifyDataSetChanged()
    }
}
