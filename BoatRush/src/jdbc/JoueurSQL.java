package jdbc;

import java.sql.*;
import java.util.ArrayList;
import boatrush.Joueur;

public class JoueurSQL {

    private final String adresseBase = "jdbc:mariadb://nemrod.ens2m.fr:3306/2024-2025_s2_vs1_tp2_boatrush";
    private final String user = "etudiant";
    private final String motdepasse = "YTDTvj9TR3CDYCmP";
    private Connection connexion;

    /**
     * Initialise la connexion à la base de données.
     */
    public JoueurSQL() {
        try {
            this.connexion = DriverManager.getConnection(adresseBase, user, motdepasse);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Récupère tous les joueurs enregistrés en base.
     */
    public ArrayList<Joueur> getTousLesJoueurs() {
        ArrayList<Joueur> joueurs = new ArrayList<>();
        try (PreparedStatement requete = connexion.prepareStatement("SELECT pseudo, x_coordinate, y_coordinate FROM joueurs"); ResultSet resultat = requete.executeQuery()) {

            while (resultat.next()) {
                joueurs.add(new Joueur(resultat.getString("pseudo"), resultat.getInt("x_coordinate"), resultat.getInt("y_coordinate")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return joueurs;
    }

    /**
     * Crée un nouveau joueur en base.
     */
    public void creerJoueur(Joueur j) {
        try (PreparedStatement requete = connexion.prepareStatement("INSERT INTO joueurs VALUES (?, ?, ?, ?, ?)")) {
            requete.setString(1, j.getNom());
            requete.setInt(2, j.getXCoord());
            requete.setInt(3, j.getYCoord());
            requete.setInt(4, 0);
            requete.setInt(5, 0);
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Met à jour la position d'un joueur en base.
     */
    public void modifierJoueur(Joueur j) {
        try (PreparedStatement requete = connexion.prepareStatement(
                "UPDATE joueurs SET pseudo = ?, x_coordinate = ?, y_coordinate = ? WHERE pseudo = ?")) {
            requete.setString(1, j.getNom());
            requete.setInt(2, j.getXCoord());
            requete.setInt(3, j.getYCoord());
            requete.setString(4, j.getNom());
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Supprime un joueur de la base.
     */
    public void supprimerJoueur(Joueur j) {
        try (PreparedStatement requete = connexion.prepareStatement("DELETE FROM joueurs WHERE pseudo = ?")) {
            requete.setString(1, j.getNom());
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Recherche un joueur par son pseudo.
     */
    public Joueur voirJoueur(String nom) {
        Joueur joueurTrouve = null;
        try (PreparedStatement requete = connexion.prepareStatement("SELECT pseudo, x_coordinate, y_coordinate FROM joueurs WHERE pseudo = ?")) {
            requete.setString(1, nom);
            try (ResultSet resultat = requete.executeQuery()) {
                if (resultat.next()) {
                    joueurTrouve = new Joueur(resultat.getString("pseudo"), resultat.getInt("x_coordinate"), resultat.getInt("y_coordinate"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return joueurTrouve;
    }

    public int getProchainClassement() {
        int maxClassement = 0;
        try (PreparedStatement requete = connexion.prepareStatement("SELECT MAX(classement) AS maxClassement FROM joueurs"); ResultSet resultat = requete.executeQuery()) {
            if (resultat.next()) {
                maxClassement = resultat.getInt("maxClassement");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return maxClassement + 1;
    }

    public void setClassement(Joueur j, int classement) {
        try (PreparedStatement requete = connexion.prepareStatement(
                "UPDATE joueurs SET classement = ? WHERE pseudo = ?")) {
            requete.setInt(1, classement);
            requete.setString(2, j.getNom());
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getClassement(Joueur j) {
        int classement = 0;
        try (PreparedStatement requete = connexion.prepareStatement(
                "SELECT classement FROM joueurs WHERE pseudo = ?")) {
            requete.setString(1, j.getNom());
            try (ResultSet resultat = requete.executeQuery()) {
                if (resultat.next()) {
                    classement = resultat.getInt("classement");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return classement;
    }

    public void setPret(Joueur j, boolean etat) {
        try (PreparedStatement requete = connexion.prepareStatement(
                "UPDATE joueurs SET pret = ? WHERE pseudo = ?")) {
            requete.setBoolean(1, etat);
            requete.setString(2, j.getNom());
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean tousLesJoueursSontPrets() {
        try (PreparedStatement requete = connexion.prepareStatement(
                "SELECT COUNT(*) as nb FROM joueurs WHERE pret = FALSE"); ResultSet resultat = requete.executeQuery()) {
            if (resultat.next()) {
                return resultat.getInt("nb") == 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Ferme la connexion à la base.
     */
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
