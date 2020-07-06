package com.cntt.cnhp.scadamobile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends Activity implements TextWatcher,
        CompoundButton.OnCheckedChangeListener{

    EditText username ;
    EditText password;
    CheckBox rem_userpass;
    Button btnLogin;
    Connection conn;
    public String z;

    SharedPreferences sharedPreferences;
    SharedPref config;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        config = new SharedPref(this);
        username  = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.pass);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        sharedPreferences = getSharedPreferences(PREF_NAME, this.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        rem_userpass = (CheckBox)findViewById(R.id.checkBox);

        if(sharedPreferences.getBoolean(KEY_REMEMBER, false))
            rem_userpass.setChecked(true);
        else
            rem_userpass.setChecked(false);
        username.setText(sharedPreferences.getString(KEY_USERNAME,""));
        password.setText(sharedPreferences.getString(KEY_PASS,""));

        username.addTextChangedListener(this);
        password.addTextChangedListener(this);
        rem_userpass.setOnCheckedChangeListener(this);


        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    username.setHint("");
                else
                    username.setHint("Tên đăng nhập");
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    password.setHint("");
                else
                    password.setHint("Mật khẩu");
            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    config.remove("lat");//editor.putString(KEY_PASS,"");
                    config.remove("lng");//editor.putString(KEY_USERNAME, "");
                    config.commit();

                    conn= Server.Connect();
                    if (conn == null) {
                        z = "Không thể kết nối với Server"; //Tiếng Việt :D
                    } else {

                        String query = "select * from dbo.ds_thanhvien where tendangnhap = '" + username.getText() + "' and matkhau = '" + password.getText() + "'";
                        Log.d("login: " , query);
                        ResultSet resultSet;
                        Statement stmt = conn.createStatement(); //blah blah blah
                        resultSet = stmt.executeQuery(query);
                        String user = String.valueOf(username.getText());


                        if(resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                        {
                            Toast.makeText(getApplicationContext(),
                                    "Đăng nhập thành công!",
                                    Toast.LENGTH_SHORT).show();

                           DanhSachThanhVien tv= new DanhSachThanhVien();
                           tv.setId(resultSet.getInt("thanhvien_id"));
                           tv.setThanhvien_name(resultSet.getString("thanhvien_name"));
                           tv.setDon_vi(resultSet.getInt("donvi"));
                           tv.setNhomquyen_id(resultSet.getInt("nhomquyen_id"));
                           int ms_tql = resultSet.getInt("ms_tql");
                           Log.d("ms_tql", String.valueOf(ms_tql));
                           tv.setMs_tql(ms_tql);
                           tv.setChuthich(resultSet.getString("chuthich"));
                           tv.setTendangnhap(resultSet.getString("tendangnhap"));
                           tv.setMatkhau(resultSet.getString("matkhau"));
                           int ms_chinhanh = resultSet.getInt("ms_chinhanh");
                           tv.setMs_chinhanh(ms_chinhanh);
                            Log.d("ms_chinhanh",String.valueOf(tv.getMs_chinhanh()));
                           tv.setPhutrachchinh(resultSet.getInt("phutrachchinh"));

                            if(ms_chinhanh == 0 ) {
                                Intent intent = new Intent(Login.this, ChiNhanh.class);

                                intent.putExtra("username", tv.getTendangnhap());
                                intent.putExtra("password", tv.getMatkhau());
                                intent.putExtra("nhomquyen_id", String.valueOf(tv.getNhomquyen_id()));
                                intent.putExtra("ms_tql", String.valueOf(tv.getMs_tql()));
                                intent.putExtra("ms_chinhanh", String.valueOf(tv.getMs_chinhanh()));
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(Login.this, MapsActivity.class);

                                intent.putExtra("username", tv.getTendangnhap());
                                intent.putExtra("password", tv.getMatkhau());
                                intent.putExtra("nhomquyen_id", String.valueOf(tv.getNhomquyen_id()));
                                intent.putExtra("ms_tql", String.valueOf(tv.getMs_tql()));
                                intent.putExtra("ms_chinhanh", String.valueOf(tv.getMs_chinhanh()));
                                startActivity(intent);
                            }


                            conn.close();//Đấm vỡ mồm SERVER xong thì phải băng bó cho nó.
                        }
                        else
                        {

                            Toast.makeText(getApplicationContext(),
                                    "Tài khoản hoặc mật khẩu không đúng, hãy kiểm tra lại.",
                                    Toast.LENGTH_SHORT).show();


                        }
                    }


                } catch (SQLException e) {

                    z = "Lỗi !";
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


        });

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
    }

    private void managePrefs(){
        if(rem_userpass.isChecked()){
            editor.putString(KEY_USERNAME, username.getText().toString().trim());
            editor.putString(KEY_PASS, password.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        }else{
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }
    }
}
