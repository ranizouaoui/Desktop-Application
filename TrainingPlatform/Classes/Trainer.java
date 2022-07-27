package Classes;

public class Trainer {
	int Code_formateur;
	String Nom;
	String Prenom;
	String Organisme;
	String Email;
	String Ntelephone;

	public Trainer(int code_formateur, String nom, String prenom, String domaine, String email, String ntelephone) {
		super();
		Code_formateur = code_formateur;
		Nom = nom;
		Prenom = prenom;
		Organisme = domaine;
		Email = email;
		Ntelephone = ntelephone;
	}

	public int getCode_formateur() {
		return Code_formateur;
	}

	public void setCode_formateur(int code_formateur) {
		Code_formateur = code_formateur;
	}

	public String getNom() {
		return Nom;
	}

	public void setNom(String nom) {
		Nom = nom;
	}

	public String getPrenom() {
		return Prenom;
	}

	public void setPrenom(String prenom) {
		Prenom = prenom;
	}

	public String getOrganisme() {
		return Organisme;
	}

	public void setOrganisme(String organisme) {
		Organisme = organisme;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getNtelephone() {
		return Ntelephone;
	}

	public void setNtelephone(String ntelephone) {
		Ntelephone = ntelephone;
	}

}
