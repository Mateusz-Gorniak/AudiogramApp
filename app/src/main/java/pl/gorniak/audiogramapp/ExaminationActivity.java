package pl.gorniak.audiogramapp;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExaminationActivity extends AppCompatActivity {


    private static final int SAMPLE_RATE = 48000 ;
    int duration = 1;
    TextView frequencyTextView;
    TextView volumeTextView;
    Button yesButton;
    Button noButton;
    Button playButton;

    int frequency = 125;
    float volume = 10;//0-1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination);

        frequencyTextView = (TextView)findViewById(R.id.frequency);
        volumeTextView = (TextView)findViewById(R.id.volume);
        yesButton = (Button)findViewById(R.id.yes);
        noButton = (Button)findViewById(R.id.no);
        playButton = (Button)findViewById(R.id.play);

        updateText();
        yesButton.setEnabled(false);
        noButton.setEnabled(false);

    }

    private void updateText(){
        frequencyTextView.setText(String.valueOf(frequency));
        volumeTextView.setText(String.valueOf(volume));
    }
    public void gotoGraph(View view) {
        Intent graphpage = new Intent(ExaminationActivity.this, GraphActivity.class);
        startActivity(graphpage);
    }


    public void onAnswerYes(View view) {
        frequency+=100;
        volume=10;
        updateText();
    }

    public void onAnswerNo(View view) {
        volume+=5;
        updateText();
    }

    public void onPlay(View view) {
        yesButton.setEnabled(false);
        noButton.setEnabled(false);
        int numSamples = duration * SAMPLE_RATE;
        // tablica przechowująca próbki (short zajmuje 16 bitów)
        short sample[] = new short[numSamples];
        // tworzenie próbek
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = (short) (volume/100 * Math.sin(2 * Math.PI * i / (SAMPLE_RATE / frequency)) *
                    Short.MAX_VALUE);
        }
        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, sample.length * 2,
                AudioTrack.MODE_STATIC);
        audioTrack.write(sample, 0, sample.length);
        audioTrack.play();
        yesButton.setEnabled(true);
        noButton.setEnabled(true);
    }


}