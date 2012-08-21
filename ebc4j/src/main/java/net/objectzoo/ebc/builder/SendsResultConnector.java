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
import net.objectzoo.ebc.split.SplitProcessToSignal;
import net.objectzoo.events.Event;

/**
 * Instances of this class allow the DSL like definition of EBC flows by attaching the next EBC to a
 * flow that ended with a {@link SendsResult} or {@link Event}.
 * 
 * @author tilmann
 * 
 * @param <T>
 *        the type of the result of the last step in the flow
 */
public class SendsResultConnector<T>
{
	private Event<T> event;
	
	SendsResultConnector(SendsResult<T> flow)
	{
		this(flow.resultEvent());
	}
	
	SendsResultConnector(Event<T> theEvent)
	{
		event = theEvent;
	}
	
	/**
	 * Attaches a {@link ProcessAndResultFlow} at the end of the current flow
	 * 
	 * @param flow
	 *        the {@link ProcessAndResultFlow} to be attached
	 * @return a {@link SendsResultConnector} to continue the flow creation with
	 */
	public <T2> SendsResultConnector<T2> then(ProcessAndResultFlow<? super T, T2> flow)
	{
		then((CanProcess<? super T>) flow);
		
		return new SendsResultConnector<T2>(flow);
	}
	
	/**
	 * Attaches a {@link ProcessAndSignalFlow} at the end of the current flow
	 * 
	 * @param flow
	 *        the {@link ProcessAndSignalFlow} to be attached
	 * @return a {@link SendsSignalConnector} to continue the flow creation with
	 */
	public SendsSignalConnector then(ProcessAndSignalFlow<? super T> flow)
	{
		then((CanProcess<? super T>) flow);
		
		return new SendsSignalConnector(flow);
	}
	
	/**
	 * Attaches a {@link CanProcess} at the end of the current flow
	 * 
	 * @param flow
	 *        the {@link CanProcess} to be attached
	 */
	public void then(CanProcess<? super T> flow)
	{
		then(flow.processAction());
	}
	
	/**
	 * Attaches an {@link Action} at the end of the current flow
	 * 
	 * @param action
	 *        the {@link Action} to be attached
	 */
	public void then(Action<? super T> action)
	{
		event.subscribe(action);
	}
	
	/**
	 * Attaches a {@link StartAndResultFlow} at the end of the current flow
	 * 
	 * @param flow
	 *        the {@link ProcessAndResultFlow} to be attached
	 * @return a {@link SendsResultConnector} to continue the flow creation with
	 */
	public <T2> SendsResultConnector<T2> then(StartAndResultFlow<T2> flow)
	{
		then((CanStart) flow);
		
		return new SendsResultConnector<T2>(flow);
	}
	
	/**
	 * Attaches a {@link StartAndSignalFlow} at the end of the current flow
	 * 
	 * @param flow
	 *        the {@link StartAndSignalFlow} to be attached
	 * @return a {@link SendsSignalConnector} to continue the flow creation with
	 */
	public SendsSignalConnector then(StartAndSignalFlow flow)
	{
		then((CanStart) flow);
		
		return new SendsSignalConnector(flow);
	}
	
	/**
	 * Attaches a {@link CanStart} at the end of the current flow
	 * 
	 * @param flow
	 *        the {@link CanStart} to be attached
	 */
	public void then(CanStart flow)
	{
		then(flow.startAction());
	}
	
	/**
	 * Attaches an {@link Action0} at the end of the current flow
	 * 
	 * @param action
	 *        the {@link Action0} to be attached
	 */
	public void then(Action0 action)
	{
		then(new Action0ToAction<T>(action));
	}
	
	/**
	 * Attaches and returns a new {@link SplitProcess} to the current flow
	 * 
	 * @return the newly created {@link SplitProcess}
	 */
	public SplitProcess<T> thenSplit()
	{
		SplitProcess<T> split = new SplitProcess<T>();
		
		then(split);
		
		return split;
	}
	
	/**
	 * Attaches and returns a new {@link SplitProcessToSignal} to the current flow
	 * 
	 * @return the newly created {@link SplitProcessToSignal}
	 */
	public SplitProcessToSignal<T> thenSplitProcessToSignal()
	{
		SplitProcessToSignal<T> split = new SplitProcessToSignal<T>();
		
		then((CanProcess<T>) split);
		
		return split;
	}
}
