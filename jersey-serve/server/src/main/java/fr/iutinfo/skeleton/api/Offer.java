package fr.iutinfo.skeleton.api;

public class Offer {

	//OFFER(string:type,string:img,string:libelle, int:nbLike,APPLICANT[]:appLike)

	protected String type;
	protected String img;
	protected String libelle;
	protected int nbLike;
	protected Applicant[] appLike=new Applicant[512];
	
	public Offer(String type, String img, String libelle, int nbLike) {
		this.type = type;
		this.img = img;
		this.libelle = libelle;
		this.nbLike = nbLike;
	}
	
	public String toString(){
		return "Type: "+ this.type + " ImageURL: "+img+" Libelle: "+ libelle+" Nombre de Like: "+ nbLike+ " TailleApplicant: "+ appLike.length;
	}
	
	public void resizePlus() {
		Applicant[] appLike2=new Applicant[this.appLike.length*2];
		for(int i = 0; i < this.appLike.length ; i++) {
			appLike2[i]=this.appLike[i];
		}
		this.appLike=appLike2;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.libelle.equals((String)obj);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public int getNbLike() {
		return nbLike;
	}

	public void setNbLike(int nbLike) {
		this.nbLike = nbLike;
	}

	public Applicant[] getAppLike() {
		return appLike;
	}

	public void setAppLike(Applicant[] appLike) {
		this.appLike = appLike;
	}
	
	
	
}
