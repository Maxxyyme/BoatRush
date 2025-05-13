package boatrush;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import jdbc.JoueurSQL;

public class FenetreDeJeu extends JFrame implements ActionListener, KeyListener {

    private BufferedImage framebuffer;
    private Graphics2D contexte;
    private JLabel jLabel1;
    private Jeu jeu;
    private Timer timer;
    public static final int LARGEUR_FENETRE = 640;
    public static final int HAUTEUR_FENETRE = 1000;

    // Constructeur principal
    public FenetreDeJeu(Joueur j) {
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jLabel1 = new JLabel();
        this.jLabel1.setPreferredSize(new java.awt.Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
        this.setContentPane(this.jLabel1);
        this.pack();

        // Création du buffer graphique
        this.framebuffer = new BufferedImage(this.jLabel1.getWidth(), this.jLabel1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(framebuffer));
        this.contexte = this.framebuffer.createGraphics();

        // Création du jeu (joueur à affecter plus tard)
        this.jeu = new Jeu(j);

        // Timer pour la boucle de jeu
        this.timer = new Timer(100, this);
        this.timer.start();

        // Écouteur clavier
        this.addKeyListener(this);

        // Écouteur fermeture de la fenêtre
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                dispose();
            }
        });
    }



    // Boucle de jeu
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
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jeu.getJoueur().setToucheHaut(true);
        }
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jeu.getJoueur().setToucheBas(true);
        }
        if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.jeu.getJoueur().setToucheDroite(true);
        }
        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            this.jeu.getJoueur().setToucheGauche(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
    }

    // Méthode appelée à la fermeture de la fenêtre pour supprimer le joueur actif
    @Override
    public void dispose() {
        if (jeu != null && jeu.getJoueur() != null) {
            JoueurSQL joueurSQL = new JoueurSQL();
            joueurSQL.supprimerJoueur(jeu.getJoueur());
            joueurSQL.closeTable();

            // Supprimer aussi de la liste locale
            jeu.getListeJoueur().getListeJoueurs().remove(jeu.getJoueur());
        }
        super.dispose();
    }

    // Démarrage manuel
    public static void main(String[] args) {
    }
}
