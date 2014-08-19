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

import net.objectzoo.ebc.SendsResult;
import net.objectzoo.ebc.SendsSignal;
import net.objectzoo.events.Event;
import net.objectzoo.events.Event0;

/**
 * This class contains some {@code static} methods used to continue a DSL sentence with a new flow
 * of EBCs
 * 
 * @author tilmann
 */
public final class NewFlowConnector
{
	NewFlowConnector()
	{
	}
	
	/**
	 * Waits for the given {@link Event} and then continues with whatever is attached to the
	 * returned {@link SendsResultConnector}.
	 * 
	 * @param event
	 *        the {@link Event} to be waited for
	 * @return an {@link SendsResultConnector} to continue the flow creation with
	 */
	public <T> SendsResultConnector<T> await(Event<T> event)
	{
		return Flow.await(event);
	}
	
	/**
	 * Waits for the result event of the given {@link SendsResult} and then continues with whatever
	 * is attached to the returned {@link SendsResultConnector}.
	 * 
	 * @param flow
	 *        the {@link SendsResult} to be waited for
	 * @return an {@link SendsResultConnector} to continue the flow creation with
	 */
	public <T> SendsResultConnector<T> await(SendsResult<T> flow)
	{
		return Flow.await(flow);
	}
	
	/**
	 * Waits for the given {@link Event0} and then continues with whatever is attached to the
	 * returned {@link SendsSignalConnector}.
	 * 
	 * @param event
	 *        the {@link Event0} to be waited for
	 * @return an {@link SendsSignalConnector} to continue the flow creation with
	 */
	public SendsSignalConnector await(Event0 event)
	{
		return Flow.await(event);
	}
	
	/**
	 * Waits for the signal event of the given {@link SendsSignal} and then continues with whatever
	 * is attached to the returned {@link SendsSignalConnector}.
	 * 
	 * @param flow
	 *        the {@link SendsSignal} to be waited for
	 * @return an {@link SendsSignalConnector} to continue the flow creation with
	 */
	public SendsSignalConnector await(SendsSignal flow)
	{
		return Flow.await(flow);
	}
}
