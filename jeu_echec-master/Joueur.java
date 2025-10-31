public class Joueur {
    private String nom;
    private boolean estBlancJoueur;

    // Constructeur
    public Joueur(String n, boolean b) {
        this.nom = n;
        this.estBlancJoueur = b;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String n) {
        this.nom = n;
    }

    public boolean getEstBlancJoueur() {
        return this.estBlancJoueur;
    }

    public void setEstBlancJoueur(boolean b) {
        this.estBlancJoueur = b;
    }
}