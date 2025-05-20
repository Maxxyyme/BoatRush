package boatrush;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Représente la carte du jeu à partir d'un fichier texte.
 * Gère l'affichage du décor selon la position du joueur.
 */
public class Carte {

    // Dimensions de la carte
    private int largeur;
    private int hauteur;
    private int tailleTuile;

    // Ensemble des images de tuiles possibles (tileset)
    private BufferedImage[] tuiles;

    // Décor de la carte : indices vers les tuiles
    private int[][] decor;

    /**
     * Constructeur principal.
     * Charge une carte et son tileset à partir d'un fichier texte.
     */
    public Carte(String fichierCarte) {
        try (BufferedReader lecteur = new BufferedReader(new FileReader(fichierCarte))) {
            lecteur.readLine(); // Nom de la carte (ignoré)
            String cheminTileset = lecteur.readLine(); // Chemin vers le fichier image
            String[] dimensions = lecteur.readLine().split(" ");

            this.largeur = Integer.parseInt(dimensions[0]);
            this.hauteur = Integer.parseInt(dimensions[1]);
            this.tailleTuile = Integer.parseInt(dimensions[2]);

            // Initialisation de la grille de décor
            this.decor = new int[hauteur][largeur];

            for (int i = 0; i < hauteur; i++) {
                String[] ligne = lecteur.readLine().split(" ");
                for (int j = 0; j < largeur; j++) {
                    decor[i][j] = Integer.parseInt(ligne[j]);
                }
            }

            // Chargement du tileset
            InputStream is = getClass().getResourceAsStream(cheminTileset);
            if (is == null) {
                throw new IllegalArgumentException("Fichier tileset introuvable : " + cheminTileset);
            }

            BufferedImage tileset = ImageIO.read(is);
            int nbTilesX = tileset.getWidth() / tailleTuile;
            int nbTilesY = tileset.getHeight() / tailleTuile;
            int nbTotalTuiles = nbTilesX * nbTilesY;

            this.tuiles = new BufferedImage[nbTotalTuiles];

            for (int i = 0; i < nbTotalTuiles; i++) {
                int x = (i % nbTilesX) * tailleTuile;
                int y = (i / nbTilesX) * tailleTuile;
                this.tuiles[i] = tileset.getSubimage(x, y, tailleTuile, tailleTuile);
            }

        } catch (IOException e) {
            Logger.getLogger(Carte.class.getName()).log(Level.SEVERE, "Erreur lors du chargement de la carte", e);
        }
    }

    /**
     * Rendu graphique d'une portion de la carte visible à l'écran.
     *
     * @param contexte             Contexte graphique
     * @param positionX           Position horizontale du "caméra" en pixels
     * @param positionY           Position verticale du "caméra" en pixels
     * @param nbTuilesFenetreX    Nombre de tuiles visibles en largeur
     * @param nbTuilesFenetreY    Nombre de tuiles visibles en hauteur
     */
    public void rendu(Graphics2D contexte, double positionX, double positionY, int nbTuilesFenetreX, int nbTuilesFenetreY) {
        int startTileX = (int) (positionX / tailleTuile);
        int startTileY = (int) (positionY / tailleTuile);

        for (int i = startTileY; i <= startTileY + nbTuilesFenetreY; i++) {
            for (int j = startTileX; j <= startTileX + nbTuilesFenetreX; j++) {
                if (i >= 0 && i < hauteur && j >= 0 && j < largeur) {
                    int tile = decor[i][j];
                    int screenX = (int) (j * tailleTuile - positionX);
                    int screenY = (int) (i * tailleTuile - positionY);
                    contexte.drawImage(tuiles[tile], screenX, screenY, null);
                }
            }
        }
    }

    public void miseAJour() {
    }

    // Utilitaires pour les collisions ou limites d’affichage
    public int getLargeurEnPixels() {
        return largeur * tailleTuile;
    }

    public int getHauteurEnPixels() {
        return hauteur * tailleTuile;
    }
}
