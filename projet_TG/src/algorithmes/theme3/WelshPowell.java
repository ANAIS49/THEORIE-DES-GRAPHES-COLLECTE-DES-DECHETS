package algorithmes.theme3;

import modele.graphe.*;
import java.util.*;

/**
 * Algorithme de Welsh-Powell pour la coloration de graphe.
 * Utilisable sur HO1, HO2, HO3 (on ignore l'orientation des arêtes).
 */
public class WelshPowell {

    private final Graphe graphe;

    public WelshPowell(Graphe graphe) {
        if (graphe == null) {
            throw new IllegalArgumentException("Le graphe ne peut pas être null");
        }
        this.graphe = graphe;
    }

    /**
     * Applique Welsh-Powell.
     * @return une map Sommet -> numéro de couleur (1,2,3,...)
     */
    public Map<Sommet, Integer> colorier() {

        // 1) Liste des sommets triés par degré décroissant
        List<Sommet> sommetsTries = new ArrayList<>(graphe.getSommets());
        sommetsTries.sort((s1, s2) ->
                Integer.compare(graphe.getDegre(s2), graphe.getDegre(s1)));

        Map<Sommet, Integer> couleurs = new HashMap<>();

        // 2) On assigne une couleur à chaque sommet dans cet ordre
        for (Sommet s : sommetsTries) {
            int couleur = 1;

            // Chercher la plus petite couleur disponible
            while (!couleurDisponiblePourSommet(s, couleur, couleurs)) {
                couleur++;
            }

            couleurs.put(s, couleur);
        }

        return couleurs;
    }

    /**
     * Vérifie si la couleur est dispo pour s (aucun voisin déjà colorié avec cette couleur).
     */
    private boolean couleurDisponiblePourSommet(Sommet s, int couleur,
                                                Map<Sommet, Integer> couleurs) {
        for (Sommet voisin : getVoisinsNonOrientes(s)) {
            Integer cVoisin = couleurs.get(voisin);
            if (cVoisin != null && cVoisin == couleur) {
                return false;
            }
        }
        return true;
    }

    /**
     * Voisins pour la coloration : on ignore l'orientation, on regarde juste les arêtes.
     */
    private Set<Sommet> getVoisinsNonOrientes(Sommet s) {
        Set<Sommet> voisins = new HashSet<>();
        for (Arete a : graphe.getAretes()) {
            if (a.contient(s)) {
                voisins.add(a.getAutreSommet(s));
            }
        }
        return voisins;
    }

    /**
     * Affiche joliment la coloration.
     */
    public void afficherColoration(Map<Sommet, Integer> coloration) {
        System.out.println("Coloration Welsh-Powell (" + graphe.getTypeHypothese() + ")");
        int nbCouleurs = new HashSet<>(coloration.values()).size();
        System.out.println("Nombre de couleurs utilisées : " + nbCouleurs);
        System.out.println("------------------------------------");
        coloration.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getKey().getId()))
                .forEach(e ->
                        System.out.println("Sommet " + e.getKey().getId()
                                + " -> couleur " + e.getValue()));
        System.out.println();
    }
}
