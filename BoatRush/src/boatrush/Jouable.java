package boatrush;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import outils.SingletonJDBC;

public class Jouable {

    public ArrayList<Joueurs> listeJoueurs;
  
    public Jouable(ArrayList<Joueurs> listeJoueurs) {
        this.listeJoueurs = listeJoueurs;
    }

    public ArrayList<Joueurs> getListeJoueurs() {
        return listeJoueurs;
    }

    public void setListeJoueurs(ArrayList<Joueurs> listeJoueurs) {
        this.listeJoueurs = listeJoueurs;
    }

    public void miseAJour() {
        for (Joueurs j : listeJoueurs) {
            j.miseAJour();
        }
    }

    public void rendu(Graphics2D contexte) {
        for (Joueurs j : listeJoueurs) {
            j.getAvatar().rendu(contexte);
        }
    }

}
