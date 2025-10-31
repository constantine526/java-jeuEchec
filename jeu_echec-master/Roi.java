public class Roi extends Piece {

    // Constructeur
    public Roi(int ligne, int colonne, boolean estBlanc) {
        super(ligne, colonne, estBlanc);
    }

    /**
     * Le roi ne peut se déplacer qu'à une seule case de sa position, dans n'importe
     * quelle direction (haut, bas, droite, gauche, diagonale).
     * Dans cette classe on ne fait que les mouvements, sans la validation
     */
    @Override
    public boolean deplacement(Case destination) {
        int ligneArr = destination.getLigne(); // récupération de la ligne de la case d'arrivée
        int colonneArr = destination.getColonne(); // récupération de la colonne de la case d'arrivée

        int hautBas = Math.abs(this.getLigne() - ligneArr); // on s'attend à 0 ou 1
        int droiteGauche = Math.abs(this.getColonne() - colonneArr); // on s'attend à 0 ou 1

        /**
         * Renvoie true si au minimum l'une des 2 variables <= 1
         * - si les 2 sont à 1, c'est un déplacement diagonale
         * - si les 2 sont à 0, il n'a pas bougé
         */
        return hautBas <= 1 && droiteGauche <= 1 && (hautBas + droiteGauche > 0);
    }
}