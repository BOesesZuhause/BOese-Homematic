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

public class ComponentDao {
	static SessionFactory factory = HibernateUtil.getSessionFactory();
	
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

	public static void updateComponent(Component component) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		session.beginTransaction();
		session.merge(component);
		session.getTransaction().commit();
		session.close();
	}

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
