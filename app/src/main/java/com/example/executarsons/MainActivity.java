package com.example.executarsons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    SeekBar seekVolume;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bach);
        inicializarSeekbar();
    }


    private void inicializarSeekbar() {
        seekVolume = findViewById(R.id.seekVolume);

        //Recuperar as informacoes do volume do usuario, volume maximo, atual, audio manager.
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //recuperar valores maximo e atual
        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        //volume maximo na seekbar
        seekVolume.setMax(volumeMaximo);
        //configurar progresso atual
        seekVolume.setProgress(volumeAtual);

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void executarSom(View view) {
        //verificar se existe
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void pausarSom(View view) {
        //saber se esta sendo executada
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void pararSom(View view) {
        //verificar se a musica esta sendoo executada
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            //parar e em seguida executar
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bach);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            //liberar recursos de media, remove na memoria, nao deixar a musica na memoria
            mediaPlayer.release();
            //media player nulo para nao ocupar musica
            mediaPlayer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //verificar se existe
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
}