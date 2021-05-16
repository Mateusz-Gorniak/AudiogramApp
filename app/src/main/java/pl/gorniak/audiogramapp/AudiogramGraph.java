package pl.gorniak.audiogramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AudiogramGraph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audiogram_graph);
    }

    public void backToMainPage(View view) {
        Intent graphpage = new Intent(AudiogramGraph.this, MainActivity.class);
        startActivity(graphpage);
    }
}