package biblio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Composant logiciel assurant la gestion des abonnés.
 */
public class ComposantBDAbonne {

  /**
   * Récupération de la liste complète des abonnés. ESto es un comentario para probar el funcionamiento de la copia de seguridad
   * 
   * @return un <code>ArrayList<String[]></code>. Chaque tableau de chaînes
   * de caractères contenu correspond à un abonné.<br/>
   * Il doit contenir 5 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id</li>
   *   <li>1 : nom</li>
   *   <li>2 : prénom</li>
   *   <li>3 : statut</li>
   *   <li>4 : adresse email</li>
   * </ul>
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<String[]> listeTousLesAbonnes() throws SQLException {
	  
	  ArrayList<String[]> abonne = new ArrayList<String[]>();
	  
	  Statement stmt = Connexion.getConnection().createStatement();
	  String sql = "select * from usagers where active = true";
	  ResultSet rset = stmt.executeQuery(sql);
	  
	  while (rset.next()) {
		  
		  String[] abonnes = new String[5];
		  
		  abonnes[0] = rset.getString("IDU");
		  abonnes[1] = rset.getString("nom");
		  abonnes[2] = rset.getString("prenom");
		  abonnes[3] = rset.getString("status");
		  abonnes[4] = rset.getString("email");
		  
		  abonne.add(abonnes);
		  
		  }
	  
	  rset.close();
	  stmt.close();
	  
	  return abonne;
	  }

  /**
   * Retourne le nombre d'abonnés référencés dans la base.
   * 
   * @return le nombre d'abonnés.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static int nbAbonnes() throws SQLException {
	 
	ArrayList<String[]> nbAbonne = new ArrayList<String[]>();
	  
	  Statement stmt = Connexion.getConnection().createStatement();
	  String sql = "select nom from usagers";
	  ResultSet rset = stmt.executeQuery(sql);

	  while (rset.next()) {
	    String[] nbAbonnes = new String[1];
	    
	    nbAbonnes[0] = rset.getString("nom");
	    
	    nbAbonne.add(nbAbonnes);
	    
	    }
	  
	  rset.close();
	  stmt.close();
	  
	  int sizeAbonne = nbAbonne.size();
	  
	  return sizeAbonne;
	  
  }

  /**
   * Récupération des informations sur un abonné à partir de son identifiant.
   * 
   * @param idAbonne : id de l'abonné à rechercher
   * @return un tableau de chaînes de caractères (<code>String[]</code>). Chaque
   * tableau doit contenir 5 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id</li>
   *   <li>1 : nom</li>
   *   <li>2 : prénom</li>
   *   <li>3 : statut</li>
   *   <li>4 : adresse email</li>
   * </ul>
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static String[] getAbonne(int idAbonne) throws SQLException {
    
  Statement stmt = Connexion.getConnection().createStatement();
  String sql = "select * from usagers where idu='"+idAbonne+"'";
  ResultSet rset = stmt.executeQuery(sql);
  
  rset.next(); 

    String[] abonne = new String[5];
    
    abonne[0] = rset.getString("idu");
    abonne[1] = rset.getString("nom");
    abonne[2] = rset.getString("prenom");
    abonne[3] = rset.getString("status");
    abonne[4] = rset.getString("email");
    
    rset.close();
    stmt.close();
    
    return abonne;
    
  }

  /**
   * Référencement d'un nouvel abonné dans la base de données.
   * 
   * @param nom
   * @param prenom
   * @param statut (deux valeurs possibles <i>Etudiant</i> et <i>Enseignant</i>)
   * @param email
   * @return l'identifiant de l'abonné référencé.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static int insererNouvelAbonne(String nom, String prenom, String statut, String email) throws SQLException {
	  
	  int idAbonne = 0;
	  
	  Statement stmt = Connexion.getConnection().createStatement();
	  String sql = "insert into usagers values (nextval('usagers_idu_seq'),'"+nom+"', '"+prenom+"', '"+statut+"', '"+email+"', true)";
	  
	  stmt.executeUpdate(sql);
	  
	  String query = "select currval('usagers_idu_seq') as valeur_courante_idu_usagers"; 
	  ResultSet rset = stmt.executeQuery(query);
	  
	  rset.next();
	  
	  idAbonne=rset.getInt("valeur_courante_idu_usagers");
	  
	  rset.close();
	  stmt.close();
	  
	  return idAbonne;
  }

  
//  private static void sql(int i, String nom) {
	// TODO Auto-generated method stub
	
//}

/**
   * Modification des informations d'un abonné donné connu à partir de son
   * identifiant : les nouvelles valeurs (nom, prenom, etc.) écrasent les anciennes.
   * 
   * @param idAbonne : identifiant de l'abonné dont on veut modifier les informations.
   * @param nom
   * @param prenom
   * @param statut (deux valeurs possibles <i>Etudiant</i> et <i>Enseignant</i>)
   * @param email
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static void modifierAbonne(int idAbonne, String nom, String prenom, String statut, String email) throws SQLException {
	  
	  Statement stmt = Connexion.getConnection().createStatement();
	  String sql = "update usagers set (nom = '"+nom+"', prenom = '"+prenom+"', status ='"+statut+"', email = '"+email+"') where idu = '"+idAbonne+"'";
	  
	  stmt.executeUpdate(sql);
	  
	  stmt.close();
  }

  /**
   * Suppression d'un abonné connu à partir de son identifiant.
   * 
   * @param idAbonne : identifiant de l'utilisateur
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static void supprimerAbonne(int idAbonne) throws SQLException {
	  
	  Statement stmt = Connexion.getConnection().createStatement();
	  String sql = "update usagers set active = ((select emp.dateret from emprunts emp join usagers us on emp.emprunteur=us.idu where idu = '"+idAbonne+"')='1111-11-11')";
	    
	  stmt.executeUpdate(sql);
	  
	  stmt.close();
  }
}
