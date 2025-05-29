package jdbc;

import java.sql.*;
import java.util.ArrayList;
import boatrush.Joueur;

public class JoueurSQL {

    private final String adresseBase = "jdbc:mariadb://nemrod.ens2m.fr:3306/2024-2025_s2_vs1_tp2_boatrush";
    private final String user = "etudiant";
    private final String motdepasse = "YTDTvj9TR3CDYCmP";
    private Connection connexion;

    //Initialise la connexion à la base de données
    public JoueurSQL() {
        try {
            this.connexion = DriverManager.getConnection(adresseBase, user, motdepasse);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Récupère tous les joueurs enregistrés en base
    public ArrayList<Joueur> getTousLesJoueurs() {
        ArrayList<Joueur> joueurs = new ArrayList<>();
        try (PreparedStatement requete = connexion.prepareStatement("SELECT pseudo, x_coordinate, y_coordinate, skin FROM joueurs"); ResultSet resultat = requete.executeQuery()) {

            while (resultat.next()) {
                joueurs.add(new Joueur(resultat.getString("pseudo"), resultat.getInt("x_coordinate"), resultat.getInt("y_coordinate"), resultat.getInt("skin")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return joueurs;
    }

    //Crée un nouveau joueur en base
    public void creerJoueur(Joueur j) {
        try (PreparedStatement requete = connexion.prepareStatement("INSERT INTO joueurs VALUES (?, ?, ?, ?, ?,?)")) {
            requete.setString(1, j.getNom());
            requete.setInt(2, j.getXCoord());
            requete.setInt(3, j.getYCoord());
            requete.setInt(4, 0);
            requete.setInt(5, 0);
            requete.setInt(6, 1);
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Met à jour la position d'un joueur en base
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

    //Setter pour le skin dans la bdd
    public void setSkin(Joueur j, int choix) {
        try (PreparedStatement requete = connexion.prepareStatement(
                "UPDATE joueurs SET skin = ? WHERE pseudo = ?")) {
            requete.setInt(1, choix);
            requete.setString(2, j.getNom());
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Supprime un joueur de la base
    public void supprimerJoueur(Joueur j) {
        try (PreparedStatement requete = connexion.prepareStatement("DELETE FROM joueurs WHERE pseudo = ?")) {
            requete.setString(1, j.getNom());
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Recherche un joueur par son pseudo
    public Joueur voirJoueur(String nom) {
        Joueur joueurTrouve = null;
        try (PreparedStatement requete = connexion.prepareStatement("SELECT pseudo, x_coordinate, y_coordinate, skin FROM joueurs WHERE pseudo = ?")) {
            requete.setString(1, nom);
            try (ResultSet resultat = requete.executeQuery()) {
                if (resultat.next()) {
                    joueurTrouve = new Joueur(resultat.getString("pseudo"), resultat.getInt("x_coordinate"), resultat.getInt("y_coordinate"), resultat.getInt("skin"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return joueurTrouve;
    }

    //Recherche l'entier correspondant au classement en fonction du classement actuel
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

    //Donne à un joueur son classement dans la bdd
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

    //Récupère le classement d'un joueur en bdd
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

    //Défini le statut d'un joueur (utile pour la salle d'attente)
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

    //Vérifie que tous les joueurs sont prêts (salle d'attente)
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

    //Return l'ensemble des joueurs qui ne sont pas prets
    public ArrayList<Joueur> getTousLesJoueursPasPret() {
        ArrayList<Joueur> joueurs = new ArrayList<>();
        String sql = "SELECT pseudo, x_coordinate, y_coordinate, skin FROM joueurs WHERE pret = FALSE";
        try (PreparedStatement stmt = connexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                joueurs.add(new Joueur(rs.getString("pseudo"), rs.getInt("x_coordinate"), rs.getInt("y_coordinate"), rs.getInt("skin")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return joueurs;
    }

    //Ferme la connexion à la bdd
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
