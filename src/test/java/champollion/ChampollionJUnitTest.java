package champollion;

import org.junit.jupiter.api.*;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ChampollionJUnitTest {
	Enseignant untel;
	UE uml, java;
		
	@BeforeEach
	public void setUp() {
		untel = new Enseignant("untel", "untel@gmail.com");
		uml = new UE("UML",50,50,50);
		java = new UE("Programmation en java",100,0,4);
	}
	

	@Test
	public void testNouvelEnseignantSansService() {
		assertEquals(0, untel.heuresPrevues(),
                        "Un nouvel enseignant doit avoir 0 heures prévues");
	}
	
	@Test
	public void testAjouteHeures() {
                // 10h TD pour UML
		untel.ajouteEnseignement(uml, 0, 10, 0);

		assertEquals(10, untel.heuresPrevuesPourUE(uml),
                        "L'enseignant doit maintenant avoir 10 heures prévues pour l'UE 'uml'");

                // 20h TD pour UML
                untel.ajouteEnseignement(uml, 0, 20, 0);
                
		assertEquals(10 + 20, untel.heuresPrevuesPourUE(uml),
                         "L'enseignant doit maintenant avoir 30 heures prévues pour l'UE 'uml'");
		
	}

	@Test
	public void testAjoutEnseignementException(){
		assertThrows(IllegalArgumentException.class, () -> untel.ajouteEnseignement(uml, 100, 1000, 1000));
	}

	@Test
	public void testAjoutInterventionException() {
		untel.ajouteEnseignement(uml, 0, 10, 0);
		assertThrows(IllegalArgumentException.class, () -> untel.ajouteIntervention(new Intervention(Date.from(Instant.now()), 500,false,5,TypeIntervention.CM,uml, new Salle("salle 1",12))));
	}

	@Test
	public void testResteAPlanifier(){
		untel.ajouteEnseignement(uml, 0, 10, 0);
		untel.ajouteIntervention(new Intervention(Date.from(Instant.now()), 5,false,5,TypeIntervention.TD,uml, new Salle("salle 1",12)));
		assertEquals(untel.resteAPlanifier(uml,TypeIntervention.TD),5);
	}

	@Test
	public void testHeuresPrevues(){
		untel.ajouteEnseignement(uml, 6, 10, 0);
		untel.ajouteEnseignement(java, 0, 0 ,4);
		assertEquals(untel.heuresPrevues(),22);
	}

	@Test
	public void testHeuresPrevuesPourUE(){
		untel.ajouteEnseignement(uml, 6, 10, 0);
		untel.ajouteEnseignement(java, 0, 0 ,4);
		assertEquals(untel.heuresPrevuesPourUE(uml),19);
	}

	@Test
	public void pasEnSousService(){
		untel.ajouteEnseignement(uml, 6, 10, 0);
		untel.ajouteEnseignement(java, 0, 0 ,4);
		assertTrue(untel.enSousService());
	}

	@Test
	public void enSousService(){
		untel.ajouteEnseignement(uml, 50, 50, 50);
		untel.ajouteEnseignement(java, 100, 0 ,4);
		assertFalse(untel.enSousService());
	}
}
