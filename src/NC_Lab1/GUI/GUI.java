package NC_Lab1.GUI;

import NC_Lab1.controller.ClientController;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ovikee 10.11.15
 */
public class GUI extends javax.swing.JFrame {

    private ClientController ctrl =new ClientController("default.muslib");// ClientController.getInstance();
    ArrayList<String> strings = new ArrayList<>();
    ArrayList<String> param = new ArrayList<>();
    private final String systemMessage = "all is good.";
    private boolean changed = false;

    public GUI() {
        initComponents();
    }
    public void startController(){
        ctrl.startClient();
    }

    public void errorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public enum FindTrack {

        ById, ByName, ByArtist, ByAlbum, ByGenre, ByAllTrack
    };

    /**
     * str - доп. параметр case 1:поиск по номеру трека case 2:поиск по названию
     * трека case 3:поиск по исполнителям case 4:поиск по альбомам case 5:поиск
     * по жанрам case 6:поиск всех треков
     */
    private void findAndShowInTable(FindTrack find, String str) {

        strings = new ArrayList<>();

        jLabelSystemMessage.setText(systemMessage);
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        for (int i = jTable.getRowCount(); i > 0; i--) {
            model.removeRow(i - 1);
        }

        try {
            switch (find) {

                case ById:
                    strings.addAll(ctrl.findTrackById(Long.parseLong(str)));
                    break;
                case ByName:
                    strings.addAll(ctrl.findTrackByName(str));
                    break;
                case ByArtist:
                    strings.addAll(ctrl.findTrackByArtist(str));
                    break;
                case ByAlbum:
                    strings.addAll(ctrl.findTrackByAlbum(str));
                    break;
                case ByGenre:
                    strings.addAll(ctrl.findTrackByGenre(str));
                    break;
                case ByAllTrack:
                    strings.addAll(ctrl.findAllTracks());
                    break;
            }

            StringTokenizer st;// = new StringTokenizer(strings);
            for (String string : strings) {
                st = new StringTokenizer(string, " ");
                System.out.println(string);
                model.addRow(
                        new Object[]{
                            st.nextToken(),
                            st.nextToken(),
                            st.nextToken(),
                            st.nextToken(),
                            st.nextToken(),
                            st.nextToken()}
                );
            }//Возможна ошибка, если все параметры трека не указаны

        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            errorMessage("Не удалось открыть/сохранить файл!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            errorMessage("ClassNotFoundException!");
        } catch (Exception ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            errorMessage("Треки не найдены!");
        }

    }

    public void startState() {
        jComboBoxGenre.removeAllItems();
        try {
            strings = new ArrayList<>();
            strings = ctrl.findAllGenre();
            StringTokenizer st;
            for (String string : strings) {
                st = new StringTokenizer(string);
                jComboBoxGenre.addItem(st.nextToken());//инициализируем комбо бокс
            }
            findAndShowInTable(FindTrack.ByAllTrack, "");
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            errorMessage("Ошибка при загрузке файла!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            errorMessage("ClassNotFoundException!");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jBAdd = new javax.swing.JButton();
        jBDelete = new javax.swing.JButton();
        jBShowAll = new javax.swing.JButton();
        jBUpdate = new javax.swing.JButton();
        jBFind = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTFName = new javax.swing.JTextField();
        jTFArtist = new javax.swing.JTextField();
        jTFAlbum = new javax.swing.JTextField();
        jTFLength = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabelSystemMessage = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTFID = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLErrorId = new javax.swing.JLabel();
        jComboBoxGenre = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTFind = new javax.swing.JTextField();
        jComboBoxFindBy = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jTFGenre = new javax.swing.JTextField();
        jToggleButton1 = new javax.swing.JToggleButton();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuOpenDefault = new javax.swing.JMenu();
        jMenuItemOpen = new javax.swing.JMenuItem();
        jOpenDefault = new javax.swing.JMenuItem();
        jMenuItemSave = new javax.swing.JMenuItem();
        jMenuItemSaveAs = new javax.swing.JMenuItem();
        jMenuItemImport = new javax.swing.JMenuItem();
        jMenuItemExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "Artist", "Album", "Length", "Genre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable);
        if (jTable.getColumnModel().getColumnCount() > 0) {
            jTable.getColumnModel().getColumn(5).setResizable(false);
        }

        jBAdd.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jBAdd.setText("Add new track");
        jBAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddActionPerformed(evt);
            }
        });

        jBDelete.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jBDelete.setText("Delete");
        jBDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDeleteActionPerformed(evt);
            }
        });

        jBShowAll.setFont(new java.awt.Font("Times New Roman", 2, 18)); // NOI18N
        jBShowAll.setText("Show all track");
        jBShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBShowAllActionPerformed(evt);
            }
        });

        jBUpdate.setFont(new java.awt.Font("Times New Roman", 2, 18)); // NOI18N
        jBUpdate.setText("Update");
        jBUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUpdateActionPerformed(evt);
            }
        });

        jBFind.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jBFind.setText("Find");
        jBFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBFindActionPerformed(evt);
            }
        });

        jLabel1.setText("Name");

        jLabel2.setText("Artist");

        jLabel3.setText("Album");

        jLabel4.setText("Length");

        jTFArtist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFArtistActionPerformed(evt);
            }
        });

        jLabel5.setText("System messge:");

        jLabelSystemMessage.setText("all is good");

        jLabel6.setText("ID");

        jTFID.setEditable(false);
        jTFID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFIDActionPerformed(evt);
            }
        });

        jLabel7.setText("Genre");

        jLabel8.setText("Добавление новых треков:");

        jLabel9.setText("Поиск треков:");

        jComboBoxFindBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Id", "Name", "Artist", "Album", "Genre" }));
        jComboBoxFindBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFindByActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Times New Roman", 2, 18)); // NOI18N
        jButton1.setText("editor of genres");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTFGenre.setEditable(false);

        jToggleButton1.setText("Переключить");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("auto");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jMenuOpenDefault.setText("File");

        jMenuItemOpen.setText("Open");
        jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOpenActionPerformed(evt);
            }
        });
        jMenuOpenDefault.add(jMenuItemOpen);

        jOpenDefault.setText("OpenDefault");
        jOpenDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jOpenDefaultActionPerformed(evt);
            }
        });
        jMenuOpenDefault.add(jOpenDefault);

        jMenuItemSave.setText("Save");
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveActionPerformed(evt);
            }
        });
        jMenuOpenDefault.add(jMenuItemSave);

        jMenuItemSaveAs.setText("Save as..");
        jMenuItemSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveAsActionPerformed(evt);
            }
        });
        jMenuOpenDefault.add(jMenuItemSaveAs);

        jMenuItemImport.setText("Import tracks");
        jMenuItemImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemImportActionPerformed(evt);
            }
        });
        jMenuOpenDefault.add(jMenuItemImport);

        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuOpenDefault.add(jMenuItemExit);

        jMenuBar1.add(jMenuOpenDefault);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelSystemMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTFind, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addGap(9, 9, 9)
                                .addComponent(jComboBoxFindBy, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLErrorId, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel6))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jTFID))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTFLength)
                                                    .addComponent(jTFAlbum)
                                                    .addComponent(jTFArtist)
                                                    .addComponent(jTFName)))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(22, 22, 22)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jComboBoxGenre, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTFGenre))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(197, 197, 197)
                                        .addComponent(jBFind, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton2)
                                            .addComponent(jToggleButton1))))
                                .addGap(94, 94, 94)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBUpdate)
                    .addComponent(jBDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jBShowAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFind, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                            .addComponent(jComboBoxFindBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBFind, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(239, 239, 239))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTFID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1)
                                    .addComponent(jTFName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jTFArtist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jTFAlbum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jTFLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jTFGenre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jToggleButton1)))
                            .addComponent(jLErrorId, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxGenre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBShowAll, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSystemMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBDelete)
                        .addGap(16, 16, 16)
                        .addComponent(jBUpdate)
                        .addContainerGap(149, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
/**
     * addTrack( String name, String albumName, String artist,long length,
     * String genre)
     */
    private void jBAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddActionPerformed
        changed = true;

        param = new ArrayList<>();

        if (jTFName.getText().equals("") || jTFArtist.getText().equals("")
                || jTFAlbum.getText().equals("") || jTFLength.getText().equals("")
                || ((jTFGenre.isEditable() == true) && (jTFGenre.getText().equals("")))
                || ((jTFGenre.isEditable() != true) && (jComboBoxGenre.getSelectedItem().equals("")))) {
            errorMessage("Заполните все поля!");
        } else {
            // Условие обязательной заполнительности полей
            param.add(jTFName.getText());
            param.add(jTFArtist.getText());
            param.add(jTFAlbum.getText());
            param.add(jTFLength.getText());
            if (jTFGenre.isEditable() == true) {
                param.add(jTFGenre.getText());
            } else {
                param.add(jComboBoxGenre.getSelectedItem().toString());
            }

            try {
                System.out.println("ParametrsCli: " + param);
                ctrl.addTrack(param);

            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                errorMessage("Не удалось открыть/сохранить файл!");
            }
            startState();
        }
    }//GEN-LAST:event_jBAddActionPerformed

    private void jTFArtistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFArtistActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFArtistActionPerformed

    /**
     * Show all tracks Возможна ошибка, если все параметры трека не указаны
     */
    private void jBShowAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBShowAllActionPerformed
        startState();
    }//GEN-LAST:event_jBShowAllActionPerformed

    /**
     * open file
     */
    private void jMenuItemOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOpenActionPerformed
        JFileChooser fileopen = new JFileChooser();

        fileopen.setCurrentDirectory(new File("C:\\Users\\User\\Documents\\NetBeansProjects\\MusicLibrary"));
        try {

            JFileChooser fc = new JFileChooser();
            //для фильтрации по формату
//            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.genre,*.track,*.*");
//            fc.setFileFilter(filter);
            fc.setCurrentDirectory(new File("C:\\Users\\User\\Documents\\NetBeansProjects\\MusicLibrary"));

            if (changed) {
                int i = JOptionPane.showConfirmDialog(null, "Хотите ли Вы сохранить изменения?", "Open", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if (i == 0) {//хотим сохранить изменения

                    if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {//название файла не пустое

                        ctrl.save(fc.getSelectedFile().getName());

                    }
                    fileopen = new JFileChooser();  //окошко для открытия файла
                    if (fileopen.showDialog(null, "Открыть файл") == JFileChooser.APPROVE_OPTION) { //выбран файл
                        ctrl.load(fileopen.getSelectedFile().getName());
                        startState();
                    }
                } else if (i == 1) {
                    fileopen = new JFileChooser();
                    if (fileopen.showDialog(null, "Открыть файл") == JFileChooser.APPROVE_OPTION) {
                        ctrl.load(fileopen.getSelectedFile().getName());
                        startState();
                    }
                }
            } else if (fileopen.showDialog(null, "Открыть файл") == JFileChooser.APPROVE_OPTION) {
                ctrl.load(fileopen.getSelectedFile().getName());
                startState();
            }

        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            errorMessage("Не удалось открыть/сохранить файл!");
        }

    }//GEN-LAST:event_jMenuItemOpenActionPerformed

    private void jBDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDeleteActionPerformed
        changed = true;
        jLabelSystemMessage.setText(systemMessage);
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        if (jTable.getSelectedRow() == -1) {
            if (jTable.getRowCount() == 0) {
                jLabelSystemMessage.setText("Таблица пустая!");
            } else {
                jLabelSystemMessage.setText("Вы должны выбрать трек!");
            }
        } else {
            try {
                ctrl.removeTrackById(Long.parseLong(model.getValueAt(jTable.getSelectedRow(), 0).toString()));
                model.removeRow(jTable.getSelectedRow());
            } catch (Exception ex) {
                errorMessage("Ошибка при удалении трека");
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_jBDeleteActionPerformed

    private void jBUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUpdateActionPerformed
        changed = true;
        param = new ArrayList<>();

        jLabelSystemMessage.setText(systemMessage);
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        if (jTable.getSelectedRow() == -1) {
            if (jTable.getRowCount() == 0) {
                jLabelSystemMessage.setText("Таблица пустая!");
            } else {
                jLabelSystemMessage.setText("Вы должны выбрать трек!");
            }
        } else {
            try {
                param.add(model.getValueAt(jTable.getSelectedRow(), 0).toString());
                param.add(model.getValueAt(jTable.getSelectedRow(), 1).toString());
                param.add(model.getValueAt(jTable.getSelectedRow(), 2).toString());
                param.add(model.getValueAt(jTable.getSelectedRow(), 3).toString());
                param.add(model.getValueAt(jTable.getSelectedRow(), 4).toString());
                param.add(model.getValueAt(jTable.getSelectedRow(), 5).toString());

                ctrl.updateTrack(param);
                startState();

            } catch (Exception ex) {
                errorMessage("Ошибка при обновлении трека");
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_jBUpdateActionPerformed

    private void jMenuItemImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemImportActionPerformed
        changed = true;
         JFileChooser fileopen = new JFileChooser();

        fileopen.setCurrentDirectory(new File("C:\\Users\\User\\Documents\\NetBeansProjects\\MusicLibrary"));
        
        fileopen = new JFileChooser();
                    if (fileopen.showDialog(null, "Импортировать файл") == JFileChooser.APPROVE_OPTION) {
            try {
                ctrl.importTracks(fileopen.getSelectedFile().getName());
                startState();
            } catch (IOException ex) {
                errorMessage("Ошибка при импорте");
            }
                    }
        
    }//GEN-LAST:event_jMenuItemImportActionPerformed

    private void jOpenDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jOpenDefaultActionPerformed
        try {
            ctrl.load("defaultFile.muslib");
            startState();
        } catch (IOException e) {
            errorMessage("Файл не найден");
        }
    }//GEN-LAST:event_jOpenDefaultActionPerformed

    private void jBFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBFindActionPerformed
        jLabelSystemMessage.setText(systemMessage);
        if (jTFind.getText().equals("")) {
            jLabelSystemMessage.setText("Введите в поле поиска значение!");
        } else {
            switch (jComboBoxFindBy.getSelectedItem().toString()) {//Id, Name, Artist, Album, Genre
                case "Id":
                    findAndShowInTable(FindTrack.ById, jTFind.getText());
                    break;
                case "Name":
                    findAndShowInTable(FindTrack.ByName, jTFind.getText());
                    break;
                case "Artist":
                    findAndShowInTable(FindTrack.ByArtist, jTFind.getText());
                    break;
                case "Album":
                    findAndShowInTable(FindTrack.ByAlbum, jTFind.getText());
                    break;
                case "Genre":
                    findAndShowInTable(FindTrack.ByGenre, jTFind.getText());
                    break;
            }

        }//!!!!!!!!!!1 не найдено
    }//GEN-LAST:event_jBFindActionPerformed

    private void jTFIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFIDActionPerformed
        //Проверка на число в поле Id и на пустоту
//        try {
//            jLErrorId.setText("");
//            if (!jTFID.getText().equals("")) {
//                Integer.parseInt(jTFID.getText());
//            }
//        } catch (NumberFormatException nfe) {
//            jLabelSystemMessage.setText("Поле Id ");
//            jLErrorId.setForeground(Color.RED);
//            jLErrorId.setText("*");
//        }
    }//GEN-LAST:event_jTFIDActionPerformed

    private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveActionPerformed

        JFileChooser fc = new JFileChooser();
        //для фильтрации по формату
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.genre,*.track,*.*");
//        fc.setFileFilter(filter);
        fc.setCurrentDirectory(new File("C:\\Users\\User\\Documents\\NetBeansProjects\\MusicLibrary"));

        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                ctrl.save(fc.getSelectedFile().getName());
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItemSaveActionPerformed

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        int i = 1;
        if (changed) {
            i = JOptionPane.showConfirmDialog(null, "Хотите ли Вы сохранить изменения?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        }
        if (i == 0) {

            JFileChooser fc = new JFileChooser();
            //для фильтрации по формату
            //FileNameExtensionFilter filter = new FileNameExtensionFilter("*.genre,*.track,*.*");
            //fc.setFileFilter(filter);
            fc.setCurrentDirectory(new File("C:\\Users\\User\\Documents\\NetBeansProjects\\MusicLibrary"));

            if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    ctrl.save(fc.getSelectedFile().getName());
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (i == 1) {
            System.exit(0);
        }

    }//GEN-LAST:event_jMenuItemExitActionPerformed

    private void jMenuItemSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveAsActionPerformed

        JFileChooser fc = new JFileChooser();
        //для фильтрации по формату
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.genre,*.track,*.*");
//        fc.setFileFilter(filter);
        fc.setCurrentDirectory(new File("C:\\Users\\User\\Documents\\NetBeansProjects\\MusicLibrary"));

        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                ctrl.save(fc.getSelectedFile().getName());
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItemSaveAsActionPerformed

    //автозаполнение
    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        jTFID.setText(model.getValueAt(jTable.getSelectedRow(), 0).toString());
        jTFName.setText(model.getValueAt(jTable.getSelectedRow(), 1).toString());
        jTFArtist.setText(model.getValueAt(jTable.getSelectedRow(), 2).toString());
        jTFAlbum.setText(model.getValueAt(jTable.getSelectedRow(), 3).toString());
        jTFLength.setText(model.getValueAt(jTable.getSelectedRow(), 4).toString());
        jTFGenre.setText(model.getValueAt(jTable.getSelectedRow(), 5).toString());
        jComboBoxGenre.setSelectedItem(model.getValueAt(jTable.getSelectedRow(), 5).toString());

    }//GEN-LAST:event_jTableMouseClicked

    //редактор жанров
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        GenreFrame genre_Frame = new GenreFrame(ctrl);
        genre_Frame.setVisible(true);
        genre_Frame.startState();
        genre_Frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBoxFindByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFindByActionPerformed

    }//GEN-LAST:event_jComboBoxFindByActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        if (jToggleButton1.isSelected()) {
            jTFGenre.setEditable(true);
        } else {
            jTFGenre.setEditable(false);
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // jTFID.setText("1");
        jTFName.setText("tr1");
        jTFArtist.setText("art1");
        jTFAlbum.setText("alb1");
        jTFLength.setText("111");
        jTFGenre.setText("gen1");
        //jComboBoxGenre.setSelectedItem("");

    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAdd;
    private javax.swing.JButton jBDelete;
    private javax.swing.JButton jBFind;
    private javax.swing.JButton jBShowAll;
    private javax.swing.JButton jBUpdate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBoxFindBy;
    private javax.swing.JComboBox jComboBoxGenre;
    private javax.swing.JLabel jLErrorId;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelSystemMessage;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemImport;
    private javax.swing.JMenuItem jMenuItemOpen;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JMenuItem jMenuItemSaveAs;
    private javax.swing.JMenu jMenuOpenDefault;
    private javax.swing.JMenuItem jOpenDefault;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFAlbum;
    private javax.swing.JTextField jTFArtist;
    private javax.swing.JTextField jTFGenre;
    private javax.swing.JTextField jTFID;
    private javax.swing.JTextField jTFLength;
    private javax.swing.JTextField jTFName;
    private javax.swing.JTextField jTFind;
    private javax.swing.JTable jTable;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}
