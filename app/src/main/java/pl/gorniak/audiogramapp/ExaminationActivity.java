package pl.gorniak.audiogramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;

public class ExaminationActivity extends AppCompatActivity {

    private static final int SAMPLE_RATE = 48000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination);

        // liczba próbek
        int  duration = 1;
        double amplitude = 1;
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
}