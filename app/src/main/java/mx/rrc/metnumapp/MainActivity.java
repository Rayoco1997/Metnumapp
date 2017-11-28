package mx.rrc.metnumapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private RadioButton gauss,gaussJordan,gaussSeidel,determinantes,cramer;

    public static double[][] generarMatriz (String entrada, int filas, int columnas){
        String[] listaRenglones= entrada.split("\n");
        String[] valoresRenglon;
        double[][] matriz = new double[filas][columnas];

        for(int fila=0; fila<filas;fila++){
            String renglon=listaRenglones[fila];
            valoresRenglon= renglon.split(" ");
            for(int columna=0; columna<columnas;columna++){
                String valor=valoresRenglon[columna];
                matriz[fila][columna]=Double.parseDouble(valor);
            }
        }
        return matriz;
    }
    public static boolean checarMatriz(String s){
        boolean isValid=true;
        for(int i =0;i<s.length();i++){
            char c=s.charAt(i);
            if(!(Character.isDigit(c)||c=='.'||c==' '||c=='\n'||c=='-'))
                isValid=false;
        }
        return isValid;
    }

    public static String imprimirMatriz(double[][] matriz){
        String resultado= "";
        for(int fila=0; fila<matriz.length;fila++){
            for(int columna=0;columna<matriz[0].length;columna++){
                resultado+= String.format("%.4f",matriz[fila][columna]) +"  ";
                System.out.print(matriz[fila][columna] + "\t");
            }
            System.out.println();
            resultado+= "\n";
        }
        return resultado;

    }
    public void intentCramer(View view){
        Intent intent = new Intent(this, MetodoCramerRay.class);
        startActivity(intent);
    }
    public void intentBisec(View view){
        Intent intent = new Intent(this, MetodoBisecRay.class);
        startActivity(intent);
    }
}
