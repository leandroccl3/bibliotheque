package biblio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Composant logiciel assurant la gestion des livres et des exemplaires
 * de livre.
 */
public class ComposantBDLivre {

  /**
   * Récupération de la liste complète des livres.
   * 
   * @return un <code>ArrayList<String[]></code>. Chaque tableau de chaînes
   * de caractères contenu correspond à un livre.<br/>
   * Il doit contenir 5 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id</li>
   *   <li>1 : isbn10</li>
   *   <li>2 : isbn13</li>
   *   <li>3 : titre</li>
   *   <li>4 : auteur</li>
   * </ul>
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<String[]> listeTousLesLivres() throws SQLException {

    ArrayList<String[]> livres = new ArrayList<String[]>();

    Statement stmt = Connexion.getConnection().createStatement();
    String sql = "select * from livre";
    ResultSet rset = stmt.executeQuery(sql);

    while (rset.next()) {
      String[] livre = new String[5];
      livre[0] = rset.getString("id");
      livre[1] = rset.getString("isbn10");
      livre[2] = rset.getString("isbn13");
      livre[3] = rset.getString("titre");
      livre[4] = rset.getString("auteur");

      livres.add(livre);
    }
    rset.close();
    stmt.close();

    return livres;
   
  }
  
  /**
   * Retourne le nombre de livres référencés dans la base.
   * 
   * @return le nombre de livres.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static int nbLivres() throws SQLException {
	  
	  ArrayList<String[]> nbLivre = new ArrayList<String[]>();
	  
	  Statement stmt = Connexion.getConnection().createStatement();
	  String sql = "select id from livre";
	  ResultSet rset = stmt.executeQuery(sql);

	  while (rset.next()) {
	    String[] nbLivres = new String[1];
	    nbLivres[0] = rset.getString("id");
	    
	    nbLivre.add(nbLivres);
	    
	  }
	  
	  rset.close();
	  stmt.close();
	  
	  int sizeNbLivres = nbLivre.size();
	  
    return sizeNbLivres;
  }

  /**
   * Récupération des informations sur un livre connu à partir de son identifiant.
   * 
   * @param idLivre : id du livre à rechercher
   * @return un tableau de chaînes de caractères (<code>String[]</code>). Chaque
   * tableau doit contenir 5 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id</li>
   *   <li>1 : isbn10</li>
   *   <li>2 : isbn13</li>
   *   <li>3 : titre</li>
   *   <li>4 : auteur</li>
   * </ul>
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
   public static String[] getLivre(int idLivre) throws SQLException {
    
     
     Statement stmt = Connexion.getConnection().createStatement();
     String sql = "select * from livre where id='"+idLivre+"'";
     ResultSet rset = stmt.executeQuery(sql);
	 
       
     rset.next();
     String[] Livre = new String[5];
       
       Livre[0] = rset.getString("id");
       Livre[1] = rset.getString("isbn10");
       Livre[2] = rset.getString("isbn13");
       Livre[3] = rset.getString("titre");
       Livre[4] = rset.getString("auteur");
     
     rset.close();
     stmt.close();
     
     return Livre;
   }
  
 /**
  * Récupération des informations sur un livre connu à partir de l'identifiant
  * de l'un de ses exemplaires.
  * 
  * @param idExemplaire : id de l'exemplaire
  * @return un tableau de chaînes de caractères (<code>String[]</code>). Chaque
  * tableau doit contenir 6 éléments (dans cet ordre) :
  * <ul>
  *   <li>0 : id de l'exemplaire</li>
  *   <li>1 : id du livre</li>
  *   <li>2 : isbn10</li>
  *   <li>3 : isbn13</li>
  *   <li>4 : titre</li>
  *   <li>5 : auteur</li>
  * </ul>
  * @throws SQLException en cas d'erreur de connexion à la base.
  */
  public static String[] getLivreParIdExemplaire(int idExemplaire) throws SQLException {
    	 	  
	Statement stmt = Connexion.getConnection().createStatement();
	String sql = " select * from livre liv join exemplaire exem on liv.id=exem.exemliv where idex='"+idExemplaire+"'";
	ResultSet rset = stmt.executeQuery(sql);

	rset.next();
	
		String[] getLivreParIdExemplaire = new String[6];
		getLivreParIdExemplaire[0] = rset.getString("id");
		getLivreParIdExemplaire[1] = rset.getString("exemliv");
	    getLivreParIdExemplaire[2] = rset.getString("isbn10");
	    getLivreParIdExemplaire[3] = rset.getString("isbn13");
	    getLivreParIdExemplaire[4] = rset.getString("titre");
	    getLivreParIdExemplaire[5] = rset.getString("auteur");

	rset.close();
	stmt.close();
 
    return getLivreParIdExemplaire;
  }

