package com.cntt.cnhp.scadamobile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.jjoe64.graphview.GraphView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Connection conn;
    private Marker mp;
    private Circle circle;
    private Dialog dialog;

    TextView edtTentram;
    TextView edtTql;
    TextView edtTrangthai;
    TextView edtthoigiansuco;
    TextView edttongluuluong;
    TextView edtluluong1;
    Button btnGraph;
    String maxtime = "";

    LatLng toado_zoom;

    String username;
    String nhomquyen_id = "";
    String ms_tql = "";
    String ms_chinhanh = "";
    IconGenerator generator ;
    Bitmap icon;

    Timer timer;


    ArrayList<DanhMucTram> arrTD = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        nhomquyen_id = intent.getStringExtra("nhomquyen_id");
        ms_tql = intent.getStringExtra("ms_tql");
        ms_chinhanh =  intent.getStringExtra("ms_chinhanh");
        getdanhsachtram();

    }

    public  void getdanhsachtram(){
        try {
            conn = Server.Connect();

            if (conn != null) {
                ResultSet resultset;
                String querymaxtime = "select Max(thoi_gian) maxtime from Nhat_Ky_Ngay";
                Statement stmt = conn.createStatement();
                resultset = stmt.executeQuery(querymaxtime);
                while (resultset.next()) {
                    maxtime = resultset.getString("maxtime");

                }


                int tql = Integer.parseInt(ms_tql);
                int chinhanh = Integer.parseInt(ms_chinhanh);
                String dk ="";
                if(tql != 0) {
                    dk = " where  tst.Id_Tql = '"+tql+"'";
                }else {
                    dk = "where tst.id_chinhanh = '"+ms_chinhanh+"'";
                }

                String query = "select distinct dt.id id , dt.ten ten, dt.dia_chi dia_chi, nhamay_duongong nm_do, dt.id_tql id_tql, dt.toa_do toa_do, " +
                        " dt.stt stt   , sc.Trang_Thai trang_thai, sc.Thoi_Gian thoi_gian" +
                        " ,tst.id_chinhanh id_chinhanh" +
                        " from dm_tram dt" +
                        " left join Su_Co sc  on dt.Id = sc.Id_Tram" +
                        " left join dm_tql tql on dt.id_tql = tql.id" +
                        " left join ThongSo_Tram tst on dt.id = tst.Id_Tram ";
                // " where tst.id_chinhanh = '"+ms_chinhanh+"'";
                query +=   dk;

                Log.d("map_toado", query);

                resultset = stmt.executeQuery(query);

                if(resultset == null){
                    Toast.makeText(getApplicationContext(),
                            "Không có dữ liệu!",
                            Toast.LENGTH_SHORT).show();
                   /* Intent intent1 = new Intent(MapsActivity.this, Login.class);
                    intent1.putExtra("username", username);
                    intent1.putExtra("nhomquyen_id", nhomquyen_id);
                    intent1.putExtra("ms_tql", ms_tql);
                    intent1.putExtra("ms_chinhanh",ms_chinhanh);
                    startActivity(intent1);*/

                }else {
                    while (resultset.next()) {
                        DanhMucTram dmt = new DanhMucTram();
                        dmt.setId(resultset.getInt("id"));
                        dmt.setTen(resultset.getString("ten"));
                        dmt.setDia_chi(resultset.getString("dia_chi"));
                        dmt.setNm_do(resultset.getInt("nm_do"));
                        dmt.setId_tql(resultset.getInt("id_tql"));

                        String toa_do = resultset.getString("toa_do");
                        String vi_do = "";
                        String kinh_do = "";
                        if (null != toa_do && !toa_do.equals("")) {

                            String[] arStr = toa_do.split("\\,");
                            vi_do = arStr[0].trim();
                            kinh_do = arStr[1].trim();
                        }
                        dmt.setVido(Double.parseDouble(vi_do));
                        dmt.setKinhdo(Double.parseDouble(kinh_do));
                        dmt.setStt(resultset.getInt("stt"));
                        int tt = resultset.getInt("trang_thai");
                        dmt.setTrang_thai(tt);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        dmt.setThoi_gian(resultset.getString("thoi_gian"));

                        //Log.d("thơi_gian", String.valueOf(resultset.getDate("thoi_gian")));

                        arrTD.add(dmt);

                    }
                }
                //Log.d("map_arrsize", String.valueOf(arrTD.size()));





            }

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




    @Override
    protected void onResume() {
        super.onResume();
        /*timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                //LatLng toado = mMap.getCameraPosition().target;
                //LatLng toado = mMap.getProjection().getVisibleRegion().latLngBounds.getCenter();
                Intent intent = new Intent(MapsActivity.this, MapsActivity.class);
                intent.putExtra("tongsodiemdung",String.valueOf(arrTD.size()) );
                intent.putExtra("username",username );
                intent.putExtra("ms_tql",String.valueOf(ms_tql) );
                intent.putExtra("ms_chinhanh",String.valueOf(ms_chinhanh) );

                startActivity(intent);
            }
        }, 30000);*/


    }

    @Override
    protected void onPause() {
        super.onPause();
       //timer.cancel();
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng toa_do;
        LatLng llfirst;
        for (int i = 0 ; i< arrTD.size(); i++) {

           // arrTD.get(i).get


            toa_do = new LatLng(arrTD.get(i).getVido(), arrTD.get(i).getKinhdo());

            generator = new IconGenerator(MapsActivity.this);
            TextView text = new TextView(MapsActivity.this);
            String tenkh= arrTD.get(i).getTen().toString();

            String tenhienthi = xulytenkh(tenkh);

           //text.setText(tenhienthi+ "\n" + String.valueOf(arrTD.get(i).getId()));
            text.setText(tenhienthi);
            if(arrTD.get(i).getTrang_thai()==2){
                //bình thường
                generator.setStyle(IconGenerator.STYLE_GREEN);
            }else if (arrTD.get(i).getTrang_thai()==0) {
                //mất kết nối
                generator.setStyle(IconGenerator.STYLE_ORANGE);
            }else {
                //mất điện
                generator.setStyle(IconGenerator.STYLE_RED);
            }

            generator.setContentView(text);
            icon = generator.makeIcon();


            Marker maker = mMap.addMarker(new MarkerOptions()
                    .title(arrTD.get(i).getTen().toString() )
                    .position(toa_do)
                    .draggable(true)
                    .snippet(String.valueOf(arrTD.get(i).getId()))
                    .icon(BitmapDescriptorFactory.fromBitmap(icon)));
            }
                     //   .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        if(arrTD.size() >0 )
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arrTD.get(0).getVido(), arrTD.get(0).getKinhdo()), 14));

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


                    showDialog(marker);






                return false;
            }
        });


    }



    public void showDialog(Marker maker) {

        dialog = new Dialog(MapsActivity.this);
        dialog.setTitle("Scada");
        dialog.setContentView(R.layout.activity_popup_marker);
        khaibaobien();
        getthongtinsucotram(conn, maker);
        dialog.show();
       /* btnGraph.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Dialog", "xxxxx");
                Intent intent = new Intent(dialog.getContext(), GbraphShow.class);
                startActivity(intent);
            }
        }); */

    }

    public void khaibaobien(){
       edtTentram = (EditText) dialog.findViewById(R.id.edtTentram);
       edtTql = (EditText) dialog.findViewById(R.id.edtTql);
       edtTrangthai =(EditText) dialog.findViewById(R.id.edtTrangthai);
        edtthoigiansuco = (EditText) dialog.findViewById(R.id.edtthoigiansuco);
        edttongluuluong =(EditText) dialog.findViewById(R.id.edttongluuluong);
        btnGraph = (Button) dialog.findViewById(R.id.graphView);
        //edtluluong1 =(EditText) dialog.findViewById(R.id.edtluluong1);

    }



    public void getthongtinsucotram (Connection conn, Marker marker) {
        String ms_tram = marker.getSnippet();
        int id_tram = Integer.parseInt(ms_tram);

        try {

            conn = Server.Connect();

            if (conn != null) {


                ResultSet resultset;
                String query = "select bt.Id, bt.ten_tram, bt.Id_Ts Id_Ts, bt.ten_thongso, bt.Toa_Do, bt.donvi,nk.Gia_Tri, nk.Thoi_Gian ,s.Trang_Thai as trang_thai, s.Thoi_Gian as ThoiGianSuco, s.id as id_suco, " +
                        "   xl.suco, xl.ket_thuc, bt.Ten_tql from (  select  tr.Id, tr.Ten as ten_tram, tr.Toa_Do, tq.Ten as Ten_tql, ts.Id as Id_Ts, ts.Ma as ten_thongso, ts.Don_Vi  as donvi, tst.Id_tql as Id_tql, tst.Id_ChiNhanh   from dm_Tram tr " +
                        "    left join ThongSo_Tram tst on tr.Id = tst.Id_Tram left join Dm_ThongSo ts on ts.Id=tst.Id_ThongSo left join Dm_Tql tq on tq.Id= tst.Id_Tql ) bt  left join (select Id_Tram, Trang_Thai, Thoi_Gian, id " +
                        "      from Su_Co where Thoi_Gian = (select Thoi_Gian from Su_co where Id= (select MAX(ID) from Su_Co))) s on s.Id_Tram=bt.Id left join xulysuco xl on s.id= xl.id_suco " +
                        "   left join (select Id_Tram, Gia_Tri, Thoi_Gian, Id_ThongSo from Nhat_Ky_Ngay where Thoi_Gian = (SELECT DATEADD(minute, -5, (select Max (Thoi_Gian) from Nhat_Ky_Ngay)))) nk  on nk.Id_ThongSo=bt.Id_Ts and bt.Id = nk.Id_Tram " +
                        "   where bt.Id='"+ms_tram+"'" +
                        " order by ten_thongso desc " +
                        "   --and bt.Id_ChiNhanh='1'" ;
                Log.d("GoogleMapActivity", query);
                Statement stmt = conn.createStatement();
                resultset = stmt.executeQuery(query);
                ArrayList<String> arrTs = new ArrayList<>();
                String s = "";

                while (resultset.next()) {
                   String tents = resultset.getString("ten_thongso");
                   String Id_Ts = resultset.getString("ten_thongso");
                   tents = tents.trim();

                  /* if(tents.length()==3){
                       tents+="  ";
                   }else if(tents.length()==4){
                       tents+="  ";
                   }else if(tents.length()==5){
                       tents+=" ";
                   }*/
                   String giatri = resultset.getString("gia_tri");
                    String donvi = resultset.getString("donvi");
                   if("null".equals(giatri)||"".equals(giatri)||null==giatri){
                        String truyvan = "select gia_tri from  Nhat_Ky_Ngay nkn " +
                                " where Thoi_Gian = (SELECT DATEADD(minute, -5, (select Max (Thoi_Gian) from Nhat_Ky_Ngay))) " +
                                " and nkn.Id_ThongSo = '"+Id_Ts+"' and Id_Tram = '"+ms_tram+"'";
                       Log.d("GoogleMapActivity1", query);
                       ResultSet resultset1;
                       Statement stmt1 = conn.createStatement();
                       resultset1 = stmt1.executeQuery(query);
                       while (resultset1.next()) {
                            giatri = resultset1.getString("gia_tri");

                       }
                   }


                   s+=tents+ ":      " + giatri+ "      " + donvi + "\n";

                }
                String query1 = "select trang_thai from Su_Co where Id_Tram = '"+id_tram+"'";
                resultset = stmt.executeQuery(query1);
                int tt = 2;
                while (resultset.next()) {
                    tt = resultset.getInt("trang_thai");

                }


                if(tt==2){
                    //bình thường
                    edttongluuluong.setTextColor(Color.parseColor("#26c926"));
                }else if (tt==0) {
                    //mất kết nối
                    edttongluuluong.setTextColor(Color.parseColor("#F06D2F"));
                }else {
                    //mất điện
                    edttongluuluong.setTextColor(Color.RED);
                }

                edttongluuluong.setText(s);
                Log.d("thongso",s);

            }

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



        for(int i = 0; i<arrTD.size(); i++){

            if(arrTD.get(i).getId() == id_tram){
                edtTentram.setText(arrTD.get(i).getTen());
                edtTql.setText(String.valueOf(arrTD.get(i).getId_tql()));
                edtthoigiansuco.setText(String.valueOf(arrTD.get(i).getThoi_gian()));
                String trangthai = "";
                int tt = arrTD.get(i).getTrang_thai();
                if(tt==2){
                    trangthai = "Bình thường";
                }else if(tt==0){
                    trangthai = "Mất kết nối";
                }else if(tt==1){
                    trangthai = "Mất điện";
                }
                edtTrangthai.setText(trangthai);

            }
        }

    }

    public String xulytenkh(String tenkh){
        String temp = "";

        String arr[] = tenkh.trim().split("\\s+");
        for(int i = 0; i< arr.length; i++) {
            arr[i].trim();
            if(i< (arr.length -2)) {
                temp += arr[i].substring(0, 1)+ ".";
            }else {

                temp +=" "+arr[i].toString();
            }

        }

        return temp;
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        doubleBackToExitPressedOnce = true;


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
                myOwnBackPress();
            }
        }, 500);
    }

    private void myOwnBackPress() {
        if(!isFinishing()) {
            super.onBackPressed();
        }
    }
    private static final int TIME_DELAY = 1500;
    private static long back_pressed;

    /*@Override
    public void onBackPressed()
    {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        else {
            //super.onBackPressed();
            Toast.makeText(getBaseContext(), "Nhấn BACK 2 lần để thoát chương trình!", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.danhbadiemdung){
            Intent intent = new Intent(MapsActivity.this, Login.class);
            intent.putExtra("tongsodiemdung",String.valueOf(arrTD.size()) );
            startActivity(intent);
        }else if (item.getItemId() == R.id.trangthaitram){
            Intent intent = new Intent(MapsActivity.this, MapsActivity.class);
            intent.putExtra("tongsodiemdung",String.valueOf(arrTD.size()) );
            intent.putExtra("username",username );
            intent.putExtra("ms_tql",String.valueOf(ms_tql) );
            intent.putExtra("ms_chinhanh",String.valueOf(ms_chinhanh) );
            startActivity(intent);
        }

        else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }





}
