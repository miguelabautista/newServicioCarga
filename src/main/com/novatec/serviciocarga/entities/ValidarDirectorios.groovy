package com.novatec.serviciocarga.entities

/**
 * Created with IntelliJ IDEA.
 * User: MIGUEL
 * Date: 05/12/12
 * Time: 10:22 AM
 * To change this template use File | Settings | File Templates.
 */
class ValidarDirectorios {
    static def file = new File('.datos.data')
    static Directorios di = new Directorios()

    static void validar() {

        if (!file.exists()) {
            di.procesar = './Procesar/'
            di.directorioDePdfsProcessador = './PdfProcesados/'
            di.direcotioProcesados = './Procesados/'

            def file = new File('./datos.data').withObjectOutputStream {
                it.writeObject(di)
            }
        }
        def filee = new File('./datos.data').withObjectInputStream {
            di = it.readObject() as Directorios
        }
        def directorioProcesar = new File(di.procesar)
        if (!directorioProcesar.exists()) {
            directorioProcesar.mkdirs()
        }
        def directorioDePdfProce = new File(di.directorioDePdfsProcessador)
        if (!directorioDePdfProce.exists()) {
            directorioDePdfProce.mkdirs()
        }
        def directorioProce = new File(di.direcotioProcesados)
        if (!directorioProce.exists()) {
            directorioProce.mkdirs()
        }
    }

    static Directorios getDirectorios() {
        def filee = new File('./datos.data').withObjectInputStream {
            di = it.readObject() as Directorios
        }
        return di
    }
}
