package newserviciocarga

import groovyx.javafx.beans.FXBindable
import griffon.util.GriffonNameUtils

class NewServicioCargaModel {
    @FXBindable boolean visible
    @FXBindable String progresoGeneralLabel
    @FXBindable String progresoArchivosLabel
    @FXBindable String progresoZipsLabel
    @FXBindable String progresoDeleteLabel
    @FXBindable String progresoMesesLabel
    @FXBindable boolean progressVisible


}
