package pl.gorniak.audiogramapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;


public class CalibrationActivity extends AppCompatActivity {


    AudioManager audioManager;
    float leftVolume = 0.03f;  //wspołczynnik skali
    float rightVolume = 0.03f; //wspołczynnik skali
    private static final String TAG = "CalibrationActivity";
    private MusicIntentReceiver myReceiver;
    MediaPlayer mp;
    Button buttonCalibration;
    Button buttonStart;
    Button buttonStop;
    TextView calibraionLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);
        myReceiver = new MusicIntentReceiver();
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);



        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        buttonCalibration = (Button) findViewById(R.id.calibrationButton);
        buttonStart = (Button) findViewById(R.id.startButton);
        buttonStop = (Button) findViewById(R.id.stopButton);
        calibraionLabel = (TextView) findViewById(R.id.calibration_label);


        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(maxVolume);
        seekBar.setProgress(currentVolume);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    @Override
    public void onResume() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(myReceiver, filter);
        super.onResume();
    }

    public void onCalibrationClick(View view) {

        mp = MediaPlayer.create(this, R.raw.testzz);
        mp.setVolume(leftVolume,rightVolume);
        mp.start();

    }

    public void onStopClick(View view) {
        mp.stop();
    }

    private String toString(float value) {
        return ""+value;
    }

    public void onHearSound(View view) {
        calibraionLabel.setText("Od teraz nie zmieniaj poziomu głośności!");
    }


    private class MusicIntentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        Log.d(TAG, "Headset is unplugged");
                        Toast.makeText(CalibrationActivity.this, "Unplugged", Toast.LENGTH_SHORT).show();
//                        buttonCalibration.setEnabled(false);
//                        buttonStart.setEnabled(false);
//                        buttonStop.setEnabled(false);
                        break;
                    case 1:
                        Log.d(TAG, "Headset is plugged");
                        Toast.makeText(CalibrationActivity.this, "Plugged", Toast.LENGTH_SHORT).show();
//                        buttonCalibration.setEnabled(true);
//                        buttonStart.setEnabled(true);
//                        buttonStop.setEnabled(true);
                        break;
                    default:
                        Log.d(TAG, "I have no idea what the headset state is");
                }
            }
        }
    }
    @Override
    public void onPause() {
        unregisterReceiver(myReceiver);
        super.onPause();
    }

}