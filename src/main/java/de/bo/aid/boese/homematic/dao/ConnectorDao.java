package de.bo.aid.boese.homematic.dao;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.bo.aid.boese.homematic.db.HibernateUtil;
import de.bo.aid.boese.homematic.model.Connector;
import javassist.NotFoundException;

public class ConnectorDao {
static SessionFactory factory = HibernateUtil.getSessionFactory();
	
	public static Connector getConnector(int id) throws NotFoundException{
		Session session = factory.openSession();
		session.beginTransaction();
		
		Connector con = new Connector();
		try{
			session.load(con, id);
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			session.close();
			throw new NotFoundException("Connector with id: " + id + " not found");
		}
		return con;
	}
	
	public static void insertConnector(Connector con){
		Session session = factory.openSession();
		session.beginTransaction();
		try{
			session.save(con);
		}catch(Exception e){
			//TODO
		}

	}
}
