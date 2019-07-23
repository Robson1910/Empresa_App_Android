package com.example.empresa_app_android.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.empresa_app_android.R;
import com.master.glideimageview.GlideImageView;

import java.util.ArrayList;

public class RetroAdapter extends ArrayAdapter<Empresa_lista> {
    private ArrayList<Empresa_lista> empresas;
    private Context context;

    public RetroAdapter(Context c, ArrayList<Empresa_lista> objects) {
        super(c, 0, objects);
        this.context = c;
        this.empresas = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if (empresas != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_empresas, parent, false);

            TextView textViewNome = (TextView) view.findViewById(R.id.text_name_empresa);
            TextView textViewNegocio = (TextView) view.findViewById(R.id.text_negocio);
            TextView textViewPais = (TextView) view.findViewById(R.id.text_country);
            TextView textViewInforme = (TextView) view.findViewById(R.id.text_description);
            TextView textRecuperar = (TextView) view.findViewById(R.id.text_img);
            GlideImageView imageEmpresa = (GlideImageView) view.findViewById(R.id.image_upload);

            Empresa_lista empresa2 = empresas.get(position);
            textViewNome.setText(empresa2.getEnterprise_name());
            textViewNegocio.setText(empresa2.getEnterprise_type_name());
            textViewPais.setText(empresa2.getCountry());
            textViewInforme.setText(empresa2.getDescription());
            imageEmpresa.loadImageUrl(empresa2.getPhoto());
            textRecuperar.setText(empresa2.getPhoto());
        }
        return view;
    }
}