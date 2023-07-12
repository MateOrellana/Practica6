/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.practica06.orellanamateo.modelo;

import java.io.File;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author mateo
 */
public class Directorio {
    public DefaultMutableTreeNode getDirectorios(String ruta) {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(ruta);
        File file = new File(ruta);
        File[] directorios = file.listFiles();

        if (directorios != null) {
            for (File directorio : directorios) {
                if (directorio.isDirectory()) {
                    raiz.add(new DefaultMutableTreeNode(directorio.getName()));
                }
            }
        }

        return raiz;
    }

    public DefaultMutableTreeNode getArchivos(String ruta) {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(ruta);
        File file = new File(ruta);
        File[] archivos = file.listFiles();

        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isFile()) {
                    raiz.add(new DefaultMutableTreeNode(archivo.getName()));
                }
            }
        }

        return raiz;
    }

    public DefaultMutableTreeNode getTodos(String ruta) {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(ruta);
        File file = new File(ruta);
        File[] elementos = file.listFiles();

        if (elementos != null) {
            for (File elemento : elementos) {
                raiz.add(new DefaultMutableTreeNode(elemento.getName()));
            }
        }

        return raiz;
    }

    public DefaultMutableTreeNode getArchivosOcultos(String ruta) {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(ruta);
        File file = new File(ruta);
        File[] archivos = file.listFiles();

        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isFile() && archivo.isHidden()) {
                    raiz.add(new DefaultMutableTreeNode(archivo.getName()));
                }
            }
        }

        return raiz;
    }

    public DefaultMutableTreeNode getDirectoriosOcultos(String ruta) {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(ruta);
        File file = new File(ruta);
        File[] directorios = file.listFiles();

        if (directorios != null) {
            for (File directorio : directorios) {
                if (directorio.isDirectory() && directorio.isHidden()) {
                    raiz.add(new DefaultMutableTreeNode(directorio.getName()));
                }
            }
        }

        return raiz;
    }
    
    public boolean crearDirectorio(String ruta, String nombre) {
        File file = new File(ruta, nombre);
        return file.mkdir();
    }

    public boolean crearArchivo(String ruta, String nombre) {
        File file = new File(ruta, nombre);

        try {
            return file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarDirectorio(String ruta) {
    File directorio = new File(ruta);

    if (directorio.exists() && directorio.isDirectory()) {
        File[] elementos = directorio.listFiles();

        if (elementos != null) {
            for (File elemento : elementos) {
                if (elemento.isDirectory()) {
                    eliminarDirectorio(elemento.getAbsolutePath());
                } else {
                    eliminarArchivo(elemento.getAbsolutePath());
                }
            }
        }

        return directorio.delete();
    }

    return false;
}

    public boolean eliminarArchivo(String ruta) {
        File archivo = new File(ruta);

        if (archivo.exists() && archivo.isFile()) {
            return archivo.delete();
        }

        return false;
    }

    public boolean renombrarDirectorio(String ruta, String nuevoNombre) {
        File directorio = new File(ruta);

        if (directorio.exists() && directorio.isDirectory()) {
            File nuevoDirectorio = new File(directorio.getParent(), nuevoNombre);
            return directorio.renameTo(nuevoDirectorio);
        }

        return false;
    }

    public boolean renombrarArchivo(String ruta, String nuevoNombre) {
        File archivo = new File(ruta);

        if (archivo.exists() && archivo.isFile()) {
            File nuevoArchivo = new File(archivo.getParent(), nuevoNombre);
            return archivo.renameTo(nuevoArchivo);
        }

        return false;
    }
    
}
