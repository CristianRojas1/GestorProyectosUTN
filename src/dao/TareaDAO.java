/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import modelo.Tarea;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TareaDAO {

    public boolean insertar(Tarea tarea) {
        String sql = "INSERT INTO Tareas (IdProyecto, Titulo, Descripcion, FechaInicio, FechaFin, Estado, PorcentajeAvance) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tarea.getIdProyecto());
            ps.setString(2, tarea.getTitulo());
            ps.setString(3, tarea.getDescripcion());
            ps.setDate(4, new java.sql.Date(tarea.getFechaInicio().getTime()));
            if (tarea.getFechaFin() != null) {
                ps.setDate(5, new java.sql.Date(tarea.getFechaFin().getTime()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setString(6, tarea.getEstado());
            ps.setInt(7, tarea.getPorcentajeAvance());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean eliminar(int id) {
        String sql = "DELETE FROM Tareas WHERE Id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Tarea obtenerPorId(int id) {
        String sql = "SELECT * FROM Tareas WHERE Id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Tarea t = new Tarea();
                t.setId(rs.getInt("Id"));
                t.setIdProyecto(rs.getInt("IdProyecto"));
                t.setTitulo(rs.getString("Titulo"));
                t.setDescripcion(rs.getString("Descripcion"));
                t.setFechaInicio(rs.getDate("FechaInicio"));
                t.setFechaFin(rs.getDate("FechaFin"));
                t.setEstado(rs.getString("Estado"));
                t.setPorcentajeAvance(rs.getInt("PorcentajeAvance"));
                return t;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Tarea> obtenerTodos() {
        List<Tarea> lista = new ArrayList<>();
        String sql = "SELECT * FROM Tareas";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Tarea t = new Tarea();
                t.setId(rs.getInt("Id"));
                t.setIdProyecto(rs.getInt("IdProyecto"));
                t.setTitulo(rs.getString("Titulo"));
                t.setDescripcion(rs.getString("Descripcion"));
                t.setFechaInicio(rs.getDate("FechaInicio"));
                t.setFechaFin(rs.getDate("FechaFin"));
                t.setEstado(rs.getString("Estado"));
                t.setPorcentajeAvance(rs.getInt("PorcentajeAvance"));
                lista.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}