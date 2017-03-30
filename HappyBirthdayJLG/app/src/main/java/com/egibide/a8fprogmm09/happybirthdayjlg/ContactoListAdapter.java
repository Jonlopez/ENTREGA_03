package com.egibide.a8fprogmm09.happybirthdayjlg;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 8fprogmm09 on 30/3/17.
 */

public class ContactoListAdapter extends ArrayAdapter<obj_contacto>{

    private final Activity context;

    private final ArrayList<obj_contacto> itemcontacto;

    public ContactoListAdapter(Activity context, ArrayList<obj_contacto> itemcontacto) {
        super(context, R.layout.fila_lista, itemcontacto);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.itemcontacto=itemcontacto;
    }

    public View getView(int posicion, View view, ViewGroup parent){

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.fila_lista,null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.texto_principal);
        TextView etxDescripcion = (TextView) rowView.findViewById(R.id.texto_secundario);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        txtTitle.setText(itemcontacto.get(posicion).getContacto_nombre());
        etxDescripcion.setText("telefóno "+itemcontacto.get(posicion).getContacto_telefono());
        if(itemcontacto.get(posicion).getContacto_foto() != null)
            imageView.setImageURI(Uri.parse(itemcontacto.get(posicion).getContacto_foto()));
        else
            imageView.setImageResource(R.drawable.alien);


        return rowView;
    }
}
