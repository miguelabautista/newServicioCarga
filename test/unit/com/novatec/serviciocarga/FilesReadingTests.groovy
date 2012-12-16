package com.novatec.serviciocarga

import griffon.test.GriffonUnitTestCase
import org.junit.Ignore
import org.junit.Test
import com.novatec.serviciocarga.pdfread.PdfDocumentWriter
import com.novatec.serviciocarga.pdfread.PdfDocumentReader
import com.novatec.serviciocarga.inicio.ReadAllPdfs
import com.novatec.serviciocarga.entities.*
import java.nio.file.Path
import java.nio.file.Files
import java.nio.file.StandardCopyOption


class FilesReadingTests extends GriffonUnitTestCase {
    def directorio = new File('./Procesar/')
    def directorioProcesados = new File('./Procesados')
    def directorioPdfYaProcesados = new File('./PdfProcesados')

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }
    
    @Ignore
    @Test
    void CrearDirectorioDeAlmacenamientoDePdfs() {
        if (!directorio.exists()) {
            directorio.mkdir()
        }
        println directorio.getAbsolutePath()
        assertTrue(directorio.exists())
    }

    @Ignore
    @Test
    void LeerArchivo() {
        def pdfs = directorio.listFiles()
        pdfs.each {println 'archivo ' + it.absoluteFile}
        assertEquals(2, pdfs.size())
    }

    @Ignore
    @Test
    void Procesados() {
        if (!directorioPdfYaProcesados.exists()) {
            directorioPdfYaProcesados.mkdir()
        }
        println directorioPdfYaProcesados.getAbsolutePath()
        assertTrue(directorioPdfYaProcesados.exists())
    }
    @Ignore
    @Test
    void PdfsYaProcesados() {
        if (!directorioProcesados.exists()) {
            directorioProcesados.mkdir()
        }
        println directorioProcesados.getAbsolutePath()
        assertTrue(directorioProcesados.exists())
    }
    @Ignore
    @Test
    void ProcesarPdf() {
        def pdfWriter = PdfDocumentWriter.instance
        def resultado = pdfWriter.ProcesarPdf("ED0011C02.PDF", "Ahorro")

        assertEquals("E:\\NOVATEC-PROJECTS\\NewServicioCarga\\.\\Procesados\\2010\\09\\CTASAHO\\", resultado)
    } 


   /* @Test
    void testProcesarTodos() {
        def files = new ReadAllPdfs()
        files.Procesar("Ahorro")
    } */

    @Ignore
    @Test
    void test(){
        Directorios di = new Directorios()
        di.procesar = 'e:/Procesar/'
        di.directorioDePdfsProcessador = 'e:/PdfProcesados/'
        di.direcotioProcesados = 'e:/Procesados'

        def file = new File('e:/datos.data').withObjectOutputStream {
            it.writeObject(di)
        }
        Directorios fina
        def filee = new File('e:/datos.data').withObjectInputStream {
            fina = it.readObject() as Directorios
        }
        assertEquals('e:/Procesar/',fina.procesar)
    }

    @Ignore
    @Test
    void testCorroborarRutas(){
        File file = new File('e:/NOVATEC-PROJECTS/0610 Corriente-2010/')

        def archivosACopiar = file.listFiles()

        println archivosACopiar.size()

        File file2 = new File("${directorio}\\${file.name}\\")

        println "ruta de la carpeta a copiar " + file2.absolutePath


        if(!file2.exists()){
            file2.mkdirs()
        }

        archivosACopiar.each{ 
            println "ruta de cada archivo " + file2.absolutePath+it.name
            Files.copy(new File(it.absolutePath).toPath(),new File(file2.absolutePath+"\\"+it.name).toPath(),StandardCopyOption.REPLACE_EXISTING)    
        }
    }
}
