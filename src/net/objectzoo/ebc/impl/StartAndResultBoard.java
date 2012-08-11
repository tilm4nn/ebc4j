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

import net.objectzoo.ebc.CanStart;
import net.objectzoo.ebc.SendsResult;
import net.objectzoo.ebc.StartAndResultFlow;
import net.objectzoo.events.Event;
import net.objectzoo.events.impl.EventDelegate;
import net.objectzoo.events.impl.EventDistributor;

/**
 * A base class for a board that {@link CanStart} and {@link SendsResult}.
 * 
 * The implementation contains the boilerplate code required to implement the interfaces and
 * provides {@link EventDelegate}s for action and event invocations.
 * 
 * @author tilmann
 * 
 * @param <ResultParameter>
 *        the type of output of this board
 */
public abstract class StartAndResultBoard<ResultParameter> extends StartBoard implements
	StartAndResultFlow<ResultParameter>
{
	/**
	 * This {@link EventDelegate} can be used by subclasses to send the result events
	 */
	protected final EventDelegate<ResultParameter> resultEvent = new EventDistributor<ResultParameter>();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Event<ResultParameter> resultEvent()
	{
		return resultEvent;
	}
}
