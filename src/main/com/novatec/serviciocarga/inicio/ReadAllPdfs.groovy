package com.novatec.serviciocarga.inicio

//import static groovyx.gpars.GParsPool.withPool
import com.novatec.serviciocarga.entities.Status
import com.novatec.serviciocarga.observers.GenerarZips
import com.novatec.serviciocarga.observers.Progreso
import com.novatec.serviciocarga.pdfread.PdfDocumentWriter
import com.novatec.serviciocarga.entities.ValidarDirectorios

class ReadAllPdfs extends Observable {
    private final static def DIRECTORIO_DE_PROCESAR = ValidarDirectorios.directorios.procesar
    private final static def DIRECTORIO_PDFS_YA_PROCESADOS = ValidarDirectorios.directorios.directorioDePdfsProcessador
    private def progresso

    void Procesar(def tipoDeCuenta, def indicadores, def barra, def labels) {
        def listaDePDfs = [] as TreeSet
        def directorioDePdfs = new File(DIRECTORIO_DE_PROCESAR)

        progresso = new Progreso(indicadores, barra,labels)

        def PdfDocumentWriter pdfDocumentWriter = new PdfDocumentWriter()

        pdfDocumentWriter.addObserver(progresso)
        this.addObserver(progresso)
        this.addObserver(new GenerarZips(progresso))

        def carpetas = directorioDePdfs.listFiles()
        def countCarpetas = 0
        // withPool {
        carpetas.each { File carpeta ->
            def archivos = carpeta.listFiles()
            ++countCarpetas
            def count = 0
            archivos.each { File archivo ->
                setChanged()
                notifyObservers(new Status(total: archivos.size(), progreso: ++count,totalGeneral: carpetas.size(),progresoGeneral:countCarpetas))
                listaDePDfs << pdfDocumentWriter.ProcesarPdf(archivo.absolutePath, tipoDeCuenta)
            }
            new File(carpeta.absolutePath).renameTo(new File(DIRECTORIO_PDFS_YA_PROCESADOS + carpeta.name))
        }
        // }
        setChanged()
        notifyObservers(listaDePDfs)

    }

}
