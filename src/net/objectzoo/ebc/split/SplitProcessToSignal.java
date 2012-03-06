package net.objectzoo.ebc.split;

import net.objectzoo.ebc.ProcessAndSignalFlow;
import net.objectzoo.ebc.impl.ProcessAndResultBase;
import net.objectzoo.events.Event0;
import net.objectzoo.events.impl.Event0Distributor;

/**
 * The {@code SplitProcess} distributes the process and its parameter to multiple regular
 * subscribers and to multiple signal subscribers.
 * 
 * @author tilmann
 * 
 * @param <Parameter>
 *        the type of the process parameter
 */
public class SplitProcessToSignal<Parameter> extends ProcessAndResultBase<Parameter, Parameter> implements
	ProcessAndSignalFlow<Parameter>
{
	private final Event0Distributor signalDistributor = new Event0Distributor();
	
	@Override
	protected void process(Parameter parameter)
	{
		sendResult(parameter);
		signalDistributor.invoke();
	}
	
	@Override
	public Event0 signalEvent()
	{
		return signalDistributor;
	}
}
