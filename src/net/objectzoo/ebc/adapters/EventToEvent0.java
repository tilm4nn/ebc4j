package net.objectzoo.ebc.adapters;

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;
import net.objectzoo.events.Event;
import net.objectzoo.events.Event0;

public class EventToEvent0<T> implements Event0
{
	private Event<T> event;
	
	public EventToEvent0(Event<T> event)
	{
		this.event = event;
	}
	
	@Override
	public void subscribe(Action0 action) throws IllegalArgumentException
	{
		Action<T> mappedAction = createMappedAction(action);
		
		event.subscribe(mappedAction);
	}
	
	@Override
	public void unsubscribe(Action0 action) throws IllegalArgumentException
	{
		Action<T> mappedAction = createMappedAction(action);
		
		event.unsubscribe(mappedAction);
	}
	
	private Action<T> createMappedAction(Action0 action)
	{
		return new Action0ToAction<T>(action);
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		EventToEvent0 other = (EventToEvent0) obj;
		if (event == null)
		{
			if (other.event != null) return false;
		}
		else if (!event.equals(other.event)) return false;
		return true;
	}
	
}
