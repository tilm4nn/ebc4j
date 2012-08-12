/*
 * The MIT License
 * 
 * Copyright (C) 2011 Tilmann Kuhn
 * 
 * http://www.object-zoo.net
 * 
 * mailto:ebc4j@object-zoo.net
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package net.objectzoo.ebc.adapters;

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;
import net.objectzoo.events.Event;
import net.objectzoo.events.Event0;

/**
 * Adapts an {@link Event} to provide the {@link Event0} interface. The implementations just adapts
 * all subscribed {@link Action0} instances with the {@link Action0ToAction} adapter.
 * 
 * @author tilmann
 * 
 * @param <T>
 *        The {@link Event}'s parameter type
 */
public class EventToEvent0<T> implements Event0
{
	private Event<T> event;
	
	/**
	 * Creates a new {@code EventToEvent0} adapting the given {@link Event} instance
	 * 
	 * @param event
	 *        the {@link Event} to be adapted
	 */
	public EventToEvent0(Event<T> event)
	{
		this.event = event;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void subscribe(Action0 action) throws IllegalArgumentException
	{
		Action<T> mappedAction = createMappedAction(action);
		
		event.subscribe(mappedAction);
	}
	
	/**
	 * {@inheritDoc}
	 */
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
		EventToEvent0<?> other = (EventToEvent0<?>) obj;
		if (event == null)
		{
			if (other.event != null) return false;
		}
		else if (!event.equals(other.event)) return false;
		return true;
	}
	
}
