package boatrush;

import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class FenetreDeJeu extends JFrame implements ActionListener, KeyListener {

    private BufferedImage framebuffer;
    private Graphics2D contexte;
    private JLabel jLabel1;
    private Jeu jeu;
    private Timer timer;

    Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    int hauteurEcranDisponible = bounds.height;
    int largeurEcran = Toolkit.getDefaultToolkit().getScreenSize().width;

    int hauteurFenetre = hauteurEcranDisponible; // Hauteur dispo sans la barre des tâches
    int largeurFenetre = largeurEcran / 4;       // Un quart de la largeur totale

    public FenetreDeJeu() {
        // initialisation de la fenetre
        //this.setSize(3240, 1680);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jLabel1 = new JLabel();
        this.jLabel1.setPreferredSize(new java.awt.Dimension(largeurFenetre, hauteurFenetre));
        this.setContentPane(this.jLabel1);
        this.pack();

        // Creation du buffer pour l'affichage du jeu et recuperation du contexte graphique
        this.framebuffer = new BufferedImage(this.jLabel1.getWidth(), this.jLabel1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(framebuffer));
        this.contexte = this.framebuffer.createGraphics();

        // Creation du jeu
        this.jeu = new Jeu();

        // Creation du Timer qui appelle this.actionPerformed() tous les 1 s
        this.timer = new Timer(100, this);
        this.timer.start();

        // Ajout d'un ecouteur clavier
        this.addKeyListener(this);
    }

    // Methode appelee par le timer et qui effectue la boucle de jeu
    @Override
    public void actionPerformed(ActionEvent e) {
        this.jeu.miseAJour();
        this.jeu.rendu(contexte);
        this.jLabel1.repaint();
        if (this.jeu.estTermine()) {
            this.timer.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent evt) {
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == evt.VK_UP) {
            this.jeu.getJoueur().setToucheHaut(true);
        }
        if (evt.getKeyCode() == evt.VK_DOWN) {
            this.jeu.getJoueur().setToucheBas(true);
        }
        if (evt.getKeyCode() == evt.VK_RIGHT) {
            this.jeu.getJoueur().setToucheDroite(true);
        }
        if (evt.getKeyCode() == evt.VK_LEFT) {
            this.jeu.getJoueur().setToucheGauche(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {

    }

    public static void main(String[] args) {
        FenetreDeJeu fenetre = new FenetreDeJeu();
        fenetre.setVisible(true);
    }

}
