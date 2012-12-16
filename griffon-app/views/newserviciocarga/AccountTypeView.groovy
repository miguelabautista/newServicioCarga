package newserviciocarga

scene(id: 'accountTypeScene', width: 247, height: 115) {
    anchorPane(prefHeight: 115.0, prefWidth: 247.0) {
        label(layoutX: 14.0, layoutY: 14.0, text: "Seleccione tipo de cuenta")
        comboBox(id: 'cuentasCombo',layoutX:44.0, layoutY:47.0, prefHeight: 21.0, prefWidth: 159.0, items: model.cuentas)
        button(layoutX: 171.0, layoutY: 86.0, mnemonicParsing: false, text: "Aceptar", onAction: controller.aceptar)
    }
}
