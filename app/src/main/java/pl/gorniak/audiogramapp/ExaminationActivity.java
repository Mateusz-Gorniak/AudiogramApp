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


    private static final int SAMPLE_RATE = 48000 ;
    int duration = 1;
    TextView frequencyTextView;
    TextView volumeTextView;
    Button yesButton;
    Button noButton;
    Button playButton;
    RadioButton rightbutton;
    RadioButton leftbutbutton;

    float
    int frequency = 125;
//    double gain = Math.pow(10,(10/20));
    float volume = 1;//0-1
    int decibels = 10;//poczatkowa wartosc
// 1, 0.3, 0.03, 0.003, 0.0003, 0,00003
    int i = 0;
    int j = 0;
    Integer[] frequencies = { 125, 250, 500, 1000, 1500, 2000, 3000, 4000, 6000, 8000, 10000};
    float[] coeffs ={0.00003f, 0.0003f, 0.003f, 0.03f,0.3f,1f};
    float gain = coeffs[j];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination);

        frequencyTextView = (TextView)findViewById(R.id.frequency);
        volumeTextView = (TextView)findViewById(R.id.volume);
        yesButton = (Button)findViewById(R.id.yes);
        noButton = (Button)findViewById(R.id.no);
        playButton = (Button)findViewById(R.id.play);
        rightbutton = (RadioButton) findViewById(R.id.rbuttonrigth);
        leftbutbutton = (RadioButton)findViewById(R.id.rbuttonleft);

        updateText();
        yesButton.setEnabled(false);
        noButton.setEnabled(false);

    }

    private void updateText(){
        frequencyTextView.setText(String.valueOf(frequencies[i]));
        volumeTextView.setText(String.valueOf(decibels));
    }
    public void gotoGraph(View view) {
        Intent graphpage = new Intent(ExaminationActivity.this, GraphActivity.class);
        startActivity(graphpage);
    }


    public void onAnswerYes(View view) {
        i++;
        decibels=10;
        updateText();
    }

    public void onAnswerNo(View view) {
        decibels+=20;
        j++;
        if(decibels >=130){
            i++;
            Toast.makeText(this, "Max volume", Toast.LENGTH_SHORT).show();
            decibels =10;
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
        gain = coeffs[j];

        for (int i = 0; i < numSamples; ++i) {
            sample[i] = (short) (Math.pow(10,(20/10)) * Math.sin(2 * Math.PI * i / (SAMPLE_RATE / frequency)) *
                    Short.MAX_VALUE);
        }
        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                SAMPLE_RATE, AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT, sample.length * 2,
                AudioTrack.MODE_STATIC);
        audioTrack.write(sample, 0, sample.length);
        audioTrack.setStereoVolume(1,0);
        audioTrack.play();
        yesButton.setEnabled(true);
        noButton.setEnabled(true);
    }


}