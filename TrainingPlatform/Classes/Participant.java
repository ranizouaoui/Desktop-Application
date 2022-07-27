package Classes;

public class Participant {

	int Matricule_participant;
	String nom;
	String prenom;
	String birth;
	int Profile;

	public Participant(int matricule_participant, String nom, String prenom, String birth, int profile) {
		this.Matricule_participant = matricule_participant;
		this.nom = nom;
		this.prenom = prenom;
		this.birth = birth;
		Profile = profile;
	}

	public int getMatricule_participant() {
		return Matricule_participant;
	}

	public void setMatricule_participant(int matricule_participant) {
		Matricule_participant = matricule_participant;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public int getProfile() {
		return Profile;
	}

	public void setProfile(int profile) {
		Profile = profile;
	}

}
