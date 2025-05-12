package TileMapping;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class Carte {
    private int[][] tuiles;
    private BufferedImage[] imagesTuiles;

    public Carte(String fichierCarte) {
        try {
            // Chargement du tileset
            BufferedImage tileset = ImageIO.read(getClass().getResource("/TileMapping/images/tileSetPlage32x32.png"));
            
            int nbColonnes = tileset.getWidth() / 32;
            int nbLignes = tileset.getHeight() / 32;
            int nbTuiles = nbColonnes * nbLignes;

            imagesTuiles = new BufferedImage[nbTuiles];
            
            for (int ligne = 0; ligne < nbLignes; ligne++) {
                for (int colonne = 0; colonne < nbColonnes; colonne++) {
                    int index = colonne + ligne * nbColonnes;
                    imagesTuiles[index] = tileset.getSubimage(colonne * 32, ligne * 32, 32, 32);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Le fichier d'image est introuvable !");
        }

        // Chargement de la carte
        try (InputStream is = getClass().getResourceAsStream("/TileMapping/" + fichierCarte);
             Scanner scanner = new Scanner(is)) {
            
            // Ignorer les deux premières lignes (titre et chemin de l'image)
            scanner.nextLine(); // Ignore "Carte Océan"
            scanner.nextLine(); // Ignore "images/tileSet32x32.png"
            
            int largeur = scanner.nextInt();
            int hauteur = scanner.nextInt();
            
            // skip 32 
            scanner.nextInt();
            
            tuiles = new int[hauteur][largeur];

            for (int y = 0; y < hauteur; y++) {
                for (int x = 0; x < largeur; x++) {
                    if (scanner.hasNextInt()) {
                        tuiles[y][x] = scanner.nextInt();
                    }
                }
            }
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Le fichier carte texte est introuvable !");
        }
    }

    public void rendu(Graphics2D g) {
        for (int y = 0; y < tuiles.length; y++) {
            for (int x = 0; x < tuiles[0].length; x++) {
                int tuileIndex = tuiles[y][x];
                if (tuileIndex >= 0 && tuileIndex < imagesTuiles.length) {
                    g.drawImage(imagesTuiles[tuileIndex], x * 32, y * 32, null);
                }
            }
        }
    }

    void miseAJour() {
        
    }
}

