package mx.rrc.metnumapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

public class Graficas extends AppCompatActivity {

    private XYPlot mySimpleXYPlot;

    public Graficas() {
        // Required empty public constructor
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
        crearGrafica();
    }
    public void crearGrafica(){
        // Creamos dos arrays de prueba. En el caso real debemos reemplazar


        // Añadimos Línea Número UNO:
        XYSeries series1 = new SimpleXYSeries(
                MainActivity.valoresX,  // ValoresX
                MainActivity.valoresY, // ValoresY
                "Función Evaluada"); // Nombre de la primera serie

        // Modificamos los colores de la primera serie
        LineAndPointFormatter series1Format = new LineAndPointFormatter(
                Color.rgb(0, 200, 0),                   // Color de la línea
                Color.rgb(0, 100, 0),                   // Color del punto
                Color.rgb(150, 190, 150), null);              // Relleno

        // Una vez definida la serie (datos y estilo), la añadimos al panel
        mySimpleXYPlot.addSeries(series1, series1Format);

    }

}
