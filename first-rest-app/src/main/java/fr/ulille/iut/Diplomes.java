package fr.ulille.iut;

public class Diplomes {
	
	private int id=0;

	private String ecole;
	private String year;
	private String intitule;
	
	public Diplomes(){}
	public Diplomes(String ecole,String year,String intitule){
		this.ecole=ecole;
		this.year=year;
		this.intitule=intitule;
	}
	
	public String getEcole() {
		return ecole;
	}
	public void setEcole(String ecole) {
		this.ecole = ecole;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	

}
