package net.objectzoo.ebc.builder;

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.CanProcess;
import net.objectzoo.ebc.CanStart;
import net.objectzoo.ebc.ProcessAndResultFlow;
import net.objectzoo.ebc.ProcessAndSignalFlow;
import net.objectzoo.ebc.SendsResult;
import net.objectzoo.ebc.StartAndResultFlow;
import net.objectzoo.ebc.StartAndSignalFlow;
import net.objectzoo.ebc.adapters.Action0ToAction;
import net.objectzoo.ebc.split.SplitProcess;
import net.objectzoo.events.Event;

public class ResultConnector<T>
{
	private Event<T> event;
	
	ResultConnector(SendsResult<T> flow)
	{
		this(flow.resultEvent());
	}
	
	ResultConnector(Event<T> theEvent)
	{
		event = theEvent;
	}
	
	public <T2> ResultConnector<T2> then(ProcessAndResultFlow<? super T, T2> flow)
	{
		then((CanProcess<? super T>) flow);
		
		return new ResultConnector<T2>(flow);
	}
	
	public SignalConnector then(ProcessAndSignalFlow<? super T> flow)
	{
		then((CanProcess<? super T>) flow);
		
		return new SignalConnector(flow);
	}
	
	public void then(CanProcess<? super T> flow)
	{
		then(flow.processAction());
	}
	
	public void then(Action<? super T> action)
	{
		event.subscribe(action);
	}
	
	public <T2> ResultConnector<T2> then(StartAndResultFlow<T2> flow)
	{
		then((CanStart) flow);
		
		return new ResultConnector<T2>(flow);
	}
	
	public SignalConnector then(StartAndSignalFlow flow)
	{
		then((CanStart) flow);
		
		return new SignalConnector(flow);
	}
	
	public void then(CanStart flow)
	{
		then(flow.startAction());
	}
	
	public void then(Action0 action)
	{
		then(new Action0ToAction<T>(action));
	}
	
	public SplitProcess<T> thenSplit()
	{
		SplitProcess<T> split = new SplitProcess<T>();
		
		then(split);
		
		return split;
	}
}
