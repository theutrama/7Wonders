package controller.event;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** EventHandler handles Events */
@Target({java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
	/**
	 * Sets priority of Event
	 * @return EventPriority	priority of Event
	 */
	public EventPriority priority() default EventPriority.MEDIUM;
}
