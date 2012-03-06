package net.objectzoo.ebc.impl;

import net.objectzoo.ebc.StartAndResultFlow;
import net.objectzoo.events.Event;
import net.objectzoo.events.impl.EventDelegate;
import net.objectzoo.events.impl.EventDistributor;

public abstract class StartAndResultBoard<ResultParameter> extends StartBoard implements
	StartAndResultFlow<ResultParameter>
{
	protected final EventDelegate<ResultParameter> resultEvent = new EventDistributor<ResultParameter>();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Event<ResultParameter> resultEvent()
	{
		return resultEvent;
	}
}
