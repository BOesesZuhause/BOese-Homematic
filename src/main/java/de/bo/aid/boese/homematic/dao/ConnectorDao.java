/*             
 * 			  (                       
 *			 ( )\         (        (   
 *			 )((_)  (    ))\ (    ))\  
 *			((_)_   )\  /((_))\  /((_) 
 *			 | _ ) ((_)(_)) ((_)(_))   
 *			 | _ \/ _ \/ -_)(_-</ -_)  
 *			 |___/\___/\___|/__/\___|
 *       
 *           			;            
 *		      +        ;;;         + 
 *			  +       ;;;;;        + 
 *			  +      ;;;;;;;       + 
 *			  ++    ;;;;;;;;;     ++ 
 *			  +++++;;;;;;;;;;;+++++  
 *			   ++++;;;;;;;;;;;+++++  
 *				++;;;;;;;;;;;;;++    
 *			     ;;;;;;;;;;;;;;;     
 *			    ;;;;;;;;;;;;;;;;;     
 *				:::::::::::::::::    
 * 				:::::::::::::::::      
 *  			:::::::::::::::::    
 *   			::::::@@@@@::::::    
 *				:::::@:::::@:::::    
 *				::::@:::::::@::::    
 * 				:::::::::::::::::    
 *  			:::::::::::::::::      
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <sebastian.lechte@hs-bochum.de> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Sebastian Lechte
 * ----------------------------------------------------------------------------
 */
package de.bo.aid.boese.homematic.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.bo.aid.boese.homematic.db.HibernateUtil;
import de.bo.aid.boese.homematic.model.Connector;
import javassist.NotFoundException;

/**
 *  This class defines an interface to access Connector-Objects from the database.
 */
public class ConnectorDao {

	/** The SessionFactory to create a hibernate-session. */
static SessionFactory factory = HibernateUtil.getSessionFactory();
	
	/**
	 * Gets the connector. There is only one connector in the database, 
	 * so no identifier is needed.
	 *
	 * @return the connector
	 * @throws NotFoundException occurs when no connector-data is found in the database.
	 */
	public static Connector getConnector() throws Exception  {
		Session session = factory.openSession();
		session.beginTransaction();

		Connector con = new Connector();

		@SuppressWarnings("unchecked")
		List<Connector> cons = session.createQuery("from Connector").list();
		session.getTransaction().commit();

		
		if (cons.size() == 1) {
			con = cons.get(0);
		} else if (cons.size() == 0){
			return null;
		}		
		else {
			throw new NotFoundException("Failed to load connector from database");
		}
		session.close();
		return con;
	}
	
	/**
	 * Inserts a connector in the database.
	 *
	 * @param con the connector to be inserted
	 */
	public static void insertConnector(Connector con){
		Session session = factory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(con);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Updates a connector in the database.
	 *
	 * @param con the connector to be updated
	 */
	public static void update(Connector con){
		Session session = factory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(con);
		session.getTransaction().commit();
		session.close();
	}
}
