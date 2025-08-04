/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import modelo.Proyecto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProyectoDAO {

    public boolean insertar(Proyecto proyecto) {
        String sql = "INSERT INTO Proyectos (Nombre, Descripcion, FechaInicio, FechaFin, PorcentajeAvance) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, proyecto.getNombre());
            ps.setString(2, proyecto.getDescripcion());
            ps.setDate(3, new java.sql.Date(proyecto.getFechaInicio().getTime()));
            if (proyecto.getFechaFin() != null) {
                ps.setDate(4, new java.sql.Date(proyecto.getFechaFin().getTime()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setInt(5, proyecto.getPorcentajeAvance());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Proyecto proyecto) {
        String sql = "UPDATE Proyectos SET Nombre = ?, Descripcion = ?, FechaInicio = ?, FechaFin = ?, PorcentajeAvance = ? WHERE Id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, proyecto.getNombre());
            ps.setString(2, proyecto.getDescripcion());
            ps.setDate(3, new java.sql.Date(proyecto.getFechaInicio().getTime()));
            if (proyecto.getFechaFin() != null) {
                ps.setDate(4, new java.sql.Date(proyecto.getFechaFin().getTime()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setInt(5, proyecto.getPorcentajeAvance());
            ps.setInt(6, proyecto.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Proyectos WHERE Id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Proyecto obtenerPorId(int id) {
        String sql = "SELECT * FROM Proyectos WHERE Id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Proyecto p = new Proyecto();
                p.setId(rs.getInt("Id"));
                p.setNombre(rs.getString("Nombre"));
                p.setDescripcion(rs.getString("Descripcion"));
                p.setFechaInicio(rs.getDate("FechaInicio"));
                p.setFechaFin(rs.getDate("FechaFin"));
                p.setPorcentajeAvance(rs.getInt("PorcentajeAvance"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Proyecto> obtenerTodos() {
        List<Proyecto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Proyectos";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Proyecto p = new Proyecto();
                p.setId(rs.getInt("Id"));
                p.setNombre(rs.getString("Nombre"));
                p.setDescripcion(rs.getString("Descripcion"));
                p.setFechaInicio(rs.getDate("FechaInicio"));
                p.setFechaFin(rs.getDate("FechaFin"));
                p.setPorcentajeAvance(rs.getInt("PorcentajeAvance"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}