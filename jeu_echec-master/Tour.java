public class Tour extends Piece {

    // Constructeur
    public Tour(int ligne, int colonne, boolean estBlancPiece) {
        super(ligne, colonne, estBlancPiece);
    }

    /**
     * La tour peut se déplacer en ligne droite horizontalement ou verticalement,
     * indépendamment de la distance
     * Dans cette classe on ne fait que les mouvements, sans la validation
     */
    @Override
    public boolean deplacement(Case destination) {
        int ligneArr = destination.getLigne(); // récupération de la ligne de la case d'arrivée
        int colonneArr = destination.getColonne(); // récupération de la colonne de la case d'arrivée

        // retourne true si la ligne d'arrivée de la pièce est la même que la ligne
        // actuelle de la pièce (Pareil pour la colonne)
        return (this.getLigne() == ligneArr || this.getColonne() == colonneArr);
    }
}