package net.objectzoo.ebc.builder;

import net.objectzoo.ebc.SendsResult;
import net.objectzoo.ebc.SendsSignal;
import net.objectzoo.events.Event;
import net.objectzoo.events.Event0;

public abstract class Flow
{
	private Flow()
	{
		
	}
	
	public static <T> ResultConnector<T> await(Event<T> event)
	{
		return new ResultConnector<T>(event);
	}
	
	public static <T> ResultConnector<T> await(SendsResult<T> flow)
	{
		return new ResultConnector<T>(flow);
	}
	
	public static SignalConnector await(Event0 event)
	{
		return new SignalConnector(event);
	}
	
	public static SignalConnector await(SendsSignal flow)
	{
		return new SignalConnector(flow);
	}
}
