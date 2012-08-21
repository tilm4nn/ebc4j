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

import net.objectzoo.delegates.Action0;

/**
 * The {@code MockAction0} is an {@link Action0} implementation that can be used in (unit) tests to
 * count the signals of a tested EBC.
 * 
 * The {@code MockAction0} can be invoked as many times as the maximum invocations allowed value
 * given at construction time before throwing an {@link AssertionError}.
 */
public class MockAction0 implements Action0
{
	private int invocationCount = 0;
	
	private final int maxInvocationsAllowed;
	
	/**
	 * Creates a new {@code MockAction0} with a maximum invocations allowed value of one
	 */
	public MockAction0()
	{
		this(1);
	}
	
	/**
	 * Creates a new {@code MockAction0} with the given maximum invocations allowed value
	 * 
	 * @param maxInvocationsAllowed
	 *        the number of invocations allowed for this {@code MockAction0}
	 */
	public MockAction0(int maxInvocationsAllowed)
	{
		this.maxInvocationsAllowed = maxInvocationsAllowed;
	}
	
	/**
	 * The {@code invoke} method internally counts the number of invocations. Invocations are
	 * allowed as many times as the maximum invocation allowed parameter given at construction time
	 * specifies. Excessive calls lead to an {@link AssertionError} being thrown.
	 * 
	 * @throws AssertionError
	 *         if the maximum invocation allowed count has been exceeded
	 */
	@Override
	public synchronized void invoke()
	{
		if (++invocationCount > maxInvocationsAllowed)
		{
			throw new AssertionError("Unexpected invocation exceeding max invocations allowed "
				+ maxInvocationsAllowed + ".");
		}
	}
	
	/**
	 * Query if this {@code MockAction0} has been invoked
	 * 
	 * @return {@code true} if it has been invoked and {@code false} otherwise
	 */
	public boolean isInvoked()
	{
		return invocationCount > 0;
	}
	
	/**
	 * Retrieve the number of invocations that have been conducted on this {@code MockAction0}
	 * 
	 * @return the number of invocations
	 */
	public int getInvocationCount()
	{
		return invocationCount;
	}
	
	/**
	 * Assert that this {@code MockAction0} has been invoked throwing an {@link AssertionError} if
	 * not.
	 * 
	 * @throws AssertionError
	 *         if this {@code MockAction0} has not been invoked yet
	 */
	public void assertInvoked()
	{
		assertInvoked("This result action has not been invoked like expected.");
	}
	
	/**
	 * Assert that this {@code MockAction0} has been invoked throwing an {@link AssertionError} with
	 * the given message if not.
	 * 
	 * @param message
	 *        the message for the thrown {@link AssertionError}
	 * 
	 * @throws AssertionError
	 *         if this {@code MockAction0} has not been invoked yet
	 */
	public void assertInvoked(String message)
	{
		if (!isInvoked())
		{
			throw new AssertionError(message);
		}
	}
	
}
