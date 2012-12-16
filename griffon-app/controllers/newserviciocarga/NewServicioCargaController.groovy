package newserviciocarga

import com.novatec.serviciocarga.inicio.ReadAllPdfs

import com.novatec.serviciocarga.entities.ProgresoLabels
import javafx.concurrent.Task
import com.novatec.serviciocarga.entities.ValidarDirectorios
import javafx.stage.DirectoryChooser

import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.text.DecimalFormat
import jfxtras.labs.scene.control.gauge.SplitFlap
import javafx.scene.paint.Color
import jfxtras.labs.scene.control.gauge.SplitFlapBuilder
import javafx.scene.layout.GridPane
import javafx.geometry.Insets
import javafx.geometry.Pos

class NewServicioCargaController {
    def model
    def view
    def indicadores = []
    def labels = []
    def barra
    ProgresoLabels progresoLabels

    void mvcGroupInit(Map args) {
        ValidarDirectorios.validar()
        indicadores << view.progresoArchivo
        indicadores << view.progresoZip
        indicadores << view.progresoDelete
        labels << view.mesesLabel
        labels << view.progresoGeneralLabel
        labels << view.progresoArchivosLabel
        labels << view.progresoZipsLabel
        labels << view.progresoDeleteLabel
        barra = view.progresoBarra

    }

    def carpetas = {
        DirectoryChooser chooser = new DirectoryChooser()
        File file = chooser.showDialog()

        def archivosACopiar = file.listFiles()

        File file2 = new File("${ValidarDirectorios.directorios.procesar}\\${file.name}\\")

        if (!file2.exists()) {
            file2.mkdirs()
        }
        def totalCount = archivosACopiar.size()
        def count = 0
        execInsideUIAsync {
            view.progresoGeneralLabel.text = "Copiando archivos"

        }
        model.visible = true
        archivosACopiar.each {
            ++count
            view.progresoBarra.progress = count.div(totalCount)
            Files.copy(new File(it.absolutePath).toPath(), new File(file2.absolutePath + "\\" + it.name).toPath(), StandardCopyOption.REPLACE_EXISTING)
        }
        execInsideUIAsync {
            view.progresoGeneralLabel.text = "Archivos copiados"
            model.visible = false
        }
        Thread.sleep(3000)
        execInsideUIAsync {
            view.progresoGeneralLabel.text = ""
            view.progresoBarra.progress = 0
        }
    }

    def configuracion = {
        def mvc = buildMVCGroup('configuration')
        mvc.controller.show(view.principal)
    }

    def iniciar = { evt = null ->
        def mvc = buildMVCGroup('accountType')
        mvc.controller.show(view.principal)
    }

    // void mvcGroupDestroy() {
    //    // this method is called when the group is destroyed
    // }

    def exit = { evt = null ->
        System.exit(0)
    }

    def onIniciar = { dato ->
        int hour = 0;
        int minutes = 0;
        int seconds = 0;
        def df = new DecimalFormat("00");
        progresoLabels = new ProgresoLabels()
        model.progressVisible = true
        def t = new Thread(new Task<Void>() {
            @Override protected Void call() throws Exception {
                for (; ;) {
                    execInsideUISync {
                        model.progresoArchivosLabel = progresoLabels.progresoArchivosLabel
                        model.progresoGeneralLabel = progresoLabels.progresoGeneralLabel
                        model.progresoZipsLabel = progresoLabels.progresoZipsLabel
                        model.progresoDeleteLabel = progresoLabels.progresoDeleteLabel
                        model.progresoMesesLabel = progresoLabels.mesesLabel
                    }
                }
            }
        })
        def t2 = new Thread(new Task<Void>() {
            @Override protected Void call() throws Exception {
                for (; ;) {
                    etiqueta: {
                        seconds++;
                        execInsideUISync {
                            view.segundos.setText(df.format(seconds));
                        }
                        if (seconds == 59) {
                            seconds = -1;
                            continue etiqueta;

                        }
                        if (seconds == 0) {
                            minutes++;
                            execInsideUISync {
                                view.minutos.setText(df.format(minutes));
                            }
                        }
                        if (minutes == 59) {
                            minutes = -1;
                            hour++;
                            execInsideUISync {
                                view.hora.setText(df.format(hour));
                            }
                        }
                    }
                    Thread.sleep(1000)
                }
            }
        })
        execInsideUIAsync {
            model.progresoArchivosLabel = ""
            model.progresoGeneralLabel = ""
            model.progresoZipsLabel = ""
            model.progresoDeleteLabel = ""
            model.progresoMesesLabel = ""
            view.progresoArchivo.progress = 0
            view.progresoZip.progress = 0
            view.progresoDelete.progress = 0
        }
        execOutsideUI {
            t.start()
            t2.start()
            view.horaHbox.visible = true
            model.visible = true
            def files = new ReadAllPdfs()

            files.Procesar(dato, indicadores, barra, progresoLabels)

            println "<-------------Finalizacion de proceso--------------------------------------------------->"

            model.visible = false
            t.interrupt()
            t2.interrupt()
        }
        execInsideUIAsync {
            model.progresoGeneralLabel = "Proceso Finalizado"
        }
    }
}
