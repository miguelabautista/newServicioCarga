package newserviciocarga

principal = application(title: 'Generador de Zips', sizeToScene: true, resizable: false, centerOnScreen: true, onCloseRequest:controller.exit) {
    scene(fill: white, width: 407.0, height: 282.0) {
        anchorPane(prefHeight: 282.0, prefWidth: 407.0) {
            progressBar(id: 'progresoBarra', layoutX: 110.0, layoutY: 44.0, prefWidth: 200.0, progress: 0.0)
            button(layoutX: 14.0, layoutY: 247.0, disable: bind(model.visibleProperty), text: "_Carpeta", onAction: controller.carpetas)
            label(id: 'progresoGeneralLabel', alignment: "CENTER", layoutX: 56.0, layoutY: 70.0, prefWidth: 311.0, text: bind(model.progresoGeneralLabelProperty))
            vbox(alignment: "CENTER", visible: bind(model.progressVisibleProperty), layoutX: 129.0, layoutY: 103.0, prefWidth: 154.0, spacing: 5.0) {
                hbox(alignment: "CENTER", spacing: 5.0) {
                    progressIndicator(id: 'progresoArchivo', progress: 0.0)
                    label(id: 'progresoArchivosLabel', prefWidth: 109.0, text: bind(model.progresoArchivosLabelProperty))
                }
                hbox(alignment: "CENTER", spacing: 5.0) {
                    progressIndicator(id: 'progresoZip', progress: 0.0)
                    label(id: 'progresoZipsLabel', prefWidth: 109.0, text: bind(model.progresoZipsLabelProperty))
                }
                hbox(alignment: "CENTER", spacing: 5.0) {
                    progressIndicator(id: 'progresoDelete', progress: 0.0)
                    label(id: 'progresoDeleteLabel', prefWidth: 109.0, text: bind(model.progresoDeleteLabelProperty))
                }
            }
            hbox(alignment: "CENTER", layoutX: 283.0, layoutY: 247.0, spacing: 5.0) {
                button(text: "_Iniciar", disable: bind(model.visibleProperty), onAction: controller.iniciar)
                button(prefWidth: 52.0, text: "_Salir", onAction: controller.exit, disable: bind(model.visibleProperty))
            }
            label(id: 'mesesLabel', layoutX: 14.0, layoutY: 10.0, text: bind(model.progresoMesesLabelProperty))
            // <Font size="15.0" />
            // button(layoutX:94.0, layoutY:247.0, mnemonicParsing:false, text:"Conf",onAction: controller.configuracion,disable: bind(model.visibleProperty))
            hbox(id: "horaHbox", alignment: "CENTER", layoutX: 328.0, layoutY: 14.0, spacing: 5.0) {
                label(id: 'hora', text: "00")
                label(text: ":")
                label(id: 'minutos', text: "00")
                label(text: ":")
                label(id: 'segundos', text: "00")
            }

        }
    }
}
