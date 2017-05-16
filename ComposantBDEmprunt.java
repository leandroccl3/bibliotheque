package biblio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Composant logiciel assurant la gestion des emprunts d'exemplaires
 * de livre par les abonnés.
 */
public class ComposantBDEmprunt {

  /**
   * Retourne le nombre total d'emprunts en cours référencés dans la base.
   * 
   * @return le nombre d'emprunts.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
	 public static int nbEmpruntsEnCours() throws SQLException {
		 
		 ArrayList<String[]> nbEmpCur = new ArrayList<String[]>();
		 
		 Statement stmt = Connexion.getConnection().createStatement();
		 String sql="select idem from emprunts where dateret = ('1111-11-11') ";
		 ResultSet rset = stmt.executeQuery(sql);
		 
		 while(rset.next()){
			 String[] nbEmpCurs = new String[1];
			 nbEmpCurs[0] = rset.getString("idem");
			 nbEmpCur.add(nbEmpCurs);
			 }
		 
		 rset.close();
		 stmt.close();
		 
		 int sizeNbEmpCur = nbEmpCur.size();
		 return sizeNbEmpCur;
		 }


  /**
   * Retourne le nombre d'emprunts en cours pour un abonné donné connu
   * par son identifiant.
   * 
   * @return le nombre d'emprunts.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static int nbEmpruntsEnCours(int idAbonne) throws SQLException {
	  
	  ArrayList<String[]> nbEmpCurIdAbonne = new ArrayList<String[]>();
		 
		 Statement stmt = Connexion.getConnection().createStatement();
		 String sql="select idem from emprunts where dateret = ('1111-11-11') and emprunteur = '"+idAbonne+"' ";
		 ResultSet rset = stmt.executeQuery(sql);
		 
		 while(rset.next()){
			 String[] nbEmpCurIdAbonnes = new String[1];
			 nbEmpCurIdAbonnes[0] = rset.getString("idem");
			 nbEmpCurIdAbonne.add(nbEmpCurIdAbonnes);
			 }
		 
		 rset.close();
		 stmt.close();
		 
		 int sizeNbEmpCurIdAbonne = nbEmpCurIdAbonne.size();
		 return sizeNbEmpCurIdAbonne;
		 }
  
  /**
   * Récupération de la liste complète des emprunts en cours.
   * 
   * @return un <code>ArrayList<String[]></code>. Chaque tableau de chaînes
   * de caractères contenu correspond à un emprunt en cours.<br/>
   * Il doit contenir 8 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id de l'exemplaire</li>
   *   <li>1 : id du livre correspondant</li>
   *   <li>2 : titre du livre</li>
   *   <li>3 : son auteur</li>
   *   <li>4 : id de l'abonné</li>
   *   <li>5 : nom de l'abonné</li>
   *   <li>6 : son prénom</li>
   *   <li>7 : la date de l'emprunt</li>
   * </ul>
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<String[]> listeEmpruntsEnCours() throws SQLException {
	  
	  ArrayList<String[]> listeEmpruntsEnCours = new ArrayList<String[]>();
		 
		 Statement stmt = Connexion.getConnection().createStatement();
		 
		 String sql = "select ex.idex, liv.id, liv.titre, liv.auteur, emp.idem, us.nom, us.prenom, emp.dateemp,"
		 		+ " emp.dateret from usagers us join emprunts emp on "
		 		+ "us.idu=emp.emprunteur join exemplaire ex on ex.idex=emp.exemempr"
		 		+ " join livre liv on ex.exemliv=liv.id where dateret='1111-11-11'";
		 
		 ResultSet rset = stmt.executeQuery(sql);
		 
		 while(rset.next()){
			 
			 String[] listeEmpruntsEnCourss = new String[8];
			 
			 listeEmpruntsEnCourss[0] = rset.getString("idex");
			 listeEmpruntsEnCourss[1] = rset.getString("id");
			 listeEmpruntsEnCourss[2] = rset.getString("titre");
			 listeEmpruntsEnCourss[3] = rset.getString("auteur");
			 listeEmpruntsEnCourss[4] = rset.getString("idem");
			 listeEmpruntsEnCourss[5] = rset.getString("nom");
			 listeEmpruntsEnCourss[6] = rset.getString("prenom");
			 listeEmpruntsEnCourss[7] = rset.getString("dateemp");
			 
			 listeEmpruntsEnCours.add(listeEmpruntsEnCourss);
			 
			 }
		 
		 rset.close();
		 stmt.close();
		 
		 return listeEmpruntsEnCours;
		 }
  /**
   * Récupération de la liste des emprunts en cours pour un abonné donné.
   * 
   * @return un <code>ArrayList<String[]></code>. Chaque tableau de chaînes
   * de caractères contenu correspond à un emprunt en cours pour l'abonné.<br/>
   * Il doit contenir 8 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id de l'exemplaire</li>
   *   <li>1 : id du livre correspondant</li>
   *   <li>2 : titre du livre</li>
   *   <li>3 : son auteur</li>
   *   <li>4 : la date de l'emprunt</li>
   * </ul>
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<String[]> listeEmpruntsEnCours(int idAbonne) throws SQLException {
    
	ArrayList<String[]> empruntsEnCours = new ArrayList<String[]>();
    
	Statement stmt = Connexion.getConnection().createStatement();
	String sql = "select ex.idex, liv.titre, us.nom, emp.dateemp,"
			+ " liv.id from usagers us join emprunts emp on"
			+ " us.idu=emp.emprunteur join exemplaire ex on"
			+ " ex.idex=emp.exemempr join livre liv on"
			+ " ex.exemliv=liv.id where dateret='1111-11-11'"
			+ " and us.idu='"+idAbonne+"'";
	
    ResultSet rset = stmt.executeQuery(sql);

    while (rset.next()) {
      String[] listeEmpruntsEnCours = new String[5];
      listeEmpruntsEnCours[0] = rset.getString("idex");
      listeEmpruntsEnCours[1] = rset.getString("id");
      listeEmpruntsEnCours[2] = rset.getString("titre");
      listeEmpruntsEnCours[3] = rset.getString("nom");
      listeEmpruntsEnCours[4] = rset.getString("dateemp");
    
      
      empruntsEnCours.add(listeEmpruntsEnCours);
 
    }
    rset.close();
    stmt.close();
    
    return empruntsEnCours;
  }

  /**
   * Récupération de la liste complète des emprunts passés.
   * 
   * @return un <code>ArrayList<String[]></code>. Chaque tableau de chaînes
   * de caractères contenu correspond à un emprunt passé.<br/>
   * Il doit contenir 9 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id de l'exemplaire</li>
   *   <li>1 : id du livre correspondant</li>
   *   <li>2 : titre du livre</li>
   *   <li>3 : son auteur</li>
   *   <li>4 : id de l'abonné</li>
   *   <li>5 : nom de l'abonné</li>
   *   <li>6 : son prénom</li>
   *   <li>7 : la date de l'emprunt</li>
   *   <li>8 : la date de retour</li>
   * </ul>
   * @return un <code>ArrayList</code> contenant autant de tableaux de String (5 chaînes de caractères) que d'emprunts dans la base.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<String[]> listeEmpruntsHistorique() throws SQLException {
	    
	ArrayList<String[]> listes = new ArrayList<String[]>();
	        
    Statement stmt = Connexion.getConnection().createStatement();
    String sql = "select ex.idex, liv.id, liv.titre, liv.auteur, "
    		+ "us.idu, us.nom, us.prenom, emp.dateemp, emp.dateret from usagers"
    		+ " us join emprunts emp on us.idu=emp.emprunteur"
    		+ " join exemplaire ex on ex.idex=emp.exemempr"
    		+ " join livre liv on ex.exemliv=liv.id"
    		+ " order by dateemp desc";
    ResultSet rset = stmt.executeQuery(sql);

    while (rset.next()) {
    	
      String[] liste = new String[9];
      
      liste[0] = rset.getString("idex");
      liste[1] = rset.getString("id");
      liste[2] = rset.getString("titre");
      liste[3] = rset.getString("auteur");
      liste[4] = rset.getString("idu");
      liste[5] = rset.getString("nom");
      liste[6] = rset.getString("prenom");
      liste[7] = rset.getString("dateemp");
      liste[8] = rset.getString("dateret");
      
      listes.add(liste);
      
      }
    
    rset.close();
    stmt.close();
    
    return listes;
  }


  /**
   * Emprunter un exemplaire à partir de l'identifiant de l'abonné et de
   * l'identifiant de l'exemplaire.
   * 
   * @param idAbonne : id de l'abonné emprunteur.
   * @param idExemplaire id de l'exemplaire emprunté.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static void emprunter(int idAbonne, int idExemplaire) throws SQLException {
	  int idem = 0;
	  
	  int year = Calendar.getInstance().get(Calendar.YEAR); 
	  int month = Calendar.getInstance().get(Calendar.MONTH)+1; 
	  int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH); 
	  
	  Statement stmt = Connexion.getConnection().createStatement();
	  
	  String sql = "select emp.idem from emprunts emp join usagers us on emp.emprunteur=us.idu where emp.emprunteur ='"+idAbonne+"' and emp.exemempr = '"+idExemplaire+"' and emp.dateret = '1111-11-11'";
	  ResultSet rset = stmt.executeQuery(sql);
	  
	  while (rset.next()) {
	    	
	  idem = rset.getInt("idem");
	  }
	  
	  
	  if (idem != 0){
		  
		  System.out.println("Exemplaire deja emprunté");
		  
	  }
	  else{
		  
		  String sql2 = "insert into emprunts values (nextval('emprunts_idem_seq'),'"+year+"-"+month+"-"+day+"','1111-11-11','"+idAbonne+"','"+idExemplaire+"')";
		  stmt.executeUpdate(sql2);
		  
	  }
	  
	  
	  rset.close();
	  stmt.close();

  }  
	  

  /**
   * Retourner un exemplaire à partir de son identifiant.
   * 
   * @param idExemplaire id de l'exemplaire à rendre.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static void rendre(int idExemplaire) throws SQLException {
	  
	  int year = Calendar.getInstance().get(Calendar.YEAR); 
	  int month = Calendar.getInstance().get(Calendar.MONTH)+1; 
	  int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH); 
	  //int hour = Calendar.getInstance().get(Calendar.HOUR);
	  
	  Statement stmt = Connexion.getConnection().createStatement();
	  String sql = "update emprunts set dateret = '"+year+"-"+month+"-"+day+"' where exemempr = '"+idExemplaire+"'";
	  
	  stmt.executeUpdate(sql);
  }
  
  /**
   * Détermine si un exemplaire sonné connu par son identifiant est
   * actuellement emprunté.
   * 
   * @param idExemplaire
   * @return <code>true</code> si l'exemplaire est emprunté, <code>false</code> sinon
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static boolean estEmprunte(int idExemplaire) throws SQLException {
    
    boolean estEmprunte = false;
    int idem ='0';
    
    Statement stmt = Connexion.getConnection().createStatement();
	String sql = "select idem from emprunts where exemempr='"+idExemplaire+"' and dateret='1111-11-11'";
	ResultSet rset = stmt.executeQuery(sql);
	
	while (rset.next()) {
	  	  
	idem = rset.getInt("idem");
	}    
	
	rset.close();
	  
	stmt.close();
	 
	 if (idem == '0') {
		 estEmprunte = false;
		 }
	 
	 else{
		 estEmprunte = true;
			 } 
    
    return estEmprunte;
  }

  /**
   * Récupération des statistiques sur les emprunts (nombre d'emprunts et de
   * retours par jour).
   * 
   * @return un <code>HashMap<String, int[]></code>. Chaque enregistrement de la
   * structure de données est identifiée par la date (la clé) exprimée sous la forme
   * d'une chaîne de caractères. La valeur est un tableau de 2 entiers qui représentent :
   * <ul>
   *   <li>0 : le nombre d'emprunts</li>
   *   <li>1 : le nombre de retours</li>
   * </ul>
   * Exemple :
   * <pre>
   * +-------------------------+
   * | "2017-04-01" --> [3, 1] |
   * | "2017-04-02" --> [0, 1] |
   * | "2017-04-07" --> [5, 9] |
   * +-------------------------+
   * </pre>
   *   
   * @throws SQLException
   */
  public static HashMap<String, int[]> statsEmprunts() throws SQLException{
	  
	  int year = Calendar.getInstance().get(Calendar.YEAR); 
	  int month = Calendar.getInstance().get(Calendar.MONTH)+1; 
	  int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH); 
	  String fecha = year+"-"+month+"-"+day;
	  
