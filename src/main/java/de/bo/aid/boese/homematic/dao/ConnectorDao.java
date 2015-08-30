package de.bo.aid.boese.homematic.dao;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.bo.aid.boese.homematic.db.HibernateUtil;
import de.bo.aid.boese.homematic.model.Connector;
import javassist.NotFoundException;

public class ConnectorDao {
static SessionFactory factory = HibernateUtil.getSessionFactory();
	
	public static Connector getConnector() throws Exception {
		Session session = factory.openSession();
		session.beginTransaction();

		Connector con = new Connector();

		List<Connector> cons = session.createQuery("from Connector").list();
		session.getTransaction().commit();

		if (cons.size() == 1) {
			con = cons.get(0);
		} else if (cons.size() > 1) {
			throw new Exception(); //TODO
		} else if (cons.size() == 0) {
			con.setName("HomeMaticDefault");
			con.setPassword(null);
			con.setIdVerteiler(-1);

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
		session.getTransaction().commit();

	}
}
