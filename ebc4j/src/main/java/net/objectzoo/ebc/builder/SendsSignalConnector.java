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

import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.CanStart;
import net.objectzoo.ebc.ProcessAndResultFlow;
import net.objectzoo.ebc.SendsSignal;
import net.objectzoo.ebc.StartAndResultFlow;
import net.objectzoo.ebc.StartAndSignalFlow;
import net.objectzoo.ebc.split.SplitSignal;
import net.objectzoo.events.Event0;

/**
 * Instances of this class allow the DSL like definition of EBC flows by attaching the next EBC to a
 * flow that ended with a {@link SendsSignal} or {@link Event0}.
 * 
 * @author tilmann
 */
public class SendsSignalConnector
{
	private Event0 event;
	
	SendsSignalConnector(SendsSignal flow)
	{
		this(flow.signalEvent());
	}
	
	SendsSignalConnector(Event0 theEvent)
	{
		event = theEvent;
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
	 * Attaches a {@link StartAndResultFlow} at the end of the current flow
	 * 
	 * @param flow
	 *        the {@link ProcessAndResultFlow} to be attached
	 * @return a {@link SendsResultConnector} to continue the flow creation with
	 */
	public <T> SendsResultConnector<T> then(StartAndResultFlow<T> flow)
	{
		then((CanStart) flow);
		
		return new SendsResultConnector<T>(flow);
	}
	
	/**
	 * Attaches a {@link CanStart} at the end of the current flow
	 * 
	 * @param flow
	 *        the {@link CanStart} to be attached
	 */
	public NewFlowConnector then(CanStart flow)
	{
		return then(flow.startAction());
	}
	
	/**
	 * Attaches an {@link Action0} at the end of the current flow
	 * 
	 * @param action
	 *        the {@link Action0} to be attached
	 */
	public NewFlowConnector then(Action0 action)
	{
		event.subscribe(action);
		
		return new NewFlowConnector();
	}
	
	/**
	 * Attaches and returns a new {@link SplitSignal} to the current flow
	 * 
	 * @return the newly created {@link SplitSignal}
	 */
	public SplitSignal thenSplit()
	{
		SplitSignal split = new SplitSignal();
		
		then(split);
		
		return split;
	}
}
