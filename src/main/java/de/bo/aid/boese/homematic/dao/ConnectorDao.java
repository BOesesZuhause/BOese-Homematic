package de.bo.aid.boese.homematic.dao;

import javax.persistence.EntityManager;

import de.bo.aid.boese.homematic.model.Connector;

public class ConnectorDao {
    
    public Connector create (EntityManager em, String name){
    	Connector c = this.get(em);
    	if(c == null){
	        c = new Connector(name);
	       // c.setConid(1);
	        em.persist(c);
    	}
        return c;
    }
    
    public Connector get (EntityManager em){
    	Connector entity = null;
    	try{
    		entity = (Connector) em.find(Connector.class, 1);
    	}
    	catch(Exception e){
    		return null;
    	}
		return entity;
    }
    
    public Connector merge(EntityManager em, Connector con){
    	return em.merge(con);
    }
    
	/**
	 * Deletes a Connector.
	 */
	public void delete(EntityManager em, Connector entity) {
		em.remove(entity);
	}

}
