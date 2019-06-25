package fr.ulille.iut;

import java.util.Date;

public class Employer extends User{

	//EMPLOYER(OFFER[]:offer)
	
	private Offer[] offer ;

	public Employer(){}


	public Employer(int id,String login,String password, String nom, String prenom, int age,String email) throws NegativeIDException {
		super(id,login,password, nom, prenom, age,email);

	}
	
	public Employer(int id,String login,String password, String nom, String prenom, int age,Date bD,String email) throws NegativeIDException {
		super(id,login,password, nom, prenom, age,bD,email);

	}

	public Offer[] getOffer() {
		return offer;
	}

	public void setOffer(Offer[] offer) {
		this.offer = offer;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	public String toString() {
		if(offer==null)return super.toString()+"Taille liste offre : -1";
		return super.toString()+"Taille liste offre : "+offer.length;

	}
	
	@Override
	public String passwdOK(String paswd) {
		return super.passwdOK(paswd)+" \"type\":\"employer\" }";
	}

}
