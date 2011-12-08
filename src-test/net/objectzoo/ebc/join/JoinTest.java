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
package net.objectzoo.ebc.join;

import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.concurrent.atomic.AtomicInteger;

import net.objectzoo.ebc.test.MockAction;

import org.junit.Test;

public class JoinTest
{
	@Test
	public void waits_for_input1_to_continue()
	{
		Join<Object, Object, Object> sut = new Join<Object, Object, Object>(
			new JoinOutputCreator<Object, Object, Object>()
			{
				@Override
				public Object createOutput(Object input1, Object input2)
				{
					fail("Did not wait for input 1");
					return null;
				}
			}, null, false);
		
		sut.input2Action().invoke(new Object());
		sut.input2Action().invoke(new Object());
		sut.input2Action().invoke(new Object());
	}
	
	@Test
	public void waits_for_input2_to_continue()
	{
		Join<Object, Object, Object> sut = new Join<Object, Object, Object>(
			new JoinOutputCreator<Object, Object, Object>()
			{
				@Override
				public Object createOutput(Object input1, Object input2)
				{
					fail("Did not wait for input 2");
					return null;
				}
			}, null, false);
		
		sut.input1Action().invoke(new Object());
		sut.input1Action().invoke(new Object());
		sut.input1Action().invoke(new Object());
	}
	
	@Test
	public void reset_resets_input1()
	{
		Join<Object, Object, Object> sut = new Join<Object, Object, Object>(null, false);
		
		sut.setOutputCreator(new JoinOutputCreator<Object, Object, Object>()
		{
			@Override
			public Object createOutput(Object input1, Object input2)
			{
				fail("Did not reset input 1");
				return null;
			}
		});
		
		sut.input1Action().invoke(new Object());
		sut.resetAction().invoke();
		sut.input2Action().invoke(new Object());
	}
	
	@Test
	public void reset_resets_input2()
	{
		Join<Object, Object, Object> sut = new Join<Object, Object, Object>(null, false);
		
		sut.setOutputCreator(new JoinOutputCreator<Object, Object, Object>()
		{
			@Override
			public Object createOutput(Object input1, Object input2)
			{
				fail("Did not reset input 2");
				return null;
			}
		});
		
		sut.input2Action().invoke(new Object());
		sut.resetAction().invoke();
		sut.input1Action().invoke(new Object());
	}
	
	@Test
	public void reset_after_result_resets_input1()
	{
		Join<Object, Object, Object> sut = new Join<Object, Object, Object>(null, true);
		
		sut.setOutputCreator(new JoinOutputCreator<Object, Object, Object>()
		{
			boolean called = false;
			
			@Override
			public Object createOutput(Object input1, Object input2)
			{
				if (called)
				{
					fail("Did not reset input 1");
				}
				
				called = true;
				return null;
			}
		});
		
		sut.input1Action().invoke(new Object());
		sut.input2Action().invoke(new Object());
		sut.input2Action().invoke(new Object());
	}
	
	@Test
	public void reset_after_result_resets_input2()
	{
		Join<Object, Object, Object> sut = new Join<Object, Object, Object>(null, true);
		
		sut.setOutputCreator(new JoinOutputCreator<Object, Object, Object>()
		{
			boolean called = false;
			
			@Override
			public Object createOutput(Object input1, Object input2)
			{
				if (called)
				{
					fail("Did not reset input 2");
				}
				
				called = true;
				return null;
			}
		});
		
		sut.input2Action().invoke(new Object());
		sut.input1Action().invoke(new Object());
		sut.input1Action().invoke(new Object());
	}
	
	@Test
	public void sends_multiple_results_for_changing_input2()
	{
		Join<Object, Object, Object> sut = new Join<Object, Object, Object>(null, false);
		
		final AtomicInteger calls = new AtomicInteger(0);
		
		sut.setOutputCreator(new JoinOutputCreator<Object, Object, Object>()
		{
			@Override
			public Object createOutput(Object input1, Object input2)
			{
				calls.incrementAndGet();
				return null;
			}
		});
		
		sut.input1Action().invoke(new Object());
		sut.input2Action().invoke(new Object());
		sut.input2Action().invoke(new Object());
		sut.input2Action().invoke(new Object());
		
		assertThat(calls.get(), is(3));
	}
	
	@Test
	public void sends_multiple_results_for_changing_input1()
	{
		Join<Object, Object, Object> sut = new Join<Object, Object, Object>(null, false);
		
		final AtomicInteger calls = new AtomicInteger(0);
		
		sut.setOutputCreator(new JoinOutputCreator<Object, Object, Object>()
		{
			@Override
			public Object createOutput(Object input1, Object input2)
			{
				calls.incrementAndGet();
				return null;
			}
		});
		
		sut.input2Action().invoke(new Object());
		sut.input1Action().invoke(new Object());
		sut.input1Action().invoke(new Object());
		sut.input1Action().invoke(new Object());
		
		assertThat(calls.get(), is(3));
	}
	
	@Test
	public void creates_output_for_last_input1()
	{
		Join<Object, Object, Object> sut = new Join<Object, Object, Object>(null, false);
		final Object input1 = new Object();
		
		sut.setOutputCreator(new JoinOutputCreator<Object, Object, Object>()
		{
			@Override
			public Object createOutput(Object input1, Object input2)
			{
				assertThat(input1, is(sameInstance(input1)));
				return null;
			}
		});
		
		sut.input1Action().invoke(new Object());
		sut.input1Action().invoke(input1);
		sut.input2Action().invoke(new Object());
	}
	
	@Test
	public void creates_output_for_last_input2()
	{
		Join<Object, Object, Object> sut = new Join<Object, Object, Object>(null, false);
		final Object input2 = new Object();
		
		sut.setOutputCreator(new JoinOutputCreator<Object, Object, Object>()
		{
			@Override
			public Object createOutput(Object input1, Object input2)
			{
				assertThat(input2, is(sameInstance(input2)));
				return null;
			}
		});
		
		sut.input2Action().invoke(new Object());
		sut.input2Action().invoke(input2);
		sut.input1Action().invoke(new Object());
	}
	
	@Test
	public void sends_created_output()
	{
		Join<Object, Object, Object> sut = new Join<Object, Object, Object>(null, false);
		final Object output = new Object();
		
		MockAction<Object> resultAction = new MockAction<Object>();
		
		sut.setOutputCreator(new JoinOutputCreator<Object, Object, Object>()
		{
			@Override
			public Object createOutput(Object input1, Object input2)
			{
				return output;
			}
		});
		
		sut.resultEvent().subscribe(resultAction);
		
		sut.input2Action().invoke(new Object());
		sut.input1Action().invoke(new Object());
		
		assertThat(resultAction.getLastResult(), is(sameInstance(output)));
	}
}
