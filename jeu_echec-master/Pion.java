public class Pion extends Piece {

    // Conducteur
    public Pion(int ligne, int colonne, boolean estBlanc) {
        super(ligne, colonne, estBlanc);
    }

    /**
     * Le pion peut se déplacer uniquement en vertical de 1 case et ne peut reculer
     * Sauf :
     * - au premier coup où il peut décider d'avancer de 2 cases
     * - et s'il veut capturer, il ne peut qu'en diagonale
     * Dans cette classe on ne fait que les mouvements, sans la validation
     * (par exemple pour la capture)
     */
    @Override
    public boolean deplacement(Case destination) {
        int ligneArr = destination.getLigne(); // récupération de la ligne de la case d'arrivée
        int colonneArr = destination.getColonne(); // récupération de la colonne de la case d'arrivée

        // Conditions selon la couleur
        int direction; // les pions ne peuvent pas reculer
        int ligneDepart; // pour savoir s'il peut avancer de 2 cases
        if (getEstBlancPiece()) {
            direction = 1; // pion blanc : avance vers le haut (ligne +1)
            ligneDepart = 1; // pion blanc : ligne de départ = 2 (index 1)
        } else {
            direction = -1; // pion noir : avance vers le bas (ligne -1)
            ligneDepart = 6; // pion noir : ligne de départ = 7 (index 6)
        }

        int hautBas = ligneArr - this.getLigne(); // pas de Math.abs(), on veut le sens : 1 ou -1
        int droiteGauche = Math.abs(colonneArr - this.getColonne()); // on s'attend à 0 ou 1

        // Déplacement sans capture : 1 case en avant (ou 2 si c'est le premier coup)
        if (colonneArr == this.getColonne()) { // s'il avance tout droit
            if (this.getLigne() == ligneDepart) { // s'il est sur sa ligne de départ
                return hautBas == direction || hautBas == 2 * direction; // peut avancer d'1 ou 2 cases
            } else {
                return hautBas == direction; // sinon il ne peut avancer que d'1 case
            }
        }

        // Capture : 1 case en diagonale
        return droiteGauche == 1 && hautBas == direction; // autoriser de bouger 1 case à droite ou à gauche
    }
}