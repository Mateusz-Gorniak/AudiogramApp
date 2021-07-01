package pl.gorniak.audiogramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }
    public void onGetGraph(View view) {
        Intent graphpage = new Intent(this, GraphActivity.class);
        startActivity(graphpage);
        //test workflow2
    }
}