  /**
   * Référencement d'un nouveau livre dans la base de données.
   * 
   * @param isbn10
   * @param isbn13
   * @param titre
   * @param auteur
   * @return l'identifiant (id) du livre créé.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static int insererNouveauLivre(String isbn10, String isbn13, String titre, String auteur) throws SQLException {
	  
	  int id= 0;
	  
	  Statement stmt = Connexion.getConnection().createStatement();
	  String sql = "insert into livre values (nextval('livre_id_seq'),'"+isbn10+"', '"+isbn13+"', '"+titre+"', '"+auteur+"')";
	  
	  stmt.executeUpdate(sql);
	  
	  String query = "select currval('livre_id_seq') as valeur_courante_id_livre"; 
	  ResultSet rset = stmt.executeQuery(query);
	  
	  rset.next();
	  
	  id=rset.getInt("valeur_courante_id_livre");
	  
	  rset.close();
	  stmt.close();
	  
	  return id;
	  
  } 
  
/**
   * Modification des informations d'un livre donné connu à partir de son
   * identifiant : les nouvelles valeurs (isbn10, isbn13, etc.) écrasent les
   * anciennes.
   * 
   * @param idLivre : id du livre à modifier.
   * @param isbn10 : nouvelle valeur d'isbn10.
   * @param isbn13 : nouvelle valeur d'isbn13.
   * @param titre : nouvelle valeur du titre.
   * @param auteur : nouvel auteur.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static void modifierLivre(int idLivre, String isbn10, String isbn13, String titre, String auteur) throws SQLException {
	  
	  Statement stmt = Connexion.getConnection().createStatement();
	  String sql = "update livre set isbn10 = '"+isbn10+"', isbn13 = '"+isbn13+"', titre = '"+titre+"', auteur = '"+auteur+"' where id='"+idLivre+"'";
	  
	  stmt.executeUpdate(sql);
	  
	  stmt.close();
  }

  /**
   * Suppression d'un abonné connu à partir de son identifiant.
   * 
   * @param idLivre : id du livre à supprimer.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
   public static void supprimerLivre(int idLivre) throws SQLException {
	   
	   Statement stmt = Connexion.getConnection().createStatement();
	   String sql = "delete from livre where id='"+idLivre+"'";
	   
	   stmt.executeUpdate(sql);
	
	   stmt.close();
   }

   /**
    * Retourne le nombre d'exemplaire d'un livre donné connu à partir
    * de son identifiant.
    * 
    * @param idLivre : id du livre dont on veut connaître le nombre d'exemplaires.
    * @return le nombre d'exemplaires
    * @throws SQLException en cas d'erreur de connexion à la base.
    */
   public static int nbExemplaires(int idLivre) throws SQLException {
	   
	   ArrayList<String[]> nbExemplaire = new ArrayList<String[]>();
		  
		  Statement stmt = Connexion.getConnection().createStatement();
		  String sql = "select idex from exemplaire where exemliv='"+idLivre+"' and exemactive = true";
		  ResultSet rset = stmt.executeQuery(sql);

		  while (rset.next()) {
		    String[] nbExemplaires = new String[1];
		    nbExemplaires[0] = rset.getString("idex");
		    
		    nbExemplaire.add(nbExemplaires);
		    
		  }
		  rset.close();
		  stmt.close();
		  
		  int sizeNbExemplaires = nbExemplaire.size();
		  
	    return sizeNbExemplaires;
   }

  /**
   * Récupération de la liste des identifiants d'exemplaires d'un livre donné
   * connu à partir de son identifiant.
   * 
   * @param idLivre : identifiant du livre dont on veut la liste des exemplaires.
   * @return un <code>ArrayList<Integer></code> contenant les identifiants des exemplaires
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<Integer> listeExemplaires(int idLivre) throws SQLException {
    
	ArrayList<Integer> exemplaires = new ArrayList<Integer>();
   
    Statement stmt = Connexion.getConnection().createStatement();
    String sql = "select idex from exemplaire where exemliv='"+idLivre+"' and exemactive = true";
    ResultSet rset = stmt.executeQuery(sql);
	 
      
    while (rset.next()) {
    	
    	int exemplaire = rset.getInt("idex");
      
    exemplaires.add(exemplaire);
      
    }
    rset.close();
    stmt.close();
    
    
    
    return exemplaires;
  }

  /**
   * Ajout d'un exemplaire à un livre donné connu par son identifiant.
   * 
   * @param id identifiant du livre dont on veut ajouter un exemplaire.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
   public static void ajouterExemplaire(int idLivre) throws SQLException {
	   
	   //int idex= 0;
		  
		  Statement stmt = Connexion.getConnection().createStatement();
		  String sql = "insert into exemplaire values (nextval('exemplaire_idex_seq'),'"+idLivre+"', true)";
		  
		  stmt.executeUpdate(sql);
	
		  stmt.close();
		  		  
   }

    /**
     * Suppression d'un exemplaire donné connu par son identifiant.
     * 
     * @param idExemplaire : identifiant du livre dont on veut supprimer un exemplaire.
     * @throws SQLException en cas d'erreur de connexion à la base.
     */
   public static void supprimerExemplaire(int idExemplaire) throws SQLException {
		
	   int idem=0;
	   
	   Statement stmt = Connexion.getConnection().createStatement();
	   String sql = "select idem from emprunts emp join exemplaire exem on emp.exemempr=exem.idex where exem.idex = '"+idExemplaire+"' and emp.dateret='1111-11-11'";
	   
	   ResultSet rset = stmt.executeQuery(sql);
	   
	   while (rset.next()) {
	    	
	    	idem = rset.getInt("idem");
	      
	   }
	   
	   if (idem == 0){
		   
		   String sql2 = "update exemplaire set exemactive = false where idex='"+idExemplaire+"'";
		   stmt.executeUpdate(sql2);

	   }
	    rset.close();
	    stmt.close();
	  }
   }

