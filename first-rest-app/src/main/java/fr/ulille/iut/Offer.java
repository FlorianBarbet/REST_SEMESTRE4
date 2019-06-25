package fr.ulille.iut;

public class Offer {

	//OFFER(string:type,string:img,string:libelle, int:nbLike,APPLICANT[]:appLike)

	protected String type;
	protected String login="unknown";
	protected String img;
	protected String libelle;
	protected Applicant[] appLike;
	protected int ID ;
	
	public Offer(){}
	public Offer(String type, String img, String libelle ,int id) {
		this.type = type;
		this.img = img;
		this.libelle = libelle;
		this.ID=id;
	}
	
	public Offer(String type, String img, String libelle,String login ,int id) {
		this.type = type;
		this.img = img;
		this.libelle = libelle;
		this.login=login;
		this.ID=id;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public void addApplicant(Applicant app){
		int ok=-1;
		for(int i=0; i < appLike.length;i++){
			if(appLike[i]==null){
				appLike[i]=app;
				ok++;
			}
		}
		if(ok<0){
			resizePlus();
			addApplicant(app);
		}
	}
	
	public String toString(){
		return "Type: "+ this.type + " ImageURL: "+img+" Libelle: "+ libelle+" ID: "+ ID;
	}
	
	public void resizePlus() {
		Applicant[] appLike2=new Applicant[this.appLike.length*2];
		for(int i = 0; i < this.appLike.length ; i++) {
			appLike2[i]=this.appLike[i];
		}
		this.appLike=appLike2;
	}
	
	public String getLogin(){
		return login;
	}
	
	public void setLogin(String login){
		this.login=login;
	}
	
	@Override
	public boolean equals(Object obj) {
		Boolean res = false;
		if(this.ID==((Offer) obj).getID()){
			res = true;
		}
		return res;
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

	public Applicant[] getAppLike() {
		return appLike;
	}

	public void setAppLike(Applicant[] appLike) {
		this.appLike = appLike;
	}
	
	
	
}
