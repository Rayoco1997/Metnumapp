package mx.rrc.metnumapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MetodoBairRay extends AppCompatActivity {

    private Button calcular;
    private EditText valores;
    private TextView resultados;
    private ArrayList<Double> coeficientes;
    private TextView titulo;
    private TextView instrucciones;
    private TextView resTitle;
    private Toast toast;
    private EditText grado;

    private double[] a = new double[20];
    private double[] b = new double[20];
    private double[] c = new double[20];
    private ArrayList<Double> raices;
    private int n;

    public MetodoBairRay(){
        //Constructor vacio requerido
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodo_bair_ray);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calcular = (Button) findViewById(R.id.Calcular);
        valores = (EditText) findViewById(R.id.Coeficientes);
        grado = (EditText) findViewById(R.id.Grado);
        resultados = (TextView) findViewById(R.id.Resultados);
        resTitle = (TextView) findViewById(R.id.ResTitle);
        titulo = (TextView) findViewById(R.id.Titulo);
        instrucciones = (TextView) findViewById(R.id.Instrucciones);
        final Activity act=this;
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                resultados.setText("");
                readInput();
                solve();
                valores.setText("");
                grado.setText("");
                hideSoftKeyboard(act);
                } catch(Exception e){
                    toast = Toast.makeText(getApplicationContext(), "Error en los valores ingresados, intente de nuevo" , Toast.LENGTH_LONG);
                    valores.setText("");
                    grado.setText("");
                    hideSoftKeyboard(act);
                    toast.show();
                }
            }
        });
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void readInput(){
        n = Integer.parseInt(grado.getText().toString());

        String[] tokens = valores.getText().toString().split(",");

        for (int i = 0; i < n + 1; i++)
            a[n-i] = Double.parseDouble(tokens[i]);
    }

    public void solve()
    {
        int i, j;
        double r1, r2, du, dv, u, v, r, dr;
        double sq, det, nu, nv, error;
        double epsilon = 1e-8;
        while (n > 3) {
            u = 0;
            v = 0;
            error = 1;
            c[n] = b[n] = a[n];

            while (error > epsilon) {
                b[n-1] = a[n-1] + u * b[n];
                c[n-1] = b[n-1] + u * c[n];

                for (i = n - 2; i > 0; i--) {
                    b[i] = a[i] + u * b[i+1] + v * b[i+2];
                    c[i] = b[i] + u * c[i+1] + v * c[i+2];
                }

                b[0] = a[0] + u * b[1] + v * b[2];

                det = (c[2] * c[2]) - c[1] * c[3];

                nu = b[0] * c[3] - b[1] * c[2];
                nv = b[1] * c[1] - b[0] * c[2];

                if (det == 0) {
                    du = dv = 1;
                } else {
                    du = nu / det;
                    dv = nv / det;
                }

                u += du;
                v += dv;

                error = Math.sqrt(du * du + dv * dv);
            }
            System.out.println("1" + " " + (-1*u) + " "+(-1*v));
            for(int t=n-2;t>=0;t--){
                System.out.print(b[t]+" ");
            }
            System.out.println();
            sq = u * u + 4 * v;

            if (sq < 0) {
                r1 = u/2;
                r2 = Math.sqrt(-sq)/2;

                resultados.append("Raiz: " + r1 + " + " + r2 + "i\n");
                resultados.append("Raiz: " + r1 + " - " + r2 + "i\n");
            } else {
                r1 = u/2 + Math.sqrt(sq)/2;
                r2 = u/2 - Math.sqrt(sq)/2;

                resultados.append("Raiz: " + r1+"\n");
                resultados.append("Raiz: " + r2+"\n");
            }

            n -= 2;

            for (i = 0; i < n + 1; i++)
                a[i] = b[i+2];
        }

        if (n == 3) {
            r = 0;
            error = 1;
            b[n] = a[n];

            while (error > epsilon) {
                b[2] = a[2] + r * b[3];
                b[1] = a[1] + r * b[2];
                b[0] = a[0] + r * b[1];

                double d = a[1] + (2 * a[2] * r) + (3 * a[3] * r * r);

                if (d == 0)
                    dr = 1;
                else
                    dr = -b[0] / d;

                r -= dr;
                error = Math.abs(dr);
            }

            resultados.append("Raiz: " + r+"\n");
            n--;

            for (i = 0; i < n + 1; i++)
                a[i] = b[i + 1];
        }


        if (n == 2) {
            u = -a[1] / a[2];
            v = -a[0] / a[2];
            sq = u * u + 4 * v;

            if (sq < 0) {
                r1 = u/2;
                r2 = Math.sqrt(-sq)/2;

                resultados.append("Raiz: " + r1 + " + " + r2 + "i\n");
                resultados.append("Raiz: " + r1 + " - " + r2 + "i\n");
            } else {
                r1 = u/2 + Math.sqrt(sq)/2;
                r2 = u/2 - Math.sqrt(sq)/2;

                resultados.append("Raiz: " + r1+"\n");
                resultados.append("Raiz: " + r2+"\n");
            }
        } else if (n == 1) {
            System.out.println(-a[0] / a[1]);
        }
    }


}
