package fr.ulille.iut;

import java.util.Date;

public abstract class User {

	//USER(int:login,string:nom,string:prenom,int:age)

    private String login;
	private String nom;
	private String prenom;
	private int age=-1;
	private int id=0;
	private String password;
	private Date birthDate;
	private String email;
	
	public User(){}
	
	public User(int id,String login,String password,String nom,String prenom,int age,String mail) throws NegativeIDException{
		this.setId(id);
		this.setLogin(login);
		this.setPassword(password);
		this.nom=nom;
		this.prenom=prenom;
		
		if(age>=0)
			this.age=age;
		else
			throw new NegativeIDException("Age negatif");
		this.setEmail(mail);


	}


	public User(int id,String login,String password,String nom,String prenom,int age,Date bD,String mail) throws NegativeIDException{
		this.setId(id);
		this.setLogin(login);
		this.setPassword(password);
		this.nom=nom;
		this.prenom=prenom;
		
		if(age>=0)
			this.age=age;
		else
			throw new NegativeIDException("Age negatif");
		
		this.setBirthDate(bD);
		this.setEmail(mail);

	}
	
	public void setNom(String nom){
		this.nom=nom;
	}
	
	public String getNom(){
		return nom;
	}
	
	public void setPrenom(String prenom){
		this.prenom=prenom;
	}
	
	public String getPrenom(){
		return prenom;
	}
	
	public void setAge(int age){
		this.age=age;
	}
	
	public int getAge(){
		return age;
	}
	
	public String toString(){
		return "login : "+login+" Nom : "+nom+" Prenom : "+prenom+"age : "+age+" pass : "+password+" ";
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.login.equals(((User)obj).getLogin());
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword(){
		return password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String passwdOK(String paswd){
		System.out.println( " passI: "+password+" passw "+paswd );
		
		if( paswd.equals(getPassword()) )return "{\"success\": \"true\", ";
		return "{\"success\": \"false\", ";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
