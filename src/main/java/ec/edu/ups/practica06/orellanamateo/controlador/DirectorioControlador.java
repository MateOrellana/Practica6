/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.practica06.orellanamateo.controlador;

import ec.edu.ups.practica06.orellanamateo.vista.VentanaPrincipal;
import ec.edu.ups.practica06.orellanamateo.modelo.Directorio;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author mateo
 */
public class DirectorioControlador {
    private Directorio modelo;
    private VentanaPrincipal vista;

    public DirectorioControlador(VentanaPrincipal vista) {
        this.vista = vista;
        this.modelo = new Directorio();
    }
    
    public void listarDirectorios() {
        String ruta = vista.getRutaTextField().getText();
        DefaultMutableTreeNode directorios = modelo.getDirectorios(ruta);
        vista.getDirectorioTree().setModel(new DefaultTreeModel(directorios));
    }

    public void listarArchivos() {
        String ruta = vista.getRutaTextField().getText();
        DefaultMutableTreeNode archivos = modelo.getArchivos(ruta);
        vista.getDirectorioTree().setModel(new DefaultTreeModel(archivos));
    }

    public void listarTodos() {
        String ruta = vista.getRutaTextField().getText();
        DefaultMutableTreeNode todos = modelo.getTodos(ruta);
        vista.getDirectorioTree().setModel(new DefaultTreeModel(todos));
    }

    public void listarArchivosOcultos() {
        String ruta = vista.getRutaTextField().getText();
        DefaultMutableTreeNode archivosOcultos = modelo.getArchivosOcultos(ruta);
        vista.getDirectorioTree().setModel(new DefaultTreeModel(archivosOcultos));
    }

    public void listarDirectoriosOcultos() {
        String ruta = vista.getRutaTextField().getText();
        DefaultMutableTreeNode directoriosOcultos = modelo.getDirectoriosOcultos(ruta);
        vista.getDirectorioTree().setModel(new DefaultTreeModel(directoriosOcultos));
    }

    public void actualizarRuta() {
        String ruta = vista.getRutaTextField().getText();
        File file = new File(ruta);

        if (!file.exists()) {
            JOptionPane.showMessageDialog(vista, "La ruta no existe", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!file.isDirectory()) {
            JOptionPane.showMessageDialog(vista, "La ruta no es un directorio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        listarDirectorios();
    }

    public void mostrarInformacion(String ruta) {
        File file = new File(ruta);

        if (file.exists()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            StringBuilder sb = new StringBuilder();
            sb.append("Path absoluto: ").append(file.getAbsolutePath()).append("\n");
            sb.append("Tamaño del archivo: ").append(file.length()).append(" bytes\n");
            sb.append("Permisos de Lectura: ").append(file.canRead()).append("\n");
            sb.append("Permisos de Escritura: ").append(file.canWrite()).append("\n");
            sb.append("Fecha última modificación: ").append(dateFormat.format(new Date(file.lastModified())));

            vista.mostrarInformacion(sb.toString());
        } else {
            vista.mostrarInformacion("");
        }
    }
    
    public void crearElemento() {
        String ruta = vista.getRutaTextField().getText();
        String nombre = JOptionPane.showInputDialog(vista, "Ingrese el nombre del directorio o archivo", "Crear Elemento", JOptionPane.PLAIN_MESSAGE);

        if (nombre != null && !nombre.isEmpty()) {
            int opcion = JOptionPane.showConfirmDialog(vista, "¿Desea crear un directorio?", "Crear Elemento", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                boolean exito = modelo.crearDirectorio(ruta, nombre);

                if (exito) {
                    listarDirectorios(); // Actualizar el JTree
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo crear el directorio", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                boolean exito = modelo.crearArchivo(ruta, nombre);

                if (exito) {
                    listarArchivos(); // Actualizar el JTree
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo crear el archivo", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void eliminarElemento() {
        TreePath seleccion = vista.getDirectorioTree().getSelectionPath();

        if (seleccion != null) {
            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) seleccion.getLastPathComponent();
            String nombre = nodo.toString();
            String ruta = vista.getRutaTextField().getText() + File.separator + nombre;

            int opcion = JOptionPane.showConfirmDialog(vista, "¿Está seguro de eliminar el elemento seleccionado?", "Eliminar Elemento", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                boolean exito = modelo.eliminarDirectorio(ruta);

                if (exito) {
                    listarDirectorios(); // Actualizar el JTree
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo eliminar el directorio", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                boolean exito = modelo.eliminarArchivo(ruta);

                if (exito) {
                    listarArchivos(); // Actualizar el JTree
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo eliminar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void renombrarElemento() {
        TreePath seleccion = vista.getDirectorioTree().getSelectionPath();

        if (seleccion != null) {
            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) seleccion.getLastPathComponent();
            String nombreActual = nodo.toString();
            String ruta = vista.getRutaTextField().getText() + File.separator + nombreActual;

            String nuevoNombre = JOptionPane.showInputDialog(vista, "Ingrese el nuevo nombre del elemento", "Renombrar Elemento", JOptionPane.PLAIN_MESSAGE);

            if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
                boolean exito;

            if (nodo.isLeaf()) {
                exito = modelo.renombrarArchivo(ruta, nuevoNombre);
                if (exito) {
                    listarArchivos(); // Actualizar el JTree
                }
            } else {
                exito = modelo.renombrarDirectorio(ruta, nuevoNombre);
                if (exito) {
                    listarDirectorios(); // Actualizar el JTree
                }
            }

            if (!exito) {
                JOptionPane.showMessageDialog(vista, "No se pudo renombrar el elemento", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        }
    }
}
