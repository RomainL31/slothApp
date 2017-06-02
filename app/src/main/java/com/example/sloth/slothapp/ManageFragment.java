package com.example.sloth.slothapp;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class ManageFragment extends Fragment implements View.OnClickListener {
    private static final int DISCOVERY_REQUEST = 1;
    private BluetoothAdapter BTAdapter;
    public static int REQUEST_BLUETOOTH = 1;

    public ManageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage, container, false);
    }

    BroadcastReceiver bluetoothState = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String stateExtra = BluetoothAdapter.EXTRA_STATE;
            int state = intent.getIntExtra(stateExtra, -1);
            String toastText = "";

            switch(state) {
                case (BluetoothAdapter.STATE_TURNING_ON):
                {
                    toastText = "Bluetooth turning on";
                    Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
                    break;
                }
                case (BluetoothAdapter.STATE_TURNING_OFF):
                {
                    toastText = "Bluetooth turning off";
                    Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        String scanModeChanged = BluetoothAdapter.ACTION_SCAN_MODE_CHANGED;
        String beDiscoverable = BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE;

        IntentFilter filter = new IntentFilter(scanModeChanged);
        //registerReceiver(bluetoothState, filter);
        startActivityForResult(new Intent(beDiscoverable), DISCOVERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DISCOVERY_REQUEST) {
            Toast.makeText(getContext(), "Discovery in progress", Toast.LENGTH_SHORT).show();
            //findDevices();
        }
    }

}