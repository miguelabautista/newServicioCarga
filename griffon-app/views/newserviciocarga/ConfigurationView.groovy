package newserviciocarga

scene(id: 'configurationScene', width: 226, height: 117) {
    anchorPane(prefHeight: 117.0, prefWidth: 226.0) {
        label(layoutX: 14.0, layoutY: 14.0, text: "Seleccione Unidad")
        comboBox(id:'combo',layoutX: 62.0, layoutY: 45.0, prefHeight: 21.0, prefWidth: 103.0,items:model.rutas)
        hbox(alignment: "CENTER", layoutX: 80.0, layoutY: 82.0, spacing: 5.0) {
            button(mnemonicParsing: false, text: "Aceptar", onAction: controller.aceptar)
            button(mnemonicParsing: "false", text: "Cancelar", onAction: controller.salir)
        }
    }
}
