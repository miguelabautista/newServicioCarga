package com.novatec.serviciocarga.observers

import com.novatec.serviciocarga.entities.Status


class DeletePdfs extends Observable implements Observer {

    DeletePdfs(def progreso){
         addObserver(progreso)
    }
    @Override
    void update(Observable o, Object arg) {
        if (o instanceof GenerarZips && arg instanceof Set) {
            def directorioDePdfs = arg

            List<File> directorios = new ArrayList<File>();

                directorioDePdfs.each {String e ->
                    directorios.add(new File(e.toString()));
                }
               def countTotal = 0
               directorios.each {File file ->
                   def count = 0
                    ++countTotal
                    File[] listFiles = file.listFiles();

                    for (File directs : listFiles) {
                        setChanged()
                        notifyObservers(new Status(total: listFiles.size(), progreso: ++count,progresoGeneral: countTotal,totalGeneral: directorioDePdfs.size()))

                        if (directs.getAbsoluteFile().getName().endsWith(".pdf")) {
                            directs.delete();
                        }
                    }
                }
        }
    }
}
