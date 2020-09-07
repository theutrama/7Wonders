package controller.event;

/** EventPriority which sets the priority of the event */
public enum EventPriority {
	LOWEST(0), //Wird als erstes ausgefuehrt
	LOW(1),
	MEDIUM(2),
	HIGH(3),
	HIGHEST(4); //Wird als letztes ausgefuehrt

	/** priority of Event */
	private int priority=0;
	/**
	 * creates new EventPriority
	 * @param priority
	 */
	EventPriority(int priority){
		this.priority=priority;
	}
	/**
 	* getter for {@link #priority}
 	* @return priority
 	*/
	public int getPriority() {
		return this.priority;
	}
}
