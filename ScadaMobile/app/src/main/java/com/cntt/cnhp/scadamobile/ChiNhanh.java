package com.cntt.cnhp.scadamobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ChiNhanh extends Activity {

    TextView lblheader;
    ListView lstchinhanh;
    Connection conn;
    TextView lsttenchinhanh;
    String username;
    String nhomquyen_id = "";
    String ms_tql = "";
    String ms_chinhanh = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_nhanh);

        lblheader = (TextView) findViewById(R.id.lblheader);
        lstchinhanh = (ListView) findViewById(R.id.lstchinhanh);
        lsttenchinhanh = (TextView) findViewById(R.id.lbltenchinhanh);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        nhomquyen_id = intent.getStringExtra("nhomquyen_id");
        ms_tql = intent.getStringExtra("ms_tql");
        ms_chinhanh =  intent.getStringExtra("ms_chinhanh");

        try {
            conn = Server.Connect();
            String querycmd = "select id, ten from dbo.dm_chinhanh where id not in ( 10) ";
            Log.d("chi nhanh", querycmd);
            Statement statement = conn.createStatement();
            ResultSet rs;
            rs = statement.executeQuery(querycmd);

            List<String> dschinhanh = new ArrayList();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String ten= rs.getString("ten").toString();
                String hienthi = "["+id+ "]: " + ten;
                dschinhanh.add(hienthi);
            }

            String[] mStringArray = new String[dschinhanh.size()];
            mStringArray = dschinhanh.toArray(mStringArray);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,R.layout.listtemplate,R.id.lbltenchinhanh  ,mStringArray);


            lstchinhanh.setAdapter(arrayAdapter);

            lstchinhanh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String selectedItem = (String) adapterView.getItemAtPosition(i);
                    selectedItem = catchuoi(selectedItem);
                    Intent intent = new Intent(ChiNhanh.this, MapsActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("nhomquyen_id", nhomquyen_id);
                    intent.putExtra("ms_tql", ms_tql);
                    intent.putExtra("ms_chinhanh",selectedItem);
                    startActivity(intent);
                }
            });

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }








    }

    public String catchuoi(String chuoi){

        chuoi = chuoi.substring(chuoi.indexOf("[") + 1);
        chuoi = chuoi.substring(0, chuoi.indexOf("]"));

        return chuoi;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.danhbadiemdung){
            Intent intent = new Intent(ChiNhanh.this, Login.class);
            startActivity(intent);
        }

        else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
