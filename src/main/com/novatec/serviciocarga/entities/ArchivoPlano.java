package com.novatec.serviciocarga.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArchivoPlano {

    private InputStreamReader archivo1;
    private File archivo2;
    public String linea; ////linea procesada actual
    private int totalL; ///total de lineas
    private String nombre; ////nombre del archivo leido
    private BufferedReader filtro;
    private String codificacion; ////setea la codificacion encode eje:UTF-8
    ///////variable que indica que no hay mas lineas q procesar
    private boolean finarchivo;

    /////construccion que setea la codificacion del archivo
    public ArchivoPlano(String cod) {

        this.codificacion = cod;

    }

    public void leerArchivo(String ruta) {

        try {

            FileInputStream archivo = new FileInputStream(ruta);

            ///obtiene el nombre del archivo
            this.archivo2 = new File(ruta);
            this.nombre = this.archivo2.getName();

            try {
                this.archivo1 = new InputStreamReader(archivo, this.codificacion);

            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ArchivoPlano.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArchivoPlano.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.filtro = new BufferedReader(this.archivo1);

    }

    ////para avanzar entre las lineas del archivo
    public void procesarLinea() {

        try {
            this.linea = this.filtro.readLine();
            this.totalL++;
            if (this.linea == null) {
                this.finarchivo = true;
            }

        } catch (IOException ex) {
            Logger.getLogger(ArchivoPlano.class.getName()).log(Level.SEVERE, null, ex);
        } catch (StringIndexOutOfBoundsException se) {

            this.linea = null;

        }

    }

    /**
     *
     * Trae el nombre del archivo anteriormente leido
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * trae exactamente un fragmento de la linea procesada
     *
     * @param desde (internamente le resta uno al desde)
     * @param tantos
     * @return
     */
    public String getFragmento(int desde, int tantos) {

        desde--;
        tantos = desde + tantos;
        try {
            return this.linea.substring(desde, tantos).trim();
        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }
    }

    ////total de lineas del archivo (debe recorrerse el archivo
    public int getTotalL() {
        return totalL;
    }

    public boolean isFinarchivo() {
        return finarchivo;
    }

    public void cerrarArchivo() {
        try {
            this.archivo1.close();
        } catch (IOException ex) {
            Logger.getLogger(ArchivoPlano.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void borrarArchivo() {

        this.cerrarArchivo();
        this.archivo2.setWritable(true);
        this.archivo2.delete();

    }
}
