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

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.bo.aid.boese.homematic.db.HibernateUtil;
import de.bo.aid.boese.homematic.model.Device;
import javassist.NotFoundException;

// TODO: Auto-generated Javadoc
/**
  * This class defines an interface to access Device-Objects from the database.
 */
public class DeviceDao {

	/** The SessionFactory to create a hibernate-session. */
static SessionFactory factory = HibernateUtil.getSessionFactory();
	
	/**
	 * Gets the device.
	 *
	 * @param id the id of the device
	 * @return the device
	 * @throws NotFoundException occurs when the device is not found
	 */
	public static Device getDevice(int id) throws NotFoundException{
		Session session = factory.openSession();
		session.beginTransaction();
		
		Device dev = new Device();
		try{
			session.load(dev, id);
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			session.close();
			throw new NotFoundException("Device with id: " + id + " not found");
		}
		session.close();
		return dev;
	}
	
	/**
	 * Gets all devices from the database.
	 *
	 * @return the devices
	 */
	@SuppressWarnings("unchecked")
	public static List<Device> getDevices(){
		Session session = factory.openSession();
		session.beginTransaction();
		
		List<Device> devices;
		devices = session.createQuery("From Device").list();
		session.getTransaction().commit();
		session.close();
		return devices;
	}
	
	/**
	 * Inserts device in the database.
	 *
	 * @param dev the device to be inserted
	 */
	public static void insertDevice(Device dev){
		Session session = factory.openSession();
		session.beginTransaction();
		try{
			session.save(dev);
		}catch(Exception e){
			//TODO
		}
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Updates device in the database.
	 *
	 * @param dev the device to be updated
	 */
	public static void updateDevice(Device dev) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		session.beginTransaction();
		session.merge(dev);
		session.getTransaction().commit();
		session.close();
		
	}
	
	/**
	 * Gets the by address.
	 *
	 * @param address the address
	 * @return the by address
	 */
	public static Device getByAddress(String address){
		Session session = factory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Device where adress = :address");
		query.setParameter("address", address);
		List<?> list = query.list();
		if(list.size() != 1){
			return null;
		}
		Device dev = (Device) list.get(0);
		session.getTransaction().commit();
		return dev;
	}

}
