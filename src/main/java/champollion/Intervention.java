package champollion;

import java.util.Date;

public class Intervention {

    private Date date;
    private int duree;
    private boolean annulee = false;
    private int heureDeDebut;
    private TypeIntervention type;
    private UE matiere;

    private Salle salle;

    public Intervention(Date date, int duree, boolean annulee, int heureDeDebut, TypeIntervention type, UE matiere, Salle salle) {
        this.date = date;
        this.duree = duree;
        this.annulee = annulee;
        this.heureDeDebut = heureDeDebut;
        this.type = type;
        this.matiere = matiere;
        this.salle = salle;
        salle.addOccupation(this);
        matiere.addSeance(this);
    }

    public Date getDate() {
        return date;
    }

    public int getDuree() {
        return duree;
    }

    public boolean isAnnulee() {
        return annulee;
    }

    public int getHeureDeDebut() {
        return heureDeDebut;
    }

    public Salle getSalle(){
        return salle;
    }

    public TypeIntervention getType() {
        return type;
    }

    public UE getMatiere() {
        return matiere;
    }
}
