package net.objectzoo.ebc.adapters;

import java.util.HashMap;
import java.util.Map;

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;
import net.objectzoo.events.Event;
import net.objectzoo.events.Event0;

public class EventToEvent0<T> implements Event0
{
	private Event<T> event;
	
	private Map<Action0, Action<T>> mappedActions = new HashMap<Action0, Action<T>>();
	
	public EventToEvent0(Event<T> event)
	{
		this.event = event;
	}
	
	@Override
	public void subscribe(Action0 action) throws IllegalArgumentException
	{
		Action<T> mappedAction = getOrCreateMappedAction(action);
		
		event.subscribe(mappedAction);
		
		mappedActions.put(action, mappedAction);
	}
	
	@Override
	public void unsubscribe(Action0 action) throws IllegalArgumentException
	{
		Action<T> mappedAction = getOrCreateMappedAction(action);
		
		event.unsubscribe(mappedAction);
		
		mappedActions.remove(action);
	}
	
	private Action<T> getOrCreateMappedAction(Action0 action)
	{
		Action<T> mappedAction = mappedActions.get(action);
		if (mappedAction == null)
		{
			mappedAction = new Action0ToAction<T>(action);
		}
		return mappedAction;
	}
	
}
