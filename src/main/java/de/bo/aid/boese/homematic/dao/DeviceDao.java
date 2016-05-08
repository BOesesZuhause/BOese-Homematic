package de.bo.aid.boese.homematic.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.model.Device;


public class DeviceDao {
	
	public Device create(EntityManager em, String adresse, String type, String name, int version, String firmware){
		Device newEntity = new Device(adresse, type, name, version, firmware);
		em.persist(newEntity);
		return newEntity;
	}
	
	public Device getById(EntityManager em, int id){
		Device dev = em.find(Device.class, id);
		return dev;
	}
	
	public void remove(EntityManager em, Device dev){
		em.remove(dev);
	}
	
	public List<Device> getAll(EntityManager em) {
		Query q = em.createQuery("SELECT a FROM Device a");
		List<?> erg = q.getResultList();
		List<Device> entities = new ArrayList<Device>();
		for(Object o : erg){
			entities.add((Device)o);
		}
		return entities;
	}

	public Device getByAddress(EntityManager em, String address) {
		Query query = em.createQuery("from Device where address = :address");
		query.setParameter("address", address);
		List<?> list = query.getResultList();
		if(list.size() != 1){
			return null;
		}
		Device dev = (Device) list.get(0);
		return dev;
	}
	
	public Device getByIdVerteiler(EntityManager em, int idVerteiler){
		Device dev;
		Query query = em.createQuery("from Device where idVerteiler = :vertid");
		query.setParameter("vertid", idVerteiler);
		List<?> list = query.getResultList();
		if(list.size() != 1){
			return null;
		}
		dev = (Device) list.get(0);
		return dev;
	}
   

	public void insert(EntityManager em, Device device) {
		em.persist(device);		
	}
	
	
	
}