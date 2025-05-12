package TileMapping;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreDeJeu extends JPanel implements ActionListener {

    private Jeu jeu;
    private Timer timer;

    public FenetreDeJeu() {
        this.jeu = new Jeu();

        // Démarre un timer pour rafraîchir la fenêtre (60 FPS = 1000ms / 60)
        this.timer = new Timer(1000 / 60, this);
        this.timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dessine le jeu (carte)
        jeu.rendu((Graphics2D) g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Redessine l’écran
        repaint();
    }

    public static void main(String[] args) {
        // Crée une fenêtre
        JFrame fenetre = new JFrame("Mon Jeu de Tuiles");
        FenetreDeJeu panneau = new FenetreDeJeu();

        fenetre.setContentPane(panneau);
        fenetre.setSize(800, 600); // Taille fixe
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setLocationRelativeTo(null); // Centre la fenêtre
        fenetre.setVisible(true);
    }
}