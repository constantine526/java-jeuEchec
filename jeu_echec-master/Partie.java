import java.util.Scanner;
import java.util.ArrayList;

public class Partie {
    private Joueur joueurBlanc;
    private Joueur joueurNoir;
    private Joueur joueurCourant;
    private Echiquier echiquier;
    private boolean fin;
    private ArrayList<String> listeCoups;

    private Scanner scanner = new Scanner(System.in); // pour lire les déplacements entrés par l'utilisateur à son tour

    // Constructeur
    public Partie(Joueur blanc, Joueur noir) {
        this.joueurBlanc = blanc;
        this.joueurNoir = noir;
        this.joueurCourant = blanc; // le premier à jouer est le joueur blanc
        this.echiquier = new Echiquier(); // stock l'objet du constructeur de Echiquier (un échiquier avec les pièces)
        this.fin = false; // au début, la partie n'est pas terminée
        this.listeCoups = new ArrayList<String>();
    }

    public void jouer() {
        while (!fin) { // tant que la partie n'est pas fini
            echiquier.afficher(); // on affiche l'échiquier sur le terminal

            boolean coupValide = false; // pour la deuxième boucle while

            String couleur = ""; // pour afficher la couleur
            if (joueurCourant.getEstBlancJoueur()) { // si le joueur au trait est blanc
                couleur = "blanc";
            } else {
                couleur = "noir";
            }

            while (!coupValide) { // tant que le coup n'est pas valide, il réessaie
                System.out.println(joueurCourant.getNom() + " (" + couleur + "), entre ton coup (ex: e2 e4) :");
                String departStr = scanner.next(); // mettre le coordonnée de départ
                String arriveeStr = scanner.next(); // mettre le coordonnée d'arrivée

                Case depart = convertirCoordonnee(departStr); // transforme ce que le joueur a tapé en Case
                Case arrivee = convertirCoordonnee(arriveeStr); // par exemple 'e2' sera cases[1][4]

                if (depart == null || arrivee == null) { // si les coups sont hors de l'échiquier
                    System.out.println("Coordonnées invalides. Réessaye :");
                } else if (!validerCoup(depart, arrivee)) {
                    System.out.println("Coup invalide. Réessaye :");
                } else {
                    // Echanger les pièces SEULEMENT après avoir validé le coup (validerCoup())
                    Piece piece = depart.getPiece(); // récupère la pièce qu'on veut déplacer
                    arrivee.setPiece(piece); // donne à la case d'arrivée notre pièce de départ
                    depart.setPiece(null); // vide l'ancienne case de départ
                    piece.setPosition(arrivee.getLigne(), arrivee.getColonne()); // màj des coordonnées de la pièce
                    coupValide = true; // sortie de boucle

                    // On teste si l’adversaire est en échec et mat après ce coup
                    if (estEchecEtMat(!joueurCourant.getEstBlancJoueur())) {
                        echiquier.afficher(); // pour afficher le dernier état de jeu
                        // Pour afficher que le joueur adverse a perdu
                        if (joueurCourant == joueurBlanc) {
                            System.out.println("Échec et mat ! " + joueurNoir.getNom() + " a perdu.");
                        } else {
                            System.out.println("Échec et mat ! " + joueurBlanc.getNom() + " a perdu.");
                        }

                        // Affichage de l'historique des coups joués quand la partie se finit
                        System.out.println("\nListe des coups joués :");
                        for (String coup : listeCoups) {
                            System.out.println("- " + coup);
                        }

                        fin = true;
                        return;
                    }

                    // Tester si le joueur au trait est en pat
                    if (estPat(joueurCourant.getEstBlancJoueur())) {
                        System.out.println("Pat ! : " + joueurCourant.getNom() + " n’a aucun coup valide.");
                        System.out.println("Partie nulle.");
                        // Pour afficher que le joueur adverse a perdu
                        if (joueurCourant == joueurBlanc) {
                            System.out.println("Pat ! : " + joueurNoir.getNom() + " n’a aucun coup valide.");
                        } else {
                            System.out.println("Pat ! : " + joueurBlanc.getNom() + " n’a aucun coup valide.");
                        }

                        // Affichage de l'historique des coups joués quand la partie se finit
                        System.out.println("\nListe des coups joués :");
                        for (String coup : listeCoups) {
                            System.out.println("- " + coup);
                        }
                        fin = true;
                        return;
                    }

                    String nomPiece = piece.getClass().getSimpleName(); // par ex : "Pion"
                    String coup = joueurCourant.getNom() + " joue " + nomPiece + " de " + depart.nomCase() + " à "
                            + arrivee.nomCase();
                    listeCoups.add(coup);

                }

            }
            changerJoueur(); // si c'est validé, on passe au prochain joueur
        }
    }

