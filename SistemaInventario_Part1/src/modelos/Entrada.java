package modelos;

import java.util.Date;

public class Entrada {
    private int idEntrada;
    private Date fecha;
    private int idUsuario;
    private String observacion;

    public Entrada() {
        this.fecha = new Date();
    }

    public Entrada(int idEntrada, Date fecha, int idUsuario, String observacion) {
        this.idEntrada = idEntrada;
        this.fecha = fecha;
        this.idUsuario = idUsuario;
        this.observacion = observacion;
    }

    public int getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(int idEntrada) {
        this.idEntrada = idEntrada;
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
