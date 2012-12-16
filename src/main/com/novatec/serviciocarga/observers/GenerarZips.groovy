package com.novatec.serviciocarga.observers

import com.novatec.serviciocarga.inicio.ReadAllPdfs
import java.util.zip.ZipOutputStream
import java.util.zip.ZipEntry
import com.novatec.serviciocarga.entities.Status


class GenerarZips extends Observable implements Observer {

    GenerarZips(def progreso) {
        addObserver(new DeletePdfs(progreso))
        addObserver(progreso)
    }

    @Override
    void update(Observable o, Object arg) {
        if (o instanceof ReadAllPdfs && arg instanceof Set) {

            byte[] buffer = new byte[1024];

            def directorioDePdfs = arg;

            List<File> archivos = new ArrayList<File>();



                directorioDePdfs.each{ String file ->
                    archivos.add(new File(file));
                }
                 def countTotalZips = 0

                archivos.each { File arch ->

                    File[] listFiles = arch.listFiles();
                    ++countTotalZips
                    def count = 0
                    try {
                        FileOutputStream fos = new FileOutputStream(arch.getAbsolutePath() + "\\" + "pdfs" + ".zip");

                        ZipOutputStream zos = new ZipOutputStream(fos);

                        for (File a : listFiles) {
                            setChanged()
                            notifyObservers(new Status(total: listFiles.size(), progreso: ++count,progresoGeneral: countTotalZips, totalGeneral: directorioDePdfs.size()))
                            if (a.getAbsoluteFile().getName().endsWith(".pdf")) {
                                ZipEntry ze = new ZipEntry(a.getName());

                                zos.putNextEntry(ze);

                                FileInputStream inp = new FileInputStream(a.getAbsoluteFile());

                                int len;
                                while ((len = inp.read(buffer)) > 0) {
                                    zos.write(buffer, 0, len);
                                }
                                inp.close();
                            }
                        }
                        zos.closeEntry();

                        zos.close();

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
            }
            setChanged()
            notifyObservers(directorioDePdfs)
        }
    }
}
