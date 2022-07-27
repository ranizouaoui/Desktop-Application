package Classes;

public class Profil {
	int Code_profil ;
	String Libelle;
	public Profil(int code_profil, String libelle) {
		//super();
		Code_profil = code_profil;
		Libelle = libelle;
	}
	public int getCode_profil() {
		return Code_profil;
	}
	public void setCode_profil(int code_profil) {
		Code_profil = code_profil;
	}
	public String getLibelle() {
		return Libelle;
	}
	public void setLibelle(String libelle) {
		Libelle = libelle;
	}
	
	
}
