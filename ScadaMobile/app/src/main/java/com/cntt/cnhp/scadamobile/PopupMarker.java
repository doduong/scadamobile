package com.cntt.cnhp.scadamobile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;

public class PopupMarker extends Activity {

    Button btnGraph;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_marker);

        btnGraph = (Button) findViewById(R.id.graphView);
        btnGraph.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Dialog", "Dialog");
                Intent intent = new Intent(PopupMarker.this, GraphShow.class);
                startActivity(intent);
            }
        });


    }
}
