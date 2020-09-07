package controller.event;
/** abstract interface for canceling Event */
public abstract interface Cancellable
{
	/** 
	 * checks if Event is cancelled
	 * @return boolean			returns true if Event is cancelled
	 */  
	public abstract boolean isCancelled();
	/**
	 * cancels or uncancels an Event
	 * @param paramBoolean		sets cancelled on true or false
	 */
	public abstract void setCancelled(boolean paramBoolean);
}