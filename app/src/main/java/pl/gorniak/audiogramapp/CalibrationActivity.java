package pl.gorniak.audiogramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class CalibrationActivity extends AppCompatActivity {

    private static final String TAG = "CalibrationActivity";
    private MusicIntentReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);
        myReceiver = new MusicIntentReceiver();

    }
    @Override public void onResume() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(myReceiver, filter);
        super.onResume();
    }
    private class MusicIntentReceiver extends BroadcastReceiver {
        @Override public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        Log.d(TAG, "Headset is unplugged");
                        break;
                    case 1:
                        Log.d(TAG, "Headset is plugged");
                        break;
                    default:
                        Log.d(TAG, "I have no idea what the headset state is");
                }
            }
        }
    }
    @Override public void onPause() {
        unregisterReceiver(myReceiver);
        super.onPause();
    }

//    private boolean isHeadphonesPlugged(){
//        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//        @SuppressLint("WrongConstant") AudioDeviceInfo[] audioDevices = audioManager.getDevices(AudioManager.GET_DEVICES_ALL);
//        for(AudioDeviceInfo deviceInfo : audioDevices){
//            if(deviceInfo.getType()==AudioDeviceInfo.TYPE_WIRED_HEADPHONES
//                    || deviceInfo.getType()==AudioDeviceInfo.TYPE_WIRED_HEADSET){
//                return true;
//
//            }
//        }
//
//        return false;
//    }


}