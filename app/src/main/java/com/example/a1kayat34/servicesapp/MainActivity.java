package com.example.a1kayat34.servicesapp;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    ServiceConnection serviceConn;
    MusicService musicService;

    File mediaFile;
    MediaPlayer player;
    boolean prepared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_play = (Button)findViewById(R.id.play);
        Button btn_pause = (Button)findViewById(R.id.pause);
        Button btn_rewind = (Button)findViewById(R.id.rewind);


        btn_pause.setOnClickListener(this);
        btn_rewind.setOnClickListener(this);
        btn_play.setOnClickListener(this);
        //Not a class
        serviceConn = new ServiceConnection() {
            public void onServiceConnected(ComponentName n, IBinder binder) {
                MusicService.MusicServiceBinder musicBinder = 		(MusicService.MusicServiceBinder) binder;
                musicService = musicBinder.getService();

                //set up media player
                mediaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/3D_Printer_Slow_Edit_for_Android.mp3");
                player  = new MediaPlayer();
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    player.setDataSource(getApplicationContext(), Uri.fromFile(mediaFile));
                    player.setOnPreparedListener(musicService);
                    player.prepareAsync();

                } catch (Exception e) {
                    // e.printStackTrace();
                    new AlertDialog.Builder(MainActivity.this).setPositiveButton("OK", null).setMessage(e.toString()).show();
                }

            }
            public void onServiceDisconnected(ComponentName name){

            }
        };

        Intent startIntent = new Intent(this, MusicService.class);
        startService(startIntent);


        Intent bindIntent = new Intent(this, MusicService.class);
        bindService(bindIntent, serviceConn,  this.BIND_AUTO_CREATE);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.play){
            musicService.play();
        }
        else if(view.getId() == R.id.pause){
            musicService.pause();
        }
        else if(view.getId() == R.id.rewind){
            musicService.rewind();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        //unbindService(gpsServiceConn);
    }

}
/*
*
* Example of service see: http://github.com/nick1/opentrail
* */
