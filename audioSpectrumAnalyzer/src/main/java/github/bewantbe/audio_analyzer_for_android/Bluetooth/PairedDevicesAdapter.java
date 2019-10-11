package github.bewantbe.audio_analyzer_for_android.Bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import github.bewantbe.audio_analyzer_for_android.R;

/**
 * Created by valentid on 26/09/2019.
 */

class PairedDevicesAdapter implements ListAdapter {
    final BluetoothDevice[] devices;
    final Context context;
    public PairedDevicesAdapter(final BluetoothDevice[] devices, Context context){
        this.devices = devices;
        this.context = context;
    }
    public boolean areAllItemsEnabled() { return true; }
    public boolean isEnabled(int i) { return true; }
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {}
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {}
    public int getCount() {return devices.length;}
    public Object getItem(int i) {return devices[i];}
    public long getItemId(int i) {return i;}
    public boolean hasStableIds() {return true;}
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.double_string_list_item, null);
        }
        ((TextView)view.findViewById(R.id.primary_text_view)).setText(devices[i].getName());
        ((TextView)view.findViewById(R.id.secondary_text_view)).setText(devices[i].getAddress());
        return view;
    }
    public int getItemViewType(int i) { return 0; }
    public int getViewTypeCount() { return 1; }
    public boolean isEmpty() { return devices.length == 0; }
}
