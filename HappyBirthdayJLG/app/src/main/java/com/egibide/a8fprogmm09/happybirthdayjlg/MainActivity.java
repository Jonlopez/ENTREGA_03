package com.egibide.a8fprogmm09.happybirthdayjlg;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    private ArrayList<obj_contacto> arrList_contactos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crearBaseDatos();
        buscarContactos("");
    }

    public void crearBaseDatos(){
        db=openOrCreateDatabase("MisCumples", Context.MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS MisCumples" +
                "( ID integer, TipoNotif char(1), Mensaje VARCHAR(160), Telefono VARCHAR(15)," +
                " FechaNacimiento VARCHAR(15), Nombre VARCHAR(128) );");
    };

    public void addContacto(obj_contacto curContacto){

        db.execSQL("INSERT INTO MisCumples VALUES (" + curContacto.getcontacto_id() + ",'" + curContacto.getTipo_notificacion() + "'," +
                "'" + curContacto.getContacto_telefono() + "', '" + curContacto.getContacto_fNacimiento() + "'," +
                "'" + curContacto.getContacto_nombre()+ "')");

        Toast.makeText(this,"Se añadió el contacto "+ curContacto.getContacto_nombre(),Toast.LENGTH_LONG).show();

    }

    public void ListarContactos(){

        ArrayAdapter<String> adaptador;

        List<String> lista_contactos = new ArrayList<String>();

        Cursor c=db.rawQuery("SELECT * FROM MisCumples", null);

        if(c.getCount()==0)
            lista_contactos.add("No hay registros");
        else{
            while(c.moveToNext())
                lista_contactos.add(c.getString(0)+"-"+c.getString(5));
        }

        for (String  curContacto : lista_contactos ) {
            Log.i ("LISTAR CONTACTOS ID -> ", curContacto.toString());
            Log.i ("LISTAR CONTACTOS NO -> ", curContacto);
        }

        ListView l=(ListView)findViewById(R.id.lstContactos);
        l.setAdapter(new ArrayAdapter<String>(this,R.layout.fila_lista,lista_contactos));

        c.close();
    }

    public void buscarContactos(String txtFiltro_nombre){


        String proyeccion[]={ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER,
                ContactsContract.Contacts.PHOTO_ID};
        String filtro=ContactsContract.Contacts.DISPLAY_NAME + " like ?";


        String args_filtro[]={"%"+txtFiltro_nombre+"%"};

        List<String> lista_contactos=new ArrayList<String>();

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                proyeccion, filtro, args_filtro, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString( cur.getColumnIndex(ContactsContract.Contacts._ID) );

                String name = cur.getString( cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME) );

                String telefono = "";

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

//                    telefono = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    lista_contactos.add(name);
                }

                obj_contacto contacto = new obj_contacto(Integer.parseInt(id) , telefono, name);

                arrList_contactos.add(contacto);

            }
        }
        cur.close();

        for (obj_contacto  curContacto : arrList_contactos ) {
            Log.i ("ID de contaco -> ", curContacto.getcontacto_id().toString());
            Log.i ("Nombre de contacto -> ", curContacto.getContacto_nombre());
        }

        ListView l=(ListView)findViewById(R.id.lstContactos);
        l.setAdapter(new ArrayAdapter<String>(this,R.layout.fila_lista, R.id.Itemname,lista_contactos));

    }

}
