package boatrush;

import boatrush.Joueur;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import jdbc.JoueurSQL;

public class FenetreDeJeu extends JFrame implements ActionListener, KeyListener {

    // Image utilisée pour dessiner l'état actuel du jeu (framebuffer)
    private BufferedImage framebuffer;
    
    // Contexte graphique utilisé pour dessiner sur le framebuffer
    private Graphics2D contexte;
    
    // Composant Swing qui affichera le framebuffer à l'écran
    private JLabel jLabel1;

    // Instance du moteur de jeu, qui contient les entités et la logique
    private Jeu jeu;

    // Timer qui gère la boucle de jeu (mise à jour + rendu toutes les 100ms)
    private Timer timer;

    // Constantes de dimension de la fenêtre
    public static final int LARGEUR_FENETRE = 640;
    public static final int HAUTEUR_FENETRE = 1000;

    // Constructeur principal de la fenêtre de jeu
    public FenetreDeJeu(Joueur j) {
        this.setResizable(false); // Empêche le redimensionnement de la fenêtre
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ferme l'application à la fermeture

        // Initialisation du composant d'affichage (JLabel contenant l'image)
        this.jLabel1 = new JLabel();
        this.jLabel1.setPreferredSize(new java.awt.Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
        this.setContentPane(this.jLabel1);
        this.pack(); // Adapte la fenêtre à la taille du contenu

        // Création du framebuffer et initialisation du contexte graphique
        this.framebuffer = new BufferedImage(this.jLabel1.getWidth(), this.jLabel1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(framebuffer));
        this.contexte = this.framebuffer.createGraphics();

        // Initialisation du moteur de jeu avec le joueur actif
        this.jeu = new Jeu(j);

        // Initialisation de la boucle de jeu : appelle actionPerformed toutes les 100 ms
        this.timer = new Timer(100, this);
        this.timer.start();

        // Enregistrement de la fenêtre comme écouteur clavier
        this.addKeyListener(this);

        // Gestion de la fermeture de fenêtre : appelle dispose()
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                dispose();
            }
        });
    }

    // Boucle principale appelée par le Timer toutes les 100 ms
    @Override
    public void actionPerformed(ActionEvent e) {
        this.jeu.miseAJour();         // Mise à jour de l’état du jeu
        this.jeu.rendu(contexte);     // Dessin de la scène actuelle
        this.jLabel1.repaint();       // Demande le rafraîchissement de l'affichage

        // Vérifie si la partie est terminée
        if (this.jeu.estTermine()) {
            this.timer.stop(); // Stoppe le Timer

            Joueur joueur = this.jeu.getJoueur();
            JoueurSQL joueurSQL = new JoueurSQL();
            int classement = joueurSQL.getClassement(joueur);

            // Affiche un message de félicitations et propose de rejouer
            int choix = JOptionPane.showOptionDialog(
                this,
                "Félicitations " + joueur.getNom() + ", tu termines à la place #" + classement + " !\nQue souhaites-tu faire ?",
                "Course terminée",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Rejouer", "Quitter"},
                "Rejouer"
            );

            // Supprime le joueur actuel de la BDD
            joueurSQL.supprimerJoueur(joueur);

            if (choix == JOptionPane.YES_OPTION) {
                // Rejouer : crée un nouveau joueur à une nouvelle position
                int nombreJoueurs = 1 + joueurSQL.getTousLesJoueursPasPret().size();
                this.dispose();
                Joueur nouveau = new Joueur(joueur.getNom(), nombreJoueurs * 125, 3100, joueur.getAvatar().getSkinId());
                JoueurSQL nouveauSQL = new JoueurSQL();
                nouveauSQL.creerJoueur(nouveau);
                nouveauSQL.closeTable();

                // Retour à l'écran de la salle d'attente
                java.awt.EventQueue.invokeLater(() -> {
                    new GUI.SalleAttente(nouveau).setVisible(true);
                });

            } else {
                // Quitter l’application
                this.dispose();
            }
        }
    }

    // Ne fait rien mais doit être implémentée pour respecter l'interface KeyListener
    @Override
    public void keyTyped(KeyEvent evt) {}

    // Lorsqu’une touche est enfoncée, on active la direction correspondante
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

    // Lorsqu’une touche est relâchée, on désactive la direction correspondante
    @Override
    public void keyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jeu.getJoueur().setToucheHaut(false);
        }
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jeu.getJoueur().setToucheBas(false);
        }
        if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.jeu.getJoueur().setToucheDroite(false);
        }
        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            this.jeu.getJoueur().setToucheGauche(false);
        }
    }

    // Variable pour éviter de supprimer plusieurs fois le même joueur
    private boolean joueurSupprime = false;

    // Méthode appelée à la fermeture de la fenêtre (manuellement ou automatiquement)
    @Override
    public void dispose() {
        if (!joueurSupprime && jeu != null && jeu.getJoueur() != null) {
            JoueurSQL joueurSQL = new JoueurSQL();
            joueurSQL.supprimerJoueur(jeu.getJoueur()); // Supprime le joueur de la base
            joueurSQL.closeTable();
            joueurSupprime = true;
        }
        super.dispose(); // Appelle le comportement par défaut de fermeture
    }

    // Point d’entrée vide ici, pourrait être utilisé pour lancer directement le jeu
    public static void main(String[] args) {
    }
}
