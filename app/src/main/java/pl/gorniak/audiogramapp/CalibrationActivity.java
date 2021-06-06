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
    float leftVolume = 0.00316f; //-50dB
    float rightVolume = 0.00316f;//-50dB
    private static final String TAG = "CalibrationActivity";
    private MusicIntentReceiver myReceiver;
    MediaPlayer mp;
    TextView textViewleft;
    TextView textViewrigth;
    Button buttonCalibration;
    Button buttonHear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);
        myReceiver = new MusicIntentReceiver();
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);


        textViewleft = (TextView) findViewById(R.id.leftsound);
        textViewrigth = (TextView) findViewById(R.id.rightsound);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        buttonCalibration = (Button) findViewById(R.id.startButton);
        buttonHear = (Button) findViewById(R.id.hearSound);


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
        textViewrigth.setText(toString(rightVolume));
        textViewleft.setText(toString(leftVolume));
        mp.start();

    }

    public void onHearClick(View view) {
        mp.stop();
        leftVolume = leftVolume*10;
        rightVolume = rightVolume*10;
    }

    private String toString(float value) {
        return ""+value;
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
                        buttonHear.setEnabled(false);
                        break;
                    case 1:
                        Log.d(TAG, "Headset is plugged");
                        Toast.makeText(CalibrationActivity.this, "Plugged", Toast.LENGTH_SHORT).show();
                        buttonCalibration.setEnabled(true);
                        buttonHear.setEnabled(true);
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