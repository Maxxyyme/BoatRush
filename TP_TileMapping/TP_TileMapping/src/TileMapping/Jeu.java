package TileMapping;

import java.awt.*;

public class Jeu {
    private Carte carte;

    public Jeu() {
        carte = new Carte("CarteOcean.txt");
    }

    public void rendu(Graphics2D g) {
        carte.rendu(g);
    }

    public void miseAJour() {
        carte.miseAJour();
    }
}