    private boolean validerCoup(Case depart, Case arrivee) {
        Piece piece = depart.getPiece(); // récupère la pièce
        // Invalide si l'on a choisi une case de départ sans pièce
        if (piece == null) { // s'il n'y a aucune pièce sur la case
            System.out.println("Choisis une case ayant une pièce");
            return false;
        }
        // Si le joueur tente de déplacer une pièce adverse
        // Invalide si la pièce et le joueur au trait ont la même couleur
        if (piece.getEstBlancPiece() != joueurCourant.getEstBlancJoueur()) {
            System.out.println("Ce n'est pas ta pièce.");
            return false;
        }
        // S'il y a une pièce dans la case d'arrivée et est de même couleur
        if (arrivee.getPiece() != null && (arrivee.getPiece().getEstBlancPiece() == piece.getEstBlancPiece())) {
            System.out.println("Attention t'allais manger ta propre pièce.");
            return false;
        }
        // Empêcher le saut des pièces au dessus d'autres pièces (sauf le cavalier)
        if (!(piece instanceof Cavalier) && pieceInter(depart, arrivee)) { // si la pièce n'est pas un cavalier
            System.out.println("Il y a une pièce sur le chemin.");
            return false;
        }
        // Empêcher la capture de face pour les pions
        if (piece instanceof Pion) { // si la pièce est un pion
            // besoin seulement de la colonne car on veut la ligne droite
            int colonneDep = depart.getColonne();
            int colonneArr = arrivee.getColonne();

            // Si le pion avance droit et la case est occupée : coup invalide
            if (colonneDep == colonneArr && arrivee.estOccupee()) {
                System.out.println("Le pion ne peut capturer qu'en diagonale.");
                return false;
            }

            // Si le pion veut capturer (diagonale), la case doit être occupée par une pièce
            // d'une autre couleur
            // si la case d'arrivée est vide OU que la pièce à capturer est de même couleur
            if (colonneDep != colonneArr
                    && (!arrivee.estOccupee() || arrivee.getPiece().getEstBlancPiece() == piece.getEstBlancPiece())) {
                System.out.println("Le pion se déplace en ligne droite.");
                return false;
            }
        }

        // Simulation du coup pour tester si le roi serait en échec
        // Rien de tout ça ne sera visible sur le terminal
        Piece temp = arrivee.getPiece(); // sauvegarde ce qu’il y a l'arrivée (peut être null) afin de la remettre
        arrivee.setPiece(piece); // place la pièce sur la case d’arrivée
        depart.setPiece(null); // vide la case de départ, comme si l'on a réellement déplacé la pièce
        piece.setPosition(arrivee.getLigne(), arrivee.getColonne()); // màj des coordonnées de la pièce

        // Vérifie si le roi serai en échec après le coup
        boolean roiEchec = roiEstEnEchec(piece.getEstBlancPiece());

        // Annule la simulation, on replace tout
        depart.setPiece(piece); // on remet notre pièce à sa place
        arrivee.setPiece(temp); // on rend la pièce d'arrivée qui a été déplacé
        piece.setPosition(depart.getLigne(), depart.getColonne()); // remet les anciennes coordonnées

        if (roiEchec) { // si le roi est en échec
            System.out.println("Coup interdit : ton roi serait en échec.");
            return false;
        }

        return piece.deplacement(arrivee);
    }

