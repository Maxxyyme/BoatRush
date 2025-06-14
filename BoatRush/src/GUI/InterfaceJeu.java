/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import boatrush.FenetreDeJeu;
import boatrush.Joueur;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import jdbc.JoueurSQL;

/**
 *
 * @author rmibord
 */

public class InterfaceJeu extends javax.swing.JFrame {
    private JPanel imagePanel;
    private JLabel imageLabel;
    private Map<String, ImageIcon> imageMap;
    private Image backgroundImage;
    /**
     * Creates new form InterfaceJeu
     */
    public InterfaceJeu() {
        
        backgroundImage = new ImageIcon(getClass().getResource("/resources/backgroundSelection.png")).getImage();
        setContentPane(new InterfaceJeu.BackgroundPanel());
        
        initComponents();
        customInit();
        this.setTitle("Boat Rush");
        this.setLocationRelativeTo(null);

    }
    
    private class BackgroundPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Draw the image to fill the panel
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtextFieldPseudo = new java.awt.TextField();
        jLabel2 = new javax.swing.JLabel();
        jButtonOk1 = new javax.swing.JButton();
        jButtonAnnuler1 = new javax.swing.JButton();
        jButtonEffacer = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(600, 600));

        jtextFieldPseudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtextFieldPseudoActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Choisissez un pseudo :");

        jButtonOk1.setText("Ok");
        jButtonOk1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOk1ActionPerformed(evt);
            }
        });

        jButtonAnnuler1.setText("Annuler");
        jButtonAnnuler1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnnuler1ActionPerformed(evt);
            }
        });

        jButtonEffacer.setText("Effacer");
        jButtonEffacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEffacerActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Choisissez un skin:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vert", "Violet", "Gris", "Brun" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtextFieldPseudo, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonOk1)
                            .addComponent(jLabel3))
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonEffacer)
                            .addComponent(jButtonAnnuler1))))
                .addContainerGap(186, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(172, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtextFieldPseudo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEffacer))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(169, 169, 169)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAnnuler1)
                    .addComponent(jButtonOk1))
                .addGap(43, 43, 43))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtextFieldPseudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtextFieldPseudoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtextFieldPseudoActionPerformed

    private void jButtonOk1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOk1ActionPerformed
        String pseudo = jtextFieldPseudo.getText().trim();

        if (pseudo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir un pseudo.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JoueurSQL joueurSQL = new JoueurSQL();
        Joueur joueur;

        try {
            Joueur joueurExiste = joueurSQL.voirJoueur(pseudo);

            if (joueurExiste != null) {
                JOptionPane.showMessageDialog(null, "Pseudo déjà choisi. Choisissez un autre nom.", "Erreur Pseudo", JOptionPane.ERROR_MESSAGE);
                jtextFieldPseudo.setText("");
                return; // Stop further execution
            }
            String skinChoisi = (String) jComboBox1.getSelectedItem();
            int skinId = 1; // Valeur par défaut

            switch (skinChoisi) {
                case "Vert":
                    skinId = 1;
                    break;
                case "Violet":
                    skinId = 2;
                    break;
                case "Gris":
                    skinId = 3;
                    break;
                case "Brun":
                    skinId = 4;
                    break;
            }
            int nombreJoueurs = 1+joueurSQL.getTousLesJoueurs().size();
            // Create a new Joueur object
            Joueur newPlayer = new Joueur(pseudo, nombreJoueurs*125, 3100, skinId); // Initialize x and y coordinates 

            // Save the new player to the database
            joueurSQL.creerJoueur(newPlayer);
            
            joueurSQL.setSkin(newPlayer, skinId);
            
            
//            // Passe le joueur au jeu
//            FenetreDeJeu fenetreJeu = new FenetreDeJeu(newPlayer);
//            fenetreJeu.setVisible(true);
//            this.setVisible(false);

            // Ouverture salle d'attente
            SalleAttente fenetre = new SalleAttente(newPlayer);
            fenetre.setVisible(true);
            this.setVisible(false);
            
        } finally {
            joueurSQL.closeTable();
        }
    }
    
    private void customInit() {
        // Map skin names to image paths
        imageMap = new HashMap<>();
        imageMap.put("Vert", new ImageIcon("src/resources/GreenBoat.png"));
        imageMap.put("Violet", new ImageIcon("src/resources/PurpleBoat.png"));
        imageMap.put("Gris", new ImageIcon("src/resources/GreyBoat.png")); // Make sure this image exists
        imageMap.put("Brun", new ImageIcon("src/resources/BrownBoat.png"));

        // Create panel and label for image display
        imagePanel = new JPanel();
        imageLabel = new JLabel();
        imagePanel.add(imageLabel);

        // Position the panel )
        imagePanel.setBounds(180, 340, 200, 120);
        this.setLayout(null); // Use absolute layout to position components manually
        this.add(imagePanel);

        // Show initial image
        updateImage((String) jComboBox1.getSelectedItem());

        // Add listener to combo box to update image
        jComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateImage((String) jComboBox1.getSelectedItem());
            }
        });
    }
    
    private void updateImage(String selectedSkin) {
    ImageIcon icon = imageMap.get(selectedSkin);
    if (icon != null) {
        imageLabel.setIcon(icon);
    } else {
        imageLabel.setIcon(null);
    }
}

      /* 

        try {
            // Vérifie si le joueur existe déjà
            joueur = joueurSQL.voirJoueur(pseudo);

            if (joueur != null) {
                // Si le joueur existe déjà, affiche une erreur et ne lance pas le jeu
                JOptionPane.showMessageDialog(this,
                        "Ce pseudo est déjà utilisé. Veuillez en choisir un autre.",
                        "Pseudo déjà utilisé",
                        JOptionPane.ERROR_MESSAGE);
                return; // Bloque l'ouverture du jeu
            }

            // Sinon, on le crée à la position (0,0)
            joueur = new Joueurs(pseudo, 0, 0);
            joueurSQL.creerJoueur(joueur);

            // Passe le joueur au jeu
            FenetreDeJeu fenetreJeu = new FenetreDeJeu(joueur);
            fenetreJeu.setVisible(true);
            this.setVisible(false);

        } finally {
            joueurSQL.closeTable();
        }
    

    }//GEN-LAST:event_jButtonOk1ActionPerformed
*/
    private void jButtonAnnuler1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnuler1ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonAnnuler1ActionPerformed

    private void jButtonEffacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEffacerActionPerformed
        jtextFieldPseudo.setText("");
    }//GEN-LAST:event_jButtonEffacerActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(InterfaceJeu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(InterfaceJeu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(InterfaceJeu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(InterfaceJeu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new InterfaceJeu().setVisible(true);
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnnuler1;
    private javax.swing.JButton jButtonEffacer;
    private javax.swing.JButton jButtonOk1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private java.awt.TextField jtextFieldPseudo;
    // End of variables declaration//GEN-END:variables
}
