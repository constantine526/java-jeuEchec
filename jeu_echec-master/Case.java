public class Case {
    private int ligne; // 0 à 7, sera adapté ensuite pour le jeu
    private int colonne; // 0 à 7, sera adapté ensuite pour le jeu
    private Piece piece; // null si la case est vide

    // Constructeur
    public Case(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.piece = null; // à sa création, une case est vide
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    // Utile dans d'autres classes pour la validation d'un coup
    public boolean estOccupee() {
        return this.piece != null; // retourne true si la case contient une pièce
    }

    // Fonction pour l'affichage des positions sous la forme 'a1'
    public String nomCase() {
        char lettreColonne = (char) ('a' + colonne); // a = 97 ; '+ colonne' pour faire les lettres suivantes
        int numeroLigne = ligne + 1; // on met + 1 car les lignes vont de 0 à 7, alors que sur le jeu de 1 à 8
        return "" + lettreColonne + numeroLigne; // concatène, ex: "e2"
    }
}