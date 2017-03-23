package com.egibide.a8fprogmm09.happybirthdayjlg;

/**
 * Created by 8fprogmm09 on 23/3/17.
 */

public class obj_contacto {

    private Integer contacto_id;
    private String contacto_telefono;
    private String contacto_fNacimiento;
    private String contacto_nombre;

    private Character tipo_notificacion;
    private String mensaje_felicitacion;

    public obj_contacto(Integer contacto_id, String contacto_telefono, String contacto_fNacimiento, String contacto_nombre, Character tipo_notificacion, String mensaje_felicitacion) {
        this.contacto_id = contacto_id;
        this.contacto_telefono = contacto_telefono;
        this.contacto_fNacimiento = contacto_fNacimiento;
        this.contacto_nombre = contacto_nombre;
        this.tipo_notificacion = tipo_notificacion;
        this.mensaje_felicitacion = mensaje_felicitacion;
    }

    public obj_contacto(Integer contacto_id, String contacto_telefono, String contacto_nombre) {
        this.contacto_id = contacto_id;
        this.contacto_telefono = contacto_telefono;
        this.contacto_nombre = contacto_nombre;
    }

    public Integer getcontacto_id() {
        return contacto_id;
    }

    public void setcontacto_id(Integer contacto_id) {
        this.contacto_id = contacto_id;
    }

    public String getContacto_telefono() {
        return contacto_telefono;
    }

    public void setContacto_telefono(String contacto_telefono) {
        this.contacto_telefono = contacto_telefono;
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
}




