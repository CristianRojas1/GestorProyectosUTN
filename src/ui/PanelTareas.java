/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PanelTareas extends javax.swing.JPanel {
    private DefaultTableModel modeloTabla;

    public PanelTareas() {
        initComponents();
        configurarComponentes();
        agregarEventos();
    }

    private void configurarComponentes() {
        // Configurar combos con ejemplos
        cmbProyectos.setModel(new DefaultComboBoxModel<>(new String[]{
                "Proyecto A", "Proyecto B", "Proyecto C"
        }));

        cmbUsuarios.setModel(new DefaultComboBoxModel<>(new String[]{
                "Usuario 1", "Usuario 2", "Usuario 3"
        }));

        cmbEstado.setModel(new DefaultComboBoxModel<>(new String[]{
                "Pendiente", "En Progreso", "Finalizado", "Cancelado"
        }));

        cmbPrioridad.setModel(new DefaultComboBoxModel<>(new String[]{
                "Baja", "Media", "Alta"
        }));

        // Configurar spinner porcentaje 0 a 100
        spnPorcentaje.setModel(new SpinnerNumberModel(0, 0, 100, 1));

        // Configurar tabla
        modeloTabla = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Proyecto", "Usuario", "Descripción", "Fecha Inicio", "Fecha Fin", "Estado", "Porcentaje", "Prioridad"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaTareas.setModel(modeloTabla);

        limpiarCampos();
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void agregarEventos() {
        tablaTareas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaTareas.getSelectedRow() != -1) {
                cargarDatosSeleccion();
                btnEditar.setEnabled(true);
                btnEliminar.setEnabled(true);
                btnAgregar.setEnabled(false);
            }
        });

        btnAgregar.addActionListener(e -> agregarTarea());
        btnEditar.addActionListener(e -> editarTarea());
        btnEliminar.addActionListener(e -> eliminarTarea());
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void limpiarCampos() {
        cmbProyectos.setSelectedIndex(0);
        cmbUsuarios.setSelectedIndex(0);
        txtDescripcion.setText("");
        txtFechaInicio.setText("");
        txtFechaFin.setText("");
        cmbEstado.setSelectedIndex(0);
        spnPorcentaje.setValue(0);
        cmbPrioridad.setSelectedIndex(0);

        tablaTareas.clearSelection();
        btnAgregar.setEnabled(true);
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void cargarDatosSeleccion() {
        int fila = tablaTareas.getSelectedRow();
        if (fila >= 0) {
            cmbProyectos.setSelectedItem(modeloTabla.getValueAt(fila, 1));
            cmbUsuarios.setSelectedItem(modeloTabla.getValueAt(fila, 2));
            txtDescripcion.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtFechaInicio.setText(modeloTabla.getValueAt(fila, 4).toString());
            txtFechaFin.setText(modeloTabla.getValueAt(fila, 5).toString());
            cmbEstado.setSelectedItem(modeloTabla.getValueAt(fila, 6));
            spnPorcentaje.setValue(Integer.parseInt(modeloTabla.getValueAt(fila, 7).toString()));
            cmbPrioridad.setSelectedItem(modeloTabla.getValueAt(fila, 8));
        }
    }

    private void agregarTarea() {
        String proyecto = cmbProyectos.getSelectedItem().toString();
        String usuario = cmbUsuarios.getSelectedItem().toString();
        String descripcion = txtDescripcion.getText().trim();
        String fechaInicio = txtFechaInicio.getText().trim();
        String fechaFin = txtFechaFin.getText().trim();
        String estado = cmbEstado.getSelectedItem().toString();
        int porcentaje = (Integer) spnPorcentaje.getValue();
        String prioridad = cmbPrioridad.getSelectedItem().toString();

        if (descripcion.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios (Descripción, Fecha Inicio, Fecha Fin).");
            return;
        }

        int id = modeloTabla.getRowCount() + 1; // ID simple incremental

        modeloTabla.addRow(new Object[]{
                id, proyecto, usuario, descripcion, fechaInicio, fechaFin, estado, porcentaje, prioridad
        });

        limpiarCampos();
    }

    private void editarTarea() {
        int fila = tablaTareas.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea para editar.");
            return;
        }

        String proyecto = cmbProyectos.getSelectedItem().toString();
        String usuario = cmbUsuarios.getSelectedItem().toString();
        String descripcion = txtDescripcion.getText().trim();
        String fechaInicio = txtFechaInicio.getText().trim();
        String fechaFin = txtFechaFin.getText().trim();
        String estado = cmbEstado.getSelectedItem().toString();
        int porcentaje = (Integer) spnPorcentaje.getValue();
        String prioridad = cmbPrioridad.getSelectedItem().toString();

        if (descripcion.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios (Descripción, Fecha Inicio, Fecha Fin).");
            return;
        }

        modeloTabla.setValueAt(proyecto, fila, 1);
        modeloTabla.setValueAt(usuario, fila, 2);
        modeloTabla.setValueAt(descripcion, fila, 3);
        modeloTabla.setValueAt(fechaInicio, fila, 4);
        modeloTabla.setValueAt(fechaFin, fila, 5);
        modeloTabla.setValueAt(estado, fila, 6);
        modeloTabla.setValueAt(porcentaje, fila, 7);
        modeloTabla.setValueAt(prioridad, fila, 8);

        limpiarCampos();
    }

    private void eliminarTarea() {
        int fila = tablaTareas.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea para eliminar.");
            return;
        }

        modeloTabla.removeRow(fila);
        limpiarCampos();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        cmbPrioridad = new javax.swing.JComboBox<>();
        btnAgregar = new javax.swing.JButton();
        lblFechaInicio = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        txtFechaInicio = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JButton();
        lblFechaFin = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        txtFechaFin = new javax.swing.JTextField();
        lblProyecto = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaTareas = new javax.swing.JTable();
        lblEstado = new javax.swing.JLabel();
        cmbProyectos = new javax.swing.JComboBox<>();
        cmbEstado = new javax.swing.JComboBox<>();
        lblUsuario = new javax.swing.JLabel();
        lblPorcentaje = new javax.swing.JLabel();
        cmbUsuarios = new javax.swing.JComboBox<>();
        spnPorcentaje = new javax.swing.JSpinner();
        lblDescripcion = new javax.swing.JLabel();
        lblPrioridad = new javax.swing.JLabel();

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        txtDescripcion.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                txtDescripcionAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(txtDescripcion);

        cmbPrioridad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbPrioridad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPrioridadActionPerformed(evt);
            }
        });

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        lblFechaInicio.setText("FechaInicio");
        lblFechaInicio.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblFechaInicioAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        txtFechaInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaInicioActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        lblFechaFin.setText("FechaFin");
        lblFechaFin.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblFechaFinAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        lblTitulo.setText("Titulo");
        lblTitulo.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblTituloAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        txtFechaFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaFinActionPerformed(evt);
            }
        });

        lblProyecto.setText("Proyecto");
        lblProyecto.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblProyectoAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        tablaTareas.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaTareas.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tablaTareasAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane2.setViewportView(tablaTareas);

        lblEstado.setText("Estado");
        lblEstado.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblEstadoAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        cmbProyectos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbProyectos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProyectosActionPerformed(evt);
            }
        });

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEstadoActionPerformed(evt);
            }
        });

        lblUsuario.setText("Usuario");
        lblUsuario.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblUsuarioAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        lblPorcentaje.setText("Porcentaje");
        lblPorcentaje.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblPorcentajeAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        cmbUsuarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbUsuariosActionPerformed(evt);
            }
        });

        spnPorcentaje.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                spnPorcentajeAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        lblDescripcion.setText("Descripcion");
        lblDescripcion.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblDescripcionAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        lblPrioridad.setText("Prioridad");
        lblPrioridad.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblPrioridadAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(lblProyecto)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblDescripcion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblFechaFin)
                    .addComponent(lblEstado)
                    .addComponent(lblPorcentaje)
                    .addComponent(lblPrioridad)
                    .addComponent(lblFechaInicio)
                    .addComponent(btnAgregar)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmbProyectos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmbUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(spnPorcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                                    .addComponent(txtFechaFin))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(246, 246, 246))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(cmbProyectos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnPorcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProyecto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDescripcion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFechaInicio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFechaFin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEstado))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblPorcentaje)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPrioridad)
                        .addGap(18, 18, 18)
                        .addComponent(btnAgregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtDescripcionAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtDescripcionAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionAncestorAdded

    private void cmbPrioridadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPrioridadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPrioridadActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void lblFechaInicioAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblFechaInicioAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_lblFechaInicioAncestorAdded

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarActionPerformed

    private void txtFechaInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaInicioActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void lblFechaFinAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblFechaFinAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_lblFechaFinAncestorAdded

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void lblTituloAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblTituloAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTituloAncestorAdded

    private void txtFechaFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaFinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaFinActionPerformed

    private void lblProyectoAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblProyectoAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_lblProyectoAncestorAdded

    private void tablaTareasAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tablaTareasAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaTareasAncestorAdded

    private void lblEstadoAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblEstadoAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEstadoAncestorAdded

    private void cmbProyectosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProyectosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbProyectosActionPerformed

    private void cmbEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEstadoActionPerformed

    private void lblUsuarioAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblUsuarioAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_lblUsuarioAncestorAdded

    private void lblPorcentajeAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblPorcentajeAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_lblPorcentajeAncestorAdded

    private void cmbUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbUsuariosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbUsuariosActionPerformed

    private void spnPorcentajeAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_spnPorcentajeAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_spnPorcentajeAncestorAdded

    private void lblDescripcionAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblDescripcionAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_lblDescripcionAncestorAdded

    private void lblPrioridadAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblPrioridadAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_lblPrioridadAncestorAdded


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<String> cmbPrioridad;
    private javax.swing.JComboBox<String> cmbProyectos;
    private javax.swing.JComboBox<String> cmbUsuarios;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JLabel lblFechaInicio;
    private javax.swing.JLabel lblPorcentaje;
    private javax.swing.JLabel lblPrioridad;
    private javax.swing.JLabel lblProyecto;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JSpinner spnPorcentaje;
    private javax.swing.JTable tablaTareas;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtFechaFin;
    private javax.swing.JTextField txtFechaInicio;
    // End of variables declaration//GEN-END:variables
}
