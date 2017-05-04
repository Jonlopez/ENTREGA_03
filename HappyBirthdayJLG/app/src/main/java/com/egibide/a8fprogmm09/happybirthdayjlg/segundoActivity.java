package com.egibide.a8fprogmm09.happybirthdayjlg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class segundoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segundo);

        Bundle bundle = getIntent().getExtras();
        String  contacto_nombre =bundle.getString("contacto_nombre");
        String  contacto_telefono =bundle.getString("contacto_telefono");
        String  contacto_fecha =bundle.getString("contacto_fecha");
        String  contacto_felicitacion =bundle.getString("contacto_felicitacion");

        TextView out = (TextView)findViewById(R.id.nombre); out.setText(contacto_nombre);
        TextView out2 = (TextView)findViewById(R.id.contacto_telefono); out2.setText(contacto_telefono);
        TextView out3 = (TextView)findViewById(R.id.contacto_fecha); out3.setText(contacto_fecha);
        TextView out4 = (TextView)findViewById(R.id.contacto_felicitacion);
        if(!contacto_felicitacion.equals(""))
            out4.setText(contacto_felicitacion);

        Button btn2 = (Button) findViewById(R.id.botonact2);

        btn2.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                Intent intent2 = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(intent2, 0);
            }
        });

    }
}
