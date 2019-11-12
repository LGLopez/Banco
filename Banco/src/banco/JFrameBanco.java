/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import com.itextpdf.text.Chunk;
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

    RandomAccessFile raf, rafAux, rafId, rafIdEliminado, rafCu, rafMo,rafCuId, rafCuIdEliminado, rafCuAux, rafMov, rafDe;
    File file, fileAux, fileId, fileIdEliminado, fileCu, fileMo, fileCuId, fileCuIdEliminado, fileCuAux, fileMov, fileDe;
    int Id, IdEliminado, IdCu, IdEliminadoCu;
    
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    
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
        
       
        for(int i = 1; i < Id; i++){
            jComboIdCliente.addItem(String.valueOf(i));
        }
        
        txtIdCliente.setText(String.valueOf(Id));
        rafId.close();
        
        Date date = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");       
        
    }
    
    String patronNombre = "^([A-Z]{1}[a-zA-Z\\s]+)$";
    String patronApellido = "^([A-Z]{1}[a-z]+)$";
    String patronEmail = "^([a-zA-Z0-9_\\-\\.]+@[a-zA-Z]{5,16}\\.[a-zA-Z]{3})$";
    String patronTelefono = "^([0-9]{10})$";
    String patronDireccion = "^([a-zA-Z0-9,.#\\s]+)$";
    String patronMonto = "^([0-9]+)$";
    String patronClabe = "^([0-9]{5})$";
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
        
        fileCuId = new File("IdCuentas.obj");
        rafCuId = new RandomAccessFile(fileCuId, "rw");
        
        if(fileCuId.length() == 0){
            IdCu = 1;
            rafCuId.writeInt(IdCu);
        }
        else{
           IdCu = rafCuId.readInt(); 
        }
        
        fileCuIdEliminado = new File("IdEliminadoCuentas.obj");
        rafCuIdEliminado = new RandomAccessFile(fileCuIdEliminado, "rw");
        
        if(fileCuIdEliminado.length() == 0){
            IdEliminadoCu = 0;
            rafCuIdEliminado.writeInt(IdEliminadoCu);
        }
        else{
           IdEliminadoCu = rafCuIdEliminado.readInt(); 
        }
        
        if(dateCuentas.getDate() == null){
            JOptionPane.showMessageDialog(this, "No se lleno el campo fecha.");
            return;
        }
        
        String idCliente;
        String clabe;
        String monto;
        String fecha;
        
        idCliente = (String) jComboIdCliente.getSelectedItem();
        clabe = txtClabe.getText();
        monto = txtMonto.getText();
        fecha = formatoFecha.format(dateCuentas.getDate());
        
        Pattern a = Pattern.compile(patronClabe);
        Pattern b = Pattern.compile(patronMonto);

        String aux, aux1 = null;
        int existe = 0, repetido = 0;
        try{
            for(int i = 0; i < Id - IdEliminado; i++){
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
            for(int i = 0; i < IdCu - IdEliminadoCu; i++){
                aux1 = rafCu.readUTF();
                if(idCliente.equals(aux1)){
                    repetido = 1;
                }
                else{
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

        IdCu++;
        rafCuId.seek(0);
        rafCuId.writeInt(IdCu);
        
        rafCuId.close();
        rafCuId.close();
        rafCuIdEliminado.close();
    }
    
    public void cleanCuenta(){
        txtClabe.setText("");
        txtMonto.setText("");
    }
    
    public void editarCuenta() throws FileNotFoundException, IOException{
        fileCu = new File("Cuentas.obj");
        rafCu = new RandomAccessFile(fileCu, "r");
        
        fileCuId = new File("IdCuentas.obj");
        rafCuId = new RandomAccessFile(fileCuId, "rw");
        
        if(fileCuId.length() == 0){
            IdCu = 1;
            rafCuId.writeInt(IdCu);
        }
        else{
           IdCu = rafCuId.readInt(); 
        }
        
        fileCuIdEliminado = new File("IdEliminadoCuentas.obj");
        rafCuIdEliminado = new RandomAccessFile(fileCuIdEliminado, "rw");
        
        if(fileCuIdEliminado.length() == 0){
            IdEliminadoCu = 0;
            rafCuIdEliminado.writeInt(IdEliminadoCu);
        }
        else{
           IdEliminadoCu = rafCuIdEliminado.readInt(); 
        }
        
        String buscar = JOptionPane.showInputDialog("Ingrese id: ");
     
        try{
            for(int i = 0; i < IdCu - IdEliminadoCu; i++){
                String a = rafCu.readUTF();
                String b = rafCu.readUTF();
                String c = rafCu.readUTF();
                String d = rafCu.readUTF();
               // String e = rafCu.readUTF();
                if(buscar.equals(a)){
                    jComboIdCliente.setSelectedItem(a);
                    txtClabe.setText(b);
                    txtMonto.setText(c);
                    //fechas
                }
            }
        }catch(EOFException ex){}
        rafCu.close();
        rafCuId.close();
        rafCuIdEliminado.close();
    }
    
    public void guardarCuenta() throws FileNotFoundException, IOException{
        fileCu = new File("Cuentas.obj");
        rafCu = new RandomAccessFile(fileCu, "r");
        
        fileCuAux = new File("CuentasAux.obj");
        rafCuAux = new RandomAccessFile(fileCuAux, "rw");
        
        fileCuId = new File("IdCuentas.obj");
        rafCuId = new RandomAccessFile(fileCuId, "rw");
        
        if(fileCuId.length() == 0){
            IdCu = 1;
            rafCuId.writeInt(IdCu);
        }
        else{
           IdCu = rafCuId.readInt(); 
        }
        
        fileCuIdEliminado = new File("IdEliminadoCuentas.obj");
        rafCuIdEliminado = new RandomAccessFile(fileCuIdEliminado, "rw");
        
        if(fileCuIdEliminado.length() == 0){
            IdEliminadoCu = 0;
            rafCuIdEliminado.writeInt(IdEliminadoCu);
        }
        else{
           IdEliminadoCu = rafCuIdEliminado.readInt(); 
        }
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        
        String idCliente;
        String clabe;
        String monto;
        String fecha;
        
        idCliente = (String) jComboIdCliente.getSelectedItem();
        clabe = txtClabe.getText();
        monto = txtMonto.getText();
        fecha = formatoFecha.format(dateCuentas.getDate());
        
        rafCu.seek(0);
        rafCuAux.seek(0);
        String aux = null;
        
        try{
            System.out.println(idCliente);
            for(int i = 0; i < IdCu - IdEliminadoCu; i++){
                System.out.println(aux);
                aux = rafCu.readUTF();
                if(!aux.equals(idCliente)){
                    rafCuAux.writeUTF(aux);
                    rafCuAux.writeUTF(rafCu.readUTF());
                    rafCuAux.writeUTF(rafCu.readUTF());
                    rafCuAux.writeUTF(rafCu.readUTF());
                    //rafAux.writeUTF(rafCu.readUTF());
                }
                else{
                    rafCu.readUTF();
                    rafCu.readUTF();
                    rafCu.readUTF();
                   // rafCu.readUTF();
                }
            }
        }catch(EOFException ex){}
        
        System.out.println(aux);
        rafCuAux.seek(rafCu.length());
        rafCuAux.writeUTF(idCliente);
        rafCuAux.writeUTF(clabe);
        rafCuAux.writeUTF(monto);
        rafCuAux.writeUTF(fecha);

        rafCu.close();
        rafCuAux.close();
        rafCuId.close();
        rafCuIdEliminado.close();
        if(aux!= null){
            fileCu.delete();
            fileCu = new File("Cuentas.obj");
            fileAux.renameTo(fileCu);
        }
        JOptionPane.showMessageDialog(this, "Editado exitosamente");
        cleanCuenta();
    }
    
    public void eliminarCuenta(String opc) throws FileNotFoundException, IOException{
        fileCu = new File("Cuentas.obj");
        rafCu = new RandomAccessFile(fileCu, "r");
        
        fileCuAux = new File("CuentasAux.obj");
        rafCuAux = new RandomAccessFile(fileCuAux, "rw");
        
        fileCuId = new File("IdCuentas.obj");
        rafCuId = new RandomAccessFile(fileCuId, "rw");
        
        if(fileCuId.length() == 0){
            IdCu = 1;
            rafCuId.writeInt(IdCu);
        }
        else{
           IdCu = rafCuId.readInt(); 
        }
        
        fileCuIdEliminado = new File("IdEliminadoCuentas.obj");
        rafCuIdEliminado = new RandomAccessFile(fileCuIdEliminado, "rw");
        
        if(fileCuIdEliminado.length() == 0){
            IdEliminadoCu = 0;
            rafCuIdEliminado.writeInt(IdEliminadoCu);
        }
        else{
           IdEliminadoCu = rafCuIdEliminado.readInt(); 
        }
        
        rafCu.seek(0);
        rafCuAux.seek(0);
        String aux = null;
        if(opc != null){
            try{
                for(int i = 0; i < IdCu - IdEliminadoCu; i++){
                    aux = rafCu.readUTF();
                    if(!aux.equals(opc)){
                        rafCuAux.writeUTF(aux);
                        rafCuAux.writeUTF(rafCu.readUTF());
                        rafCuAux.writeUTF(rafCu.readUTF());
                        rafCuAux.writeUTF(rafCu.readUTF());
                       // rafAux.writeUTF(rafCu.readUTF());
                    }
                    else{
                        rafCu.readUTF();
                        rafCu.readUTF();
                        rafCu.readUTF();
                      //  rafCu.readUTF();
                    }
                }   
            }catch(EOFException ex){}
            IdEliminadoCu++;
            rafCuIdEliminado.seek(0);
            rafCuIdEliminado.writeInt(IdEliminadoCu);
            JOptionPane.showMessageDialog(this, "Eliminado exitosamente");
        }
        
        rafCuId.close();
        rafCuIdEliminado.close();
        //rafCu.close();
        rafCuAux.close();
        if(aux != null){
            fileCu.delete();
            fileCu = new File("Cuentas.obj");
            fileCuAux.renameTo(fileCu);
        }
        
    }
    
    public void addTransferencia() throws FileNotFoundException, IOException{
        int sizeOfMov = 0;
        
        fileCu = new File("Cuentas.obj");
        rafCu = new RandomAccessFile(fileCu, "rw");
        
        fileCuId = new File("IdCuentas.obj");
        rafCuId = new RandomAccessFile(fileCuId, "rw");
        
        fileCuAux = new File("CuentasAux.obj");
        rafCuAux = new RandomAccessFile(fileCuAux, "rw");
        
        fileMov = new File("Movimientos.obj");
        rafMov = new RandomAccessFile(fileMov, "rw");
        
        if(fileMov.length() == 0){
            sizeOfMov = 0;
            rafMov.writeInt(sizeOfMov);
        }
        else{
            sizeOfMov = rafMov.readInt();
        }
        
        if(fileCuId.length() == 0){
            IdCu = 1;
            rafCuId.writeInt(IdCu);
        }
        else{
           IdCu = rafCuId.readInt(); 
        }
        
        fileCuIdEliminado = new File("IdEliminadoCuentas.obj");
        rafCuIdEliminado = new RandomAccessFile(fileCuIdEliminado, "rw");
        
        if(fileCuIdEliminado.length() == 0){
            IdEliminadoCu = 0;
            rafCuIdEliminado.writeInt(IdEliminadoCu);
        }
        else{
           IdEliminadoCu = rafCuIdEliminado.readInt(); 
        }
        
        String a = null, b = null, c = null, d = null, A = null, B = null, C = null, D = null;
        String aux2 = null, aux3 = null;
        int creada1 = 0, creada2 = 0;
        
        String aux = txtOrigenTra.getText();
        String aux1 = txtDestinoTra.getText();
        String cantidad = txtMontoTra.getText();
        String fechaMov = formatoFecha.format(jDateFechaTra.getDate());
        
        Pattern x = Pattern.compile(patronClabe);
        Pattern y = Pattern.compile(patronMonto);
        
        if(jDateFechaTra.getDate() == null){
            JOptionPane.showMessageDialog(this, "No se lleno el campo fecha.");
            return;
        }
        if(!x.matcher(aux).matches()){
            JOptionPane.showMessageDialog(this, "CLABE de origen invalida");
        }
        else if(!x.matcher(aux1).matches()){
            JOptionPane.showMessageDialog(this, "CLABE de destino invalida");
        }
        else if(!y.matcher(cantidad).matches()){
            JOptionPane.showMessageDialog(this, "Monto invalido");
        }
        else{
            if(aux.equals(aux1)){
                JOptionPane.showMessageDialog(this, "La CLABE no puede ser la misma");
            }
        else{
            try{
                for(int i = 0; i < IdCu - IdEliminadoCu; i++){
                    a = rafCu.readUTF();
                    b = rafCu.readUTF();
                    c = rafCu.readUTF();
                    d = rafCu.readUTF();
                
                    if(aux.equals(b)){
                        creada1 = 1;

                        rafCu.seek(0);
                        for(int j = 0; j < IdCu - IdEliminadoCu; j++){
                            A = rafCu.readUTF();
                            B = rafCu.readUTF();
                            C = rafCu.readUTF();
                            D = rafCu.readUTF();                   
                        
                            if(aux1.equals(B)){
                                creada2 = 1;
                                if(Integer.parseInt(cantidad) <= Integer.parseInt(c)){
                                    rafCu.seek(0);
                                    for(int k = 0; k < IdCu - IdEliminadoCu; k++){
                                        aux2 = rafCu.readUTF();
                                        if(!aux2.equals(a)){
                                        
                                            rafCuAux.writeUTF(aux2);
                                            rafCuAux.writeUTF(rafCu.readUTF());
                                            rafCuAux.writeUTF(rafCu.readUTF());
                                            rafCuAux.writeUTF(rafCu.readUTF());
                                            //rafAux.writeUTF(rafCu.readUTF());
                                        }
                                        else{
                                            rafCu.readUTF();
                                            rafCu.readUTF();
                                            rafCu.readUTF();
                                           // rafCu.readUTF();
                                        }                                   
                                    }
                                }
                            }
                        }
                    }
                }
            } catch(EOFException ex){}
            
            if(creada1 == 1){
                rafCuAux.seek(rafCuAux.length());
                rafCuAux.writeUTF(a);
                rafCuAux.writeUTF(b);
                rafCuAux.writeUTF(String.valueOf(Integer.parseInt(c) - Integer.parseInt(cantidad)));
                rafCuAux.writeUTF(d);
                rafCuAux.close();
            }
            
            rafCu.close();
            rafCuAux.close();
            rafCuId.close();
            rafCuIdEliminado.close();
            
            rafMov.seek(0);
            sizeOfMov++;
            rafMov.writeInt(sizeOfMov);
            rafMov.seek(rafMov.length());
            rafMov.writeUTF(aux);
            rafMov.writeUTF(aux1);
            rafMov.writeUTF(cantidad);
            rafMov.writeUTF(fechaMov);

                if(aux2 != null){
                    fileCu.delete();
                    fileCu = new File("Cuentas.obj");
                    fileCuAux.renameTo(fileCu);
                }
                
            fileCuAux = new File("CuentasAux.obj");
            rafCuAux = new RandomAccessFile(fileCuAux, "rw");
            fileCu = new File("Cuentas.obj");
            rafCu = new RandomAccessFile(fileCu, "rw");
            
            try{
                if(creada2 == 1){
                    for(int k = 0; k < IdCu - IdEliminadoCu; k++){
                        aux2 = rafCu.readUTF();
                        if(!aux2.equals(A)){   
                            rafCuAux.writeUTF(aux2);
                            rafCuAux.writeUTF(rafCu.readUTF());
                            rafCuAux.writeUTF(rafCu.readUTF());
                            rafCuAux.writeUTF(rafCu.readUTF());
                            //rafAux.writeUTF(rafCu.readUTF());
                        }
                        else{
                            rafCu.readUTF();
                            rafCu.readUTF();
                            rafCu.readUTF();
                             // rafCu.readUTF();
                        }                                  
                    }     
                }
            } catch(EOFException ex){}
                
            if(creada2== 1){
                rafCuAux.seek(rafCuAux.length());
                rafCuAux.writeUTF(A);
                rafCuAux.writeUTF(B);
                rafCuAux.writeUTF(String.valueOf(Integer.parseInt(C) + Integer.parseInt(cantidad)));
                rafCuAux.writeUTF(D);
                
            }
            rafMov.close();
            rafCu.close();
            rafCuAux.close();
            rafCuId.close();
            rafCuIdEliminado.close();
             
            if(aux2 != null){
                fileCu.delete();
                fileCu = new File("Cuentas.obj");
                fileCuAux.renameTo(fileCu);
            }
        
        JOptionPane.showMessageDialog(this, "Se hizo la transferencia correctamente");
        }
    }
}
    
    public void cleanTransferencia(){
        txtMontoTra.setText("");
    }
    
    public void addDeposito() throws FileNotFoundException, IOException{
        int sizeOfDe = 0;
        
        fileDe = new File("Depositos.obj");
        rafDe = new RandomAccessFile(fileDe, "rw");
        
        fileCu = new File("Cuentas.obj");
        rafCu = new RandomAccessFile(fileCu, "rw");
        
        fileCuId = new File("IdCuentas.obj");
        rafCuId = new RandomAccessFile(fileCuId, "rw");
        
        fileCuAux = new File("CuentasAux.obj");
        rafCuAux = new RandomAccessFile(fileCuAux, "rw");
        
        rafDe.seek(0);
        if(fileDe.length() == 0){
            rafDe.writeInt(0);
            sizeOfDe = 0;
        }
        else{
            sizeOfDe = rafDe.readInt();
        }
        
        if(fileCuId.length() == 0){
            IdCu = 1;
            rafCuId.writeInt(IdCu);
        }
        else{
           IdCu = rafCuId.readInt(); 
        }
        
        fileCuIdEliminado = new File("IdEliminadoCuentas.obj");
        rafCuIdEliminado = new RandomAccessFile(fileCuIdEliminado, "rw");
        
        if(fileCuIdEliminado.length() == 0){
            IdEliminadoCu = 0;
            rafCuIdEliminado.writeInt(IdEliminadoCu);
        }
        else{
           IdEliminadoCu = rafCuIdEliminado.readInt(); 
        }
        
        String a = null,b = null,c = null,d = null;
        String aux2 = null; 
        int creada = 0;
        String aux = txtDestinoDep.getText();
        String cantidad = txtMontoDep.getText();
        String fechaDe = formatoFecha.format(jDateFechaDep.getDate());
        
        Pattern x = Pattern.compile(patronClabe);
        Pattern y = Pattern.compile(patronMonto);
        
        if(jDateFechaDep.getDate() == null){
            JOptionPane.showMessageDialog(this, "No se lleno el campo fecha.");
            return;
        }
        if(!x.matcher(aux).matches()){
            JOptionPane.showMessageDialog(this, "CLABE invalida");
        }
        else if(!y.matcher(cantidad).matches()){
            JOptionPane.showMessageDialog(this, "Monto invalido");
        }
        else{
        
        try{
            for(int i = 0; i < IdCu - IdEliminadoCu; i++){
                
                a = rafCu.readUTF();
                b = rafCu.readUTF();
                c = rafCu.readUTF();
                d = rafCu.readUTF();
                
                if(aux.equals(b)){
                    creada = 1;
                    for(int j = 0; j < IdCu - IdEliminadoCu; j++){
                        aux2 = rafCu.readUTF();
                        if(!aux2.equals(a)){
                            rafCuAux.writeUTF(aux2);
                            rafCuAux.writeUTF(rafCu.readUTF());
                            rafCuAux.writeUTF(rafCu.readUTF());
                            rafCuAux.writeUTF(rafCu.readUTF());
                            //rafAux.writeUTF(rafCu.readUTF());
                        }
                        else{
                            rafCu.readUTF();
                            rafCu.readUTF();
                            rafCu.readUTF();
                           // rafCu.readUTF();
                        }
                    }
                }
            }
        }catch(EOFException ex){}
            
        rafDe.seek(0);
        sizeOfDe++;
        rafDe.writeInt(sizeOfDe);
        rafDe.seek(rafDe.length());
        rafDe.writeUTF(aux);
        rafDe.writeUTF(cantidad);
        rafDe.writeUTF(fechaDe);
  
        if(creada == 1){
            aux2 = "hola";
                System.out.println(aux2);
                
                rafCuAux.seek(rafCuAux.length());
                rafCuAux.writeUTF(a);
                rafCuAux.writeUTF(b);
                rafCuAux.writeUTF(String.valueOf(Integer.parseInt(c) + Integer.parseInt(cantidad)));
                rafCuAux.writeUTF(d);
                
        }
                rafDe.close();
                rafCu.close();
                rafCuAux.close();
                rafCuId.close();
                rafCuIdEliminado.close();
                
                if(aux2 != null){
                    fileCu.delete();
                    fileCu = new File("Cuentas.obj");
                    fileCuAux.renameTo(fileCu);
                }
            
       
        JOptionPane.showMessageDialog(this, "Se hizo el deposito correctamente");
        }
    }
    
    public void generarReporte(Document documento, String tipoMovimiento) throws IOException{
        Date dInicio = jDateFechaInicio.getDate();
        Date dFinal = jDateFechaFin.getDate();
        
        if(dInicio == null || dFinal == null){
            JOptionPane.showMessageDialog(this, "No se han llenado todas las fechas.");
            return;
        }
        
        String fechaStringInicio = formatoFecha.format(dInicio);
        String fechaStringFinal = formatoFecha.format(dFinal);
        
        documento.addAuthor("Banco el cerdin");
        documento.addTitle("Reportes");
        
        boolean estaEscrito = false, fechaCorrecta = false;
        
        if(Integer.parseInt(fechaStringInicio.substring(6,10)) > Integer.parseInt(fechaStringFinal.substring(6, 10))){
            JOptionPane.showMessageDialog(this, "No hay un rango correcto de fechas(Año).");
            return;
        }
        else{
            fechaCorrecta = true;
        }
        
        if(Integer.parseInt(fechaStringInicio.substring(3, 5)) > Integer.parseInt(fechaStringFinal.substring(3, 5)) && !fechaCorrecta){
            JOptionPane.showMessageDialog(this, "No hay un rango correcto de fechas(Mes).");
            return;
        }
        else{
            fechaCorrecta = true;
        }
        
        if(Integer.parseInt(fechaStringInicio.substring(0, 2)) > Integer.parseInt(fechaStringFinal.substring(0, 2)) && !fechaCorrecta){
            JOptionPane.showMessageDialog(this, "No hay un rango correcto de fechas(Dia).");
            return;
        }
        
        if(tipoMovimiento.equals("Depositos")){
            fileMov = new File("Movimientos.obj");
            rafMov = new RandomAccessFile(fileMov, "r");
            try {
                PdfWriter.getInstance(documento, new FileOutputStream("Reportes depositos.pdf"));
                documento.open();

                rafMov.seek(0);
                int sizeOfMov = rafMov.readInt();

                for(int i =0; i<sizeOfMov; i++){
                    boolean movimientoImpreso = false;

                    String envia = rafMov.readUTF();
                    String recibe = rafMov.readUTF();
                    String cantidad = rafMov.readUTF();
                    String fechaDocumento = rafMov.readUTF();

                    String revisarDia = fechaDocumento.substring(0,2);
                    String revisarMes = fechaDocumento.substring(3,5);
                    String revisarAnio = fechaDocumento.substring(6,10);

                    if(Integer.parseInt(fechaStringInicio.substring(6, 10)) < Integer.parseInt(revisarAnio) && Integer.parseInt(fechaStringFinal.substring(6,10)) > Integer.parseInt(revisarAnio)){
                        estaEscrito = true;
                        movimientoImpreso = true;
                        documento.add(new Paragraph("Cuenta origen: "+envia));
                        documento.add(Chunk.NEWLINE);
                        documento.add(new Paragraph("Cuenta destino: "+recibe));
                        documento.add(Chunk.NEWLINE);
                        documento.add(new Paragraph("Cantidad depositada: "+cantidad));
                        documento.add(Chunk.NEWLINE);
                        documento.add(new Paragraph("Fecha del movimiento: "+fechaDocumento));
                        documento.add(Chunk.NEWLINE);
                        documento.add(Chunk.NEWLINE);
                    }
                    int fechaInicioMes = Integer.parseInt(fechaStringInicio.substring(3, 5));
                    int fechaFinalMes = Integer.parseInt(fechaStringFinal.substring(3, 5));
                    int checkMes = Integer.parseInt(revisarMes);

                    if(fechaInicioMes < checkMes &&  fechaFinalMes > checkMes && !movimientoImpreso){
                        estaEscrito = true;
                        movimientoImpreso = true;
                        documento.add(new Paragraph("Cuenta origen: "+envia));
                        documento.add(Chunk.NEWLINE);
                        documento.add(new Paragraph("Cuenta destino: "+recibe));
                        documento.add(Chunk.NEWLINE);
                        documento.add(new Paragraph("Cantidad depositada: "+cantidad));
                        documento.add(Chunk.NEWLINE);
                        documento.add(new Paragraph("Fecha del movimiento: "+fechaDocumento));
                        documento.add(Chunk.NEWLINE);
                        documento.add(Chunk.NEWLINE);
                    }

                    int fechaInicioDia = Integer.parseInt(fechaStringInicio.substring(0, 2));
                    int fechaFinalDia = Integer.parseInt(fechaStringFinal.substring(0, 2));
                    int checkDia = Integer.parseInt(revisarDia);

                    if(fechaInicioDia < checkDia && fechaFinalDia > checkDia && !movimientoImpreso){
                        estaEscrito = true;
                        documento.add(new Paragraph("Cuenta origen: "+envia));
                        documento.add(Chunk.NEWLINE);
                        documento.add(new Paragraph("Cuenta destino: "+recibe));
                        documento.add(Chunk.NEWLINE);
                        documento.add(new Paragraph("Cantidad depositada: "+cantidad));
                        documento.add(Chunk.NEWLINE);
                        documento.add(new Paragraph("Fecha del movimiento: "+fechaDocumento));
                        documento.add(Chunk.NEWLINE);
                        documento.add(Chunk.NEWLINE);
                    }
                }
                if(!estaEscrito){
                documento.add(new Paragraph("Ningun movimiento sucedio en esa fecha"));
                }
            } catch (DocumentException ex) {
                System.out.println("Error al crear el PDF");
            }

            rafMov.close();
            documento.close();
        }
        else{
            int sizeOfDe = 0;
        
            fileDe = new File("Depositos.obj");
            rafDe = new RandomAccessFile(fileDe, "r");
            
            rafDe.seek(0);
            sizeOfDe = rafDe.readInt();
            
            try {
                PdfWriter.getInstance(documento, new FileOutputStream("Reportes transferencias.pdf"));
                documento.open();

                for(int i =0; i<sizeOfDe; i++){
                    boolean movimientoImpreso = false;

                    String aux = rafDe.readUTF();
                    String cantidad = rafDe.readUTF();
                    String fechaDocumento = rafDe.readUTF();

                    String revisarDia = fechaDocumento.substring(0,2);
                    String revisarMes = fechaDocumento.substring(3,5);
                    String revisarAnio = fechaDocumento.substring(6,10);

                    if(Integer.parseInt(fechaStringInicio.substring(6, 10)) < Integer.parseInt(revisarAnio) && Integer.parseInt(fechaStringFinal.substring(6,10)) > Integer.parseInt(revisarAnio)){
                        estaEscrito = true;
                        movimientoImpreso = true;
                        documento.add(new Paragraph("Cuenta: "+aux));
                        documento.add(Chunk.NEWLINE);
                        documento.add(new Paragraph("Cantidad depositada: "+cantidad));
                        documento.add(Chunk.NEWLINE);
                        documento.add(new Paragraph("Fecha del movimiento: "+fechaDocumento));
                        documento.add(Chunk.NEWLINE);
                        documento.add(Chunk.NEWLINE);
                    }
                    int fechaInicioMes = Integer.parseInt(fechaStringInicio.substring(3, 5));
                    int fechaFinalMes = Integer.parseInt(fechaStringFinal.substring(3, 5));
                    int checkMes = Integer.parseInt(revisarMes);

                    if(fechaInicioMes < checkMes &&  fechaFinalMes > checkMes && !movimientoImpreso){
                        estaEscrito = true;
                        movimientoImpreso = true;
                        documento.add(new Paragraph("Cuenta: "+aux));
                        documento.add(Chunk.NEWLINE);
                        documento.add(new Paragraph("Cantidad depositada: "+cantidad));
                        documento.add(Chunk.NEWLINE);
                        documento.add(new Paragraph("Fecha del movimiento: "+fechaDocumento));
                        documento.add(Chunk.NEWLINE);
                        documento.add(Chunk.NEWLINE);
                    }

                    int fechaInicioDia = Integer.parseInt(fechaStringInicio.substring(0, 2));
                    int fechaFinalDia = Integer.parseInt(fechaStringFinal.substring(0, 2));
                    int checkDia = Integer.parseInt(revisarDia);

                    if(fechaInicioDia < checkDia && fechaFinalDia > checkDia && !movimientoImpreso){
                        estaEscrito = true;
                        documento.add(new Paragraph("Cuenta: "+aux));
                        documento.add(Chunk.NEWLINE);
                        documento.add(new Paragraph("Cantidad depositada: "+cantidad));
                        documento.add(Chunk.NEWLINE);
                        documento.add(new Paragraph("Fecha del movimiento: "+fechaDocumento));
                        documento.add(Chunk.NEWLINE);
                        documento.add(Chunk.NEWLINE);
                    }
                }
                if(!estaEscrito){
                    documento.add(new Paragraph("Ningun movimiento sucedio en esa fecha"));
                }
            } catch (DocumentException ex) {
                System.out.println("Error al crear el PDF");
            }

            rafDe.close();
            documento.close();
        }
        
    }
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
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
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtClabe = new javax.swing.JTextField();
        jComboIdCliente = new javax.swing.JComboBox<>();
        txtMonto = new javax.swing.JTextField();
        btnAgregarCuenta = new javax.swing.JButton();
        btnCancelarCuenta = new javax.swing.JButton();
        btnEliminarCuenta = new javax.swing.JButton();
        btnEditarCuenta = new javax.swing.JButton();
        btnGuardarCuenta = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        dateCuentas = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jComboCuentaRe = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnGenerar = new javax.swing.JButton();
        jDateFechaInicio = new com.toedter.calendar.JDateChooser();
        jDateFechaFin = new com.toedter.calendar.JDateChooser();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jDateFechaTra = new com.toedter.calendar.JDateChooser();
        txtMontoTra = new javax.swing.JTextField();
        btnCancelarTra = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnGuardarTra = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txtOrigenTra = new javax.swing.JTextField();
        txtDestinoTra = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jDateFechaDep = new com.toedter.calendar.JDateChooser();
        txtMontoDep = new javax.swing.JTextField();
        btnCancelarDep = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        btnGuardarDep = new javax.swing.JButton();
        txtDestinoDep = new javax.swing.JTextField();

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

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
                            .addComponent(txtEmail)
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

        jLabel12.setText("Monto:");

        jLabel13.setText("Fecha:");

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
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtClabe)
                            .addComponent(jComboIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMonto)
                            .addComponent(dateCuentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(dateCuentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarCuenta)
                    .addComponent(btnCancelarCuenta)
                    .addComponent(btnEliminarCuenta)
                    .addComponent(btnEditarCuenta)
                    .addComponent(btnGuardarCuenta))
                .addGap(62, 62, 62))
        );

        jTabbedPane1.addTab("Cuentas", jPanel2);

        jLabel19.setText("Tipo de cuenta:");

        jComboCuentaRe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Depositos", "Transferencias" }));

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
                            .addComponent(jComboCuentaRe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateFechaFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(216, 216, 216)
                        .addComponent(btnGenerar)))
                .addContainerGap(217, Short.MAX_VALUE))
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

        jLabel17.setText("Monto:");

        btnCancelarTra.setText("Cancelar");
        btnCancelarTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarTraActionPerformed(evt);
            }
        });

        jLabel14.setText("Id Cuenta Origen:");

        jLabel15.setText("Fecha:");

        btnGuardarTra.setText("Guardar");
        btnGuardarTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarTraActionPerformed(evt);
            }
        });

        jLabel16.setText("Id Cuenta Destino:");

        txtDestinoTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDestinoTraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(145, 145, 145)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(1, 1, 1))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMontoTra, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateFechaTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDestinoTra, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                            .addComponent(txtOrigenTra)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(btnGuardarTra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelarTra)))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtOrigenTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtDestinoTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMontoTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jDateFechaTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarTra)
                    .addComponent(btnGuardarTra))
                .addGap(63, 63, 63))
        );

        jTabbedPane2.addTab("Transferecias", jPanel6);

        jLabel24.setText("Monto:");

        btnCancelarDep.setText("Cancelar");
        btnCancelarDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarDepActionPerformed(evt);
            }
        });

        jLabel26.setText("CLABE cuenta destino :");

        jLabel27.setText("Fecha:");

        btnGuardarDep.setText("Guardar");
        btnGuardarDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarDepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel24)
                    .addComponent(btnGuardarDep))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnCancelarDep)
                        .addComponent(txtMontoDep)
                        .addComponent(jDateFechaDep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtDestinoDep, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDestinoDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateFechaDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtMontoDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarDep)
                    .addComponent(btnGuardarDep))
                .addGap(63, 63, 63))
        );

        jTabbedPane2.addTab("Depositos", jPanel7);

        jTabbedPane1.addTab("Moviminetos", jTabbedPane2);

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

    private void btnGuardarTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarTraActionPerformed
        try {
            addTransferencia();
        } catch (IOException ex) {
            System.err.print("Ocurrio un fallo en la transferencia");
        }
    }//GEN-LAST:event_btnGuardarTraActionPerformed

    private void btnCancelarTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarTraActionPerformed

    }//GEN-LAST:event_btnCancelarTraActionPerformed

    private void btnGuardarDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarDepActionPerformed
        try {
            addDeposito();
        } catch (IOException ex) {
            Logger.getLogger(JFrameBanco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarDepActionPerformed

    private void btnCancelarDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarDepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarDepActionPerformed

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        Document doc = new Document(PageSize.A4);
        String tipoCuenta = jComboCuentaRe.getSelectedItem().toString();
        
        try {
            generarReporte(doc, tipoCuenta);
        } catch (IOException ex) {
            doc.close();
            System.out.println("561No se pudo crear el pdf");
        }
    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnGuardarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCuentaActionPerformed
        try {
            guardarCuenta();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnGuardarCuentaActionPerformed

    private void btnEditarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCuentaActionPerformed
        try {
            editarCuenta();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnEditarCuentaActionPerformed

    private void btnEliminarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCuentaActionPerformed
        
        String opc = JOptionPane.showInputDialog("Ingrese Id a eliminar");
        try {
            eliminarCuenta(opc);
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnEliminarCuentaActionPerformed

    private void btnCancelarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCuentaActionPerformed
        cleanCuenta();
    }//GEN-LAST:event_btnCancelarCuentaActionPerformed

    private void btnAgregarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCuentaActionPerformed
        try {
            addCuenta();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnAgregarCuentaActionPerformed

    private void jComboIdClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboIdClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboIdClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        try {
            editarCliente();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnEditarClienteActionPerformed

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

    private void btnAgregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarClienteActionPerformed
        try {
            addCliente();
        } catch (IOException ex) {}
    }//GEN-LAST:event_btnAgregarClienteActionPerformed

    private void txtIdClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdClienteActionPerformed

    private void txtDestinoTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDestinoTraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDestinoTraActionPerformed

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
    private javax.swing.JButton btnCancelarCliente;
    private javax.swing.JButton btnCancelarCuenta;
    private javax.swing.JButton btnCancelarDep;
    private javax.swing.JButton btnCancelarTra;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarCuenta;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarCuenta;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnGuardarCuenta;
    private javax.swing.JButton btnGuardarDep;
    private javax.swing.JButton btnGuardarTra;
    private com.toedter.calendar.JDateChooser dateCuentas;
    private javax.swing.JComboBox<String> jComboCuentaRe;
    private javax.swing.JComboBox<String> jComboEstado;
    private javax.swing.JComboBox<String> jComboIdCliente;
    private com.toedter.calendar.JDateChooser jDateFechaDep;
    private com.toedter.calendar.JDateChooser jDateFechaFin;
    private com.toedter.calendar.JDateChooser jDateFechaInicio;
    private com.toedter.calendar.JDateChooser jDateFechaTra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField txtApeMaterno;
    private javax.swing.JTextField txtApePaterno;
    private javax.swing.JTextField txtClabe;
    private javax.swing.JTextField txtDestinoDep;
    private javax.swing.JTextField txtDestinoTra;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtMontoDep;
    private javax.swing.JTextField txtMontoTra;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtOrigenTra;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
