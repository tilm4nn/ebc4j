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
package net.objectzoo.ebc.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class TestAction0Test
{
	@Test
	public void isInvoked_returns_false()
	{
		MockAction0 sut = new MockAction0();
		
		assertThat(sut.isInvoked(), is(false));
	}
	
	@Test
	public void isInvoked_returns_true()
	{
		MockAction0 sut = new MockAction0();
		
		sut.invoke();
		
		assertThat(sut.isInvoked(), is(true));
	}
	
	@Test
	public void getInvocationCount_returns_zero()
	{
		MockAction0 sut = new MockAction0();
		
		assertThat(sut.getInvocationCount(), is(0));
	}
	
	@Test
	public void getInvocationCount_returns_three()
	{
		MockAction0 sut = new MockAction0(100);
		
		sut.invoke();
		sut.invoke();
		sut.invoke();
		
		assertThat(sut.getInvocationCount(), is(3));
	}
	
	@Test()
	public void assertInvoked_completes_without_AssertionError()
	{
		MockAction0 sut = new MockAction0();
		
		sut.invoke();
		
		sut.assertInvoked();
	}
	
	@Test(expected = AssertionError.class)
	public void assertInvoked_throws_AssertionError()
	{
		MockAction0 sut = new MockAction0();
		
		sut.assertInvoked();
	}
	
	@Test()
	public void assertInvoked_throws_AssertionError_with_given_message()
	{
		MockAction0 sut = new MockAction0();
		
		try
		{
			sut.assertInvoked("FooBar");
			
			fail("Should throw AssertionError");
		}
		catch (AssertionError e)
		{
			assertThat(e.getMessage(), is("FooBar"));
		}
	}
	
	@Test(expected = AssertionError.class)
	public void maxAssertionCount_violation_throws_AssertionError()
	{
		MockAction0 sut = new MockAction0();
		
		sut.invoke();
		sut.invoke();
	}
	
	@Test
	public void maxAssertionCount_can_be_set()
	{
		MockAction0 sut = new MockAction0(2);
		
		sut.invoke();
		sut.invoke();
	}
	
	@Test(expected = AssertionError.class)
	public void maxAssertionCount_can_be_set_and_violated()
	{
		MockAction0 sut = new MockAction0(2);
		
		sut.invoke();
		sut.invoke();
		sut.invoke();
	}
}
