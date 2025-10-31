public class Dame extends Piece {

    // Constructeur
    public Dame(int ligne, int colonne, boolean estBlanc) {
        super(ligne, colonne, estBlanc);
    }

    /**
     * La dame combine les déplacements du fou et de la tour.
     * Dans cette classe on ne fait que les mouvements, sans la validation
     */
    @Override
    public boolean deplacement(Case destination) {
        int ligneArr = destination.getLigne(); // récupération de la ligne de la case d'arrivée
        int colonneArr = destination.getColonne(); // récupération de la colonne de la case d'arrivée

        // Soit elle va en ligne droite, soit en diagonale
        return (this.getLigne() == ligneArr || this.getColonne() == colonneArr) ||
                (Math.abs(this.getLigne() - ligneArr) == Math.abs(this.getColonne() - colonneArr));
    }
}