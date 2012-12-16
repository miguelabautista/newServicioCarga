package com.novatec.serviciocarga.pdfread

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;



import java.text.DecimalFormat;
import java.text.SimpleDateFormat
import com.novatec.serviciocarga.entities.Status
import com.novatec.serviciocarga.entities.ValidarDirectorios;


//@Singleton(lazy = true)
class PdfDocumentWriter extends Observable {
    private static String RUTA;
    private final static String RUTA_TEMPORAL_FILE = "./temp.txt";
    private SimpleDateFormat sdf = new SimpleDateFormat("hh-mm");
    private DecimalFormat df = new DecimalFormat("00");
    private static int initialState, totalState, archivos = 0;
    private static String progressMesasage;
    private static String finishMessage;
    private static String tiempoTranscurrido;
    private List listaDeDirectorios = new ArrayList();
    // private final static def DIRECTORIO_DE_PROCESAR = './Procesar/'

    String ProcesarPdf(String archivo, String cuentaSeleccionada) throws DocumentException {

        String tipoCuenta = null;
        String directorioDePdfs = null;
        File directorioPadre = null;
        File directorioHijo = null;
        File directorioCuenta = null;
        String[] fechaArchivo = null;

        if (tipoCuenta == null && cuentaSeleccionada.equalsIgnoreCase("Corriente")) {
            tipoCuenta = "CTASCTES";
        } else if (tipoCuenta == null && cuentaSeleccionada.equalsIgnoreCase("Ahorro")) {
            tipoCuenta = "CTASAHO";
        }

        PdfReader reader = null;
        try {
            reader = new PdfReader(archivo);

            //numero de paginas del archivo historico
            int n = reader.getNumberOfPages();

            PdfImportedPage page = null;
            Image instance = null; ///imagen
            Document document = null;
            PdfWriter writer = null;

            PdfDocumentReader pdfDocumentReader = new PdfDocumentReader()

            byte[] content = null;

            int i = 1;
            while (i <= n) {
                setChanged()
                notifyObservers(new Status(total: n,progreso: i))
                content = reader.getPageContent(i)
                pdfDocumentReader.generarBinario(content, RUTA_TEMPORAL_FILE);
                pdfDocumentReader.ReadPdf(RUTA_TEMPORAL_FILE)


                if (pdfDocumentReader.isProcesable()) {

                    document = new Document(PageSize.LETTER, 2, 2, 2, 2);

                    try {

                        if (directorioPadre == null && directorioHijo == null && directorioCuenta == null && fechaArchivo == null) {

                            fechaArchivo = pdfDocumentReader.getFecha().split("-");

                            directorioPadre = new File("${ValidarDirectorios.directorios.direcotioProcesados}${fechaArchivo[1]}");
                            if (!directorioPadre.exists()) {
                                directorioPadre.mkdir();
                            }
                            directorioHijo = new File("${directorioPadre.getAbsolutePath()}\\${fechaArchivo[0]}");
                            if (!directorioHijo.exists()) {
                                directorioHijo.mkdir();
                            }
                            directorioCuenta = new File("${directorioPadre.getAbsolutePath()}\\${fechaArchivo[0]}\\${tipoCuenta}");
                            if (!directorioCuenta.exists()) {
                                directorioCuenta.mkdir();
                            }
                        }
                        writer = PdfWriter.getInstance(document,
                                new FileOutputStream("${directorioPadre.getAbsoluteFile()}\\${fechaArchivo[0]}\\$tipoCuenta\\${pdfDocumentReader.getNombrePdf()}.pdf"));

                    } catch (DocumentException e1) {
                    }
                    document.open();

                    for (int j = 0; j < pdfDocumentReader.getNumpag(); j++) {
                        try {
                            page = writer.getImportedPage(reader, i);
                            instance = Image.getInstance(page);
                            document.add(instance);
                        } catch (IllegalArgumentException e) {
                        }
                        i++; //incrementando las paginas
                    }
                    document.close();
                } else {
                    i++; //incrementando las paginas
                }
            }
        } catch (IOException e1) {
        } finally {
           try{
                directorioDePdfs = "${directorioPadre.getAbsoluteFile()}\\${fechaArchivo[0]}\\$tipoCuenta\\";
            }  catch(NullPointerException e){}
        }

        return directorioDePdfs;
    }
}
