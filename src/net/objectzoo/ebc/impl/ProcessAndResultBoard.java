package net.objectzoo.ebc.impl;

import net.objectzoo.ebc.ProcessAndResultFlow;
import net.objectzoo.events.Event;
import net.objectzoo.events.impl.EventDelegate;
import net.objectzoo.events.impl.EventDistributor;

public abstract class ProcessAndResultBoard<ProcessParameter, ResultParameter> extends
	ProcessBoard<ProcessParameter> implements ProcessAndResultFlow<ProcessParameter, ResultParameter>
{
	protected final EventDelegate<ResultParameter> resultEvent = new EventDistributor<ResultParameter>();
	
	@Override
	public Event<ResultParameter> resultEvent()
	{
		return resultEvent;
	}
	
}
