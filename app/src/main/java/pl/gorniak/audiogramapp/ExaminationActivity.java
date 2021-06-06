package pl.gorniak.audiogramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.View;

import github.nisrulz.zentone.ToneStoppedListener;
import github.nisrulz.zentone.ZenTone;

public class ExaminationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination);
    }


    public void gotoGraph(View view) {
        Intent graphpage = new Intent(ExaminationActivity.this, GraphActivity.class);
        startActivity(graphpage);
    }
}