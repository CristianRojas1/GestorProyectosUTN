/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import dao.UsuarioDAO;
import modelo.Usuario;

import java.util.List;

public class UsuarioServicio {
    private UsuarioDAO usuarioDAO;

    public UsuarioServicio() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean crearUsuario(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
            System.out.println("El nombre del usuario es obligatorio");
            return false;
        }
        if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
            System.out.println("El correo es obligatorio");
            return false;
        }
        return usuarioDAO.insertar(usuario);
    }

    public boolean actualizarUsuario(Usuario usuario) {
        if (usuario.getId() <= 0) {
            System.out.println("Id de usuario inválido");
            return false;
        }
        return usuarioDAO.actualizar(usuario);
    }

    public boolean eliminarUsuario(int id) {
        if (id <= 0) {
            System.out.println("Id inválido");
            return false;
        }
        return usuarioDAO.eliminar(id);
    }

    public Usuario obtenerUsuario(int id) {
        return usuarioDAO.obtenerPorId(id);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioDAO.obtenerTodos();
    }
}