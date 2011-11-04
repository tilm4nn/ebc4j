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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Assert;
import org.junit.Test;

import net.objectzoo.delegates.Action;
import net.objectzoo.ebc.test.MockAction;
import net.objectzoo.events.impl.EventDelegate;

public class ResultBaseTest
{
	static class ResultImpl extends ResultBase<String>
	{
		public ResultImpl()
		{
			
		}
		
		public ResultImpl(EventDelegate<String> delegate)
		{
			super(delegate);
		}
		
		boolean called = false;
	}
	
	static class TestEventDelegate implements EventDelegate<String>
	{
		String result = null;
		
		@Override
		public void invoke(String param)
		{
			if (result != null)
			{
				Assert.fail("invoke has already been called");
			}
			else
			{
				result = param;
			}
		}
		
		@Override
		public void subscribe(Action<? super String> arg0) throws IllegalArgumentException
		{
			// Not testing this
		}
		
		@Override
		public void unsubscribe(Action<? super String> arg0) throws IllegalArgumentException
		{
			// Not testing this
		}
		
	}
	
	@Test
	public void sendResult_sends_result_event()
	{
		ResultImpl sut = new ResultImpl();
		MockAction<String> result = new MockAction<String>();
		sut.resultEvent().subscribe(result);
		
		sut.sendResult("FooBar");
		
		assertThat(result.getLastResult(), is("FooBar"));
	}
	
	@Test
	public void sendResult_invokes_given_delegate()
	{
		TestEventDelegate testDelegate = new TestEventDelegate();
		ResultImpl sut = new ResultImpl(testDelegate);
		
		sut.sendResult("FooBar");
		
		assertThat(testDelegate.result, is("FooBar"));
	}
	
}
