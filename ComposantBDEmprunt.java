package biblio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
		 String sql="select idem from emprunts where dateret = ('1111-11-11') and emprunteur = idAbonne ";
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
		 String sql="select * from emprunts";
		 ResultSet rset = stmt.executeQuery(sql);
		 
		 while(rset.next()){
			 
			 String[] listeEmpruntsEnCourss = new String[5];
			 
			 listeEmpruntsEnCourss[0] = rset.getString("idem");
			 listeEmpruntsEnCourss[1] = rset.getString("dateemp");
			 listeEmpruntsEnCourss[2] = rset.getString("dateret");
			 listeEmpruntsEnCourss[3] = rset.getString("emprunteur");
			 listeEmpruntsEnCourss[4] = rset.getString("exemempr");
			 
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
   * Il doit contenir 5 éléments (dans cet ordre) :
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
    String sql = "select ex.idex, liv.titre, us.nom,"
    		+ " emp.dateemp, emp.dateret from usagers"
    		+ " us join emprunts emp on us.idu=emp.emprunteur"
    		+ " join exemplaire ex on ex.idex=emp.exemempr"
    		+ " join livre liv on ex.exemliv=liv.id"
    		+ " order by dateemp desc";
    ResultSet rset = stmt.executeQuery(sql);

    while (rset.next()) {
      String[] listeEmpruntsEnCours = new String[5];
      listeEmpruntsEnCours[0] = rset.getString("idex");
      listeEmpruntsEnCours[1] = rset.getString("titre");
      listeEmpruntsEnCours[2] = rset.getString("nom");
      listeEmpruntsEnCours[3] = rset.getString("dateemp");
      listeEmpruntsEnCours[4] = rset.getString("dateret");
      
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
    String sql = "select ex.idex, liv.titre, us.nom,"
    		+ " emp.dateemp, emp.dateret from usagers"
    		+ " us join emprunts emp on us.idu=emp.emprunteur"
    		+ " join exemplaire ex on ex.idex=emp.exemempr"
    		+ " join livre liv on ex.exemliv=liv.id"
    		+ " order by dateemp desc";
    ResultSet rset = stmt.executeQuery(sql);

    while (rset.next()) {
    	
      String[] liste = new String[5];
      
      liste[0] = rset.getString("idex");
      liste[1] = rset.getString("titre");
      liste[2] = rset.getString("nom");
      liste[3] = rset.getString("dateemp");
      liste[4] = rset.getString("dateret");
      
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
    //
    // A COMPLETER
    //
  }

  /**
   * Retourner un exemplaire à partir de son identifiant.
   * 
   * @param idExemplaire id de l'exemplaire à rendre.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static void rendre(int idExemplaire) throws SQLException {
    //
    // A COMPLETER
    //
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
    //
    // A COMPLETER
    //
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
  public static HashMap<String, int[]> statsEmprunts() throws SQLException
  {
    HashMap<String, int[]> stats = new HashMap<String, int[]>();
    //
    // A COMPLETER
    //
    return stats;
  }
}
