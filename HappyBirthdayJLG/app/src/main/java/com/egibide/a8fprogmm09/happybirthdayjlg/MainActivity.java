package com.egibide.a8fprogmm09.happybirthdayjlg;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

        db.execSQL("INSERT INTO MisCumples VALUES (" + curContacto.getContacto_id() + ",'" + curContacto.getTipo_notificacion() + "'," +
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

        String proyeccion[]={
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.Contacts.PHOTO_URI,
                ContactsContract.CommonDataKinds.Event.START_DATE
        };

        String filtro=ContactsContract.Contacts.DISPLAY_NAME + " like ?";


        String args_filtro[]={"%"+txtFiltro_nombre+"%"};

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                proyeccion, filtro, args_filtro, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {

                obj_contacto contacto = new obj_contacto();

                contacto.setContacto_id(Integer.parseInt(cur.getString( cur.getColumnIndex(ContactsContract.Contacts._ID))));
                contacto.setContacto_nombre(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                contacto.setContacto_telefono(cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                contacto.setContacto_foto(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)));
                contacto.setContacto_fNacimiento( getBDate(contacto.getContacto_id().toString()));

                arrList_contactos.add(contacto);

            }
        }
        cur.close();

        llenarListView();

    }

    private String getBDate(String id) {
        String bday = null;
        ContentResolver cr = getContentResolver();
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Event.CONTACT_ID,
                ContactsContract.CommonDataKinds.Event.START_DATE };
        String where = ContactsContract.Data.MIMETYPE + "= ? AND "
                + ContactsContract.CommonDataKinds.Event.TYPE + "="
                + ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY;
        String[] selectionArgs = new String[] { ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE };
        String sortOrder = null;
        Cursor cur = cr.query(uri, projection, where, selectionArgs, sortOrder);
        while (cur.moveToNext()) {
            bday = cur
                    .getString(cur
                            .getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
            Log.v("Birthday", bday);
        }
        cur.close();
        return bday;
    }

    public void llenarListView(){
        ContactoListAdapter adapter=new ContactoListAdapter(this,arrList_contactos);
        ListView lista =(ListView)findViewById(R.id.lstContactos);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                obj_contacto Slecteditem = arrList_contactos.get(+position);
                Toast.makeText(getApplicationContext(), Slecteditem.getContacto_nombre(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
