package mx.rrc.metnumapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//Probando el primer commit
    ImageView metodo1rodrigo;
    ImageView metodoCramerRay;
    public static ArrayList<Number> valoresX = new ArrayList<Number>();
    public static ArrayList<Number> valoresY = new ArrayList<Number>();
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.jingleballs);
        mediaPlayer.start();

    }

    public void intentCramer(View view){
        Intent intent = new Intent(this, MetodoCramerRay.class);
        startActivity(intent);
    }
    public void intentBisec(View view){
        Intent intent = new Intent(this, MetodoBisecRay.class);
        startActivity(intent);
    }

    public void intentGaussJordan(View view){
        Intent intent = new Intent(this, GaussJordan.class);
        startActivity(intent);
    }
}
