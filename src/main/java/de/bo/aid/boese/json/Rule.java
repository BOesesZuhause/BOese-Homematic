/*             
 * 			  (                       
 *			 ( )\         (        (   
 *			 )((_)  (    ))\ (    ))\  
 *			((_)_   )\  /((_))\  /((_) 
 *			 | _ ) ((_)(_)) ((_)(_))   
 *			 | _ \/ _ \/ -_)(_-</ -_)  
 *			 |___/\___/\___|/__/\___|
 *       
 *           			;            
 *		      +        ;;;         + 
 *			  +       ;;;;;        + 
 *			  +      ;;;;;;;       + 
 *			  ++    ;;;;;;;;;     ++ 
 *			  +++++;;;;;;;;;;;+++++  
 *			   ++++;;;;;;;;;;;+++++  
 *				++;;;;;;;;;;;;;++    
 *			     ;;;;;;;;;;;;;;;     
 *			    ;;;;;;;;;;;;;;;;;     
 *				:::::::::::::::::    
 * 				:::::::::::::::::      
 *  			:::::::::::::::::    
 *   			::::::@@@@@::::::    
 *				:::::@:::::@:::::    
 *				::::@:::::::@::::    
 * 				:::::::::::::::::    
 *  			:::::::::::::::::      
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <sebastian.lechte@hs-bochum.de> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Sebastian Lechte
 * ----------------------------------------------------------------------------
 */
package de.bo.aid.boese.json;

// TODO: Auto-generated Javadoc
/**
 * The Class Rule.
 */
public class Rule {
	
	/** The rule id. */
	private int ruleId;
	
	/** The active. */
	private boolean active;
	
	/** The insert date. */
	private long insertDate;
	
	/** The modify date. */
	private long modifyDate;
	
	/** The permissions. */
	private String permissions;
	
	/** The conditions. */
	private String conditions;
	
	/** The actions. */
	private String actions;
	
	/**
	 * Instantiates a new rule.
	 *
	 * @param ruleId the rule id
	 * @param active the active
	 * @param insertDate the insert date
	 * @param modifyDate the modify date
	 * @param permissions the permissions
	 * @param conditions the conditions
	 * @param actions the actions
	 */
	public Rule(int ruleId, boolean active, long insertDate, long modifyDate, String permissions, String conditions,
			String actions) {
		this.ruleId = ruleId;
		this.active = active;
		this.insertDate = insertDate;
		this.modifyDate = modifyDate;
		this.permissions = permissions;
		this.conditions = conditions;
		this.actions = actions;
	}

	/**
	 * Gets the rule id.
	 *
	 * @return the rule id
	 */
	public int getRuleId() {
		return ruleId;
	}

	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Gets the insert date.
	 *
	 * @return the insert date
	 */
	public long getInsertDate() {
		return insertDate;
	}

	/**
	 * Gets the modify date.
	 *
	 * @return the modify date
	 */
	public long getModifyDate() {
		return modifyDate;
	}

	/**
	 * Gets the permissions.
	 *
	 * @return the permissions
	 */
	public String getPermissions() {
		return permissions;
	}

	/**
	 * Gets the conditions.
	 *
	 * @return the conditions
	 */
	public String getConditions() {
		return conditions;
	}

	/**
	 * Gets the actions.
	 *
	 * @return the actions
	 */
	public String getActions() {
		return actions;
	}
	
	
	
}
