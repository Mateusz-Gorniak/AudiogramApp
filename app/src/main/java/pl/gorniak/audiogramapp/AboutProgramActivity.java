package pl.gorniak.audiogramapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AboutProgramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void backToMainPage(View view) {
        Intent info = new Intent(AboutProgramActivity.this, MainActivity.class);
        startActivity(info);
    }
}



