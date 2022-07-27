package Classes;

public class Training {
	int Code_formation;
	String Intitulé;
	String Domaine;
	String Nombre_jours;
	String Annee;
	String mois;
	String Formateur;
	String Nombre_participants;

	public Training(int code_formation, String intitulé, String domaine, String nombre_jours, String annee, String mois,
			String formateur, String nombre_participants) {
		Code_formation = code_formation;
		Intitulé = intitulé;
		Domaine = domaine;
		Nombre_jours = nombre_jours;
		Annee = annee;
		this.mois = mois;
		Formateur = formateur;
		Nombre_participants = nombre_participants;
	}

	public int getCode_formation() {
		return Code_formation;
	}

	public void setCode_formation(int code_formation) {
		Code_formation = code_formation;
	}

	public String getIntitulé() {
		return Intitulé;
	}

	public void setIntitulé(String intitulé) {
		Intitulé = intitulé;
	}

	public String getDomaine() {
		return Domaine;
	}

	public void setDomaine(String domaine) {
		Domaine = domaine;
	}

	public String getNombre_jours() {
		return Nombre_jours;
	}

	public void setNombre_jours(String nombre_jours) {
		Nombre_jours = nombre_jours;
	}

	public String getAnnee() {
		return Annee;
	}

	public void setAnnee(String annee) {
		Annee = annee;
	}

	public String getMois() {
		return mois;
	}

	public void setMois(String mois) {
		this.mois = mois;
	}

	public String getFormateur() {
		return Formateur;
	}

	public void setFormateur(String formateur) {
		Formateur = formateur;
	}

	public String getNombre_participants() {
		return Nombre_participants;
	}

	public void setNombre_participants(String nombre_participants) {
		Nombre_participants = nombre_participants;
	}

}
