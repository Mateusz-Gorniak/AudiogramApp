package pl.gorniak.audiogramapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CalibrationActivity extends AppCompatActivity {

    private static final int SAMPLE_RATE = 48000;
    AudioManager audioManager;
    Button buttonCalibration;
    private static final String TAG = "CalibrationActivity";
    private MusicIntentReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);
        myReceiver = new MusicIntentReceiver();
        buttonCalibration = (Button) findViewById(R.id.startButton);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);


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

    public void onCalibrationClick(View view) throws InterruptedException {
        for (int i = 0; i<10;i++){
        soundGenerate();
        Thread.sleep(1_000);
        }

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
                        buttonCalibration.setEnabled(false);
                        break;
                    case 1:
                        Log.d(TAG, "Headset is plugged");
                        Toast.makeText(CalibrationActivity.this, "Plugged", Toast.LENGTH_SHORT).show();
                        buttonCalibration.setEnabled(true);
                        break;
                    default:
                        Log.d(TAG, "I have no idea what the headset state is");
                }
            }
        }
    }

    public void soundGenerate() throws InterruptedException {

        // liczba próbek
        int  duration = 1;
        float amplitude = 0.56f;
        int frequency = 1000;
        int numSamples = duration * SAMPLE_RATE;
        // tablica przechowująca próbki (short zajmuje 16 bitów)
        short sample[] = new short[numSamples];
        // tworzenie próbek



        for (int i = 0; i < numSamples; ++i) {
            sample[i] = (short) (amplitude * Math.sin(2 * Math.PI * i / (SAMPLE_RATE / frequency)) *
                    Short.MAX_VALUE);
        }
        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, sample.length * 2,
                AudioTrack.MODE_STATIC);
        audioTrack.write(sample, 0, sample.length);
        audioTrack.play();
    }

    @Override
    public void onPause() {
        unregisterReceiver(myReceiver);
        super.onPause();
    }

}