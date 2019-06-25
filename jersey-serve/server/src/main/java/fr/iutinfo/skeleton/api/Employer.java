package fr.iutinfo.skeleton.api;

public class Employer extends UserJobber{
	
	//EMPLOYER(OFFER[]:offer)
	
	private Offer[] offer = new Offer[512];

	public Employer(int id,String login,String password, String nom, String prenom, int age, Offer[] offer) throws NegativeIDException {
		super(id,login,password, nom, prenom, age);
		this.offer = offer;
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
		return "Taille liste offre : "+offer.length;
	}

}
