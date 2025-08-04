/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class PanelProyectos extends javax.swing.JPanel {
private DefaultTableModel modeloTabla;

    public PanelProyectos() {
        initComponents();
        configurarComponentes();
        agregarEventos();
    }

    private void configurarComponentes() {
        // Configurar ComboBox estados
        String[] estados = {"Planeado", "En Progreso", "Finalizado", "Cancelado"};
        cmbEstadoProyecto.setModel(new DefaultComboBoxModel<>(estados));

        // Configurar tabla con columnas
        modeloTabla = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nombre", "Fecha Inicio", "Fecha Fin", "Estado", "Avance", "Descripción"}
        ) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaProyectos.setModel(modeloTabla);

        // Barra progreso 0 a 100
        progressAvanceProyecto.setMinimum(0);
        progressAvanceProyecto.setMaximum(100);
        progressAvanceProyecto.setValue(0);

        limpiarCampos();

        btnEditarProyecto.setEnabled(false);
        btnEliminarProyecto.setEnabled(false);
    }

    private void agregarEventos() {
        // Evento para seleccionar fila en la tabla
        tablaProyectos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaProyectos.getSelectedRow() != -1) {
                cargarDatosSeleccion();
                btnEditarProyecto.setEnabled(true);
                btnEliminarProyecto.setEnabled(true);
                btnGuardarProyecto.setEnabled(false);
            }
        });

        btnNuevoProyecto.addActionListener(e -> {
            limpiarCampos();
            tablaProyectos.clearSelection();
            btnGuardarProyecto.setEnabled(true);
            btnEditarProyecto.setEnabled(false);
            btnEliminarProyecto.setEnabled(false);
            lblMensajeProyecto.setText("Campos listos para nuevo proyecto");
        });

        btnGuardarProyecto.addActionListener(e -> guardarProyecto());

        btnEditarProyecto.addActionListener(e -> editarProyecto());

        btnEliminarProyecto.addActionListener(e -> eliminarProyecto());

        btnBuscarProyecto.addActionListener(e -> buscarProyecto());
    }

    private void limpiarCampos() {
        txtNombreProyecto.setText("");
        txtFechaInicioProyecto.setText("");
        txtFechaFinProyecto.setText("");
        txtDescripcionProyecto.setText("");
        txtBuscarProyecto.setText("");
        lblMensajeProyecto.setText("");
        cmbEstadoProyecto.setSelectedIndex(0);
        progressAvanceProyecto.setValue(0);
    }

    private void cargarDatosSeleccion() {
        int fila = tablaProyectos.getSelectedRow();
        if (fila >= 0) {
            txtNombreProyecto.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtFechaInicioProyecto.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtFechaFinProyecto.setText(modeloTabla.getValueAt(fila, 3).toString());
            cmbEstadoProyecto.setSelectedItem(modeloTabla.getValueAt(fila, 4).toString());
            progressAvanceProyecto.setValue(Integer.parseInt(modeloTabla.getValueAt(fila, 5).toString()));
            txtDescripcionProyecto.setText(modeloTabla.getValueAt(fila, 6).toString());
            lblMensajeProyecto.setText("Proyecto cargado para edición");
        }
    }

    private void guardarProyecto() {
        String nombre = txtNombreProyecto.getText().trim();
        String fechaInicio = txtFechaInicioProyecto.getText().trim();
        String fechaFin = txtFechaFinProyecto.getText().trim();
        String estado = cmbEstadoProyecto.getSelectedItem().toString();
        int avance = progressAvanceProyecto.getValue();
        String descripcion = txtDescripcionProyecto.getText().trim();

        if (nombre.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            lblMensajeProyecto.setText("Debe completar nombre y fechas");
            return;
        }

        int id = modeloTabla.getRowCount() + 1; // ID automático sencillo

        modeloTabla.addRow(new Object[]{id, nombre, fechaInicio, fechaFin, estado, avance, descripcion});

        lblMensajeProyecto.setText("Proyecto guardado correctamente");
        limpiarCampos();
    }

    private void editarProyecto() {
        int fila = tablaProyectos.getSelectedRow();
        if (fila < 0) {
            lblMensajeProyecto.setText("Seleccione un proyecto para editar");
            return;
        }

        String nombre = txtNombreProyecto.getText().trim();
        String fechaInicio = txtFechaInicioProyecto.getText().trim();
        String fechaFin = txtFechaFinProyecto.getText().trim();
        String estado = cmbEstadoProyecto.getSelectedItem().toString();
        int avance = progressAvanceProyecto.getValue();
        String descripcion = txtDescripcionProyecto.getText().trim();

        if (nombre.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            lblMensajeProyecto.setText("Debe completar nombre y fechas");
            return;
        }

        modeloTabla.setValueAt(nombre, fila, 1);
        modeloTabla.setValueAt(fechaInicio, fila, 2);
        modeloTabla.setValueAt(fechaFin, fila, 3);
        modeloTabla.setValueAt(estado, fila, 4);
        modeloTabla.setValueAt(avance, fila, 5);
        modeloTabla.setValueAt(descripcion, fila, 6);

        lblMensajeProyecto.setText("Proyecto editado correctamente");
        limpiarCampos();

        tablaProyectos.clearSelection();
        btnEditarProyecto.setEnabled(false);
        btnEliminarProyecto.setEnabled(false);
        btnGuardarProyecto.setEnabled(true);
    }

    private void eliminarProyecto() {
        int fila = tablaProyectos.getSelectedRow();
        if (fila < 0) {
            lblMensajeProyecto.setText("Seleccione un proyecto para eliminar");
            return;
        }
        modeloTabla.removeRow(fila);
        lblMensajeProyecto.setText("Proyecto eliminado");
        limpiarCampos();

        btnEditarProyecto.setEnabled(false);
        btnEliminarProyecto.setEnabled(false);
        btnGuardarProyecto.setEnabled(true);
    }

    private void buscarProyecto() {
        String buscar = txtBuscarProyecto.getText().trim().toLowerCase();
        if (buscar.isEmpty()) {
            lblMensajeProyecto.setText("Ingrese texto para buscar");
            return;
        }

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String nombre = modeloTabla.getValueAt(i, 1).toString().toLowerCase();
            if (nombre.contains(buscar)) {
                tablaProyectos.setRowSelectionInterval(i, i);
                cargarDatosSeleccion();
                lblMensajeProyecto.setText("Proyecto encontrado y seleccionado");
                return;
            }
        }

        lblMensajeProyecto.setText("No se encontró proyecto con ese nombre");
        tablaProyectos.clearSelection();
        limpiarCampos();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progressAvanceProyecto = new javax.swing.JProgressBar();
        btnNuevoProyecto = new javax.swing.JButton();
        btnEditarProyecto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProyectos = new javax.swing.JTable();
        txtBuscarProyecto = new javax.swing.JTextField();
        btnEliminarProyecto = new javax.swing.JButton();
        btnBuscarProyecto = new javax.swing.JButton();
        btnGuardarProyecto = new javax.swing.JButton();
        txtNombreProyecto = new javax.swing.JTextField();
        lblMensajeProyecto = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcionProyecto = new javax.swing.JTextArea();
        txtFechaInicioProyecto = new javax.swing.JTextField();
        txtFechaFinProyecto = new javax.swing.JTextField();
        cmbEstadoProyecto = new javax.swing.JComboBox<>();

        progressAvanceProyecto.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                progressAvanceProyectoAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        btnNuevoProyecto.setText("NuevoProyecto");
        btnNuevoProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProyectoActionPerformed(evt);
            }
        });

        btnEditarProyecto.setText("EditarProyecto");
        btnEditarProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProyectoActionPerformed(evt);
            }
        });

        tablaProyectos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaProyectos.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tablaProyectosAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(tablaProyectos);

        txtBuscarProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarProyectoActionPerformed(evt);
            }
        });

        btnEliminarProyecto.setText("EliminarProyecto");
        btnEliminarProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProyectoActionPerformed(evt);
            }
        });

        btnBuscarProyecto.setText("BuscarProyecto");
        btnBuscarProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProyectoActionPerformed(evt);
            }
        });

        btnGuardarProyecto.setText("GuardarProyecto");
        btnGuardarProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProyectoActionPerformed(evt);
            }
        });

        txtNombreProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProyectoActionPerformed(evt);
            }
        });

        lblMensajeProyecto.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblMensajeProyectoAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        txtDescripcionProyecto.setColumns(20);
        txtDescripcionProyecto.setRows(5);
        txtDescripcionProyecto.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                txtDescripcionProyectoAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane2.setViewportView(txtDescripcionProyecto);

        txtFechaInicioProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaInicioProyectoActionPerformed(evt);
            }
        });

        txtFechaFinProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaFinProyectoActionPerformed(evt);
            }
        });

        cmbEstadoProyecto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEstadoProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEstadoProyectoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnNuevoProyecto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(progressAvanceProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(161, 161, 161))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnEditarProyecto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbEstadoProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBuscarProyecto))
                            .addComponent(lblMensajeProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEliminarProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnGuardarProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGap(96, 96, 96)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtBuscarProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtNombreProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtFechaInicioProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtFechaFinProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(310, 310, 310))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnNuevoProyecto)
                    .addComponent(progressAvanceProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarProyecto)
                    .addComponent(cmbEstadoProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarProyecto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEliminarProyecto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarProyecto))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBuscarProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFechaInicioProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaFinProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMensajeProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void progressAvanceProyectoAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_progressAvanceProyectoAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_progressAvanceProyectoAncestorAdded

    private void btnNuevoProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProyectoActionPerformed
        limpiarCampos();
        tablaProyectos.clearSelection();
        btnGuardarProyecto.setEnabled(true);
        btnEditarProyecto.setEnabled(false);
        btnEliminarProyecto.setEnabled(false);
        lblMensajeProyecto.setText("Campos listos para nuevo proyecto");
    }//GEN-LAST:event_btnNuevoProyectoActionPerformed

    private void btnEditarProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProyectoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarProyectoActionPerformed

    private void tablaProyectosAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tablaProyectosAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaProyectosAncestorAdded

    private void txtBuscarProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarProyectoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarProyectoActionPerformed

    private void btnEliminarProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProyectoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarProyectoActionPerformed

    private void btnBuscarProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProyectoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarProyectoActionPerformed

    private void btnGuardarProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProyectoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarProyectoActionPerformed

    private void txtNombreProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProyectoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProyectoActionPerformed

    private void lblMensajeProyectoAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblMensajeProyectoAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_lblMensajeProyectoAncestorAdded

    private void txtDescripcionProyectoAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtDescripcionProyectoAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionProyectoAncestorAdded

    private void txtFechaInicioProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaInicioProyectoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaInicioProyectoActionPerformed

    private void txtFechaFinProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaFinProyectoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaFinProyectoActionPerformed

    private void cmbEstadoProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEstadoProyectoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEstadoProyectoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarProyecto;
    private javax.swing.JButton btnEditarProyecto;
    private javax.swing.JButton btnEliminarProyecto;
    private javax.swing.JButton btnGuardarProyecto;
    private javax.swing.JButton btnNuevoProyecto;
    private javax.swing.JComboBox<String> cmbEstadoProyecto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMensajeProyecto;
    private javax.swing.JProgressBar progressAvanceProyecto;
    private javax.swing.JTable tablaProyectos;
    private javax.swing.JTextField txtBuscarProyecto;
    private javax.swing.JTextArea txtDescripcionProyecto;
    private javax.swing.JTextField txtFechaFinProyecto;
    private javax.swing.JTextField txtFechaInicioProyecto;
    private javax.swing.JTextField txtNombreProyecto;
    // End of variables declaration//GEN-END:variables
}
