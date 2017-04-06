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

        TextView texto_nombre = (TextView) rowView.findViewById(R.id.texto_nombre);
        TextView texto_telefono = (TextView) rowView.findViewById(R.id.texto_telefono);
        TextView texto_nacimiento = (TextView) rowView.findViewById(R.id.texto_nacimiento);
        TextView texto_notificacion = (TextView) rowView.findViewById(R.id.texto_notificacion);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        texto_nombre.setText(itemcontacto.get(posicion).getContacto_nombre());
        texto_telefono.setText("Nº telefóno : "+itemcontacto.get(posicion).getContacto_telefonos().get(0));
        if(itemcontacto.get(posicion).getContacto_fNacimiento()!=null){
            texto_nacimiento.setText("Nació el "+itemcontacto.get(posicion).getContacto_fNacimiento());
            if(itemcontacto.get(posicion).getTipo_notificacion() != 'S' )
                texto_notificacion.setText("Aviso: Sólo notificación");
            else
                texto_notificacion.setText("Aviso: Enviar SMS");
        }

        if(itemcontacto.get(posicion).getContacto_foto() != null)
            imageView.setImageURI(Uri.parse(itemcontacto.get(posicion).getContacto_foto()));
        else
            imageView.setImageResource(R.drawable.alien);


        return rowView;
    }
}
