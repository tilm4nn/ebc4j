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
import net.objectzoo.ebc.ProcessAndResultFlow;
import net.objectzoo.ebc.SendsResult;
import net.objectzoo.ebc.util.LoggingUtils;
import net.objectzoo.events.Event;
import net.objectzoo.events.impl.EventDelegate;

/**
 * A base class for an EBC that {@link CanProcess} and {@link SendsResult}.
 * 
 * The implementation contains the boilerplate code required to implement the interfaces and
 * provides trace logging of action and event invocations.
 * 
 * @author tilmann
 * 
 * @param <ProcessParameter>
 *        the type of input processed by this EBC
 * @param <ResultParameter>
 *        the type of output of this EBC
 */
public abstract class ProcessAndResultBase<ProcessParameter, ResultParameter> extends
	ProcessBase<ProcessParameter> implements ProcessAndResultFlow<ProcessParameter, ResultParameter>
{
	private final EventDelegate<ResultParameter> resultEvent;
	
	/**
	 * Creates a new {@code ProcessAndResultBase} that allows only a single subscriber to the result
	 * event.
	 */
	public ProcessAndResultBase()
	{
		this(false);
	}
	
	/**
	 * Creates a new {@code ProcessAndResultBase}.
	 * 
	 * @param mutliSubscription
	 *        defines if multiple subscribers are allowed for the result event
	 */
	public ProcessAndResultBase(boolean mutliSubscription)
	{
		this(EventDelegateFactory.<ResultParameter> createEventDelegate(mutliSubscription));
	}
	
	/**
	 * Creates a new {@code ProcessAndResultBase}.
	 * 
	 * @param resultEventDelegate
	 *        uses the given event delegate to implement the result event
	 */
	public ProcessAndResultBase(EventDelegate<ResultParameter> resultEventDelegate)
	{
		if (resultEventDelegate == null)
		{
			throw new IllegalArgumentException("resultEventDelegate=null");
		}
		
		this.resultEvent = resultEventDelegate;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Event<ResultParameter> resultEvent()
	{
		return resultEvent;
	}
	
	/**
	 * This method can be used by subclasses to send the result.
	 * 
	 * @param parameter
	 *        the parameter value for the invocation
	 */
	protected void sendResult(ResultParameter parameter)
	{
		LoggingUtils.log(logger, logLevel, "sending result: ", parameter);
		
		resultEvent.invoke(parameter);
	}
}
