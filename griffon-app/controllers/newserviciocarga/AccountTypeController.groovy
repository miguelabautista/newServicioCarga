package newserviciocarga

import griffon.transform.Threading
import javafx.stage.Stage
import javafx.stage.Modality
import javafx.stage.StageStyle

import java.awt.event.FocusEvent
import java.awt.event.FocusListener

class AccountTypeController {
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
            dialog.scene = view.accountTypeScene
            // dialog.initStyle(StageStyle.UNDECORATED)
        }
        app.windowManager.show(dialog)
    }

    @Threading(Threading.Policy.INSIDE_UITHREAD_SYNC)
    def hide = { evt = null ->
        destroyMVCGroup('accountType')
        app.windowManager.hide(dialog)
    }



    def aceptar = { evt = null ->

        def cuenta = view.cuentasCombo.selectionModel.selectedItem
        if (cuenta == "") {
            println "seleccione una cuenta"
           // hide.call()
           // app.event('iniciar', [cuenta])
        } else {
            hide.call()
            app.event('iniciar', [cuenta])
        }
    }
}
