/*
 * 
 */
package de.bo.aid.boese.homematic.dao;


import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.bo.aid.boese.homematic.db.HibernateUtil;
import de.bo.aid.boese.homematic.model.Device;
import javassist.NotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Class DeviceDao.
 */
public class DeviceDao {

/** The factory. */
static SessionFactory factory = HibernateUtil.getSessionFactory();
	
	/**
	 * Gets the device.
	 *
	 * @param id the id
	 * @return the device
	 * @throws NotFoundException the not found exception
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
	 * Gets the devices.
	 *
	 * @return the devices
	 */
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
	 * Insert device.
	 *
	 * @param dev the dev
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
	 * Update device.
	 *
	 * @param dev the dev
	 */
	public static void updateDevice(Device dev) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		session.beginTransaction();
		session.merge(dev);
		session.getTransaction().commit();
		session.close();
		
	}

}
