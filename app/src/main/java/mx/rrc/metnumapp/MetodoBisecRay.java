package mx.rrc.metnumapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MetodoBisecRay extends AppCompatActivity {
    private Button upButton;
    private EditText funcion;
    private EditText puntoInicial;
    private EditText puntoFinal;
    private EditText error;
    private TextView resultados;
    private Toast toast;
    private Button graficar;
    private String res;

    public MetodoBisecRay() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodo_bisec_ray);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        upButton = findViewById(R.id.Aceptar);
        funcion = findViewById(R.id.Funcion);
        puntoInicial = findViewById(R.id.PuntoInicial);
        puntoFinal = findViewById(R.id.PuntoFinal);
        error = findViewById(R.id.Error);
        resultados = findViewById(R.id.Resultados);
        graficar = findViewById(R.id.Graficar);
        final Activity act=this;
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar(funcion.getText().toString())) {
                    res = biseccionCompleta(funcion.getText().toString(), Double.parseDouble(puntoInicial.getText().toString()),
                            Double.parseDouble(puntoFinal.getText().toString()), Double.parseDouble(error.getText().toString()));
                    toast = Toast.makeText(getApplicationContext(), "Raíces obtenidas", Toast.LENGTH_LONG);
                    toast.show();
                    resultados.setText(res);
                    resultados.setMovementMethod(new ScrollingMovementMethod());
                    hideSoftKeyboard(act);
                }
            }
        });
        graficar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(res != null)
                    cambiarPantalla();
                else{
                    toast = Toast.makeText(getApplicationContext(), "No hay función a graficar" , Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    private double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    private double biseccion(String funcion, double puntoInicial, double puntoFinal, double error){
        String sustitucionMitad, sustitucionInicial;
        double mitad = (puntoInicial + puntoFinal)/2;
        while((Math.abs((mitad-puntoInicial)/mitad)*100)>error){
            try {
                sustitucionMitad = funcion.replaceAll("x", "(" + Double.toString(mitad) + ")");

                if (eval(sustitucionMitad) == 0.0)
                    break;

                sustitucionInicial = funcion.replaceAll("x", "(" + Double.toString(puntoInicial) + ")");
                if ((eval(sustitucionMitad) * eval(sustitucionInicial)) < 0) {
                    puntoFinal = mitad;
                } else {
                    puntoInicial = mitad;
                }

                mitad = (puntoInicial + puntoFinal) / 2;
            }catch (Exception e){
                return mitad;
            }
        }
        return mitad;
    }

    private ArrayList<Double> intervalos(String funcion, double puntoInicial, double puntoFinal, double paso){
        ArrayList<Double> intervalos = new ArrayList<Double>();
        String sustitucionInicial, sustitucionFinal;
        for(double i = puntoInicial; i < puntoFinal; i+=paso){
            sustitucionInicial = funcion.replaceAll("x", "("+Double.toString(i)+")");
            sustitucionFinal = funcion.replaceAll("x", "("+Double.toString(i+paso)+")");
            if((eval(sustitucionInicial)*eval(sustitucionFinal)) < 0 || eval(sustitucionInicial) == 0.0){
                intervalos.add(i);
                intervalos.add(i+paso);
            }
        }
        return intervalos;
    }

    private String biseccionCompleta(String funcion, double puntoInicial, double puntoFinal, double error){
        funcion = funcion.replace("pi", "(3.1416)");
        funcion = funcion.replace("e", "(2.71828)");
        String raices = "";
        ArrayList<Double> intervalos = intervalos(funcion, puntoInicial, puntoFinal, 0.3);
        for(int i = 0, count = 1; i < intervalos.size(); i+=2, count++){
            raices += "Raíz "+count+": "+Double.toString(biseccion(funcion, intervalos.get(i), intervalos.get(i+1), error))
                    +"\n";
        }
        return raices;
    }

    private boolean validar(String funcion){
        try{
            funcion = funcion.replace("x", "(-2)");
            funcion = funcion.replace("pi", "(3.1416)");
            funcion = funcion.replace("e", "(2.71828)");
            eval(funcion);
            return true;
        } catch(Exception e){
            toast = Toast.makeText(getApplicationContext(), "Verificar escritura de la función", Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
    }

    private void cambiarPantalla(){
        generarPuntos();
        Intent intent = new Intent(this, Graficas.class);
        startActivity(intent);
    }

    private void generarPuntos() {
        String extra = funcion.getText().toString();
        extra = extra.replace("pi", "(3.1416)");
        extra = extra.replace("e", "(2.71828)");
        Double inferior = Double.parseDouble(puntoInicial.getText().toString());
        Double superior = Double.parseDouble(puntoFinal.getText().toString());
        MainActivity.valoresX.clear();
        MainActivity.valoresY.clear();
        String operaciones;
        Double resultado;
        for(Double i = inferior; i <= superior; i+=0.1){
            operaciones = extra.replaceAll("x", "(" + Double.toString(i) + ")");
            try{
                resultado = eval(operaciones);
                MainActivity.valoresX.add(i);
                MainActivity.valoresY.add(resultado);
                Log.v(Double.toString(i), Double.toString(resultado));
            } catch (Exception e){
                Log.v("Error","Error");
            }
        }
    }

}
