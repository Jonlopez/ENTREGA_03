package com.egibide.a8fprogmm09.happybirthdayjlg;

import android.content.ContentResolver;
import android.content.ContentValues;
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

        //crearBaseDatos();
        //buscarContactos("");
        ListarContactos();

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        UsuariosSQLiteHelper usdbh =
                new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);

        db = usdbh.getWritableDatabase();

    }


    public void consultarBD(){
        //Alternativa 1: m�todo rawQuery()
        Cursor c = db.rawQuery("SELECT codigo, nombre FROM Usuarios", null);

        //Alternativa 2: m�todo delete()
        //String[] campos = new String[] {"codigo", "nombre"};
        //Cursor c = db.query("Usuarios", campos, null, null, null, null, null);

        //Recorremos los resultados para mostrarlos en pantalla

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya m�s registros
            do {
                String cod = c.getString(0);
                String nom = c.getString(1);

                Log.i(" " , cod + " - " + nom );
            } while(c.moveToNext());
        }
    }

    public void eliminarBD(){
        //Recuperamos los valores de los campos de texto
        String cod = "";

        //Alternativa 1: m�todo sqlExec()
        //String sql = "DELETE FROM Usuarios WHERE codigo=" + cod;
        //db.execSQL(sql);

        //Alternativa 2: m�todo delete()
        db.delete("Usuarios", "codigo=" + cod, null);
    }

    public void actualizarBD(){
        //Recuperamos los valores de los campos de texto
        String cod = "";
        String nom = "";

        //Alternativa 1: m�todo sqlExec()
        //String sql = "UPDATE Usuarios SET nombre='" + nom + "' WHERE codigo=" + cod;
        //db.execSQL(sql);

        //Alternativa 2: m�todo update()
        ContentValues valores = new ContentValues();
        valores.put("nombre", nom);
        db.update("Usuarios", valores, "codigo=" + cod, null);
    }

    public void insertarBD(){
        //Recuperamos los valores de los campos de texto
        String cod = "";
        String nom = "";

        //Alternativa 1: m�todo sqlExec()
        //String sql = "INSERT INTO Usuarios (codigo,nombre) VALUES ('" + cod + "','" + nom + "') ";
        //db.execSQL(sql);

        //Alternativa 2: m�todo insert()
        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("codigo", cod);
        nuevoRegistro.put("nombre", nom);
        db.insert("Usuarios", null, nuevoRegistro);
    }

    public void crearBaseDatos(){

        db=openOrCreateDatabase("MisCumples", Context.MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS MisCumples" +
                "( ID integer, TipoNotif char(1), Mensaje VARCHAR(160), Telefono VARCHAR(15)," +
                " FechaNacimiento VARCHAR(15), Nombre VARCHAR(128) );");
    };

    public void addContacto(obj_contacto curContacto){

        db.execSQL("INSERT INTO MisCumples VALUES (" + curContacto.getContacto_id() + ",'" + curContacto.getTipo_notificacion() + "', " +
                "'" + curContacto.getMensaje_felicitacion() + "', '" + curContacto.getContacto_telefonos().get(0) + "', " +
                "'" + curContacto.getContacto_fNacimiento() + "', '" + curContacto.getContacto_nombre()+ "')");

        Toast.makeText(this,"Se añadió el contacto "+ curContacto.getContacto_nombre(),Toast.LENGTH_LONG).show();

    }

    public void ListarContactos(){

        ArrayAdapter<String> adaptador;

        Cursor c=db.rawQuery(" SELECT * FROM MisCumples ", null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String codigo= c.getString(0);
                String nombre = c.getString(1);
            } while(c.moveToNext());
        }


        c.close();
    }

    public void buscarContactos(String txtFiltro_nombre){

        String proyeccion[]={
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI
        };

        String filtro=ContactsContract.Contacts.DISPLAY_NAME + " like ?";


        String args_filtro[]={"%"+txtFiltro_nombre+"%"};

        ContentResolver cr = getContentResolver();

        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                proyeccion, filtro, args_filtro, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {

                obj_contacto contacto = new obj_contacto();

                contacto.setContacto_id( Integer.parseInt(cur.getString( cur.getColumnIndex(ContactsContract.Contacts._ID))) );
                contacto.setContacto_nombre( cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)) );
                contacto.setContacto_telefonos( getNumbersContact(contacto.getContacto_id().toString()) );
                contacto.setContacto_foto( cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)) );
                contacto.setContacto_fNacimiento( getDateBirthDay(contacto.getContacto_id().toString()) );

                arrList_contactos.add(contacto);

            }
        }
        cur.close();

        llenarListView();

    }

    private ArrayList<String> getNumbersContact(String id) {

        ArrayList<String> telefonos = new ArrayList<>();

        String proyeccion[]={
                ContactsContract.Contacts._ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
        };

        String filtro=ContactsContract.Contacts.DISPLAY_NAME + " like ? AND "
                + ContactsContract.CommonDataKinds.Event.CONTACT_ID + "=" + id;

        String args_filtro[]={"%%"};

        ContentResolver cr = getContentResolver();

        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                proyeccion, filtro, args_filtro, null);

        if (cur.getCount() > 0) {

            int cont = 0;

            while (cur.moveToNext()) {

                telefonos.add(cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                cont += 1;

            }

        }
        cur.close();

        return telefonos;
    }

    private String getDateBirthDay(String id) {
        String bday = null;
        ContentResolver cr = getContentResolver();
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Event.CONTACT_ID,
                ContactsContract.CommonDataKinds.Event.START_DATE };

        String where = ContactsContract.Data.MIMETYPE + "= ? AND "
                + ContactsContract.CommonDataKinds.Event.TYPE + "="
                + ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY + " AND "
                + ContactsContract.CommonDataKinds.Event.CONTACT_ID + "=" + id
                ;

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

        //for(obj_contacto curContacto : arrList_contactos){
          //  addContacto(curContacto);
        //}

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
