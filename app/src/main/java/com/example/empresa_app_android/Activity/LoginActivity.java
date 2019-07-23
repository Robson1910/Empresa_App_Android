package com.example.empresa_app_android.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.empresa_app_android.Interface.Api;
import com.example.empresa_app_android.R;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity implements Callback<String> {

    Button login;
    EditText email, password;
    private static final String BASE_URL = "http://empresas.ioasys.com.br";
    private static final String API_VERSION = "v1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.btnLogin);
        email = (EditText) findViewById(R.id.inputEmail);
        password = (EditText) findViewById(R.id.inputPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Campo Vazio", Toast.LENGTH_LONG).show();
                } else if (password.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Campo Vazio", Toast.LENGTH_LONG).show();
                } else {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Api ioasysApi = retrofit.create(Api.class);
                    try {
                        JSONObject params = new JSONObject();
                        params.put("email", email.getText().toString());
                        params.put("password", password.getText().toString());
                        Call<String> response = ioasysApi.login_sign(API_VERSION, params.toString());
                        response.enqueue(LoginActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            String access_token = response.headers().get("access-token");
            String client = response.headers().get("client");
            String uid = response.headers().get("uid");

            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("token", access_token);
            intent.putExtra("cliente", client);
            intent.putExtra("id", uid);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Falha ao Entrar", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        Log.d("Falha", "Error");
        Log.d("Falha", t.toString());
    }
}
