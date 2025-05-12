package boatrush;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import outils.SingletonJDBC;

public class Avatar {

    private String pseudo;
    protected BufferedImage spriteSheet;
    protected BufferedImage currentsprite;
    private int indiceSprite;

    public Avatar() {
        


        try {
            this.spriteSheet = ImageIO.read(getClass().getResource("../resources/SpritesVert.png"));

        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.indiceSprite = 0;

    }

    public void updateSprite() {
        this.currentsprite = spriteSheet.getSubimage(indiceSprite * 32, 0, 32, 50);
    }

    public void miseAJour() {

        this.updateSprite();
        this.indiceSprite = (this.indiceSprite + 1) % 4;


    }
    
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void rendu(Graphics2D contexte) {
        try {
            Connection connexion = SingletonJDBC.getInstance().getConnection();

            PreparedStatement requete = connexion.prepareStatement("SELECT x_coordinate, y_coordinate FROM joueurs WHERE pseudo = ?");
            requete.setString(1, pseudo);
            ResultSet resultat = requete.executeQuery();
            if (resultat.next()) {
                int x_coordinate = resultat.getInt("x_coordinate");
                int y_coordinate = resultat.getInt("y_coordinate");
                //System.out.println(pseudo + " = (" + latitude + "; " + longitude + ")");

                //contexte.setColor(Color.red);
                //contexte.drawOval(x_coordinate - 7, y_coordinate - 7, 14, 14);
                //contexte.drawString(pseudo, x + 8, y - 8);
                contexte.drawImage(this.currentsprite, (int) x_coordinate, (int) y_coordinate, null);
            }
            requete.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
