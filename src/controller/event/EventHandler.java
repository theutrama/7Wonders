package controller.event;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/** EventHandler sets priorities to Events */
@Target({java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
	/**
	 * sets priority of event
	 * @return priority of Event
	 */
	public EventPriority priority() default EventPriority.MEDIUM;
}
