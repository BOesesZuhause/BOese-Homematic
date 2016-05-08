package de.bo.aid.boese.homematic.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;

import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.model.Device;

public class ComponentDao {

    
   public Component create(EntityManager em, Device device, String name, String address, String hm_id, String unit, boolean aktor, String type) {
	   Component comp = new Component(device, name, address, hm_id, unit, aktor, type);
	   em.persist(comp);
	   return comp;
   }

   public Component getById(EntityManager em, int id){
	   Component comp = em.find(Component.class, id);
	   return comp;
   }
   
   public void remove(EntityManager em, Component comp){
	   em.remove(comp);
   }
   
	public List<Component> getAll(EntityManager em) {
		Query q = em.createQuery("SELECT a FROM Component a");
		List<?> erg = q.getResultList();
		List<Component> entities = new ArrayList<Component>();
		for(Object o : erg){
			entities.add((Component)o);
		}
		return entities;
	}

	public Component getComponentByAddressAndName(EntityManager em, String address, String hm_id) {
		Component comp;
		Query query = em.createQuery("from Component where address = :address and hm_id = :hm_id");
		query.setParameter("address", address);
		query.setParameter("hm_id", hm_id);
		List<?> list = query.getResultList();
		if(list.size() != 1){
			return null;
		}
		comp = (Component) list.get(0);
		return comp;
	}

	public void insert(EntityManager em, Component comp) {
		em.persist(comp);		
	}

	public Component getByVertID(EntityManager em, int deviceComponentId) {
		Component comp;
		Query query = em.createQuery("from Component where idVerteiler = :vertid");
		query.setParameter("vertid", deviceComponentId);
		List<?> list = query.getResultList();
		if(list.size() != 1){
			return null;
		}
		comp = (Component) list.get(0);
		return comp;
	}
   
}
