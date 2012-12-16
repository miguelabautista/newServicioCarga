package com.novatec.serviciocarga.pdfread

import com.novatec.serviciocarga.entities.ArchivoPlano

/**
 * Created with IntelliJ IDEA.
 * User: MIGUEL
 * Date: 03/12/12
 * Time: 02:17 PM
 * To change this template use File | Settings | File Templates.
 */

//@Singleton(lazy = true)
class PdfDocumentReader {
    private static int contador
    int numpag
    String cuenta
    String fecha
    String dia
    String oficina
    String agencia
    String nombrePdf
    String docpathID
    boolean procesable
    String salida

    public void generarBinario(byte[] content, String pref) throws FileNotFoundException, IOException {
        File someFile = new File(pref);
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(content);
        fos.flush();
        fos.close();
    }

    def ReadPdf(def rutaPdf) {
        ArchivoPlano file;
        file = new ArchivoPlano("ISO-8859-1");
        file.leerArchivo(rutaPdf);
        file.procesarLinea();

        while (!file.isFinarchivo()) {
            if (file.linea.length() == 17) {
                ////sabe que es la linea antes de los indices
                if (file.getFragmento(1, 17).equals("1 0 0 1 0 3226 Tm")) {

                    file.procesarLinea(); ///la siguiente

                    cuenta = file.getFragmento(105, 23); ///cuenta
                    //cuenta=cuenta.substring(9, 19);//ultimos 10num de la cuenta
                    if (cuenta.isEmpty() == true) {
                        procesable = false;
                    } else {
                        try {
                            dia = file.getFragmento(89, 3);
                        } catch (StringIndexOutOfBoundsException ex) {
                            dia = "";
                           // LOGGER.info("no existe el dia");
                        }
                        fecha = file.getFragmento(76, 8); ///fecha
                        // nombrePdf = fecha.replace("-", "") + cuenta; //nombre del pdf (fecha + nro cuenta)
                        numpag = Integer.parseInt(file.getFragmento(136, 3));///numeros de paginas que se lleva el edo
                        procesable = true;
                        for (int i = 1; i < 10; i++) {
                            file.procesarLinea();
                        }
                        try {
                            oficina = file.getFragmento(70, 20);
                        } catch (StringIndexOutOfBoundsException exc) {
                            oficina = ""

                        }
                        for (int i = 1; i < 15; i++) {
                            file.procesarLinea();
                        }
                        try {
                            agencia = file.getFragmento(22, 3);
                        } catch (StringIndexOutOfBoundsException e) {
                            agencia = ""
                        }
                        String[] fechaSeparada = fecha.split("-");
                        nombrePdf = oficina + "_" + agencia + "_" + dia + "_" + fechaSeparada[0] + "_" + fechaSeparada[1] + "_" + cuenta;
                        // Log.info("dia: " + dia + " fecha: " + fecha + " oficina: " + oficina + " agencia: " + agencia + " nombrePdf: " + nombrePdf + " numpag: " + numpag);
                        break;
                    }
                }
            }
            file.procesarLinea();
        }
        file.borrarArchivo()
        return file
    }
}
