application {
    title = 'NewServicioCarga'
    startupGroups = ['newServicioCarga']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "configuration"
    'configuration' {
        model      = 'newserviciocarga.ConfigurationModel'
        view       = 'newserviciocarga.ConfigurationView'
        controller = 'newserviciocarga.ConfigurationController'
    }

    // MVC Group for "accountType"
    'accountType' {
        model      = 'newserviciocarga.AccountTypeModel'
        view       = 'newserviciocarga.AccountTypeView'
        controller = 'newserviciocarga.AccountTypeController'
    }

    // MVC Group for "newServicioCarga"
    'newServicioCarga' {
        model      = 'newserviciocarga.NewServicioCargaModel'
        view       = 'newserviciocarga.NewServicioCargaView'
        controller = 'newserviciocarga.NewServicioCargaController'
    }

}
