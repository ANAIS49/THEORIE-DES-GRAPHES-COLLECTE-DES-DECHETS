import modele.graphe.Graphe;
import modele.graphe.Sommet;
import utils.LecteurGraphe;
import algorithmes.theme3.WelshPowell;

import java.util.Map;

public class TestTheme3H1 {

    public static void main(String[] args) {
        try {
            testerFichier("data/HO1.txt");
            testerFichier("data/HO2.txt");
            testerFichier("data/HO3.txt");

            System.out.println(" ");
            System.out.println("=====================================");
            System.out.println("   ✅ Tests Thème 3 - Hypothèse 1 terminés");
            System.out.println("=====================================");

        } catch (Exception e) {
            System.err.println("❌ Erreur pendant les tests : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testerFichier(String chemin) throws Exception {
        System.out.println("=====================================");
        System.out.println("   TEST THEME 3 - HYP 1 SUR : " + chemin);
        System.out.println("=====================================");

        // Charger le graphe
        Graphe g = LecteurGraphe.chargerDepuisFichier(chemin);

        // Appliquer Welsh-Powell
        WelshPowell wp = new WelshPowell(g);
        Map<Sommet, Integer> coloration = wp.colorier();

        // Afficher les résultats
        wp.afficherColoration(coloration);
        System.out.println("");
    }
}
