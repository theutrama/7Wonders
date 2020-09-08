package controller.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/** Helps realizing events */
public class EventHelper {
	/** HashMap for handlers */
	public static HashMap<EventListener,HashMap<Integer,ArrayList<Method>>> handlers = new HashMap<EventListener, HashMap<Integer,ArrayList<Method>>>();
	/**
	 * unregisters class
	 * @param c 	class to be unregistered
	 * @return		returns true if class was successfully unregistered
	 */
	public static boolean unregister(Class cla){
		for(int i = 0; i<handlers.size(); i++)
			if( ((EventListener) handlers.keySet().toArray()[i]).getClass().equals(cla)){
				handlers.remove(i);
				return true;
			}
		return false;
	}
	/**
	 * unregisters EventListener
	 * @param listener		EventListener to be unregistered
	 * @return				returns true if EventListener was successfully unregistered	
	 */
	public static boolean unregister(EventListener listener) {
		if(handlers.containsKey(listener)) {
			handlers.remove(listener);
			return true;
		}
		return false;
	}
	/** 
	 * Registers class with EventListener
	 * @param listener		EventListener to be registered
	 */
	public static void register(EventListener listener){
		if(handlers.containsKey(listener))return;
    	if(!handlers.containsKey(listener))handlers.put(listener, new HashMap<Integer,ArrayList<Method>>());
		//Sucht alle Methoden in dem Listener raus
		Method[] methods = listener.getClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; ++i) {
        	//Filtert alle EventHandler raus!
            EventHandler eventHandler = methods[i].getAnnotation(EventHandler.class);
            
            if (eventHandler != null) {
            	//Fuegt ihn zur Liste hinzu
            	if(!handlers.get(listener).containsKey(eventHandler.priority().getPriority()))handlers.get(listener).put(eventHandler.priority().getPriority(), new ArrayList<Method>());
            	handlers.get(listener).get(eventHandler.priority().getPriority()).add(methods[i]);
            }
        }
	}
	/**
	 * calls the event
	 * @param event		event to be called
	 */
	public static void callEvent(final Event event) {
        for (EventListener listener : handlers.keySet()) {
            for (int i = 0; i<EventPriority.values().length; i++) {
            		if(handlers.get(listener).containsKey(i) && !handlers.get(listener).get(i).isEmpty()){
            			for(Method method : handlers.get(listener).get(i)){
            				if (!event.getClass().getSimpleName().equals(method.getParameterTypes()[0].getSimpleName())) continue;
                            try {
                                method.invoke(listener, new Object[]{event});
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
            			}
            		}
                }
            }
    }
}