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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.test.MockAction0;
import net.objectzoo.events.impl.Event0Delegate;

@SuppressWarnings("javadoc")
public class StartAndSignalBaseTest
{
	static class StartAndSignalImpl extends StartAndSignalBase
	{
		boolean called = false;
		
		@Override
		protected void start()
		{
			if (called)
			{
				fail("start has already been called");
			}
			else
			{
				called = true;
			}
		}
	}
	
	static class TestEvent0Delegate implements Event0Delegate
	{
		boolean called = false;
		
		@Override
		public void invoke()
		{
			if (called)
			{
				Assert.fail("invoke has already been called");
			}
			else
			{
				called = true;
			}
		}
		
		@Override
		public void subscribe(Action0 arg0) throws IllegalArgumentException
		{
			// Not testing this
		}
		
		@Override
		public void unsubscribe(Action0 arg0) throws IllegalArgumentException
		{
			// Not testing this
		}
		
	}
	
	@Test
	public void start_action_invoke_calls_start()
	{
		StartAndSignalImpl sut = new StartAndSignalImpl();
		
		sut.startAction().invoke();
		
		assertTrue("start has not been called", sut.called);
	}
	
	@Test
	public void sendSignal_sends_signal_event()
	{
		StartAndSignalImpl sut = new StartAndSignalImpl();
		MockAction0 result = new MockAction0();
		sut.signalEvent().subscribe(result);
		
		sut.sendSignal();
		
		result.assertInvoked("signal event has not been send.");
	}
}