	  ArrayList<String[]> idRetAuj = new ArrayList<String[]>();
	  ArrayList<String[]> idEmpAuj = new ArrayList<String[]>();
	  ArrayList<String[]> exemp = new ArrayList<String[]>();
	
	
	  HashMap<String, int[]> stats = new HashMap<String, int[]>();
    

    Statement stmt = Connexion.getConnection().createStatement();
	 String sql="select idem from emprunts where dateret = '2017-05-14' ";
	 ResultSet rset = stmt.executeQuery(sql);
	 
	 while(rset.next()){
		 String [] nbRetAuj = new String[1];
		 nbRetAuj[0] = rset.getString("idem");
		 idRetAuj.add(nbRetAuj);	 
		 }
	 
	 
	 int nbIdRetAuj=idRetAuj.size();
	 	
	 
	 String sql2="select idem from emprunts where dateemp = '"+fecha+"' ";
	 ResultSet rset2 = stmt.executeQuery(sql2);
	 
	 while(rset2.next()){
		 String [] nbEmpAuj = new String[1];
		 nbEmpAuj[0] = rset2.getString("idem");
		 idEmpAuj.add(nbEmpAuj);	 
		 }
	 
	 
	 int nbIdEmpAuj=idEmpAuj.size();
	 
	 String sql3="select idex from exemplaire";
	 ResultSet rset3 = stmt.executeQuery(sql3);
	 
	 while(rset3.next()){
		 String[] exem = new String[1];
		 exem[0] = rset3.getString("idex");
		 exemp.add(exem);
		 }
	 int nbExemp=exemp.size();
	 
	 int [] nbRetAujStat = new int[2];
	  
	 nbExemp=5; //borrar despues
	 
	 nbRetAujStat[0]=nbIdRetAuj/nbExemp;
	 nbRetAujStat[1]=nbIdEmpAuj/nbExemp;
	 	 	 
	 
	 stats.put(fecha, nbRetAujStat);
	 
	 rset.close();
	 rset2.close();
	 rset3.close();
	 
	 stmt.close();
    
    
    return stats;
  }
}
