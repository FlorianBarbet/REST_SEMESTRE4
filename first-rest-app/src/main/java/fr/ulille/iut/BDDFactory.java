package fr.ulille.iut;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BDDFactory {
	private Connection c = null;
	private Statement stmt = null;

	public BDDFactory() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			this.c = DriverManager.getConnection("jdbc:sqlite:test.db");;
			this.stmt = c.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public Offer getOffer(String id){

		String query = "SELECT * FROM offer WHERE ID="+id+";";
		
		ArrayList<Applicant> arrayApp = new ArrayList<>();

		Offer off=new Offer();
		try {
		
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( query );
			if(rs.next()){
				off.setType(rs.getString("secteur"));
				off.setLibelle(rs.getString("offer"));
				off.setID(rs.getInt("ID"));
				off.setImg(rs.getString("image"));
				off.setLogin(getEmployerById(getOfferFrom(id)).getLogin());
			}
			rs.close();
				
			ArrayList<Integer> arrayID = new ArrayList<>();
			System.out.println("bidon 1 "+off.getID());
			query = "SELECT * FROM applicantOffer WHERE IDoffer="+off.getID()+";";
			
			rs = stmt.executeQuery( query );
			
			if(rs.next()){
				System.out.println("bidon --- "+rs.getInt("IDapplicant"));
				arrayID.add(rs.getInt("IDapplicant"));
			}
			rs.close();

			for(int i=0;i<arrayID.size();i++) {

				arrayApp.add(this.getApplicantById( arrayID.get(i)+""));
			}
			
			Applicant[] appLike = new Applicant[arrayApp.size()];

			for(int i=0;i<arrayApp.size();i++) {
				appLike[i]=arrayApp.get(i);
				System.out.println("tab "+appLike[i]);
			}
			
			
			off.setAppLike(appLike);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(off.getType()==null)return null;
		return off;

	}
	
//	public Offer getOfferAppFrom(String idapp){
//
//		String query = "SELECT * FROM applicantOffer WHERE IDapplicant="+idapp+";";
//
//		Offer off=new Offer();
//		try {
//			stmt = c.createStatement();
//			ResultSet rs = stmt.executeQuery( query );
//			if(rs.next()){
//				off=getOffer(rs.getString("IDoffer"));
//			}
//			rs.close();
//
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		if(off.getLogin()==null)return null;
//		return off;
//	}


	public Employer getEmployerById(String id){
		Employer emp=new Employer();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM employer WHERE ID="+id+";" );

			while ( rs.next() ) {

				String  nom = rs.getString("nom");
				String  prenom = rs.getString("prenom");
				Date ddn  = rs.getDate("ddn");
				String  password = rs.getString("password");

				emp.setId(rs.getInt("ID"));
				emp.setNom(nom);
				emp.setPrenom(prenom);
				emp.setBirthDate(ddn);
				emp.setLogin(rs.getString("login"));
				emp.setPassword(password);

			}
			rs.close();

		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}

		return emp;
	}

	public String getOfferFrom(String id){
		String query = "SELECT * FROM employerOffer WHERE IDoffer="+id+";";
		String tmp="";
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( query );
			if(rs.next()){
				tmp=rs.getString("IDemployer");
			}

			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tmp;

	}
	

	public int updateOffer(Offer off,String id){
		int res =0;

		try {

			String sql="";

			sql = "UPDATE offer SET ";
			
			
			if(off.getLibelle()!=null)
				sql+="offer='"+off.getLibelle()+"'";
			else
				sql+="offer='Unknown'";

			if(off.getImg()!=null)
				sql+=",image='"+off.getImg()+"'";

			if(off.getType()!=null)
				sql+=",secteur='"+off.getType()+"'";

			sql+=" WHERE ID="+id+";";
			System.out.println("QUERY ===================> "+sql);
			stmt.executeUpdate(sql);

		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			e.printStackTrace();
			res=-1;
		}

		return res;
	}

	
	public int addDiplomes(Diplomes dip) {
		int res =0;

		try {
			String sql="";
			int id=0;
			ResultSet rs = stmt.executeQuery( "SELECT * FROM diplomes;" );
			while ( rs.next() ) {
				id = rs.getInt("ID");
			}
			id++;
			rs.close();
			dip.setId(id);
			sql = "";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO employer (ID,ecole,year,intitule) " +
					"VALUES("+id+",'"+dip.getEcole()+"','"+dip.getYear()+"','"+dip.getIntitule()+"');";
			stmt.executeUpdate(sql);


		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			res=-1;
		}

		return res;
	}

	public Diplomes getDiplome(String id){
		String query = "SELECT * FROM diplome WHERE ID="+id+";";

		Diplomes dip=new Diplomes();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( query );
			if(rs.next()){
				dip.setId(rs.getInt("ID"));
				dip.setEcole(rs.getString("ecole"));
				dip.setYear(rs.getString("year"));
				dip.setIntitule(rs.getString("intitule"));
			}
			rs.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(dip.getYear()==null)return null;
		return dip;
	}

	public Applicant getApplicantById(String id){
		Applicant app=new Applicant();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM applicant WHERE ID="+id+";" );

			while ( rs.next() ) {

				String  nom = rs.getString("nom");
				String  prenom = rs.getString("prenom");
				String  password = rs.getString("password");

				app.setId(rs.getInt("ID"));
				app.setNom(nom);
				app.setPrenom(prenom);
				app.setLogin(rs.getString("login"));
				app.setPassword(password);

			}
			rs.close();

		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}

		return app;
	}

	public Diplomes getDiplomeFrom(String idapp){

		String query = "SELECT * FROM appDip WHERE IDapp="+idapp+";";

		Diplomes dip=new Diplomes();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( query );
			if(rs.next()){
				dip=getDiplome(rs.getString("IDdip"));
			}
			rs.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(dip.getYear()==null)return null;
		return dip;
	}

	public int addOfferToApp(Offer off,Applicant app) {
		int res =0;

		try {
			String sql="";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO applicantOffer (IDapplicant,IDoffer) " +
					"VALUES("+app.getId()+","+off.getID()+");";
			stmt.executeUpdate(sql);


		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			res=-1;
		}

		return res;
	}



	public void CreateBDD() {
		try {

			String sql="";
			//System.out.println("Opened database successfully");

			sql="CREATE TABLE diplomes (";
			sql+="ID INT PRIMARY KEY NOT NULL,";
			sql+="ecole LONGTEXT,";
			sql+="year LONGTEXT,";
			sql+="intitule LONGTEXT )";
			stmt.executeUpdate(sql);
			sql="INSERT INTO diplomes (ID,ecole,year,intitule) VALUES(0,'Lille 1','2018','DUT info'),(1,'Lille 1','2018','DUT bio');";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE applicant " +

                  " (ID           INT PRIMARY KEY    NOT NULL, " + 
                  " nom            LONGTEXT, " + 
                  " prenom       LONGTEXT, " + 
                  " ddn       DATE, " + 
                  " email       LONGTEXT, " + 
                  " login            LONGTEXT, " + 
                  " password       LONGTEXT, " + 
                  " image            LONGTEXT, " +
                  " age			INT,"+
                  " libelle       LONGTEXT, " + 
                  " domain         LONGTEXT)"; 
			stmt.executeUpdate(sql);
			sql = "INSERT INTO applicant (ID,login,password,email) " +
					"VALUES(0,'root','root','root@gmail.com');";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE appDip " +

                  " (IDdip   INT  NOT NULL, " + 
                  " IDapp    INT NOT NULL, "+
                  " FOREIGN KEY(IDdip) REFERENCES diplomes(ID), "+
                  " FOREIGN KEY(IDapp) REFERENCES applicant(ID),  " +
                  " PRIMARY KEY (IDdip,IDapp ) ) " ;
			stmt.executeUpdate(sql);
			sql=
					"INSERT INTO appDip (IDdip,IDapp) " +
							"VALUES(0,0);";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE offer " +

                  " (ID           INT PRIMARY KEY    NOT NULL, " + 
                  " secteur            LONGTEXT, " + 
                  " image            LONGTEXT, " + 
                  " offer         LONGTEXT)"; 
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE employer " +

                  " (ID           INT PRIMARY KEY    NOT NULL, " + 
                  " nom            LONGTEXT, " + 
                  " prenom       LONGTEXT, " + 
                  " ddn       DATE, " + 
                  " age			INT,"+
                  " email       LONGTEXT, " + 
                  " login            LONGTEXT, " + 
                  " password       LONGTEXT) " ;

			stmt.executeUpdate(sql);

			sql = "INSERT INTO employer (ID,login,password,email) " +
					"VALUES(0,'rootemp','rootemp','rootemp@gmail.com');";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE employerOffer " +

                  " (IDemployer   INT  NOT NULL, " + 
                  " IDoffer    INT NOT NULL, "+
                  " FOREIGN KEY(IDoffer) REFERENCES offer(ID), "+
                  " FOREIGN KEY(IDemployer) REFERENCES employer(ID),  " +
                  " PRIMARY KEY (IDemployer,IDoffer ) ) " ;
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE applicantOffer " +

                  " (IDapplicant  INT  NOT NULL, " + 
                  " IDoffer    INT NOT NULL, "+
                  " FOREIGN KEY(IDoffer) REFERENCES offer(ID), "+
                  " FOREIGN KEY(IDapplicant) REFERENCES applicant(ID),  " +
                  " PRIMARY KEY (IDapplicant,IDoffer ) ) " ;
			stmt.executeUpdate(sql);


		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
	

	
	public Offer getOfferAppFrom(String idapp){

		String query = "SELECT * FROM applicantOffer WHERE IDapplicant="+idapp+";";

		Offer off=new Offer();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( query );
			if(rs.next()){
				off=getOffer(rs.getString("IDoffer"));
			}
			rs.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(off.getLogin()==null)return null;
		return off;
	}

	public int addDiplomesToApp(Diplomes dip,Applicant app) {
		int res =0;

		try {
			String sql="";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO appDip (IDapp,IDdip) " +
					"VALUES("+app.getId()+","+dip.getId()+");";
			stmt.executeUpdate(sql);


		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			res=-1;
		}

		return res;
	}


	public void dropTable() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");

			stmt = c.createStatement();
			String sql="";
			sql="DROP TABLE IF EXISTS'applicant'";
			stmt.executeUpdate(sql);
			sql="DROP TABLE IF EXISTS'employer'";
			stmt.executeUpdate(sql);
			sql="DROP TABLE IF EXISTS'offer'";
			stmt.executeUpdate(sql);
			sql="DROP TABLE IF EXISTS'employerOffer'";
			stmt.executeUpdate(sql);
			sql="DROP TABLE IF EXISTS'applicantOffer'";
			stmt.executeUpdate(sql);
			sql="DROP TABLE IF EXISTS'diplomes'";
			stmt.executeUpdate(sql);
			sql="DROP TABLE IF EXISTS'appDip'";
			stmt.executeUpdate(sql);

		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}

	public int addOffer(String login, String offre, String image,String domain) {
		int res =0;

		try {
			String sql="";
			int id=0;
			ResultSet rs = stmt.executeQuery( "SELECT * FROM offer;" );
			while ( rs.next() ) {
				id = rs.getInt("ID");
			}
			id++;
			rs.close();
			if(image==null)image="https://wallscover.com/images/unknown-6.jpg";
			sql = "INSERT INTO offer (ID,secteur,image,offer) " +
					"VALUES("+id+",'"+domain+"','"+image+"','"+offre+"');";
			stmt.executeUpdate(sql);
			System.out.println(sql);

			Employer emp = getEmployer(login);
			int ide=emp.getId();
			sql = "INSERT INTO employerOffer (IDemployer,IDoffer) " +
					"VALUES("+ide+","+id+");";
			stmt.executeUpdate(sql);
			System.out.println(sql);


		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			res=-1;
		}

		return res;
	}

	public int likeOffer(String login,int idOffer) {
		int res =0;		System.out.println("likeOffer : "+login + " "+idOffer);


		try {
			int idApp = getApplicant(login).getId();
			String sql = "INSERT INTO applicantOffer (IDapplicant,IDoffer) " +
					"VALUES("+idApp+","+idOffer+");";
			System.out.println("likeOffer -- "+stmt.executeUpdate(sql));

		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			res=-1;
		}

		return res;
	}



	public ArrayList<Offer> getOfferLikedBy(String login) throws SQLException{

		ArrayList<Offer> array = new ArrayList<>();
		ArrayList<Integer> arrayID = new ArrayList<>();


		Applicant app = getApplicant(login);
		int id=app.getId();

		ResultSet rs = stmt.executeQuery( "SELECT * FROM applicantOffer WHERE IDapplicant="+id+";" );

		while ( rs.next() ) {
			int ido = rs.getInt("IDoffer");
			arrayID.add(ido);
		}
		rs.close();

		for(int i = 0; i<arrayID.size();i++ ) {
			rs = stmt.executeQuery( "SELECT * FROM offer WHERE ID="+arrayID.get(i)+";" );

			while ( rs.next() ) {
				Offer off = new Offer(rs.getString("offer"),rs.getString("image"),rs.getString("secteur"),rs.getInt("ID"));
				array.add(off);
			}
			rs.close();
		}

		return array;
	} 

	public ArrayList<Offer> getOfferOf(String login) throws SQLException{

		ArrayList<Offer> array = new ArrayList<>();
		ArrayList<Integer> arrayID = new ArrayList<>();

		Employer emp = getEmployer(login);
		int id=emp.getId();

		ResultSet rs = stmt.executeQuery( "SELECT * FROM employerOffer WHERE IDemployer="+id+";" );

		while ( rs.next() ) {
			int ido = rs.getInt("IDoffer");
			arrayID.add(ido);
		}
		rs.close();

		for(int i = 0; i<arrayID.size();i++ ) {
			rs = stmt.executeQuery( "SELECT * FROM offer WHERE ID="+arrayID.get(i)+";" );

			while ( rs.next() ) {
				Offer off = new Offer(rs.getString("offer"),rs.getString("image"),rs.getString("secteur"),rs.getInt("ID"));
				array.add(off);
			}
			rs.close();
		}

		return array;
	}

	public ArrayList<Offer> getAllOffer() throws SQLException{
		ArrayList<Offer> array = new ArrayList<>();


		ResultSet rs = stmt.executeQuery( "SELECT * FROM offer ;" );

		while ( rs.next() ) {
			Offer off = new Offer(rs.getString("offer"),rs.getString("image"),rs.getString("secteur"),rs.getInt("ID"));
			off.setLogin(getEmployerById(getOfferFrom(rs.getString("ID"))).getLogin());
			array.add(off);
		}
		rs.close();


		return array;
	}

	public int deleteOffer(String login, int idOffer) {
		int res =0;

		try {
			String sql = "DELETE from employerOffer where IDemployer="+getEmployer(login).getId()+" and IDoffer="+idOffer+";";
			stmt.executeUpdate(sql);
			System.out.println(sql);

			sql = "DELETE from offer where ID="+idOffer+";";
			stmt.executeUpdate(sql);
			System.out.println(sql);

		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			res=-1;
		}

		return res;
	}

	public int addAppilcant(String login,String password,String email) {
		int res =0;

		try {
			String sql="";
			int id=0;
			ResultSet rs = stmt.executeQuery( "SELECT * FROM applicant;" );
			while ( rs.next() ) {
				id = rs.getInt("ID");
			}
			id++;
			rs.close();
			sql = "";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO applicant (ID,login,password,email) " +
					"VALUES("+id+",'"+login+"','"+password+"','"+email+"');";
			stmt.executeUpdate(sql);

		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			res=-1;
		}

		return res;
	}

	public int updateAppilcant(String nom,String prenom,Date ddn,String login,String image,String libelle,String domain,String email) {
		int res =0;

		try {
			String sql="";
			sql = "UPDATE applicant SET nom='"+nom+"',prenom='"+prenom+"',ddn='"+ddn+"',image='"+image+"',libelle='"+libelle+"',domain='"+domain+"',email='"+email+"' WHERE login='"+login+"'";

			stmt.executeUpdate(sql);

		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			res=-1;
		}

		return res;
	}

	public int updateAppilcant(Applicant app,String login,String age) {
		int res =0;
		System.out.println("Age "+age+" - "+app.toString());
		try {
			app.setEmail(app.getEmail().replace("@", "%40"));
			app.setEmail(app.getEmail().replaceAll("\\.", "%2E"));
			app.setEmail(app.getEmail().replace("-", "%2D"));
			String sql="";
			if(app.getDiplomes()!=null){
				Diplomes dip=new Diplomes();
				for(int i=0;i < app.getDiplomes().length;i++){
					dip=app.getDiplomes()[i];
					if(getDiplome(dip.getId()+"")==null)
						this.addDiplomes(dip);
					
					addDiplomesToApp(dip, app);
				}
				
			}
			
			sql = "UPDATE applicant SET ";
			if(app.getNom()!=null)
				sql+="nom='"+app.getNom()+"'";
			else
				sql+="nom='unknown'";
			if(app.getPrenom()!=null)
				sql+=",prenom='"+app.getPrenom()+"'";
			if(app.getImg()!=null)
				sql+=",image='"+app.getImg()+"'";
			if(app.getLibelle()!=null)
				sql+=",libelle='"+app.getLibelle()+"'";
			if(app.getDomain()!=null)
				sql+=",domain='"+app.getDomain()+"'";
			if(app.getEmail()!=null)
				sql+=",email='"+app.getEmail()+"'";
			if(age!=null)
				sql+=",age='"+age+"'";
			if(app.getPassword()!=null)
				sql+=",password='"+app.getPassword()+"'";
			sql+=" WHERE login='"+login+"'";

			stmt.executeUpdate(sql);
			
			
			System.out.println("QUERY ===================> "+sql);
		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			res=-1;
		}

		return res;
	}

	public int deleteApplicant(String login) {
		int res =0;

		try {
			String sql = "DELETE from applicant where login='"+login+"';";
			stmt.executeUpdate(sql);

		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			res=-1;
		}

		return res;
	}


	public Applicant getApplicant(String login) {
		Applicant app=new Applicant();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM applicant WHERE login='"+login+"';" );

			if ( rs.next() ) {

				int id = rs.getInt("ID");
				String  nom = rs.getString("nom");
				String  prenom = rs.getString("prenom");
				String password = rs.getString("password");
				String image = rs.getString("image");
				String libelle = rs.getString("libelle");
				String domain = rs.getString("domain");
				int age=rs.getInt("age");

				app.setId(id);
				app.setNom(nom);
				app.setLibelle(libelle);
				app.setPrenom(prenom);
				app.setEmail(rs.getString("email"));
				app.setLogin(login);
				app.setPassword(password);
				app.setImg(image);
				app.setDomain(domain);
				app.setAge(age);
				app.setEmail(rs.getString("email"));

			}
			rs.close();

		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}

		if(app.getPassword()==null)return null;
		return app;
	}



	public Applicant getEmailApplicant(String email) {
		Applicant app=new Applicant();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM applicant WHERE email='"+email+"';" );

			while ( rs.next() ) {
				int id = rs.getInt("ID");
				String  nom = rs.getString("nom");
				String  prenom = rs.getString("prenom");
				Date ddn  = rs.getDate("ddn");
				String  password = rs.getString("password");
				String  image = rs.getString("image");
				String  libelle = rs.getString("libelle");
				String  domain = rs.getString("domain");
				int age=rs.getInt("age");

				app.setId(id);
				app.setNom(nom);
				app.setLibelle(libelle);
				app.setPrenom(prenom);
				app.setBirthDate(ddn);
				app.setLogin(rs.getString("login"));
				app.setEmail(email);
				app.setPassword(password);
				app.setImg(image);
				app.setDomain(domain);
				app.setAge(age);


			}
			rs.close();

		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}

		if(app.getPassword()==null)return null;
		return app;
	}

	public List<Applicant> getAllApplicant() {
		List<Applicant> apps=new ArrayList<>();

		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM applicant ;" );

			while ( rs.next() ) {
				Applicant app=new Applicant();
				int id = rs.getInt("ID");
				String login=rs.getString("login");
				String  nom = rs.getString("nom");
				String  prenom = rs.getString("prenom");
				Date ddn  = rs.getDate("ddn");
				String  password = rs.getString("password");
				String  image = rs.getString("image");
				String  libelle = rs.getString("libelle");
				String  domain = rs.getString("domain");

				app.setId(id);
				app.setLibelle(libelle);
				app.setNom(nom);
				app.setPrenom(prenom);
				app.setBirthDate(ddn);
				app.setLogin(login);
				app.setPassword(password);
				app.setImg(image);
				app.setEmail(rs.getString("email"));
				app.setDomain(domain);
				apps.add(app);

			}
			rs.close();

		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}

		return apps;
	}



	public int addEmployer(String login,String password,String email) {
		int res =0;

		try {
			String sql="";
			int id=0;
			ResultSet rs = stmt.executeQuery( "SELECT * FROM employer;" );
			while ( rs.next() ) {
				id = rs.getInt("ID");
			}
			id++;
			rs.close();
			sql = "";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO employer (ID,login,password,email) " +
					"VALUES("+id+",'"+login+"','"+password+"','"+email+"');";
			stmt.executeUpdate(sql);


		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			res=-1;
		}

		return res;
	}


	public int updateEmployer(String nom,String prenom,Date ddn,String login,String email) {


		int res =0;

		try {
			String sql="";

			sql = "UPDATE applicant SET nom='"+nom+"',prenom='"+prenom+"',ddn='"+ddn+"',email='"+email+"' WHERE login='"+login+"'";


			stmt.executeUpdate(sql);

		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			res=-1;
		}

		return res;
	}

	public int updateEmployer(Employer emp) {
		int res =0;

		try {
			String sql="";
			sql = "UPDATE applicant SET ";
			if(emp.getNom()!=null)
				sql+="nom='"+emp.getNom()+"'";
			else
				sql+="nom='unknown'";
			if(emp.getPrenom()!=null)
				sql+=",prenom='"+emp.getPrenom()+"'";

			if(emp.getEmail()!=null)
				sql+=",email='"+emp.getEmail()+"'";
			if(emp.getAge()==-1)
				sql+=",age='"+emp.getAge()+"'";
			if(emp.getPassword()!=null)
				sql+=",password='"+emp.getPassword()+"'";

			sql+=" WHERE login='"+emp.getLogin()+"'";

			stmt.executeUpdate(sql);
			System.out.println("QUERY ===================> "+sql);
		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			res=-1;
		}

		return res;
	}

	public int deleteEmployer(String login) {
		int res =0;

		try {
			String sql = "DELETE from employer where login='"+login+"';";
			stmt.executeUpdate(sql);

		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			res=-1;
		}

		return res;
	}

	public Employer getEmployer(String login) {
		Employer emp=null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM employer WHERE login='"+login+"';" );

			if ( rs.next() ) {
				emp=new Employer();
				int id = rs.getInt("ID");
				String  nom = rs.getString("nom");
				String  prenom = rs.getString("prenom");
				String  password = rs.getString("password");

				emp.setId(id);
				emp.setNom(nom);
				emp.setPrenom(prenom);
				emp.setLogin(login);
				emp.setPassword(password);
				
			}
			rs.close();

		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}

		
		return emp;
	}

	public List<Employer> getAllEmployer() {
		List<Employer> emps=new ArrayList<>();

		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM employer ;" );

			while ( rs.next() ) {
				Employer emp=new Employer();
				int id = rs.getInt("ID");
				String  nom = rs.getString("nom");
				String  prenom = rs.getString("prenom");
				String  password = rs.getString("password");

				emp.setId(id);
				emp.setNom(nom);
				emp.setPrenom(prenom);
				emp.setLogin(rs.getString("login"));
				emp.setPassword(password);
				emps.add(emp);

			}
			rs.close();

		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}

		return emps;
	}

	public static void main(String[] args) throws SQLException {
		BDDFactory bdd = new BDDFactory();
		bdd.dropTable();
		bdd.CreateBDD();
		System.out.println("Start");
		bdd.addAppilcant("elowe", "passwd", "theo@leicht.fr");
		System.out.println("Added elowe");
		bdd.addAppilcant("trololo", "123", "idk@gmail.com");
		System.out.println("Added trololo");
		Applicant app = new Applicant(); 
		app=bdd.getApplicant("elowe");
		System.out.println(app);
		//		bdd.addEmployer("patron", "123", "toto@gmail.com");
		//		System.out.println("patron added");
		//		bdd.addEmployer("boss", "123", "titi@gmail.com");
		//		System.out.println("boss added");
		//		bdd.addOffer("patron", "info", "url", "blablabla");
		//		bdd.addOffer("patron", "deco", "url", "blablabla");
		//		bdd.addOffer("patron", "brico", "url", "blablabla");
		//		bdd.addOffer("patron", "cuisine", "url", "blablabla");
		//		bdd.addOffer("boss", "info", "url", "bozbozboz");
		//		System.out.println("offer added");
		//		ArrayList<Offer> array = bdd.getOfferOf("patron");
		//		for(int i=0;i<array.size();i++) {
		//			System.out.println(array.get(i).toString());
		//		}
		//		System.out.println("Start deletion of offer deco");
		//		bdd.deleteOffer("patron", array.get(1).getID());
		//		ArrayList<Offer> array2 = bdd.getOfferOf("patron");
		//		for(int i=0;i<array2.size();i++) {
		//			System.out.println(array2.get(i).toString());
		//		}
		//		bdd.likeOffer("elowe", array.get(0).getID());
		//		array = bdd.getOfferLikedBy("elowe");
		//		for(int i=0;i<array.size();i++) {
		//			System.out.println(array.get(i).toString());
		//		}
		//		array = bdd.getAllOffer();
		//		for(int i=0;i<array.size();i++) {
		//			System.out.println(array.get(i).toString());
		//		}
	}


}
