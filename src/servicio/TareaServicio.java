/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;


import dao.TareaDAO;
import modelo.Tarea;

import java.util.List;

public class TareaServicio {
    private TareaDAO tareaDAO;

    public TareaServicio() {
        this.tareaDAO = new TareaDAO();
    }

    public boolean crearTarea(Tarea tarea) {
        if (tarea.getTitulo() == null || tarea.getTitulo().isEmpty()) {
            System.out.println("El título de la tarea es obligatorio");
            return false;
        }
        if (tarea.getIdProyecto() <= 0) {
            System.out.println("Id de proyecto inválido para la tarea");
            return false;
        }
        return tareaDAO.insertar(tarea);
    }

    public boolean actualizarTarea(Tarea tarea) {
        if (tarea.getId() <= 0) {
            System.out.println("Id de tarea inválido");
            return false;
        }
        return tareaDAO.actualizar(tarea);
    }

    public boolean eliminarTarea(int id) {
        if (id <= 0) {
            System.out.println("Id inválido");
            return false;
        }
        return tareaDAO.eliminar(id);
    }

    public Tarea obtenerTarea(int id) {
        return tareaDAO.obtenerPorId(id);
    }

    public List<Tarea> obtenerTodas() {
        return tareaDAO.obtenerTodos();
    }
}