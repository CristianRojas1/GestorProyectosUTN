/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import dao.ProyectoDAO;
import modelo.Proyecto;

import java.util.List;

public class ProyectoServicio {
    private ProyectoDAO proyectoDAO;

    public ProyectoServicio() {
        this.proyectoDAO = new ProyectoDAO();
    }

    public boolean crearProyecto(Proyecto proyecto) {
        if (proyecto.getNombre() == null || proyecto.getNombre().isEmpty()) {
            System.out.println("El nombre del proyecto es obligatorio");
            return false;
        }
        return proyectoDAO.insertar(proyecto);
    }

    public boolean actualizarProyecto(Proyecto proyecto) {
        if (proyecto.getId() <= 0) {
            System.out.println("Id de proyecto inválido");
            return false;
        }
        return proyectoDAO.actualizar(proyecto);
    }

    public boolean eliminarProyecto(int id) {
        if (id <= 0) {
            System.out.println("Id inválido");
            return false;
        }
        return proyectoDAO.eliminar(id);
    }

    public Proyecto obtenerProyecto(int id) {
        return proyectoDAO.obtenerPorId(id);
    }

    public List<Proyecto> obtenerTodos() {
        return proyectoDAO.obtenerTodos();
    }
}