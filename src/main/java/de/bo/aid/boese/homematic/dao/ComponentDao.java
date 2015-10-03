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

import org.eclipse.jetty.util.PathWatcher.DepthLimitedFileVisitor;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.bo.aid.boese.homematic.db.HibernateUtil;
import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.model.Device;
import javassist.NotFoundException;

// TODO: Auto-generated Javadoc
/**
 * This class defines an interface to access Component-Objects from the database.
 */
public class ComponentDao {
	
	/** The SessionFactory to create a hibernate-session. */
	static SessionFactory factory = HibernateUtil.getSessionFactory();
	
	/**
	 * Gets the component.
	 *
	 * @param id the id of the component
	 * @return the component
	 * @throws NotFoundException occurs when the requested component was not found
	 */
	public static Component getByVertID(int id) throws NotFoundException{
		Session session = factory.openSession();
		session.beginTransaction();
		
		Component comp;
		Query query = session.createQuery("from Component where idVerteiler = :vertid");
		query.setParameter("vertid", id);
		List<?> list = query.list();
		if(list.size() != 1){
			return null;
		}
		comp = (Component) list.get(0);
		session.getTransaction().commit();

		session.close();
		return comp;
	}
	
	/**
	 * Inserts a component in the database.
	 *
	 * @param comp the component to be inserted
	 */
	public static void insertComponent(Component comp){
		Session session = factory.openSession();
		session.beginTransaction();
		try{
			session.save(comp);
		}catch(Exception e){
			//TODO
		}
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * updates a component in the database.
	 *
	 * @param component the component to be updated
	 */
	public static void updateComponent(Component component) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		session.beginTransaction();
		session.merge(component);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Gets the component by address and name.
	 *
	 * @param address the address of the component
	 * @param name the name of the component
	 * @return the component. Returns null if no component was found.
	 */
	public static Component getComponentByAddressAndName(String address, String name) {
		Session session = factory.openSession();
		session.beginTransaction();
		Component comp;
		Query query = session.createQuery("from Component where address = :address and hm_id = :hm_id");
		query.setParameter("address", address);
		query.setParameter("hm_id", name);
		List<?> list = query.list();
		if(list.size() != 1){
			return null;
		}
		comp = (Component) list.get(0);
		session.getTransaction().commit();
		return comp;
	}

}
