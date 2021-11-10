package com.smk9smg.latihan.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smk9smg.latihan.R;
import com.smk9smg.latihan.network.ServiceClient;
import com.smk9smg.latihan.network.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    //deklarasikan komponen layout yang mau dihubungkan dengan komponen login
    EditText etNIS,etPass;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //hubungkan komponen logc ke layoutnya jangan sampai tertukar
        etNIS = findViewById(R.id.et_nis_login);
        etPass = findViewById(R.id.et_password_login);
        btnLogin = findViewById(R.id.btn_login);

        //menempelkan fungsi pendengaran ke tombol login
        //ketika tombol di klik tombol akan merespon
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //munculkan loading
                ProgressDialog loading = new ProgressDialog(LoginActivity.this);
                loading.setMessage("Cek user di database ...");
                loading.show();

                //menangkap inputan user yang di layout
                //simpan di variabel nis dan password
                //yang bertipe string
                String nis = etNIS.getText().toString();
                String password = etPass.getText().toString();

                //mengirim nis dan pass melalui
                //network yang kita buat

                //buat service
                ServiceClient serviceClient = ServiceGenerator.createService(ServiceClient.class);
                //pilih jenis service
                //dan mau disimpan dimana respon dari servicenya
                Call<ResponseLogin> cekLogin = serviceClient.login(
                        "login",
                        ""+nis,
                        ""+password
                );

                //mengirim request ceklogin ke server
                cekLogin.enqueue(new Callback<ResponseLogin>() {
                    @Override
                    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                        //loading dihilangkan
                        loading.dismiss();

                        //tangkap hasil login di variabel hasil
                        String hasil = response.body().getHasil();
                        //cek apakah login sukses ato gagal
                        if (hasil.equals("sukses")){
                        //munculkan notif
                            Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseLogin> call, Throwable t) {
                        //loading dihilangkan
                        loading.dismiss();
                        Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}