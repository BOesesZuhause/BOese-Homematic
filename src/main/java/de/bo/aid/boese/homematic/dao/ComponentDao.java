/*
 * 
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
 * The Class ComponentDao.
 */
public class ComponentDao {
	
	/** The factory. */
	static SessionFactory factory = HibernateUtil.getSessionFactory();
	
	/**
	 * Gets the component.
	 *
	 * @param id the id
	 * @return the component
	 * @throws NotFoundException the not found exception
	 */
	public static Component getComponent(int id) throws NotFoundException{
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
	 * Insert component.
	 *
	 * @param comp the comp
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
	 * Update component.
	 *
	 * @param component the component
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
	 * @param address the address
	 * @param name the name
	 * @return the component by address and name
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
