package mx.rrc.metnumapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
//Probando el primer commit
    ImageView metodo1rodrigo;
    ImageView metodoCramerRay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Rodrigo
        //Listener para botón
        metodo1rodrigo = (ImageView) findViewById(R.id.metodo1rodrigo);
        metodo1rodrigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        //Raymundo



        //Chema



    }
    public void intentCramer(View view){
        Intent intent = new Intent(this, MetodoCramerRay.class);
        startActivity(intent);
    }
}
