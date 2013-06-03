
package trussoptimizater.Truss.Events;

import java.util.EventObject;

/**
 * All events should extend this class
 * @author Chris
 */
public abstract class TrussEvent extends EventObject {

    protected int eventType;

    
    public TrussEvent(Object source, int eventType) {
        super(source);
        this.eventType = eventType;
    }

    public int getEventType() {
        return eventType;
    }


}
