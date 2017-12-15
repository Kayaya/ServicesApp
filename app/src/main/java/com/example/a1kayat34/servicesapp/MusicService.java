package com.example.a1kayat34.servicesapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.SurfaceView;

import java.io.File;

/**
 * Created by 1kayat34 on 15/12/2017.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener
{
    MediaPlayer mplayer;
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mplayer =  mediaPlayer;

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {


    }

    class MusicServiceBinder extends Binder{
        public MusicService getService(){
            return MusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicServiceBinder();
    }

    //Methods to control the player
    public void play(){
        if(mplayer != null){
            mplayer.start();
        }
    }

    public void pause(){
        if(mplayer != null){
            mplayer.pause();
        }

    }

    public void rewind(){
        if(mplayer != null){
            mplayer.seekTo(0);
        }
    }

}
