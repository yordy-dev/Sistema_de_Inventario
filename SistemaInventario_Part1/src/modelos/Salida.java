package modelos;

import java.util.Date;

public class Salida {
    private int idSalida;
    private Date fecha;
    private int idUsuario;
    private String observacion;

    public Salida() {
        this.fecha = new Date();
    }

    public Salida(int idSalida, Date fecha, int idUsuario, String observacion) {
        this.idSalida = idSalida;
        this.fecha = fecha;
        this.idUsuario = idUsuario;
        this.observacion = observacion;
    }

    public int getIdSalida() {
        return idSalida;
    }

    public void setIdSalida(int idSalida) {
        this.idSalida = idSalida;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