    /**
     * Méthode qui fait exactement la même chose que validerCoup() à la différence
     * où on a enlevé tous les System.out.print() et ce afin de l'utiliser dans
     * estEchecEtMat() et estPat() et éviter la répétition de plusieurs print à
     * chaque coup (un bug que l'on a rencontré)
     */
    private boolean validerCoupSilencieux(Case depart, Case arrivee) {
        Piece piece = depart.getPiece();
        if (piece == null) {
            return false;
        }
        if (piece.getEstBlancPiece() != joueurCourant.getEstBlancJoueur()) {
            return false;
        }
        if (arrivee.getPiece() != null && (arrivee.getPiece().getEstBlancPiece() == piece.getEstBlancPiece())) {
            return false;
        }
        if (!(piece instanceof Cavalier) && pieceInter(depart, arrivee)) {
            return false;
        }
        if (piece instanceof Pion) {
            int colonneDep = depart.getColonne();
            int colonneArr = arrivee.getColonne();
            if (colonneDep == colonneArr && arrivee.estOccupee()) {
                return false;
            }
            if (colonneDep != colonneArr
                    && (!arrivee.estOccupee() || arrivee.getPiece().getEstBlancPiece() == piece.getEstBlancPiece())) {
                return false;
            }
        }
        Piece temp = arrivee.getPiece();
        arrivee.setPiece(piece);
        depart.setPiece(null);
        piece.setPosition(arrivee.getLigne(), arrivee.getColonne());

        boolean roiEchec = roiEstEnEchec(piece.getEstBlancPiece());

        depart.setPiece(piece);
        arrivee.setPiece(temp);
        piece.setPosition(depart.getLigne(), depart.getColonne());

        if (roiEchec) {
            System.out.println("Coup interdit : ton roi serait en échec.");
            return false;
        }

        return piece.deplacement(arrivee);
    }

    // Vérifie si une pièce se trouve entre la case de départ et la case d'arrivée
    // Ne vérifie pas la case d'arrivée elle-même (deplacement() le fait déjà)
    private boolean pieceInter(Case depart, Case arrivee) {
        // Coordonnées de départ et d'arrivée
        int ligneDep = depart.getLigne();
        int colDep = depart.getColonne();
        int ligneArr = arrivee.getLigne();
        int colArr = arrivee.getColonne();

        int deplacementLigne = 0; // déplacement vertical
        int deplacementColonne = 0; // déplacement horizontal

        // Déplacement vertical, connaitre le sens
        if (ligneArr > ligneDep)
            deplacementLigne = 1; // vers le haut
        else if (ligneArr < ligneDep)
            deplacementLigne = -1; // vers le bas

        // Déplacement horizontal, connaitre le sens
        if (colArr > colDep)
            deplacementColonne = 1; // vers la droite
        else if (colArr < colDep)
            deplacementColonne = -1; // vers la gauche

        int ligne = ligneDep + deplacementLigne; // avance d'une case à la fois, afin de vérifier
        int colonne = colDep + deplacementColonne; // avance d'une case à la fois, afin de vérifier

        // Parcours du chemin case par case
        while (ligne != ligneArr || colonne != colArr) { // tant qu'on n'est pas sur la case d'arrivée
            Case intermediaire = echiquier.coordonneeCase(ligne, colonne); // on récupère la case
            if (intermediaire != null && intermediaire.estOccupee()) { // si la case est valide et qu'il y a une piece
                return true; // il y a un obstacle
            }

            ligne += deplacementLigne;
            colonne += deplacementColonne;
        }

        return false; // il n'y a aucun obstacle
    }

    // Changement de tour
    private void changerJoueur() {
        if (joueurCourant == joueurBlanc) {
            joueurCourant = joueurNoir;
        } else {
            joueurCourant = joueurBlanc;
        }
    }

    // Converti une chaîne tapée par le joueur (e2 par exemple) en indices
    // utilisables (en cases[1][4])
    private Case convertirCoordonnee(String input) {
        if (input.length() != 2) // s'il n'a pas exactement 2 caractères
            return null; // ce n'est pas une case

        char colonneCarac = input.charAt(0); // correspond à la colonne, par exemple 'e'
        char ligneCarac = input.charAt(1); // correspond à la ligne, par exemple '2'

        int colonne = colonneCarac - 'a'; // 'a' = 97 ; ex: 'e' (101) - 'a' = 4
        int ligne = ligneCarac - '1'; // '1' = 49 ; ex: '2' (50) - '1' = ligne 1

        return echiquier.coordonneeCase(ligne, colonne); // le converti en cases[l][c]
    }

