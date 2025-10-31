import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nom du joueur blanc : ");
        String nomBlanc = scanner.nextLine();

        System.out.print("Nom du joueur noir : ");
        String nomNoir = scanner.nextLine();

        Joueur blanc = new Joueur(nomBlanc, true);
        Joueur noir = new Joueur(nomNoir, false);

        Partie partie = new Partie(blanc, noir);
        partie.jouer();

        scanner.close();
    }
}
