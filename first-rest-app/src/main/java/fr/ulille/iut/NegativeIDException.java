package fr.ulille.iut;

public class NegativeIDException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String err="ID < 0";
	public NegativeIDException(){}
	public NegativeIDException(String err){this.err=err;}
	public String toString(){return err;}

	
}
