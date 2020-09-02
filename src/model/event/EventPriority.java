package model.event;

public enum EventPriority {
LOWEST(0), //Wird als erstes ausgef�hrt
LOW(1),
MEDIUM(2),
HIGH(3),
HIGHEST(4); //Wird als letztes ausgef�hrt

private int priority=0;
EventPriority(int priority){
	this.priority=priority;
}

public int getPriority() {
	return this.priority;
}

}
