package com.example.empresa_app_android.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.empresa_app_android.R;

public class EmpresaActivity extends AppCompatActivity {

    ImageView imageView, logo_empresa;
    TextView name_empresa, descricao_empresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);

        imageView = (ImageView) findViewById(R.id.imageView_back);
        logo_empresa = (ImageView) findViewById(R.id.imageView_logo);
        name_empresa = (TextView) findViewById(R.id.textview_empresa);
        descricao_empresa = (TextView) findViewById(R.id.textview_descricao);

        Bundle extra = getIntent().getExtras();
        String image = extra.getString("img");
        String description = extra.getString("des");
        String name = extra.getString("na");

        name_empresa.setText(name);
        Glide.with(this).load(image).into(logo_empresa);
        descricao_empresa.setText(description);
    }
}
