package fr.iutinfo.skeleton.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class Applicant extends UserJobber{
	
	//APPLICANT(string:img,string:libelle,int:nbLike,EMPLOYER[]:empLike)
	
	private String img;
	private String libelle;
	private int nbLike=0;
	private String domain;
	
	public Applicant(){}
	
	public Applicant(int id,String login,String password,String nom,String prenom, int age,String img, String libelle,int nbLike,String domain) throws NegativeIDException{

		super(id,login,password,nom,prenom,age);
		this.img=img;
		this.libelle=libelle;
		this.nbLike=nbLike;
		this.setDomain(domain);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString()+"\n img : "+img+" description : "+libelle+" Like : "+nbLike;
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

	public int getNbLike() {
		return nbLike;
	}

	public void setNbLike(int nbLike) {
		this.nbLike = nbLike;
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
	
}
