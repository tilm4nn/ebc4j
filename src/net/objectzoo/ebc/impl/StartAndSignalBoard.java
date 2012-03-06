package net.objectzoo.ebc.impl;

import net.objectzoo.ebc.StartAndSignalFlow;
import net.objectzoo.events.Event0;
import net.objectzoo.events.impl.Event0Delegate;
import net.objectzoo.events.impl.Event0Distributor;

public abstract class StartAndSignalBoard extends StartBoard implements StartAndSignalFlow
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
