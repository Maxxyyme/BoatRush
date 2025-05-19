package jdbc;

import boatrush.Monstre;
import boatrush.Obstacle;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class MonstreSQL {

    private final String adresseBase = "jdbc:mariadb://nemrod.ens2m.fr:3306/2024-2025_s2_vs1_tp2_boatrush";
    private final String user = "etudiant";
    private final String motdepasse = "YTDTvj9TR3CDYCmP";
    private Connection connexion;

    public MonstreSQL() {
        try {
            this.connexion = DriverManager.getConnection(adresseBase, user, motdepasse);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Monstre> getTousLesMonstres() {
        ArrayList<Monstre> monstres = new ArrayList<>();
        try (PreparedStatement requete = connexion.prepareStatement("SELECT id, x_coordinate, y_coordinate FROM monstres"); ResultSet resultat = requete.executeQuery()) {

            while (resultat.next()) {
                monstres.add(new Monstre(
                        resultat.getInt("id"),
                        resultat.getInt("x_coordinate"),
                        resultat.getInt("y_coordinate")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return monstres;
    }

    public void creerMonstre(Monstre m) {
        try (PreparedStatement requete = connexion.prepareStatement(
                "INSERT INTO monstres (id, x_coordinate, y_coordinate) VALUES (?, ?, ?)")) {
            requete.setInt(1, m.getId());
            requete.setInt(2, m.getX());
            requete.setInt(3, m.getY());
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void modifierMonstre(Monstre m) {
        try (PreparedStatement requete = connexion.prepareStatement(
                "UPDATE monstres SET x_coordinate = ?, y_coordinate = ? WHERE id = ?")) {
            requete.setInt(1, m.getX());
            requete.setInt(2, m.getY());
            requete.setInt(3, m.getId());
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void supprimerTousLesMonstres() {
        try (PreparedStatement requete = connexion.prepareStatement("DELETE FROM monstres")) {
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void genererMonstres(int n) {
        Random rand = new Random();
        ArrayList<Monstre> existants = getTousLesMonstres();
        int idDepart = existants.size() + 1;

        // Définir les bornes de génération en Y
        int yMin = 200;
        int yMax = 2800;

        // Calcul de l'espacement entre chaque monstre
        int espace = (yMax - yMin) / (n + 1); // +1 pour éviter les extrêmes

        for (int i = 0; i < n; i++) {
            int x = rand.nextInt(600 - 10 + 1) + 10; // X aléatoire
            int y = yMin + (i + 1) * espace;        // Y réparti linéairement

            Monstre nouveauMonstre = new Monstre(idDepart + i, x, y);
            creerMonstre(nouveauMonstre);
        }
    }

    public void closeTable() {
        try {
            if (this.connexion != null && !this.connexion.isClosed()) {
                this.connexion.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
