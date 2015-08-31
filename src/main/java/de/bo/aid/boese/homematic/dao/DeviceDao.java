package de.bo.aid.boese.homematic.dao;


import java.util.List;

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
		session.close();
		return dev;
	}
	
	public static List<Device> getDevices(){
		Session session = factory.openSession();
		session.beginTransaction();
		
		List<Device> devices;
		devices = session.createQuery("From Device").list();
		session.getTransaction().commit();
		session.close();
		return devices;
	}
	
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

	public static void updateDevice(Device dev) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		session.beginTransaction();
		session.merge(dev);
		session.getTransaction().commit();
		session.close();
		
	}

}
