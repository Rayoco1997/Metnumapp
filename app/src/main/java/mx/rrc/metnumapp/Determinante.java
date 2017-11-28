package mx.rrc.metnumapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by rodri on 28/11/2017.
 */

public class Determinante extends AppCompatActivity{
    private EditText matrizTexto;
    private String entradaMatriz;
    private  int filasMatriz;
    private  int columnasMatriz;
    private EditText filas;
    private EditText columnas;
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_determinante);
        matrizTexto=(EditText) findViewById(R.id.TxtMatriz);
        filas= (EditText) findViewById(R.id.TxtFilas);
        columnas= (EditText) findViewById(R.id.TxtColumnas);
        resultado= (TextView) findViewById(R.id.txtResultado);
        resultado.setText("");
        resultado.setMovementMethod(new ScrollingMovementMethod());
    }
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


    public void BtnCalcular(View view){
        entradaMatriz= matrizTexto.getText().toString();
        filasMatriz= Integer.parseInt(filas.getText().toString());
        columnasMatriz= Integer.parseInt(columnas.getText().toString());
        if(checarMatriz(entradaMatriz)){
            resultado.setText(String.format("%.4f",determinantes(generarMatriz(entradaMatriz, filasMatriz, columnasMatriz))));
        }else{
            Toast.makeText(getApplicationContext(),"Ingrese datos válidos",Toast.LENGTH_SHORT).show();
        }

    }

    public static double determinantes(double [][] matriz){
        int count=0;
        double deter=1;
        for(int pivote=0;pivote<matriz.length; pivote++){
            if(matriz[pivote][pivote]== 0){
                intercambiaCount(matriz, pivote, count);
            }
            for(int numerosPiv=0; numerosPiv<matriz.length-pivote-1;numerosPiv++){
                matriz[pivote+numerosPiv+1]= sumaArrayConArray(multiplicaArrayPorNumero(multiplicaArrayPorNumero(matriz[pivote], -1*(matriz[pivote+numerosPiv+1][pivote])),1/matriz[pivote][pivote]),matriz[pivote+numerosPiv+1]);
            }
            for(int numerosPiv=0;numerosPiv<pivote;numerosPiv++){
                matriz[pivote-numerosPiv-1]= sumaArrayConArray(multiplicaArrayPorNumero(multiplicaArrayPorNumero(matriz[pivote], -1*(matriz[pivote-numerosPiv-1][pivote])),1/matriz[pivote][pivote]),matriz[pivote-numerosPiv-1]);
            }
            deter= deter*matriz[pivote][pivote];
        }

        deter= deter*Math.pow(-1,(double)count);
        return deter;
    }

    public static void intercambiaCount(double[][] matriz, int posPivote, int count){
        while(matriz[posPivote][posPivote]==0){
            for(int renglon=posPivote+1;renglon<matriz.length;renglon++){
                if(matriz[renglon][posPivote]!=0){
                    intercambiaRenglon(matriz, posPivote, renglon);
                    count++;
                }
            }
        }
    }

    public static void intercambiaRenglon(double[][] matriz, int i, int j){
        double[] temp= matriz[i];
        matriz[i]= matriz[j];
        matriz[j]= temp;

    }

    public static double[] sumaArrayConArray(double[] array1,double[] array2){
        double[] renglon= new double[array1.length];
        for(int i=0; i<array1.length;i++){
            renglon[i]= array1[i]+array2[i];
        }
        return renglon;

    }

    public static double[] multiplicaArrayPorNumero(double[] array, double numero){
        double[]renglon= new double[array.length];
        for(int elemento=0; elemento<renglon.length; elemento++){
            renglon[elemento]= array[elemento]*numero;
        }
        return renglon;
    }

}
