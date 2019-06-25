package fr.ulille.iut;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class Applicant extends User{

	//APPLICANT(string:img,string:libelle,int:nbLike,EMPLOYER[]:empLike)
	private String img;
	private String libelle;
	private String domain;
	private Diplomes[] diplomes;
	//private String mail;

	public Applicant(){}

	public Applicant(String email,int id,String login,String password,String nom,String prenom, int age,String img, String libelle,String domain) throws NegativeIDException{

		super(id,login,password,nom,prenom,age,email);
		this.img=img;
		this.libelle=libelle;
		this.setDomain(domain);
	}

	public Applicant(String email,int id,String login,String password,String nom,String prenom, int age,String img, String libelle,String domain,Date bD) throws NegativeIDException{

		super(id,login,password,nom,prenom,age,bD,email);
		this.img=img;
		this.libelle=libelle;
		this.setDomain(domain);
	}

	public Diplomes[] getDiplomes() {
		return diplomes;
	}

	public void setDiplomes(Diplomes[] diplomes) {
		this.diplomes = diplomes;
	}

	//	public void addDiplomes(Diplomes dip){
	//		for(int i=0; i < this.diplomes.length;i++){
	//			if(this.diplomes[i]==null){
	//				this.diplomes[i]=dip;
	//			}
	//		}
	//	}
	//	
	//	private int sizeDip(){
	//		int ret=-1;
	//		for(int i=0;i < diplomes.length;i++)
	//			if(diplomes[i]==null)ret=i;
	//		return ret;
	//	}

	@Override
	public String toString() {

		return super.toString()+"\n img : "+img+" description : "+libelle;
	}

	public void setImg(String img){
		this.img=img;
	}

	public String getImg(){
		return img;
	}

	public void setLibelle(String libelle){
		this.libelle=libelle;
	}



	public String getLibelle() {
		return libelle;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}	

	@Override
	public void setLogin(String login){
		super.setLogin(login);
	}

	@Override
	public String getLogin(){
		return super.getLogin();
	}

	@Override
	public void setPassword(String password){
		super.setPassword(password);
	}


	@Override
	public void setNom(String nom){
		super.setNom(nom);
	}

	@Override
	public String getNom(){
		return super.getNom();
	}

	@Override
	public void setPrenom(String prenom){
		super.setPrenom(prenom);
	}

	@Override
	public String getPrenom(){
		return super.getPrenom();
	}

	@Override
	public void setAge(int age){
		super.setAge(age);
	}

	@Override
	public int getAge(){
		return super.getAge();
	}

	@Override
	public String passwdOK(String paswd) {
		return super.passwdOK(paswd)+" \"type\":\"applicant\" }";
	}

}
