package NC_Lab1.GUI;

import NC_Lab1.controller.ClientController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Графическое окно для редактирования жанров
 *
 * @author ovikeee 18.11.2015
 */
public class GenreFrame extends javax.swing.JFrame {

    private static ClientController ctrl;
    ArrayList<String> strings;
    ArrayList<String> param;

    public enum FindGenre {

        ByName, ByAllGenres
    };

    /**
     * Конструктор.
     *
     * @param cl Клиент-контроллер
     */
    public GenreFrame(ClientController cl) {
        initComponents();
        ctrl = cl;
    }

    /**
     * Обновляет содержимое таблицы, загружая в нее все жанры.
     */
    public void updateTables() {
        showTable(FindGenre.ByAllGenres, "");
    }

    /**
     * Выводит в таблицу результат поиска
     *
     * @param findGenre поиск по имени или все: ByName, ByAllGenres
     * @param str название жанра
     */
    private void showTable(FindGenre findGenre, String str) {
        if (strings == null) {
            strings = new ArrayList<>();
        } else {
            strings.clear();
        }
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (int i = jTable1.getRowCount(); i > 0; i--) {
            model.removeRow(i - 1);
        }
        try {
            switch (findGenre) {
                case ByName:
                    strings = ctrl.findGenreByName(str);
                    break;
                case ByAllGenres:
                    strings = ctrl.findAllGenre();
                    break;
            }
        } catch (IOException e) {
            Logger.getLogger(GenreFrame.class.getName()).log(Level.SEVERE, null, e);
            errorMessage("Ошибка при открытии файла");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenreFrame.class.getName()).log(Level.SEVERE, null, ex);
            errorMessage("ClassNotFoundException!");
        }
        for (String string : strings) {
            model.addRow(
                    new Object[]{string}
            );
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTF_Find = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldNewGenre = new javax.swing.JTextField();
        jButtonAdd = new javax.swing.JButton();
        jButtonRemove = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Найти");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Поиск жанров:");

        jLabel2.setText("Новый жанр:");

        jButtonAdd.setText("Добавить");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonRemove.setText("Удалить");
        jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveActionPerformed(evt);
            }
        });

        jButton4.setText("Найти все!");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButtonUpdate.setText("Изменить");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jTF_Find, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonRemove, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonUpdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonAdd, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addComponent(jTextFieldNewGenre))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTF_Find, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton4))
                .addGap(27, 27, 27)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNewGenre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRemove)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Обработчик кнопки: Add Добавляет новый жанр со сгенерированным id
     * NC_Lab1.GUI.GUI.changed = true;
     */
    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        try {
            ctrl.addGenre(jTextFieldNewGenre.getText());
            NC_Lab1.GUI.GUI.changed = true;
        } catch (IOException ex) {
            Logger.getLogger(GenreFrame.class.getName()).log(Level.SEVERE, null, ex);
            errorMessage("Ошибка ввода/вывода!");
        }
        showTable(FindGenre.ByAllGenres, "");
    }//GEN-LAST:event_jButtonAddActionPerformed

    /**
     * Модальное окно с выводом информации о ошибке
     *
     * @param msg сообщение об ощибке
     */
    public void errorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Обработчик кнопки: Find Поиск по названию жанра
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jTF_Find.getText().equals("")) {
            errorMessage("Введите в поле поиска значение!");
        } else {
            showTable(FindGenre.ByName, jTF_Find.getText());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * Обработчик кнопки: Найти все Выводит все треки в таблицу
     * NC_Lab1.GUI.GUI.changed = true;
     */
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        showTable(FindGenre.ByAllGenres, "");
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * Обработчик кнопки: Delete Удаляет введеный жанр
     */
    private void jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        if (jTable1.getSelectedRow() == -1) {
            if (jTable1.getRowCount() == 0) {
                errorMessage("Таблица пустая!");
            } else {
                errorMessage("Вы должны выбрать жанр!");
            }
        } else {
            try {
                ctrl.removeGenreByName(model.getValueAt(jTable1.getSelectedRow(), 0).toString());
                model.removeRow(jTable1.getSelectedRow());
                NC_Lab1.GUI.GUI.changed = true;
            } catch (IOException ex) {
                errorMessage("Ошибка при удалении трека");
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jButtonRemoveActionPerformed

    /**
     * Обработчик кнопки: Update Изменяет название выделеного жанра
     * NC_Lab1.GUI.GUI.changed = true;
     */
    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        if (param == null) {
            param = new ArrayList<>();
        } else {
            param.clear();
        }
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        if (jTable1.getSelectedRow() == -1) {
            if (jTable1.getRowCount() == 0) {
                errorMessage("Таблица пустая!");
            } else {
                errorMessage("Вы должны выбрать жанр!");
            }
        } else {
            try {
                param.add(model.getValueAt(jTable1.getSelectedRow(), 0).toString());//изменяемый жанр
                param.add(jTextFieldNewGenre.getText());//его новое значение
                ctrl.updateGenre(param);
                showTable(FindGenre.ByAllGenres, "");
                NC_Lab1.GUI.GUI.changed = true;
            } catch (IOException ex) {
                errorMessage("Ошибка при удалении трека");
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButtonUpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonRemove;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTF_Find;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldNewGenre;
    // End of variables declaration//GEN-END:variables

}
