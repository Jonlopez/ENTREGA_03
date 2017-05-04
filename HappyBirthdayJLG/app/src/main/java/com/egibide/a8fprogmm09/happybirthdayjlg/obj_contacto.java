package com.egibide.a8fprogmm09.happybirthdayjlg;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 8fprogmm09 on 23/3/17.
 */

public class obj_contacto implements Serializable{

    private Integer contacto_id;
    private ArrayList<String> contacto_telefonos;
    private String contacto_fNacimiento;
    private String contacto_nombre;
    private String contacto_foto;

    private Character tipo_notificacion = 'N';
    private String mensaje_felicitacion = "¡Eres un grande, felidicades pasalo estupendo! =D";

    public obj_contacto() {

    }

    public ArrayList<String> getContacto_telefonos() {
        return contacto_telefonos;
    }

    public void setContacto_telefonos(ArrayList<String> contacto_telefonos) {
        this.contacto_telefonos = contacto_telefonos;
    }

    public String getContacto_fNacimiento() {
        return contacto_fNacimiento;
    }

    public void setContacto_fNacimiento(String contacto_fNacimiento) {
        this.contacto_fNacimiento = contacto_fNacimiento;
    }

    public String getContacto_nombre() {
        return contacto_nombre;
    }

    public void setContacto_nombre(String contacto_nombre) {
        this.contacto_nombre = contacto_nombre;
    }

    public Character getTipo_notificacion() {
        return tipo_notificacion;
    }

    public void setTipo_notificacion(Character tipo_notificacion) {
        this.tipo_notificacion = tipo_notificacion;
    }

    public String getMensaje_felicitacion() {
        return mensaje_felicitacion;
    }

    public void setMensaje_felicitacion(String mensaje_felicitacion) {
        this.mensaje_felicitacion = mensaje_felicitacion;
    }

    public Integer getContacto_id() {
        return contacto_id;
    }

    public void setContacto_id(Integer contacto_id) {
        this.contacto_id = contacto_id;
    }

    public String getContacto_foto() {
        return contacto_foto;
    }

    public void setContacto_foto(String contacto_foto) {
        this.contacto_foto = contacto_foto;
    }
}




