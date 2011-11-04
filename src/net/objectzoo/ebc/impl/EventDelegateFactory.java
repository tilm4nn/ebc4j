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

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;
import net.objectzoo.events.impl.Event0Caller;
import net.objectzoo.events.impl.Event0Delegate;
import net.objectzoo.events.impl.Event0Distributor;
import net.objectzoo.events.impl.EventCaller;
import net.objectzoo.events.impl.EventDelegate;
import net.objectzoo.events.impl.EventDistributor;

/**
 * This class is used internally by the EBC base classes implementations to create event delegates.
 * 
 * @author tilmann
 */
class EventDelegateFactory
{
	private EventDelegateFactory()
	{
		
	}
	
	/**
	 * Creates a new {@link Event0Delegate} that might be capable of multiple {@link Action0}
	 * subscription depending on the given parameter value.
	 * 
	 * @param mutliSubscription
	 *        determines if the returned delegate is capable of multiple subscription
	 * @return a newly created {@link Event0Delegate}
	 */
	static Event0Delegate createEvent0Delegate(boolean mutliSubscription)
	{
		if (mutliSubscription)
		{
			return new Event0Distributor();
		}
		else
		{
			return new Event0Caller();
		}
	}
	
	/**
	 * Creates a new {@link EventDelegate} that might be capable of multiple {@link Action}
	 * subscription depending on the given parameter value.
	 * 
	 * @param mutliSubscription
	 *        determines if the returned delegate is capable of multiple subscription
	 * @return a newly created {@link EventDelegate}
	 */
	static <ResultParameter> EventDelegate<ResultParameter> createEventDelegate(boolean mutliSubscription)
	{
		if (mutliSubscription)
		{
			return new EventDistributor<ResultParameter>();
		}
		else
		{
			return new EventCaller<ResultParameter>();
		}
	}
}
