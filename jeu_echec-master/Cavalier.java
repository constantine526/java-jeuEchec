public class Cavalier extends Piece {

    // Conducteur
    public Cavalier(int ligne, int colonne, boolean estBlanc) {
        super(ligne, colonne, estBlanc);
    }

    /**
     * Le cavalier peut se déplacer en 'L', vertical ou horizontal
     * une rangée et deux colonnes ou une colonne et deux rangées
     * Dans cette classe on ne fait que les mouvements, sans la validation
     */
    @Override
    public boolean deplacement(Case destination) {
        int ligneArr = destination.getLigne(); // récupération de la ligne de la case d'arrivée
        int colonneArr = destination.getColonne(); // récupération de la colonne de la case d'arrivée

        int hautBas = Math.abs(this.getLigne() - ligneArr); // on s'attend à 1 ou 2
        int droiteGauche = Math.abs(this.getColonne() - colonneArr); // on s'attend à 1 ou 2

        // Le cavalier se déplace en L, retourne seulement si 2 + 1 OU 1 + 2
        return (hautBas == 2 && droiteGauche == 1) || (hautBas == 1 && droiteGauche == 2);
    }
}