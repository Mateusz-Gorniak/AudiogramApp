package pl.gorniak.audiogramapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import static pl.gorniak.audiogramapp.R.id.nav_about_application;
import static pl.gorniak.audiogramapp.R.id.nav_audiogram_graph;
import static pl.gorniak.audiogramapp.R.id.show_menu;

public class MainActivity extends AppCompatActivity {


    ImageView menu_show;
    MenuBuilder menuBuilder;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menu_show = (ImageView) findViewById(show_menu);

        menuBuilder = new MenuBuilder(this);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu, menuBuilder);//this will show our menu list we add


        //Set item click listener
        menu_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPopupHelper optionMenu = new MenuPopupHelper(MainActivity.this,
                        menuBuilder,view);
                optionMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        if (item.getItemId() == nav_about_application) {
                            Intent info = new Intent(MainActivity.this, AboutProgramActivity.class);
                            startActivity(info);
                            return true;
                        }
                        if (item.getItemId() == nav_audiogram_graph) {
                            Intent info = new Intent(MainActivity.this, AudiogramGraph.class);
                            startActivity(info);
                            return true;
                        }
                        return false;
                    }
                    @Override
                    public void onMenuModeChange(@NonNull MenuBuilder menu) {
                        //empty
                    }
                });

                optionMenu.show();
            }
        });

    }

    public void onCalibrationClick(View view) {
        Intent calibrationPage = new Intent(MainActivity.this,CalibrationActivity.class);
        startActivity(calibrationPage);
    }

    public void onStartClick(View view) {
        Intent examinationPage = new Intent(MainActivity.this,ExaminationActivity.class);
        startActivity(examinationPage);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}