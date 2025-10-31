public abstract class Piece {
    private int ligne;
    private int colonne;
    private boolean estBlancPiece;

    // Constructeur
    public Piece(int ligne, int colonne, boolean estBlancPiece) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.estBlancPiece = estBlancPiece;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public boolean getEstBlancPiece() {
        return estBlancPiece;
    }

    public void setPosition(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
    }

    /**
     * Méthode abstraite que chaque sous-classe (Tour, Fou, etc.) doit redéfinir.
     * Vérifie si le déplacement de la pièce est conforme aux règles du jeu.
     */
    public abstract boolean deplacement(Case destination);
}