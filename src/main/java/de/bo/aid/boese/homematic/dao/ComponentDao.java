package de.bo.aid.boese.homematic.dao;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.bo.aid.boese.homematic.db.HibernateUtil;
import de.bo.aid.boese.homematic.model.Component;
import javassist.NotFoundException;

public class ComponentDao {
	static SessionFactory factory = HibernateUtil.getSessionFactory();
	
	public static Component getComponent(int id) throws NotFoundException{
		Session session = factory.openSession();
		session.beginTransaction();
		
		Component comp = new Component();
		try{
			session.load(comp, id);
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			session.close();
			throw new NotFoundException("Component with id: " + id + " not found");
		}
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

	}

	public static void updateComponent(Component component) {
		// TODO Auto-generated method stub
		
	}

}
