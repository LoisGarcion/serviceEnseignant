package champollion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Enseignant extends Personne {

    // TODO : rajouter les autres méthodes présentes dans le diagramme UML

    List<Intervention> interventionList = new ArrayList<>();
    Map<UE,ServicePrevu> servicePrevuList = new HashMap<>();

    public Enseignant(String nom, String email) {
        super(nom, email);
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant en "heures équivalent TD" Pour le calcul : 1 heure
     * de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure de TP vaut 0,75h
     * "équivalent TD"
     *
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevues() {
        double s = 0;
        for(Map.Entry<UE, ServicePrevu> servicePrevu : servicePrevuList.entrySet()){
            s = s + servicePrevu.getValue().getVolumeCM() * 1.5 + servicePrevu.getValue().getVolumeTP() * 0.75 + servicePrevu.getValue().getVolumeTD();
        }
        return (int) s;
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant dans l'UE spécifiée en "heures équivalent TD" Pour
     * le calcul : 1 heure de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure
     * de TP vaut 0,75h "équivalent TD"
     *
     * @param ue l'UE concernée
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevuesPourUE(UE ue) {
        double s = 0;
        for(Map.Entry<UE, ServicePrevu> servicePrevu : servicePrevuList.entrySet()) {
            if (servicePrevu.getKey().equals(ue)) {
                s = s + servicePrevu.getValue().getVolumeCM() * 1.5 + servicePrevu.getValue().getVolumeTP() * 0.75 + servicePrevu.getValue().getVolumeTD();
            }
        }
        return (int) s;
    }

    /**
     * Ajoute un enseignement au service prévu pour cet enseignant
     *
     * @param ue l'UE concernée
     * @param volumeCM le volume d'heures de cours magitral
     * @param volumeTD le volume d'heures de TD
     * @param volumeTP le volume d'heures de TP
     */
    public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP)throws IllegalArgumentException {
        if(servicePrevuList.get(ue) != null){
            int newVolumeCM = volumeCM + servicePrevuList.get(ue).getVolumeCM();
            int newVolumeTD = volumeTD + servicePrevuList.get(ue).getVolumeTD();
            int newVolumeTP = volumeTP + servicePrevuList.get(ue).getVolumeTP();

            if(newVolumeCM <= ue.getHeuresCM() && newVolumeTD <= ue.getHeuresTD() && newVolumeTP <= ue.getHeuresTP()) {
                servicePrevuList.put(ue, new ServicePrevu(newVolumeCM, newVolumeTD, newVolumeTP));
            }
            else{
                throw new IllegalArgumentException("La somme des heures de l'enseignement déjà existant et du nouveau dépassent le volume horaire des UE");
            }
        }
        else {
            if (volumeCM <= ue.getHeuresCM() && volumeTD <= ue.getHeuresTD() && volumeTP <= ue.getHeuresTP()) {
                servicePrevuList.put(ue, new ServicePrevu(volumeCM, volumeTD, volumeTP));
                ue.addIntervenant(this);
            } else {
                throw new IllegalArgumentException("On ne peut pas ajouter un service prevu à un enseignant avec des volumes horaires supérieurs à ceux de l'UE");
            }
        }
    }

    public void ajouteIntervention(Intervention intervention) throws IllegalArgumentException{
        if(getHeureInterventionByUEByType(intervention.getMatiere(),intervention.getType()) + intervention.getDuree() <= resteAPlanifier(intervention.getMatiere(),intervention.getType())) {
            this.interventionList.add(intervention);
        }
        else{
            throw new IllegalArgumentException("Le volume horaire des interventions de doit pas dépasser le volume horaire du service prévu.");
        }
    }

    public boolean enSousService(){
        return heuresPrevues() < 192;
    }

    private int getHeureInterventionByUEByType(UE ue, TypeIntervention typeIntervention){
        int s = 0;
        for(Intervention intervention : interventionList){
            s = (intervention.getType().equals(typeIntervention)&&intervention.getMatiere().equals(ue))?s+intervention.getDuree() : s;
        }
        return s;
    }

    public int resteAPlanifier(UE ue, TypeIntervention type) {
        switch (type) {
            case TD:
                return servicePrevuList.get(ue).getVolumeTD() - getHeureInterventionByUEByType(ue, TypeIntervention.TD);
            case TP:
                return servicePrevuList.get(ue).getVolumeTP() - getHeureInterventionByUEByType(ue, TypeIntervention.TP);
            case CM:
                return servicePrevuList.get(ue).getVolumeCM() - getHeureInterventionByUEByType(ue, TypeIntervention.CM);
        }
        return 0;
    }
}
