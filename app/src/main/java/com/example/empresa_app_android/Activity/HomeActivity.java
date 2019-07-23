package com.example.empresa_app_android.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.empresa_app_android.Classes.Empresa_lista;
import com.example.empresa_app_android.Classes.RetroAdapter;
import com.example.empresa_app_android.Interface.Api;
import com.example.empresa_app_android.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://empresas.ioasys.com.br";
    private static final String API_VERSION = "v1";
    private Toolbar toolbar;
    private ListView listView;
    private MaterialSearchView searchView;
    private TextView textView;
    private RetroAdapter retroAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView = (TextView) findViewById(R.id.text_view1);
        listView = (ListView) findViewById(R.id.list_View);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView image = (TextView) view.findViewById(R.id.text_img);
                TextView description = (TextView) (TextView) view.findViewById(R.id.text_description);
                TextView name = (TextView) view.findViewById(R.id.text_name_empresa);

                Intent intent = new Intent(HomeActivity.this, EmpresaActivity.class);
                intent.putExtra("img", image.getText().toString());
                intent.putExtra("des", description.getText().toString());
                intent.putExtra("na", name.getText().toString());
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    getJSONResponse(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                textView.setVisibility(View.GONE);
                if (!newText.isEmpty()) {
                    getJSONResponse(newText);
                    listView.setVisibility(View.VISIBLE);
                } else {
                    listView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }

    private void getJSONResponse(String search) {
        Bundle extra = getIntent().getExtras();
        final String access_token = extra.getString("token");
        final String cliente = extra.getString("cliente");
        final String uid = extra.getString("id");

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("access-token", access_token)
                        .addHeader("client", cliente)
                        .addHeader("uid", uid)
                        .build();
                return chain.proceed(request);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api ioasysApi = retrofit.create(Api.class);

        Call<String> call = ioasysApi.getEnterprises(API_VERSION, search);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String jsonresponse = response.body();
                        writeListView(jsonresponse);

                    } else {
                        System.out.println("Lista vazio" + response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private void writeListView(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            ArrayList<Empresa_lista> modelListViewArrayList = new ArrayList<>();
            JSONArray dataArray = obj.getJSONArray("enterprises");

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataobj = dataArray.getJSONObject(i);
                JSONObject objectJson = new JSONObject(dataobj.getString("enterprise_type"));

                Empresa_lista modelListView = new Empresa_lista();
                modelListView.setEnterprise_name(dataobj.getString("enterprise_name"));
                modelListView.setPhoto(BASE_URL + dataobj.getString("photo"));
                modelListView.setCountry(dataobj.getString("country"));
                modelListView.setDescription(dataobj.getString("description"));
                modelListView.setEnterprise_type_name(objectJson.getString("enterprise_type_name"));
                modelListViewArrayList.add(modelListView);
            }
            retroAdapter = new RetroAdapter(this, modelListViewArrayList);
            listView.setAdapter(retroAdapter);
            retroAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

}
