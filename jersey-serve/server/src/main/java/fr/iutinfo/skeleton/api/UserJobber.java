package fr.iutinfo.skeleton.api;


public abstract class UserJobber {

	//USER(int:login,string:nom,string:prenom,int:age)

    private String login;
	private String nom;
	private String prenom;
	private int age=-1;
	private int id=0;
	private String password;
	
	public UserJobber(){}
	
	public UserJobber(int id,String login,String password,String nom,String prenom,int age) throws NegativeIDException{
		this.setId(id);
		this.setLogin(login);
		this.setPassword(password);
		this.nom=nom;
		this.prenom=prenom;
		
		if(age>=0)
			this.age=age;
		else
			throw new NegativeIDException("Age negatif");

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
		return "login : "+login+" Nom : "+nom+" Prenom : "+prenom+"age : "+age;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.login.equals(((UserJobber)obj).getLogin());
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
		if( paswd.equals(this.password))return "{success: true}";
		return "{success: false}";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
