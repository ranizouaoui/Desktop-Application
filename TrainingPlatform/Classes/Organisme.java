package Classes;

public class Organisme {
 int id ;
 String Libelle;
public Organisme(int id, String libelle) {
	//super();
	this.id = id;
	Libelle = libelle;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getLibelle() {
	return Libelle;
}
public void setLibelle(String libelle) {
	Libelle = libelle;
}
 
}
