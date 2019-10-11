package github.bewantbe.audio_analyzer_for_android.Bluetooth;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import github.bewantbe.audio_analyzer_for_android.R;

/**
 * Created by valentid on 26/09/2019.
 */

public class BluetoothScanner {
    public interface OnBluetoothDeviceChosenListener{
        void onBluetoothDeviceChosen(BluetoothDevice chosenDevice);
    }

    public static final int BLUETOOTH_TURN_ON_REQUEST_CODE = 0;

    public static void promptForBluetoothDevice(Activity activity, final OnBluetoothDeviceChosenListener callback){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //make sure bluetooth exist
        if(bluetoothAdapter == null){
            Toast.makeText(activity, "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
        } else if (!bluetoothAdapter.isEnabled()) {
            //make sure bluetooth enabled
            askToTurnOnBluetooth(activity);
        }


        final BluetoothDevice[] devices;
        {
            final List<BluetoothDevice> _devices = new LinkedList<>();
            for (BluetoothDevice device : bluetoothAdapter.getBondedDevices()) _devices.add(device);
            devices = _devices.toArray(new BluetoothDevice[0]);
        }
        Arrays.sort(devices, new Comparator<BluetoothDevice>() {
            @Override
            public int compare(BluetoothDevice d1, BluetoothDevice d2) {
                return (d1.getName() + d1.getAddress()).compareTo(d2.getName() + d2.getAddress());
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.AppTheme_AlertDialog));
        builder.setTitle("Choose ValkA Vibrometer device");
//        builder.setNegativeButton("Cancel", (DialogInterface dialog, int id)->callback.onBluetoothDeviceChosen(null));
        builder.setSingleChoiceItems(new PairedDevicesAdapter(devices, activity), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.onBluetoothDeviceChosen(devices[i]);
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    static void askToTurnOnBluetooth(Activity activity){
        activity.startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), BLUETOOTH_TURN_ON_REQUEST_CODE);
    }
}
