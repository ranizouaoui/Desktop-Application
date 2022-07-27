package Classes;

public class Domaine {
	int Code_domaine ;
	String Libelle;
	public Domaine(int code_domaine, String libelle) {
		//super();
		Code_domaine = code_domaine;
		Libelle = libelle;
	}
	public int getCode_domaine() {
		return Code_domaine;
	}
	public void setCode_domaine(int code_domaine) {
		Code_domaine = code_domaine;
	}
	public String getLibelle() {
		return Libelle;
	}
	public void setLibelle(String libelle) {
		Libelle = libelle;
	}
	

}
