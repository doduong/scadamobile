package com.cntt.cnhp.scadamobile;

import android.app.Activity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphShow extends Activity {

    LineGraphSeries<DataPoint> series;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_show);

        double x,y;
        x= 0.5;

        GraphView graph  =  (GraphView) findViewById(R.id.graphView);
        series = new LineGraphSeries<DataPoint>();
        for (int i =0 ; i<500; i++) {
            x= x+1;
            y= 5*x+1;

            series.appendData(new DataPoint(x,y), true, 500);
        }
        graph.addSeries(series);

    }
}
