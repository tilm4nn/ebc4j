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

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TestActionTest
{
	@Test
	public void isInvoked_returns_false()
	{
		MockAction<String> sut = new MockAction<String>();
		
		assertThat(sut.isInvoked(), is(false));
	}
	
	@Test
	public void isInvoked_returns_true()
	{
		MockAction<String> sut = new MockAction<String>();
		
		sut.invoke("Eins");
		
		assertThat(sut.isInvoked(), is(true));
	}
	
	@Test
	public void getInvocationCount_returns_zero()
	{
		MockAction<String> sut = new MockAction<String>();
		
		assertThat(sut.getInvocationCount(), is(0));
	}
	
	@Test
	public void getInvocationCount_returns_three()
	{
		MockAction<String> sut = new MockAction<String>(100);
		
		sut.invoke("Eins");
		sut.invoke("Zwei");
		sut.invoke("Drei");
		
		assertThat(sut.getInvocationCount(), is(3));
	}
	
	@Test()
	public void assertInvoked_completes_without_AssertionError()
	{
		MockAction<String> sut = new MockAction<String>();
		
		sut.invoke("Eins");
		
		sut.assertInvoked();
	}
	
	@Test(expected = AssertionError.class)
	public void assertInvoked_throws_AssertionError()
	{
		MockAction<String> sut = new MockAction<String>();
		
		sut.assertInvoked();
	}
	
	@Test()
	public void assertInvoked_throws_AssertionError_with_given_message()
	{
		MockAction<String> sut = new MockAction<String>();
		
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
		MockAction<String> sut = new MockAction<String>();
		
		sut.invoke("Eins");
		sut.invoke("Zwei");
	}
	
	@Test
	public void maxAssertionCount_can_be_set()
	{
		MockAction<String> sut = new MockAction<String>(2);
		
		sut.invoke("Eins");
		sut.invoke("Zwei");
	}
	
	@Test(expected = AssertionError.class)
	public void maxAssertionCount_can_be_set_and_violated()
	{
		MockAction<String> sut = new MockAction<String>(2);
		
		sut.invoke("Eins");
		sut.invoke("Zwei");
		sut.invoke("Drei");
	}
	
	@Test
	public void getLastResult_returns_last_result()
	{
		MockAction<String> sut = new MockAction<String>(3);
		
		sut.invoke("Eins");
		sut.invoke("Zwei");
		sut.invoke("Drei");
		
		assertThat(sut.getLastResult(), is("Drei"));
	}
	
	@Test(expected = AssertionError.class)
	public void getLastResult_throws_AssertionError_when_not_invoked()
	{
		MockAction<String> sut = new MockAction<String>();
		
		sut.getLastResult();
	}
	
	@Test
	public void getFirstResult_returns_first_result()
	{
		MockAction<String> sut = new MockAction<String>(3);
		
		sut.invoke("Eins");
		sut.invoke("Zwei");
		sut.invoke("Drei");
		
		assertThat(sut.getFirstResult(), is("Eins"));
	}
	
	@Test(expected = AssertionError.class)
	public void getFirstResult_throws_AssertionError_when_not_invoked()
	{
		MockAction<String> sut = new MockAction<String>();
		
		sut.getFirstResult();
	}
	
	@Test
	public void getResults_returns_results_in_correct_order()
	{
		MockAction<String> sut = new MockAction<String>(3);
		
		sut.invoke("Eins");
		sut.invoke("Zwei");
		sut.invoke("Drei");
		
		assertThat(sut.getResults(), is(asList("Eins", "Zwei", "Drei")));
	}
}
