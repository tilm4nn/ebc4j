package net.objectzoo.ebc.impl;

import net.objectzoo.ebc.ProcessAndSignalFlow;
import net.objectzoo.events.Event0;
import net.objectzoo.events.impl.Event0Delegate;
import net.objectzoo.events.impl.Event0Distributor;

public abstract class ProcessAndSignalBoard<ProcessParameter> extends ProcessBoard<ProcessParameter> implements
	ProcessAndSignalFlow<ProcessParameter>
{
	protected final Event0Delegate signalEvent = new Event0Distributor();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Event0 signalEvent()
	{
		return signalEvent;
	}
}
