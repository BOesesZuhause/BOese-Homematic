package de.bo.aid.boese.homematic.dao;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.bo.aid.boese.homematic.db.HibernateUtil;
import de.bo.aid.boese.homematic.model.Device;
import javassist.NotFoundException;

public class DeviceDao {
static SessionFactory factory = HibernateUtil.getSessionFactory();
	
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
		return dev;
	}
	
	public static void insertDevice(Device dev){
		Session session = factory.openSession();
		session.beginTransaction();
		try{
			session.save(dev);
		}catch(Exception e){
			//TODO
		}

	}
}
