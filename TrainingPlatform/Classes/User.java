package Classes;

public class User {
	int Code_utilisateur;
	String user_name;
	String user_password;
	public String userPermissions;
	public enum Role {
		ADMIN, PARTICIPANT, FORMATEUR }
	
	public User(int code_utilisateur, String login, String password , String  role) {
		//super();
		this.Code_utilisateur = code_utilisateur;
		this.user_name = login;
		this.user_password = password;
		this.userPermissions = role;
	}
	public int getCode_utilisateur() {
		return Code_utilisateur;
	}
	public void setCode_utilisateur(int code_utilisateur) {
		Code_utilisateur = code_utilisateur;
	}
	
		
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getRole() {
		return userPermissions;
	}
	public void setRole(Role type) {
		userPermissions = type.toString();
    }

}