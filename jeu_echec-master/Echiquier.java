public class Echiquier {
    private Case[][] cases; // tableau de Case à 2 dimensions

    // Constructeur
    public Echiquier() {
        this.cases = new Case[8][8]; // il y aura toujours 64 cases dans l'échiquier (tous null)

        // Remplissage de chaque case (null) du tableau avec un objet Case
        // Toutes les cases en dehors de ces 2 intervalles seront null
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                this.cases[ligne][colonne] = new Case(ligne, colonne);
            }
        }
        initialiser(); // positionne les pièces
    }

    // Place les pièces de début de partie
    public void initialiser() {
        // Placement des pions
        for (int col = 0; col < 8; col++) {
            cases[1][col].setPiece(new Pion(1, col, true)); // pions blancs en rangée 2
            cases[6][col].setPiece(new Pion(6, col, false)); // pions noirs en rangée 7
        }

        // Autres pièces pour les blancs
        placerPieces(0, true); // place les autres pièces dans la rangée 1 pour les blancs

        // Autres pièces pour les noirs
        placerPieces(7, false); // place les autres pièces dans la rangée 8 pour les noirs
    }

    private void placerPieces(int ligne, boolean estBlanc) {
        // setPiece() de la class Case, remplace le 'vide' par une nouvelle pièce
        cases[ligne][0].setPiece(new Tour(ligne, 0, estBlanc));
        cases[ligne][1].setPiece(new Cavalier(ligne, 1, estBlanc));
        cases[ligne][2].setPiece(new Fou(ligne, 2, estBlanc));
        cases[ligne][3].setPiece(new Dame(ligne, 3, estBlanc));
        cases[ligne][4].setPiece(new Roi(ligne, 4, estBlanc));
        cases[ligne][5].setPiece(new Fou(ligne, 5, estBlanc));
        cases[ligne][6].setPiece(new Cavalier(ligne, 6, estBlanc));
        cases[ligne][7].setPiece(new Tour(ligne, 7, estBlanc));
    }

    // Récupérer une case avec ses coordonnées (li, col), si valide
    // Utilisation dans les autres classes, donc 'public'
    public Case coordonneeCase(int ligne, int colonne) {
        if (ligne >= 0 && ligne < 8 && colonne >= 0 && colonne < 8) { // si la position est valide (bien à l'intérieur)
            return cases[ligne][colonne];
        }
        return null;
    }

    // Affichage de l'échiquier dans le terminal
    public void afficher() {
        System.out.println(); // pour que ce soit plus lisible
        for (int ligne = 7; ligne >= 0; ligne--) { // rangée du haut vers le bas (8 est en haut)
            System.out.print((ligne + 1) + "  "); // affiche le numéro de rangée (8 à 1) à gauche
            for (int colonne = 0; colonne < 8; colonne++) {
                Piece p = cases[ligne][colonne].getPiece(); // on récupère la pièce qu'est sur la case
                if (p == null) { // s'il n'y en a pas
                    System.out.print(". "); // on met un point pour marquer un vide
                } else {
                    System.out.print(symbolePiece(p) + " "); // sinon affiche la pièce
                }
            }
            System.out.println();
        }
        System.out.println(); // pour que ce soit plus lisible
        System.out.println("   a b c d e f g h"); // affiche les lettres de rangée
        System.out.println(); // pour que ce soit plus lisible
    }

    // Donner vie à nos pièces
    private String symbolePiece(Piece p) {
        String s = "";
        if (p instanceof Pion) // si la pièce (objet) est de type Pion, on affiche P (en maj pour les blancs)
            s = "P";
        else if (p instanceof Tour) // même schéma
            s = "T";
        else if (p instanceof Cavalier)
            s = "C";
        else if (p instanceof Fou)
            s = "F";
        else if (p instanceof Dame)
            s = "D";
        else if (p instanceof Roi)
            s = "R";

        if (p.getEstBlancPiece()) { // si la pièce est blanche, laisse la majuscule
            return s;
        } else {
            return s.toLowerCase(); // sinon, en minuscule
        }

    }
}