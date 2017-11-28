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

public class GaussSeidel extends AppCompatActivity{
    private EditText matrizTexto;
    private String entradaMatriz;
    private  int filasMatriz;
    private  int columnasMatriz;
    private EditText filas;
    private EditText columnas;
    private EditText aprox;
    private String aproxString;
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaussseidel);
        matrizTexto=(EditText) findViewById(R.id.TxtMatriz);
        filas= (EditText) findViewById(R.id.TxtFilas);
        columnas= (EditText) findViewById(R.id.TxtColumnas);
        resultado= (TextView) findViewById(R.id.txtResultado);
        resultado.setText("");
        resultado.setMovementMethod(new ScrollingMovementMethod());
        aprox= (EditText) findViewById(R.id.TxtAprox);

    }
    public void BtnCalcular(View view){
        entradaMatriz= matrizTexto.getText().toString();
        filasMatriz= Integer.parseInt(filas.getText().toString());
        columnasMatriz= Integer.parseInt(columnas.getText().toString());
        aproxString= aprox.getText().toString();
        if(checarMatriz(entradaMatriz)){
            resultado.setText(imprimirLista(gaussSeidel(generarMatriz(entradaMatriz,filasMatriz,columnasMatriz),generarLista(aproxString,filasMatriz))));
        }else{
            Toast.makeText(getApplicationContext(),"Ingrese datos válidos",Toast.LENGTH_SHORT).show();
        }

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

    public static double[] generarLista (String entradaAprox, int column){
        String[] listaValores= entradaAprox.split(" ");
        double[] lista = new double[column];
        for(int columna=0; columna<column;columna++){
            String valor=listaValores[columna];
            lista[columna]=Double.parseDouble(valor);
        }
        return lista;
    }

    public static String imprimirLista(double[]lista){
        String resultado= "";
        for(int columna=0;columna<lista.length;columna++){
            resultado+= String.format("%.4f",lista[columna]) +"  ";
            System.out.print(lista[columna] + "\t");
        }
        return resultado;
    }


    public static double[] gaussSeidel (double[][] matriz, double[] aprox){
        int ultimaColumna= matriz[0].length-1;
        //System.out.println("valor de la ultima columna: " + ultimaColumna);
        //double errorTotal=100;
        double xCalculada;
        double cons;
        double suma;
        //int countFor=0;
        int count=0;
        while(count<100){
            //errorTotal=1;
            //countFor=0;
            for(int fila=0; fila<matriz.length; fila++){
                //countFor++;
                suma=0;
                cons= matriz[fila][ultimaColumna];
                //PARTE DE LA SUMATORIA
                for(int k=0;k<ultimaColumna;k++){
                    if(fila!=k){
                        suma+=(matriz[fila][k])*(aprox[k]);
                    }
                }
                xCalculada=(cons-suma)/matriz[fila][fila];
                //errorTotal= errorTotal*(Math.abs((xCalculada-aprox[fila])/xCalculada)*100);
                aprox[fila]=xCalculada;
            }
            //System.out.println("IMPRIME EL COUNTFOR: " + countFor);
            //errorTotal=Math.pow(errorTotal, (double)(1/matriz.length));
            count++;
        }
        System.out.println("IMPRE EL COUNT DEL WHILE:" + count);
        return aprox;
    }

}
