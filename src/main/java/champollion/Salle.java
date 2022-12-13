package champollion;

import java.util.HashSet;
import java.util.Set;

public class Salle {

    private final String intitule;
    private final int capacite;

    private Set<Intervention> occupations = new HashSet<>();

    public Salle(String intitule, int capacite) {
        this.intitule = intitule;
        this.capacite = capacite;
    }

    public String getIntitule() {
        return intitule;
    }

    public int getCapacite() {
        return capacite;
    }

    public void addOccupation(Intervention intervention){
        occupations.add(intervention);
    }
}
