/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

/**
 *
 * @author monse
 */
public class Cuenta {
    private String idCliente;
    private String clabe;
    private String monto;
    private String fecha;

    public Cuenta(String idCliente, String clabe, String monto, String fecha) {
        this.idCliente = idCliente;
        this.clabe = clabe;
        this.monto = monto;
        this.fecha = fecha;
    }

    public Cuenta() {
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getClabe() {
        return clabe;
    }

    public void setClabe(String clabe) {
        this.clabe = clabe;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
}
