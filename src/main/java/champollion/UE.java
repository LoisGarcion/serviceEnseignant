package champollion;

import java.util.ArrayList;
import java.util.List;

public class UE {
    private final String intitule;

    private int heuresCM;
    private int heuresTD;
    private int heuresTP;

    private List<Enseignant> intervenants = new ArrayList<>();
    private List<Intervention> seancesPlanifiees = new ArrayList<>();

    public UE(String intitule, int heuresCM, int heuresTD, int heuresTP) {
        this.intitule = intitule;
        this.heuresCM = heuresCM;
        this.heuresTD = heuresTD;
        this.heuresTP = heuresTP;
    }

    public String getIntitule() {
        return intitule;
    }

    public int getHeuresCM() {
        return heuresCM;
    }

    public int getHeuresTD() {
        return heuresTD;
    }

    public int getHeuresTP() {
        return heuresTP;
    }

    public void addIntervenant(Enseignant enseignant){
        intervenants.add(enseignant);
    }

    public void addSeance(Intervention intervention){
        this.seancesPlanifiees.add(intervention);
    }

    public List<Enseignant> getIntervenants() {
        return intervenants;
    }

    public List<Intervention> getSeancesPlanifiees() {
        return seancesPlanifiees;
    }

}
