/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

/**
 *
 * @author roman
 */
public class Movimiento {
    private String idCliente;
    private String cuenta;
    private String monto;
    private String fecha;
    
    public Movimiento(String idCliente, String cuenta, String monto, String fecha) {
        this.idCliente = idCliente;
        this.cuenta = cuenta;
        this.monto = monto;
        this.fecha = fecha;
    }
    
    
    public Movimiento() {
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
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
