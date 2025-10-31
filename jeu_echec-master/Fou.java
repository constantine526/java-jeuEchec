public class Fou extends Piece {

    // Constructeur
    public Fou(int ligne, int colonne, boolean estBlanc) {
        super(ligne, colonne, estBlanc);
    }

    /**
     * Le fou peut se déplacer uniquement en diagonale indépendamment de la distance
     * Dans cette classe on ne fait que les mouvements, sans la validation
     */
    @Override
    public boolean deplacement(Case destination) {
        int ligneArr = destination.getLigne(); // récupération de la ligne de la case d'arrivée
        int colonneArr = destination.getColonne(); // récupération de la ligne de la case d'arrivée

        // expression de base d'une diagonale en programmation
        // vérifie son égalité
        return Math.abs(this.getLigne() - ligneArr) == Math.abs(this.getColonne() - colonneArr);
    }
}