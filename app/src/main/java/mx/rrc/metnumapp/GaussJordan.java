package mx.rrc.metnumapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by rodri on 27/11/2017.
 */

public class GaussJordan extends AppCompatActivity {
    private EditText matrizTexto;
    private String entradaMatriz;
    private  int filasMatriz;
    private  int columnasMatriz;
    private android.widget.EditText filas;
    private EditText columnas;
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaussjordan);

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
    public void BtnCalcular(View view){
        entradaMatriz= matrizTexto.getText().toString();
        filasMatriz= Integer.parseInt(filas.getText().toString());
        columnasMatriz= Integer.parseInt(columnas.getText().toString());
        if(checarMatriz(entradaMatriz)){
            resultado.setText(imprimirMatriz(gaussJordan(generarMatriz(entradaMatriz,filasMatriz,columnasMatriz))));
        }else{
            Toast.makeText(getApplicationContext(),"Ingrese datos válidos",Toast.LENGTH_SHORT).show();
        }


    }
    private static double[][] cambioRen(double[][]matrix, int i){
        double[] temp;
        while(matrix[i][i]==0){
            for(int j=i+1;j<matrix.length;j++){
                if(matrix[j][i]!=0){
                    temp=matrix[i];
                    matrix[i]=matrix[j];
                    matrix[j]=temp;
                }
            }
        }
        return matrix;
    }
    public static double[][] gauss(double[][] matrix){
        double pivote=1;
        double numBajoPivote;
        for(int i=0;i<matrix.length-1;i++){
            for(int j=i+1;j<matrix.length;j++){
                if(matrix[i][i]==0){
                    matrix=cambioRen(matrix,i);
                }

                for(int num=0;num<matrix[i].length;num++){
                    if(num==i){
                        pivote=matrix[i][i];
                    }

                    matrix[i][num]=matrix[i][num]/pivote;
                }
                numBajoPivote= matrix[j][i];
                for(int num=0;num<matrix[j].length;num++){
                    matrix[j][num]=(matrix[i][num]*(-numBajoPivote))+matrix[j][num];
                }
                pivote= matrix[j][j];
                for(int k=0;k<matrix[j].length;k++){
                    matrix[j][k]=(matrix[j][k])/pivote;
                }
            }
        }
        return matrix;
    }
    public static double[][] gaussJordan(double[][] matrix){
        double numArribaPivote=1;
        double pivote=1;
        matrix=gauss(matrix);
        for(int j=0;j<matrix.length-1;j++){
            for(int i=j+1;i<matrix.length;i++){

                numArribaPivote=matrix[j][i];
                for(int num=0;num<matrix[j].length;num++){
                    matrix[j][num]=(matrix[i][num])*(-numArribaPivote)+matrix[j][num];
                }

                pivote=matrix[j][j];
                for(int num=0;num<matrix[j].length;num++){
                    matrix[j][num]=matrix[j][num]/pivote;
                }
            }
        }
        return matrix;
    }

}
