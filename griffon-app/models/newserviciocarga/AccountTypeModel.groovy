package newserviciocarga

import groovyx.javafx.beans.FXBindable

class AccountTypeModel {
    @FXBindable String tipoCuenta
    @FXBindable def cuentas = ["Ahorro","Corriente"]
}
