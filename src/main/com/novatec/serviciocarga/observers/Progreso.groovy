package com.novatec.serviciocarga.observers

import com.novatec.serviciocarga.pdfread.PdfDocumentWriter
import com.novatec.serviciocarga.inicio.ReadAllPdfs
import com.novatec.serviciocarga.entities.Status
import groovyx.javafx.beans.FXBindable
import com.novatec.serviciocarga.entities.ProgresoLabels

/**
 * Created with IntelliJ IDEA.
 * User: MIGUEL
 * Date: 03/12/12
 * Time: 04:35 PM
 * To change this template use File | Settings | File Templates.
 */
class Progreso implements Observer {
    def indicatorProgreso
    def indicadorZip
    def indicadorDelete

    def barra

    ProgresoLabels progresoLabels

    Progreso(def indicadores, def barra, def labels) {
        this.indicatorProgreso = indicadores[0]
        this.indicadorZip = indicadores[1]
        this.indicadorDelete = indicadores[2]

        this.progresoLabels = labels

        this.barra = barra
    }

    @Override
    void update(Observable o, Object arg) {
        if (o instanceof PdfDocumentWriter) {
            Status status = arg as Status
            progresoLabels.progresoGeneralLabel = "procesando archivos ${status.progreso} de un total de ${status.total}"
            println("va corriendo ${status.progreso} de un total de ${status.total}")
            indicatorProgreso.progress = (status.progreso * 1).div(status.total)
        } else if (o instanceof ReadAllPdfs && arg instanceof Status) {
            if (barra.progress == 0) {
                indicatorProgreso.progress = 0
                indicadorZip.progress = 0
                indicadorDelete.progress = 0
            }
            Status status = arg as Status
            println("<---------------Status Total es ${status.progreso} de un total de ${status.total}------------->")
            barra.progress = (status.progreso * 1).div(status.total)
            progresoLabels.mesesLabel = "Meses ${status.progresoGeneral}/${status.totalGeneral}"
            progresoLabels.progresoArchivosLabel = "Procesando ${status.progreso}/${status.total}"
        } else if (o instanceof DeletePdfs && arg instanceof Status) {
            Status status = arg as Status
            progresoLabels.progresoGeneralLabel =  "eliminando archivos ${status.progreso} de un total de ${status.total}"
            println("<---------------Status de eliminacion de files es ${status.progreso} de un total de ${status.total}------------->")
            indicadorDelete.progress = (status.progreso * 1).div(status.total)
            progresoLabels.progresoDeleteLabel = "Eliminando ${status.progresoGeneral}/${status.totalGeneral}"
        } else if (o instanceof GenerarZips && arg instanceof Status) {
            Status status = arg as Status
            progresoLabels.progresoGeneralLabel =  "guardando archivos a zip ${status.progreso} de un total de ${status.total}"
            println("<---------------Status de generacion de zips es ${status.progreso} de un total de ${status.total}------------->")
            indicadorZip.progress = (status.progreso * 1).div(status.total)
            progresoLabels.progresoZipsLabel = "Zip ${status.progresoGeneral}/${status.totalGeneral}"
        }
    }
}
