package newserviciocarga

import groovyx.javafx.beans.FXBindable

class ConfigurationModel {
    @FXBindable def rutas = []

    void mvcGroupInit(Map args) {
        def ile  = File.listRoots()

        ile.each{ rutas << it.absolutePath }
    }
}
