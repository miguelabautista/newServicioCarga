package newserviciocarga

import javafx.stage.Stage
import griffon.transform.Threading
import javafx.stage.Modality
import javafx.stage.StageStyle
import com.novatec.serviciocarga.entities.Directorios
import javafx.scene.control.ComboBox

class ConfigurationController {
    def model
    def view
    protected Stage dialog




    // void mvcGroupInit(Map args) {
    //    // this method is called after model and view are injected
    // }

    @Threading(Threading.Policy.INSIDE_UITHREAD_SYNC)
    def show = { Stage owner = null ->
        if (!dialog) {
            dialog = new Stage()
            if (null != owner) dialog.initOwner(owner)
            dialog.initModality(Modality.APPLICATION_MODAL)
            dialog.sizeToScene()
            dialog.scene = view.configurationScene
          //  dialog.initStyle(StageStyle.UNDECORATED)
        }
        app.windowManager.show(dialog)
    }

    @Threading(Threading.Policy.INSIDE_UITHREAD_SYNC)
    def hide = { evt = null ->
        destroyMVCGroup('configuration')
        app.windowManager.hide(dialog)
    }

    def aceptar = { evt = null ->
        def seleccionado = view.combo.selectionModel.selectedItem
        def selec = seleccionado.replace('\\','/')
        Directorios dir = new Directorios()
        dir.procesar = "${selec}historicosBE/Procesar/"
        dir.directorioDePdfsProcessador = "${selec}historicosBE/PdfProcesados/"
        dir.direcotioProcesados = "${selec}historicosBE/Procesados"

        def file = new File('./datos.data').withObjectOutputStream {
            it.writeObject(dir)
        }

        def filee = new File('./datos.data').withObjectInputStream {
            dir = it.readObject() as Directorios
        }
        def directorioProcesar = new File(dir.procesar)
        if (!directorioProcesar.exists()) {
            directorioProcesar.mkdirs()
        }
        def directorioDePdfProce = new File(dir.directorioDePdfsProcessador)
        if (!directorioDePdfProce.exists()) {
            directorioDePdfProce.mkdirs()
        }
        def directorioProce = new File(dir.direcotioProcesados)
        if (!directorioProce.exists()) {
            directorioProce.mkdirs()
        }
        hide.call()
    }

    def salir = { evt = null ->
        hide.call()
    }


}
