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

import java.util.LinkedList;
import java.util.List;

import net.objectzoo.delegates.Action;

/**
 * The {@code MockAction} is an {@link Action} implementation that can be used in (unit) tests to
 * collect the results of a tested EBC.
 * 
 * The {@code MockAction} can be invoked as many times as the maximum invocations allowed value
 * given at construction time before throwing an {@link AssertionError}.
 * 
 * @param <T>
 *        The type of the {@code Action}'s parameter
 */
public class MockAction<T> implements Action<T>
{
	private int invocationCount = 0;
	
	private final int maxInvocationsAllowed;
	
	private List<T> results = new LinkedList<T>();
	
	/**
	 * Creates a new {@code MockAction} with a maximum invocations allowed value of one
	 */
	public MockAction()
	{
		this(1);
	}
	
	/**
	 * Creates a new {@code MockAction} with the given maximum invocations allowed value
	 * 
	 * @param maxInvocationsAllowed
	 *        the number of invocations allowed for this {@code MockAction}
	 */
	public MockAction(int maxInvocationsAllowed)
	{
		this.maxInvocationsAllowed = maxInvocationsAllowed;
	}
	
	/**
	 * The {@code invoke} method internally counts the number of invocations and collects the given
	 * parameters. Invocations are allowed as many times as the maximum invocation allowed parameter
	 * given at construction time specifies. Excessive calls lead to an {@link AssertionError} being
	 * thrown.
	 * 
	 * @param parameter
	 *        the parameter value for the invocation
	 * @throws AssertionError
	 *         if the maximum invocation allowed count has been exceeded
	 */
	@Override
	public synchronized void accept(T parameter)
	{
		if (++invocationCount > maxInvocationsAllowed)
		{
			throw new AssertionError("Unexpected invocation with " + parameter
				+ " exceeding max invocations allowed " + maxInvocationsAllowed
				+ ". Already invoked with " + results);
		}
		
		results.add(parameter);
	}
	
	/**
	 * Query if this {@code MockAction} has been invoked
	 * 
	 * @return {@code true} if it has been invoked and {@code false} otherwise
	 */
	public boolean isInvoked()
	{
		return invocationCount > 0;
	}
	
	/**
	 * Retrieve the number of invocations that have been conducted on this {@code MockAction}
	 * 
	 * @return the number of invocations
	 */
	public int getInvocationCount()
	{
		return invocationCount;
	}
	
	/**
	 * Retrieve parameter value given to the last invocation of this {@code MockAction}. If no
	 * invocation has taken place so far an {@link AssertionError} is thrown.
	 * 
	 * @return the parameter value of the last invocation
	 * @throws AssertionError
	 *         if this {@code MockAction} has not been invoked yet
	 */
	public T getLastResult()
	{
		assertInvoked();
		return results.get(results.size() - 1);
	}
	
	/**
	 * Retrieve the parameter value given to the first invocation of this {@code MockAction}. If no
	 * invocation has taken place so far an {@link AssertionError} is thrown.
	 * 
	 * @return the parameter value of the first invocation
	 * @throws AssertionError
	 *         if this {@code MockAction} has not been invoked yet
	 */
	public T getFirstResult()
	{
		assertInvoked();
		return results.get(0);
	}
	
	/**
	 * Retrieve the parameter values of all invocations of this {@code MockAction} as a {@link List}
	 * .
	 * 
	 * @return the parameter values in the order of the invocations or an empty {@link List} if this
	 *         {@code MockAction} has not been invoked yet
	 */
	public List<T> getResults()
	{
		return results;
	}
	
	/**
	 * Assert that this {@code MockAction} has been invoked throwing an {@link AssertionError} if
	 * not.
	 * 
	 * @throws AssertionError
	 *         if this {@code MockAction} has not been invoked yet
	 */
	public void assertInvoked()
	{
		assertInvoked("This result action has not been invoked like expected.");
	}
	
	/**
	 * Assert that this {@code MockAction} has been invoked throwing an {@link AssertionError} with
	 * the given message if not.
	 * 
	 * @param message
	 *        the message for the thrown {@link AssertionError}
	 * 
	 * @throws AssertionError
	 *         if this {@code MockAction} has not been invoked yet
	 */
	public void assertInvoked(String message)
	{
		if (!isInvoked())
		{
			throw new AssertionError(message);
		}
	}
}
