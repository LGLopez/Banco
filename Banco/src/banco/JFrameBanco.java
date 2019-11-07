/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class JFrameBanco extends javax.swing.JFrame {

    RandomAccessFile raf, rafAux, rafId, rafIdEliminado, rafCu, rafMo;
    File file, fileAux, fileId, fileIdEliminado, fileCu, fileMo;
    int Id, IdEliminado;  
    
    public JFrameBanco() throws IOException {
        initComponents();
        fileId = new File("Id.obj");
        rafId = new RandomAccessFile(fileId, "rw");
        
        if(fileId.length() == 0){
            Id = 1;
            rafId.writeInt(Id);
        }
        else{
           Id = rafId.readInt(); 
        }
        
        txtIdCliente.setEditable(false);
        txtFechaCuenta.setEditable(false);
       
        for(int i = 1; i < Id; i++){
            jComboIdCliente.addItem(String.valueOf(i));
            jComboIdMov.addItem(String.valueOf(i));
        }
        txtIdCliente.setText(String.valueOf(Id));
        rafId.close();
        
        Date date = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");       
        txtFechaCuenta.setText(formatoFecha.format(date));
    }
    
    String patronNombre = "^([A-Z]{1}[a-zA-Z\\s]+)$";
    String patronApellido = "^([A-Z]{1}[a-z]+)$";
    String patronEmail = "^([a-zA-Z0-9_\\-\\.]+@[a-zA-Z]{5,16}\\.[a-zA-Z]{3})$";
    String patronTelefono = "^([0-9]{10})$";
    String patronDireccion = "^([a-zA-Z0-9,.#\\s]+)$";
    String patronMonto = "^([0-9]+)$";
    String patronClabe = "^([0-9]{18})$";
    String patronCuenta = "^([0-9]{18})$";
    
    public void addCliente() throws FileNotFoundException, IOException{
        file = new File("Clientes.obj");
        raf = new RandomAccessFile(file, "rw");
        
        fileId = new File("Id.obj");
        rafId = new RandomAccessFile(fileId, "rw");
        
        if(fileId.length() == 0){
            Id = 1;
            rafId.writeInt(Id);
        }
        else{
           Id = rafId.readInt(); 
        }
        
        txtIdCliente.setText(String.valueOf(Id));
        String id;
        String nombre;
        String materno;
        String paterno;
        String direccion;
        String telefono;
        String email;
        
        id = txtIdCliente.getText();
        nombre = txtNombre.getText();
        paterno = txtApePaterno.getText();
        materno = txtApeMaterno.getText();
        direccion = txtDireccion.getText();
        telefono = txtTelefono.getText();
        email = txtEmail.getText();
        
        Pattern a = Pattern.compile(patronNombre);
        Pattern b = Pattern.compile(patronApellido);
        Pattern c = Pattern.compile(patronDireccion);
        Pattern d = Pattern.compile(patronTelefono);
        Pattern e = Pattern.compile(patronEmail);
        
        if(!a.matcher(nombre).matches()){
            JOptionPane.showMessageDialog(this, "Nombre invalido");
        }
        else if(!b.matcher(paterno).matches()){
            JOptionPane.showMessageDialog(this, "Apellido paterno invalido");
        }
        else if(!b.matcher(materno).matches()){
            JOptionPane.showMessageDialog(this, "Apellido materno invalido");
        }
        else if(!c.matcher(direccion).matches()){
            JOptionPane.showMessageDialog(this, "Direccion invalida");
        }
        else if(!d.matcher(telefono).matches()){
            JOptionPane.showMessageDialog(this, "Telefono invalido");
        }
        else if(!e.matcher(email).matches()){
            JOptionPane.showMessageDialog(this, "Email invalido");
        }
        else{
            raf.seek(raf.length());
            raf.writeUTF(id);
            raf.writeUTF(nombre);
            raf.writeUTF(paterno);
            raf.writeUTF(materno);
            raf.writeUTF(direccion);
            raf.writeUTF(telefono);
            raf.writeUTF(email);

            raf.close();

            Id++;

            rafId.seek(0);
            rafId.writeInt(Id);
            rafId.close();

            cleanCliente();

            JOptionPane.showMessageDialog(this, "Agregado exitosamente"); 
        }
        
        jComboIdCliente.removeAllItems();
        for(int i = 1; i < Id; i++){
            jComboIdCliente.addItem(Integer.toString(i));
        }
        
    }
    
    public void cleanCliente() throws FileNotFoundException, IOException{
        fileId = new File("Id.obj");
        rafId = new RandomAccessFile(fileId, "rw");
        
        if(fileId.length() == 0){
            Id = 1;
            rafId.writeInt(Id);
        }
        else{
           Id = rafId.readInt(); 
        }
        
        txtIdCliente.setText(String.valueOf(Id));
        txtNombre.setText("");
        txtApePaterno.setText("");
        txtApeMaterno.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        rafId.close();
    } 
    
    public int buscar(String opc) throws FileNotFoundException, IOException{
       file = new File("Clientes.obj");
       raf = new RandomAccessFile(file, "r");
       
       fileId = new File("Id.obj");
       rafId = new RandomAccessFile(fileId, "rw");
        
       if(fileId.length() == 0){
            Id = 1;
            rafId.writeInt(Id);
       }
       else{
          Id = rafId.readInt(); 
       }
        fileIdEliminado = new File("IdEliminado.obj");
        rafIdEliminado = new RandomAccessFile(fileIdEliminado, "rw");
        
        if(fileIdEliminado.length() == 0){
            IdEliminado = 0;
            rafIdEliminado.writeInt(IdEliminado);
        }
        else{
           IdEliminado = rafIdEliminado.readInt();
        }
        for(int i = 1; i < Id-IdEliminado; i++){
           System.out.println(i);
           String a = raf.readUTF();
           System.out.println("       " +a);
           System.out.println("-------" +opc);
           if(a.equals(opc)){
               System.out.println("hola");
               raf.close();
               rafId.close();
               rafIdEliminado.close();
               return i;
           }
           else{
               String b = raf.readUTF();
               String c = raf.readUTF();
               String d = raf.readUTF();
               String e = raf.readUTF();
               String f = raf.readUTF();
               String g = raf.readUTF();
           }
        }
        raf.close();
        rafId.close();
        rafIdEliminado.close();
        return -1;
    }
    
    public void editarCliente() throws FileNotFoundException, IOException{
        String nombre;
        String materno;
        String paterno;
        String direccion;
        String telefono;
        String email;
        
        nombre = txtNombre.getText();
        paterno = txtApePaterno.getText();
        materno = txtApeMaterno.getText();
        direccion = txtDireccion.getText();
        telefono = txtTelefono.getText();
        email = txtEmail.getText();
        
        Pattern a = Pattern.compile(patronNombre);
        Pattern b = Pattern.compile(patronApellido);
        Pattern c = Pattern.compile(patronDireccion);
        Pattern d = Pattern.compile(patronTelefono);
        Pattern e = Pattern.compile(patronEmail);
        
        if(!a.matcher(nombre).matches()){
            JOptionPane.showMessageDialog(this, "Nombre invalido");
        }
        else if(!b.matcher(paterno).matches()){
            JOptionPane.showMessageDialog(this, "Apellido paterno invalido");
        }
        else if(!b.matcher(materno).matches()){
            JOptionPane.showMessageDialog(this, "Apellido materno invalido");
        }
        else if(!c.matcher(direccion).matches()){
            JOptionPane.showMessageDialog(this, "Direccion invalida");
        }
        else if(!d.matcher(telefono).matches()){
            JOptionPane.showMessageDialog(this, "Telefono invalido");
        }
        else if(!e.matcher(email).matches()){
            JOptionPane.showMessageDialog(this, "Email invalido");
        }
        else{
            file = new File("Clientes.obj");
            raf = new RandomAccessFile(file, "r");

            fileAux = new File("ClientesAux.obj");
            rafAux = new RandomAccessFile(fileAux, "rw");

            fileId = new File("Id.obj");
            rafId = new RandomAccessFile(fileId, "rw");

            if(fileId.length() == 0){
                Id = 1;
                rafId.writeInt(Id);
            }
            else{
               Id = rafId.readInt(); 
            }

            fileIdEliminado = new File("IdEliminado.obj");
            rafIdEliminado = new RandomAccessFile(fileIdEliminado, "rw");

            if(fileIdEliminado.length() == 0){
                IdEliminado = 0;
                rafIdEliminado.writeInt(IdEliminado);
            }
            else{
               IdEliminado = rafIdEliminado.readInt(); 
            }

            String opc = JOptionPane.showInputDialog("Ingrese id: ");
            raf.seek(0);
            rafAux.seek(0);
            String aux = null;

            try{
                for(int i = 0; i < Id-IdEliminado; i++){
                    aux = raf.readUTF();
                    if(!aux.equals(opc)){
                        rafAux.writeUTF(aux);
                        rafAux.writeUTF(raf.readUTF());
                        rafAux.writeUTF(raf.readUTF());
                        rafAux.writeUTF(raf.readUTF());
                        rafAux.writeUTF(raf.readUTF());
                        rafAux.writeUTF(raf.readUTF());
                        rafAux.writeUTF(raf.readUTF());
                    }
                    else{
                        raf.readUTF();
                        raf.readUTF();
                        raf.readUTF();
                        raf.readUTF();
                        raf.readUTF();
                        raf.readUTF();
                    }
                }
            }catch(EOFException ex){}

            System.out.println(aux);
            rafAux.seek(raf.length());
            rafAux.writeUTF(opc);
            rafAux.writeUTF(nombre);
            rafAux.writeUTF(paterno);
            rafAux.writeUTF(materno);
            rafAux.writeUTF(direccion);
            rafAux.writeUTF(telefono);
            rafAux.writeUTF(email);

            rafIdEliminado.close();               
            rafId.close();
            raf.close();
            rafAux.close();

            if(aux!= null){
                file.delete();
                file = new File("Clientes.obj");
                fileAux.renameTo(file);
            }
            JOptionPane.showMessageDialog(this, "Editado exitosamente");
            cleanCliente();
        }
    }
    
    public void eliminarCliente() throws FileNotFoundException, IOException{
        file = new File("Clientes.obj");
        raf = new RandomAccessFile(file, "r");
        
        fileAux = new File("ClientesAux.obj");
        rafAux = new RandomAccessFile(fileAux, "rw");
        
        fileId = new File("Id.obj");
        rafId = new RandomAccessFile(fileId, "rw");
        
        if(fileId.length() == 0){
            Id = 1;
            rafId.writeInt(Id);
        }
        else{
           Id = rafId.readInt(); 
        }
        
        fileIdEliminado = new File("IdEliminado.obj");
        rafIdEliminado = new RandomAccessFile(fileIdEliminado, "rw");
        
        if(fileIdEliminado.length() == 0){
            IdEliminado = 0;
            rafIdEliminado.writeInt(IdEliminado);
        }
        else{
           IdEliminado = rafIdEliminado.readInt(); 
        }
        
        String opc = JOptionPane.showInputDialog("Ingrese id: ");
        raf.seek(0);
        rafAux.seek(0);
        String aux = null;
        if(opc != null){
            try{
                for(int i = 0; i < Id-IdEliminado; i++){
                aux = raf.readUTF();
                    if(!aux.equals(opc)){
                        rafAux.writeUTF(aux);
                        rafAux.writeUTF(raf.readUTF());
                        rafAux.writeUTF(raf.readUTF());
                        rafAux.writeUTF(raf.readUTF());
                        rafAux.writeUTF(raf.readUTF());
                        rafAux.writeUTF(raf.readUTF());
                        rafAux.writeUTF(raf.readUTF());
                    }
                    else{
                        raf.readUTF();
                        raf.readUTF();
                        raf.readUTF();
                        raf.readUTF();
                        raf.readUTF();
                        raf.readUTF();
                    }       
                }
            }catch(EOFException ex){}
            
            IdEliminado++;
            rafIdEliminado.seek(0);
            rafIdEliminado.writeInt(IdEliminado);
            JOptionPane.showMessageDialog(this, "Eliminado exitosamente");
        }
        
        rafIdEliminado.close();
        rafId.close();
        raf.close();
        rafAux.close();
        
        if(aux != null){
            file.delete();
            file = new File("Clientes.obj");
            fileAux.renameTo(file);
        }       
    }
    
    public void addCuenta() throws FileNotFoundException, IOException{
        fileCu = new File("Cuentas.obj");
        rafCu = new RandomAccessFile(fileCu, "rw");
        
        file = new File("Clientes.obj");
        raf = new RandomAccessFile(file, "r");
        
        String idCliente;
        String clabe;
        String tipoCuenta;
        String monto;
        String fecha;
        
        idCliente = (String) jComboIdCliente.getSelectedItem();
        clabe = txtClabe.getText();
        tipoCuenta = (String) jComboTipoCuenta.getSelectedItem();
        monto = txtMonto.getText();
        fecha = txtFechaCuenta.getText();
        
        Pattern a = Pattern.compile(patronClabe);
        Pattern b = Pattern.compile(patronMonto);

        String aux, aux1 = null;
        int existe = 0, repetido = 0;
        try{
            for(int i = 0; i < raf.length(); i++){
                aux = raf.readUTF();
                if(idCliente.equals(aux)){
                    existe = 1;
                }
                else{
                    raf.readUTF();
                    raf.readUTF();
                    raf.readUTF();
                    raf.readUTF();
                    raf.readUTF();
                    raf.readUTF();
                }
            }
        }catch(EOFException ex){}    
        try{
            for(int i = 0; i < rafCu.length(); i++){
                aux1 = rafCu.readUTF();
                if(idCliente.equals(aux1)){
                    repetido = 1;
                }
                else{
                    rafCu.readUTF();
                    rafCu.readUTF();
                    rafCu.readUTF();
                    rafCu.readUTF();
                }
            }
        }catch(EOFException ex){}

        if(existe == 1){
            if(repetido == 0){
                if(!a.matcher(clabe).matches()){
                    JOptionPane.showMessageDialog(this, "Clabe invalida");
                }
                else if(!b.matcher(monto).matches()){
                    JOptionPane.showMessageDialog(this, "Monto invalido");
                }
                else{
                    rafCu.seek(rafCu.length());
                    rafCu.writeUTF(idCliente);
                    rafCu.writeUTF(clabe);
                    rafCu.writeUTF(tipoCuenta);
                    rafCu.writeUTF(monto);
                    rafCu.writeUTF(fecha);
                    rafCu.close();

                    cleanCuenta();
                    JOptionPane.showMessageDialog(this, "Agregado exitosamente");
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "El cliente con ese ID ya tiene una cuenta");

            }
        }
        else{
            JOptionPane.showMessageDialog(this, "El cliente con ese ID no existe");
        }
        raf.close();
   
    }
    
    public void cleanCuenta(){
        txtClabe.setText("");
        txtMonto.setText("");
    }
    
    public void editarCuenta() throws FileNotFoundException, IOException{
        fileCu = new File("Cuentas.obj");
        rafCu = new RandomAccessFile(fileCu, "r");
              
        String buscar = JOptionPane.showInputDialog("Ingrese id: ");
     
        try{
            for(int i = 0; i < rafCu.length(); i++){
                String a = rafCu.readUTF();
                String b = rafCu.readUTF();
                String c = rafCu.readUTF();
                String d = rafCu.readUTF();
                String e = rafCu.readUTF();
                if(buscar.equals(a)){
                    jComboIdCliente.setSelectedItem(a);
                    txtClabe.setText(b);
                    jComboTipoCuenta.setSelectedItem(c);
                    txtMonto.setText(d);
                    txtFechaCuenta.setText(e);
                }
            }
        }catch(EOFException ex){}
        rafCu.close();
    }
    
    public void guardarCuenta() throws FileNotFoundException, IOException{
        fileCu = new File("Cuentas.obj");
        rafCu = new RandomAccessFile(fileCu, "r");
        
        fileAux = new File("CuentasAux.obj");
        rafAux = new RandomAccessFile(fileAux, "rw");
        
        String idCliente;
        String clabe;
        String tipoCuenta;
        String monto;
        String fecha;
        
        idCliente = (String) jComboIdCliente.getSelectedItem();
        clabe = txtClabe.getText();
        tipoCuenta = (String) jComboTipoCuenta.getSelectedItem();
        monto = txtMonto.getText();
        fecha = txtFechaCuenta.getText();
        
        rafCu.seek(0);
        rafAux.seek(0);
        String aux = null;
        
        try{
            System.out.println(idCliente);
            for(int i = 0; i < rafCu.length(); i++){
                System.out.println(aux);
                aux = rafCu.readUTF();
                if(!aux.equals(idCliente)){
                    rafAux.writeUTF(aux);
                    rafAux.writeUTF(rafCu.readUTF());
                    rafAux.writeUTF(rafCu.readUTF());
                    rafAux.writeUTF(rafCu.readUTF());
                    rafAux.writeUTF(rafCu.readUTF());
                }
                else{
                    rafCu.readUTF();
                    rafCu.readUTF();
                    rafCu.readUTF();
                    rafCu.readUTF();
                }
            }
        }catch(EOFException ex){}
        
        System.out.println(aux);
        rafAux.seek(rafCu.length());
        rafAux.writeUTF(idCliente);
        rafAux.writeUTF(clabe);
        rafAux.writeUTF(tipoCuenta);
        rafAux.writeUTF(monto);
        rafAux.writeUTF(fecha);

        rafCu.close();
        rafAux.close();
        
        if(aux!= null){
            file.delete();
            file = new File("Cuentas.obj");
            fileAux.renameTo(file);
        }
        JOptionPane.showMessageDialog(this, "Editado exitosamente");
        cleanCuenta();
    }
    
    public void eliminarCuenta() throws FileNotFoundException, IOException{
        fileCu = new File("Cuentas.obj");
        rafCu = new RandomAccessFile(fileCu, "r");
        
        fileAux = new File("CuentasAux.obj");
        rafAux = new RandomAccessFile(fileAux, "rw");
        
        String opc = JOptionPane.showInputDialog("Ingrese id: ");
        rafCu.seek(0);
        rafAux.seek(0);
        String aux = null;
        
        if(opc != null){
            try{
                for(int i = 0; i < rafCu.length(); i++){
                    aux = rafCu.readUTF();
                    if(!aux.equals(opc)){
                        rafAux.writeUTF(aux);
                        rafAux.writeUTF(rafCu.readUTF());
                        rafAux.writeUTF(rafCu.readUTF());
                        rafAux.writeUTF(rafCu.readUTF());
                        rafAux.writeUTF(rafCu.readUTF());
                    }
                    else{
                        rafCu.readUTF();
                        rafCu.readUTF();
                        rafCu.readUTF();
                        rafCu.readUTF();
                    }        
                }   
            }catch(EOFException ex){}
       
            JOptionPane.showMessageDialog(this, "Eliminado exitosamente");
        }
        
        rafCu.close();
        rafAux.close();
        if(aux != null){
            file.delete();
            file = new File("Cuentas.obj");
            fileAux.renameTo(file);
        }      
    }
    
    public void addMovimiento() throws FileNotFoundException, IOException{
        fileMo = new File("Movimientos.obj");
        rafMo = new RandomAccessFile(fileMo, "rw");
        
        file = new File("Clientes.obj");
        raf = new RandomAccessFile(file, "r");
        
        String idCliente;
        String cuenta;
        String tipoMovimiento;
        String monto;
        String fecha;
        
        
        idCliente = (String) jComboIdMov.getSelectedItem();
        cuenta = txtCuenta.getText();
        tipoMovimiento = (String) jComboTipoMovimiento.getSelectedItem();
        monto = txtMontoMov.getText();
        SimpleDateFormat semFormato = new SimpleDateFormat("dd/MM/yyyy");
        fecha = semFormato.format(jDateFechaMov.getDate());

        
        Pattern a = Pattern.compile(patronCuenta);
        Pattern b = Pattern.compile(patronMonto);

        String aux, aux1 = null;
        int existe = 0, repetido = 0;
        try{
            for(int i = 0; i < raf.length(); i++){
                aux = raf.readUTF();
                if(idCliente.equals(aux)){
                    existe = 1;
                }
                else{
                    raf.readUTF();
                    raf.readUTF();
                    raf.readUTF();
                    raf.readUTF();
                    raf.readUTF();
                    raf.readUTF();
                }
            }
        }catch(EOFException ex){}    
        try{
            for(int i = 0; i < rafMo.length(); i++){
                aux1 = rafMo.readUTF();
                if(idCliente.equals(aux1)){
                    repetido = 1;
                }
                else{
                    rafMo.readUTF();
                    rafMo.readUTF();
                    rafMo.readUTF();
                    rafMo.readUTF();
                }
            }
        }catch(EOFException ex){}

        if(existe == 1){
            if(!a.matcher(cuenta).matches()){
                JOptionPane.showMessageDialog(this, "Cuenta invalida");
            }
            else if(!b.matcher(monto).matches()){
                JOptionPane.showMessageDialog(this, "Monto invalido");
            }
            else{
                rafMo.seek(rafMo.length());
                rafMo.writeUTF(idCliente);
                rafMo.writeUTF(cuenta);
                rafMo.writeUTF(tipoMovimiento);
                rafMo.writeUTF(monto);
                rafMo.writeUTF(fecha);
                rafMo.close();

                cleanMovimiento();
                JOptionPane.showMessageDialog(this, "Agregado exitosamente");
            }
            
        }
        else{
            JOptionPane.showMessageDialog(this, "El cliente con ese ID no existe");
        }
        raf.close();
    }
    
    public void cleanMovimiento(){
        txtCuenta.setText("");
        txtMontoMov.setText("");
    }
    
    public void eliminarMovimiento() throws FileNotFoundException, IOException{
        fileMo = new File("Movimientos.obj");
        rafMo = new RandomAccessFile(fileMo, "r");
        
        fileAux = new File("MovimientosAux.obj");
        rafAux = new RandomAccessFile(fileAux, "rw");
        
        String opc = JOptionPane.showInputDialog("Ingrese id: ");
        rafMo.seek(0);
        rafAux.seek(0);
        String aux = null;
        
        if(opc != null){
            try{
                for(int i = 0; i < rafMo.length(); i++){
                    aux = rafMo.readUTF();
                    if(!aux.equals(opc)){
                        rafAux.writeUTF(aux);
                        rafAux.writeUTF(rafMo.readUTF());
                        rafAux.writeUTF(rafMo.readUTF());
                        rafAux.writeUTF(rafMo.readUTF());
                        rafAux.writeUTF(rafMo.readUTF());
                    }
                    else{
                        rafMo.readUTF();
                        rafMo.readUTF();
                        rafMo.readUTF();
                        rafMo.readUTF();
                    }        
                }   
            }catch(EOFException ex){}
            
            rafMo.close();
            rafAux.close();
            if(aux != null){
            file.delete();
            file = new File("Movimientos.obj");
            fileAux.renameTo(file);
            }
       
            JOptionPane.showMessageDialog(this, "Eliminado exitosamente");
        }
        
              
    }
    
    public void editarMovimiento() throws FileNotFoundException, IOException{
        fileMo = new File("Movimientos.obj");
        rafMo = new RandomAccessFile(fileMo, "r");
              
        String buscar = JOptionPane.showInputDialog("Ingrese id: ");
     
        try{
            for(int i = 0; i < rafMo.length(); i++){
                String a = rafMo.readUTF();
                String b = rafMo.readUTF();
                String c = rafMo.readUTF();
                String d = rafMo.readUTF();
                String e = rafMo.readUTF();
                if(buscar.equals(a)){
                    jComboIdMov.setSelectedItem(a);
                    txtCuenta.setText(b);
                    jComboTipoMovimiento.setSelectedItem(c);
                    txtMontoMov.setText(d);
                    jDateFechaMov.setDateFormatString(e);
                } 
            }
        }catch(EOFException ex){}
        rafMo.close();
    }
    
    public void guardarMovimiento() throws FileNotFoundException, IOException{
        fileMo = new File("Movimientos.obj");
        rafMo = new RandomAccessFile(fileMo, "r");
        
        fileAux = new File("MovimientosAux.obj");
        rafAux = new RandomAccessFile(fileAux, "rw");
        
        String idCliente;
        String cuenta;
        String tipoMovimiento;
        String monto;
        String fecha;
        
        
        idCliente = (String) jComboIdMov.getSelectedItem();
        cuenta = txtCuenta.getText();
        tipoMovimiento = (String) jComboTipoMovimiento.getSelectedItem();
        monto = txtMontoMov.getText();
        SimpleDateFormat semFormato = new SimpleDateFormat("dd/MM/yyyy");
        fecha = semFormato.format(jDateFechaMov.getDate());
        
        rafMo.seek(0);
        rafAux.seek(0);
        String aux = null;
        
        try{
            System.out.println(idCliente);
            for(int i = 0; i < rafMo.length(); i++){
                System.out.println(aux);
                aux = rafMo.readUTF();
                if(!aux.equals(idCliente)){
                    rafAux.writeUTF(aux);
                    rafAux.writeUTF(rafMo.readUTF());
                    rafAux.writeUTF(rafMo.readUTF());
                    rafAux.writeUTF(rafMo.readUTF());
                    rafAux.writeUTF(rafMo.readUTF());
                }
                else{
                    rafMo.readUTF();
                    rafMo.readUTF();
                    rafMo.readUTF();
                    rafMo.readUTF();
                }
            }
        }catch(EOFException ex){}
        
        System.out.println(aux);
        rafAux.seek(rafMo.length());
        rafAux.writeUTF(idCliente);
        rafAux.writeUTF(cuenta);
        rafAux.writeUTF(tipoMovimiento);
        rafAux.writeUTF(monto);
        rafAux.writeUTF(fecha);

        rafMo.close();
        rafAux.close();
        
        if(aux!= null){
            file.delete();
            file = new File("Movimientos.obj");
            fileAux.renameTo(file);
        }
        JOptionPane.showMessageDialog(this, "Editado exitosamente");
        cleanMovimiento();
    }
    
    public void generarReporte() throws IOException{
        Document documento = new Document(PageSize.A4);
        documento.addAuthor("No Author");
        documento.addTitle("No Title");
        
        try {
            PdfWriter.getInstance(documento, new FileOutputStream("MiPDF.pdf"));
            
            documento.open();
            
            Paragraph parrafo = new Paragraph("PDF de prueba");
            
            documento.add(parrafo);
            
            
            
        } catch (DocumentException ex) {
            System.out.println("Error al crear el PDF");
        }
        
        documento.close();
    }
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApePaterno = new javax.swing.JTextField();
        txtApeMaterno = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        btnAgregarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        btnCancelarCliente = new javax.swing.JButton();
        jComboEstado = new javax.swing.JComboBox<>();
        btnEditarCliente = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtClabe = new javax.swing.JTextField();
        txtFechaCuenta = new javax.swing.JTextField();
        jComboTipoCuenta = new javax.swing.JComboBox<>();
        jComboIdCliente = new javax.swing.JComboBox<>();
        txtMonto = new javax.swing.JTextField();
        btnAgregarCuenta = new javax.swing.JButton();
        btnCancelarCuenta = new javax.swing.JButton();
        btnEliminarCuenta = new javax.swing.JButton();
        btnEditarCuenta = new javax.swing.JButton();
        btnGuardarCuenta = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jComboTipoMovimiento = new javax.swing.JComboBox<>();
        txtMontoMov = new javax.swing.JTextField();
        txtCuenta = new javax.swing.JTextField();
        jComboIdMov = new javax.swing.JComboBox<>();
        btnAgregarMov = new javax.swing.JButton();
        btnCancelarMov = new javax.swing.JButton();
        btnEliminarMov = new javax.swing.JButton();
        btnGuardarMov = new javax.swing.JButton();
        btnEditarMov = new javax.swing.JButton();
        jDateFechaMov = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jComboCuentaRe = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnGenerar = new javax.swing.JButton();
        jDateFechaInicio = new com.toedter.calendar.JDateChooser();
        jDateFechaFin = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ID:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Apellido paterno:");

        jLabel4.setText("Apellido materno:");

        jLabel5.setText("Direccion:");

        jLabel6.setText("Telefono:");

        jLabel7.setText("Email:");

        jLabel8.setText("Estado:");

        txtIdCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdClienteActionPerformed(evt);
            }
        });

        btnAgregarCliente.setText("Agregar");
        btnAgregarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarClienteActionPerformed(evt);
            }
        });

        btnEliminarCliente.setText("Eliminar");
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });

        btnCancelarCliente.setText("Cancelar");
        btnCancelarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarClienteActionPerformed(evt);
            }
        });

        jComboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));

        btnEditarCliente.setText("Editar");
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregarCliente, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtApeMaterno, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                            .addComponent(txtApePaterno)
                            .addComponent(txtNombre))
                        .addGap(71, 71, 71)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(5, 5, 5))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(txtTelefono)
                            .addComponent(txtDireccion)
                            .addComponent(jComboEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelarCliente)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditarCliente)
                        .addContainerGap(197, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApePaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApeMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(jComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarCliente)
                    .addComponent(btnEliminarCliente)
                    .addComponent(btnCancelarCliente)
                    .addComponent(btnEditarCliente))
                .addGap(76, 76, 76))
        );

        jTabbedPane1.addTab("Clientes", jPanel1);

        jLabel9.setText("ID Cliente:");

        jLabel10.setText("Clave:");

        jLabel11.setText("Tipo de cuenta:");

        jLabel12.setText("Monto:");

        jLabel13.setText("Fecha:");

        jComboTipoCuenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Deposito", "Ahorro", "Debito" }));

        jComboIdCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboIdClienteActionPerformed(evt);
            }
        });

        btnAgregarCuenta.setText("Agregar");
        btnAgregarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCuentaActionPerformed(evt);
            }
        });

        btnCancelarCuenta.setText("Cancelar");
        btnCancelarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarCuentaActionPerformed(evt);
            }
        });

        btnEliminarCuenta.setText("Eliminar");
        btnEliminarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCuentaActionPerformed(evt);
            }
        });

        btnEditarCuenta.setText("Editar");
        btnEditarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCuentaActionPerformed(evt);
            }
        });

        btnGuardarCuenta.setText("Guardar");
        btnGuardarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCuentaActionPerformed(evt);
            }
        });

        jLabel22.setText("$");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel11)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtClabe)
                            .addComponent(txtFechaCuenta)
                            .addComponent(jComboTipoCuenta, 0, 108, Short.MAX_VALUE)
                            .addComponent(jComboIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMonto)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(btnAgregarCuenta)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelarCuenta)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarCuenta)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditarCuenta)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardarCuenta)))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtClabe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jComboTipoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtFechaCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarCuenta)
                    .addComponent(btnCancelarCuenta)
                    .addComponent(btnEliminarCuenta)
                    .addComponent(btnEditarCuenta)
                    .addComponent(btnGuardarCuenta))
                .addGap(62, 62, 62))
        );

        jTabbedPane1.addTab("Cuentas", jPanel2);

        jLabel14.setText("ID:");

        jLabel15.setText("Fecha:");

        jLabel16.setText("Tipo de movimiento:");

        jLabel17.setText("Monto:");

        jLabel18.setText("Cuenta:");

        jComboTipoMovimiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Deposito", "Cheque" }));

        btnAgregarMov.setText("Agregar");
        btnAgregarMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMovActionPerformed(evt);
            }
        });

        btnCancelarMov.setText("Cancelar");
        btnCancelarMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarMovActionPerformed(evt);
            }
        });

        btnEliminarMov.setText("Eliminar");
        btnEliminarMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarMovActionPerformed(evt);
            }
        });

        btnGuardarMov.setText("Guardar");
        btnGuardarMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarMovActionPerformed(evt);
            }
        });

        btnEditarMov.setText("Editar");
        btnEditarMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarMovActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(140, 140, 140)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(jLabel16)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboTipoMovimiento, 0, 123, Short.MAX_VALUE)
                            .addComponent(txtMontoMov)
                            .addComponent(txtCuenta)
                            .addComponent(jComboIdMov, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateFechaMov, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(btnAgregarMov)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelarMov)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarMov)
                        .addGap(14, 14, 14)
                        .addComponent(btnEditarMov)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGuardarMov)))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jComboIdMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jDateFechaMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jComboTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtMontoMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarMov)
                    .addComponent(btnCancelarMov)
                    .addComponent(btnEliminarMov)
                    .addComponent(btnGuardarMov)
                    .addComponent(btnEditarMov))
                .addGap(63, 63, 63))
        );

        jTabbedPane1.addTab("Movimientos", jPanel3);

        jLabel19.setText("Tipo de cuenta:");

        jComboCuentaRe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cuentas" }));

        jLabel20.setText("Fecha inicio:");

        jLabel21.setText("Fecha fin:");

        btnGenerar.setText("Generar");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel20)
                            .addComponent(jLabel19))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboCuentaRe, 0, 106, Short.MAX_VALUE)
                            .addComponent(jDateFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateFechaFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(216, 216, 216)
                        .addComponent(btnGenerar)))
                .addContainerGap(208, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jComboCuentaRe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jDateFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jDateFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(74, 74, 74)
                .addComponent(btnGenerar)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Reportes", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarClienteActionPerformed
        try {
            addCliente();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnAgregarClienteActionPerformed

    private void btnCancelarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarClienteActionPerformed
        try {
            cleanCliente();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnCancelarClienteActionPerformed

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        try {
            eliminarCliente();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        try {
            editarCliente();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void btnAgregarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCuentaActionPerformed
        try {
            addCuenta();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnAgregarCuentaActionPerformed

    private void btnCancelarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCuentaActionPerformed
        cleanCuenta();
    }//GEN-LAST:event_btnCancelarCuentaActionPerformed

    private void btnEliminarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCuentaActionPerformed
        try {
            eliminarCuenta();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnEliminarCuentaActionPerformed

    private void btnEditarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCuentaActionPerformed
        try {
            editarCuenta();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnEditarCuentaActionPerformed

    private void btnGuardarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCuentaActionPerformed
        try {
            guardarCuenta();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnGuardarCuentaActionPerformed

    private void btnAgregarMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMovActionPerformed
       try {
            addMovimiento();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnAgregarMovActionPerformed

    private void btnCancelarMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarMovActionPerformed
        cleanMovimiento();
    }//GEN-LAST:event_btnCancelarMovActionPerformed

    private void btnEliminarMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarMovActionPerformed
      try {
            eliminarMovimiento();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnEliminarMovActionPerformed

    private void btnEditarMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarMovActionPerformed
       try {
            editarMovimiento();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnEditarMovActionPerformed

    private void btnGuardarMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarMovActionPerformed
        try {
            guardarMovimiento();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnGuardarMovActionPerformed

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        try {
            generarReporte();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnGenerarActionPerformed

    private void txtIdClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdClienteActionPerformed

    private void jComboIdClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboIdClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboIdClienteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrameBanco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameBanco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameBanco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameBanco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new JFrameBanco().setVisible(true);
                } catch (IOException ex) {}
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCliente;
    private javax.swing.JButton btnAgregarCuenta;
    private javax.swing.JButton btnAgregarMov;
    private javax.swing.JButton btnCancelarCliente;
    private javax.swing.JButton btnCancelarCuenta;
    private javax.swing.JButton btnCancelarMov;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarCuenta;
    private javax.swing.JButton btnEditarMov;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarCuenta;
    private javax.swing.JButton btnEliminarMov;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnGuardarCuenta;
    private javax.swing.JButton btnGuardarMov;
    private javax.swing.JComboBox<String> jComboCuentaRe;
    private javax.swing.JComboBox<String> jComboEstado;
    private javax.swing.JComboBox<String> jComboIdCliente;
    private javax.swing.JComboBox<String> jComboIdMov;
    private javax.swing.JComboBox<String> jComboTipoCuenta;
    private javax.swing.JComboBox<String> jComboTipoMovimiento;
    private com.toedter.calendar.JDateChooser jDateFechaFin;
    private com.toedter.calendar.JDateChooser jDateFechaInicio;
    private com.toedter.calendar.JDateChooser jDateFechaMov;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtApeMaterno;
    private javax.swing.JTextField txtApePaterno;
    private javax.swing.JTextField txtClabe;
    private javax.swing.JTextField txtCuenta;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFechaCuenta;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtMontoMov;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
