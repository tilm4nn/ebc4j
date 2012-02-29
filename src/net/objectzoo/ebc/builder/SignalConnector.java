package net.objectzoo.ebc.builder;

import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.CanStart;
import net.objectzoo.ebc.SendsSignal;
import net.objectzoo.ebc.StartAndResultFlow;
import net.objectzoo.ebc.StartAndSignalFlow;
import net.objectzoo.ebc.split.SplitSignal;
import net.objectzoo.events.Event0;

public class SignalConnector
{
	private Event0 event;
	
	SignalConnector(Event0 theEvent)
	{
		event = theEvent;
	}
	
	SignalConnector(SendsSignal flow)
	{
		event = flow.signalEvent();
	}
	
	public static SignalConnector await(Event0 event)
	{
		return new SignalConnector(event);
	}
	
	public static SignalConnector await(SendsSignal flow)
	{
		return new SignalConnector(flow);
	}
	
	public SignalConnector then(StartAndSignalFlow flow)
	{
		then((CanStart) flow);
		
		return new SignalConnector(flow);
	}
	
	public <T> ResultConnector<T> then(StartAndResultFlow<T> flow)
	{
		then((CanStart) flow);
		
		return new ResultConnector<T>(flow);
	}
	
	public void then(CanStart flow)
	{
		then(flow.startAction());
	}
	
	public void then(Action0 action)
	{
		event.subscribe(action);
	}
	
	public SplitSignal thenSplit()
	{
		SplitSignal split = new SplitSignal();
		
		then(split);
		
		return split;
	}
}
