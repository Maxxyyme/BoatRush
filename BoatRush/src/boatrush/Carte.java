/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boatrush;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Exemple de classe carte
 *
 * @author guillaume.laurent
 */
public class Carte {

    private int largeur = 11;
    private int hauteur = 9;
    private int tailleTuile = 32;
    private BufferedImage[] tuiles;
    private int[][] decor;
    private BufferedImage uneTuile;

    public Carte() {
        tuiles = new BufferedImage[180];

        try {
            BufferedImage tileset = ImageIO.read(getClass().getResource("tileSetPlage32x32.png"));
            uneTuile = tileset.getSubimage(2 * tailleTuile, 1 * tailleTuile, tailleTuile, tailleTuile);
            for (int i = 0; i < tuiles.length; i++) {
                int x = (i % 16) * tailleTuile;
                int y = (i / 16) * tailleTuile;
                tuiles[i] = tileset.getSubimage(x, y, tailleTuile, tailleTuile);
            }
        } catch (IOException ex) {
            Logger.getLogger(Carte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void miseAJour() {

    }

//    public void rendu(Graphics2D contexte) {
//        for (int i = 0; i < this.hauteur; i += 1) {
//            for (int j = 0; j < this.largeur; j += 1) {
//                contexte.drawImage(tuiles[decor[i][j]], j * tailleTuile, i * tailleTuile, null);
//            }
//        }
//}
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

    public Carte(String fichierCarte) {
        try (BufferedReader lecteur = new BufferedReader(new FileReader(fichierCarte))) {
            String nomCarte = lecteur.readLine(); // Ligne 1 : nom de la carte (on peut l'ignorer)
            String cheminTileset = lecteur.readLine(); // Ligne 2 : chemin du fichier de tuiles
            System.out.println(cheminTileset);

            String[] dimensions = lecteur.readLine().split(" ");
            this.largeur = Integer.parseInt(dimensions[0]);
            this.hauteur = Integer.parseInt(dimensions[1]);
            this.tailleTuile = Integer.parseInt(dimensions[2]); // On suppose carrée : largeur = hauteur

            // Initialisation du tableau decor
            this.decor = new int[hauteur][largeur]; // ⚠️ hauteur en premier (ligne), largeur ensuite (colonne)

            // Lecture des lignes du décor
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
            e.printStackTrace();
        }
    }

    public int getLargeurEnPixels() {
        return largeur * tailleTuile;
    }

    public int getHauteurEnPixels() {
        return hauteur * tailleTuile;
    }

}