    // Empêcher le joueur au trait de se mettre lui-même en échec et mat
    private boolean roiEstEnEchec(boolean estBlanc) {
        Case caseRoi = null;

        // Chercher la case où se trouve le roi du joueur afin de la protéger
        for (int li = 0; li < 8; li++) { // double boucle pour parcourir tout l'échiquier
            for (int col = 0; col < 8; col++) {
                Case c = echiquier.coordonneeCase(li, col);
                Piece p = c.getPiece();
                if (p != null && p instanceof Roi && p.getEstBlancPiece() == estBlanc) {
                    caseRoi = c;
                }
            }
        }

        // Si on ne trouve pas le roi, qu'il est mort ou pas placé, on considère qu’il
        // est "en échec"
        if (caseRoi == null)
            return true;

        // Vérifie si une pièce ennemie peut atteindre la case du roi
        for (int li = 0; li < 8; li++) {
            for (int col = 0; col < 8; col++) {
                Case c = echiquier.coordonneeCase(li, col);
                Piece p = c.getPiece();
                // S'il y a une pièce et qu'elle est de couleur différente
                if (p != null && p.getEstBlancPiece() != estBlanc) {
                    // condition pour Pion car le roi reconnait le pion comme un danger même quand
                    // il est en face (hors ce n'est pas le cas)
                    if (p instanceof Pion) {
                        int direction;
                        if (p.getEstBlancPiece()) {
                            direction = 1; // pion blanc -> avance vers le haut
                        } else {
                            direction = -1; // pion noir -> avance vers le bas
                        }
                        int lignePion = c.getLigne();
                        int colonnePion = c.getColonne();
                        int ligneRoi = caseRoi.getLigne();
                        int colonneRoi = caseRoi.getColonne();

                        // Le pion ne peut capturer qu'en diagonale
                        if ((ligneRoi - lignePion == direction) && Math.abs(colonneRoi - colonnePion) == 1) {
                            return true; // le roi est attaqué en diagonale
                        }
                        // Et si la pièce peut atteindre la case du Roi selon ses règles de déplacement
                    } else if (p.deplacement(caseRoi)) {
                        // Mais ignore les obstacles, donc vérifie si la pièce n'est pas bloqué
                        if (p instanceof Cavalier || !pieceInter(c, caseRoi)) {
                            return true; // le roi est attaqué
                        }
                    }
                }
            }
        }
        return false; // sinon personne ne peut attaquer le roi
    }

    private boolean estEchecEtMat(boolean estBlanc) {
        // Si le roi n’est pas en échec → ce n’est pas un mat
        if (!roiEstEnEchec(estBlanc))
            return false;

        // Tester tous les coups possibles pour ce joueur
        for (int ligneDep = 0; ligneDep < 8; ligneDep++) {
            for (int colDep = 0; colDep < 8; colDep++) {
                Case depart = echiquier.coordonneeCase(ligneDep, colDep);
                Piece piece = depart.getPiece();

                if (piece != null && piece.getEstBlancPiece() == estBlanc) {
                    for (int ligneArr = 0; ligneArr < 8; ligneArr++) {
                        for (int colArr = 0; colArr < 8; colArr++) {
                            Case arrivee = echiquier.coordonneeCase(ligneArr, colArr);

                            if (arrivee != null && depart != arrivee) {
                                if (validerCoupSilencieux(depart, arrivee)) {
                                    return false; // Un coup peut sauver → pas mat
                                }
                            }
                        }
                    }
                }
            }
        }

        return true; // Roi en échec + aucun coup possible = mat
    }

    private boolean estPat(boolean estBlanc) {
        // Si le roi est déjà en échec, ce n’est pas un pat
        if (roiEstEnEchec(estBlanc))
            return false;

        // Parcourt de toutes les cases de l’échiquier
        for (int ligneDep = 0; ligneDep < 8; ligneDep++) {
            for (int colonneDep = 0; colonneDep < 8; colonneDep++) {
                Case dep = echiquier.coordonneeCase(ligneDep, colonneDep);
                Piece p = dep.getPiece();

                // Si la pièce appartient au joueur au trait
                if (p != null && p.getEstBlancPiece() == estBlanc) {
                    // Essayer toutes les cases comme destination
                    for (int ligneArr = 0; ligneArr < 8; ligneArr++) {
                        for (int colonneArr = 0; colonneArr < 8; colonneArr++) {
                            Case arr = echiquier.coordonneeCase(ligneArr, colonneArr);

                            // Simuler le coup comme dans validerCoup
                            if (arr != null && dep != arr) {
                                if (validerCoupSilencieux(dep, arr)) {
                                    return false; // Au moins un coup possible, pas de pat
                                }
                            }
                        }
                    }
                }
            }
        }
        return true; // Aucun coup trouvé, pat
    }

}