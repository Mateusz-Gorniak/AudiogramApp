package pl.gorniak.audiogramapp;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ExaminationActivity extends AppCompatActivity {


    private static final int SAMPLE_RATE = 100000 ;
    int duration = 1;
    TextView frequencyTextView;
    TextView volumeTextView;
    Button yesButton;
    Button noButton;
    Button playButton;
    RadioButton rightRbutton;
    RadioButton leftRbutton;

    float scale_factor = 0.01f;
    //współczynnik skali użyty w kalibracji
    int frequency = 125;
//    float volume = 1;//0-1
    int decibels = 0;//poczatkowa wartosc
    int i = 0;
    int j = 0;
    int leftEarFlag = 1, rightEarFlag=0;
    Integer[] frequencies = { 125, 250, 500, 1000, 1500, 2000, 3000, 4000, 6000,};
    // 0dB, 10dB, 20dB, 30dB, 40dB, 50dB, 60dB, 70dB, 80dB, 90dB, 100dB,110dB
    float [] volume = {1,3.16f,10f,31.6f,100f,316.2f,1000f, 3162.2f, 10000f,31622.7f,100000f,316227.7f};
    int[] leftEar = new int[9];
    int[] rightEar = new int[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination);

        frequencyTextView = (TextView)findViewById(R.id.frequency);
        volumeTextView = (TextView)findViewById(R.id.volume);
        yesButton = (Button)findViewById(R.id.yes);
        noButton = (Button)findViewById(R.id.no);
        playButton = (Button)findViewById(R.id.play);
        //radio button
        rightRbutton = (RadioButton) findViewById(R.id.rbuttonrigth);
        leftRbutton = (RadioButton)findViewById(R.id.rbuttonleft);
        leftRbutton.setChecked(true);
        rightRbutton.setChecked(false);
        rightRbutton.setEnabled(false);

        updateText();
        yesButton.setEnabled(false);
        noButton.setEnabled(false);

    }

    private void updateText(){
        frequencyTextView.setText(String.valueOf(frequencies[i]));
        volumeTextView.setText(String.valueOf(decibels));
    }

    public void onAnswerYes(View view) {
        if(leftEarFlag == 1) {
            leftEar[i] = j * 10;
        }
        if(leftEarFlag == 0) {
            rightEar[i] = j * 10;
        }
        i++;
        decibels=0;
        j=0;
        if(frequencies[i] >= 6000){
            if(leftEarFlag == 0) {
                Intent result = new Intent(getBaseContext(), ResultActivity.class);
                result.putExtra("leftEar", leftEar);
                result.putExtra("rightEar", rightEar);
                startActivity(result);
                finish();
            }
            Toast.makeText(this, "Max frequency", Toast.LENGTH_SHORT).show();
            Toast.makeText(this,"Ear swap", Toast.LENGTH_LONG).show();
            leftRbutton.setChecked(false);
            leftRbutton.setEnabled(false);
            rightRbutton.setEnabled(true);
            rightRbutton.setChecked(true);
            i=0;
            j=0;
            leftEarFlag=0;
            rightEarFlag=1;
        }
        updateText();
        //parse do listy
    }

    public void onAnswerNo(View view) {
        decibels+=10;
        j++;
        if(decibels > 110){
            if(leftEarFlag == 1) {
                leftEar[i] = 110;
            }
            if(leftEarFlag == 0) {
                rightEar[i] = 110;
            }
            i++;
            Toast.makeText(this, "Max volume", Toast.LENGTH_SHORT).show();
            decibels =0;
            j=0;
        }
        updateText();
    }

    public void onPlay(View view) {
        yesButton.setEnabled(false);
        noButton.setEnabled(false);
        int numSamples = duration * SAMPLE_RATE;
        // tablica przechowująca próbki (short zajmuje 16 bitów)
        short sample[] = new short[numSamples];
        // tworzenie próbek
        frequency = frequencies[i];
        float volumes = volume[j];

        for (int i = 0; i < numSamples; ++i) {
            sample[i] = (short) (volumes*scale_factor * Math.sin(2 * Math.PI * i / (SAMPLE_RATE / frequency)) *
                    Short.MAX_VALUE);
        }
        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                SAMPLE_RATE, AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT, sample.length * 2,
                AudioTrack.MODE_STATIC);
        audioTrack.write(sample, 0, sample.length);
        audioTrack.setStereoVolume(leftEarFlag,rightEarFlag);
        audioTrack.play();
        yesButton.setEnabled(true);
        noButton.setEnabled(true);
    }



}