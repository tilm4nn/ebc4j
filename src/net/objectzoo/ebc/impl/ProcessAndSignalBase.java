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
package net.objectzoo.ebc.impl;

import net.objectzoo.ebc.CanProcess;
import net.objectzoo.ebc.SendsSignal;
import net.objectzoo.events.Event0;
import net.objectzoo.events.impl.Event0Delegate;

/**
 * A base class for an EBC that {@link CanProcess} and {@link SendsSignal}.
 * 
 * The implementation contains the boilerplate code required to implement the interfaces and
 * provides trace logging of action and event invocations.
 * 
 * @author tilmann
 * 
 * @param <ProcessParameter>
 *        the type of input processed by this EBC
 */
public abstract class ProcessAndSignalBase<ProcessParameter> extends ProcessBase<ProcessParameter> implements
	SendsSignal
{
	private final Event0Delegate signalEvent;
	
	/**
	 * Creates a new {@code SignalBase} that allows only a single subscriber to the signal event.
	 */
	public ProcessAndSignalBase()
	{
		this(false);
	}
	
	/**
	 * Creates a new {@code SignalBase}.
	 * 
	 * @param mutliSubscription
	 *        defines if multiple subscribers are allowed for the signal event
	 */
	public ProcessAndSignalBase(boolean mutliSubscription)
	{
		this(EventDelegateFactory.createEvent0Delegate(mutliSubscription));
	}
	
	/**
	 * Creates a new {@code SignalBase}.
	 * 
	 * @param signalEventDelegate
	 *        uses the given event delegate to implement the signal event
	 */
	public ProcessAndSignalBase(Event0Delegate signalEventDelegate)
	{
		if (signalEventDelegate == null)
		{
			throw new IllegalArgumentException("signalEventDelegate=null");
		}
		
		this.signalEvent = signalEventDelegate;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Event0 signalEvent()
	{
		return signalEvent;
	}
	
	/**
	 * This method can be used by subclasses to send the signal.
	 */
	protected void sendSignal()
	{
		logger.log(logLevel, "sending singal");
		
		signalEvent.invoke();
	}
}
