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
package net.objectzoo.ebc.adapters;

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;

/**
 * Adapts an {@link Action0} to provide the {@link Action} interface. The implementations just
 * forwards any calls ignoring the given call parameter value.
 * 
 * @author tilmann
 * 
 * @param <T>
 *        The {@link Action}'s parameter type
 */
public class Action0ToAction<T> implements Action<T>
{
	private final Action0 action;
	
	/**
	 * Create a new {@code Action0ToAction} forwarding the incoming calls to the given
	 * {@link Action0} instance.
	 * 
	 * @param action
	 */
	public Action0ToAction(Action0 action)
	{
		this.action = action;
	}
	
	@Override
	public void invoke(T parameter)
	{
		action.invoke();
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Action0ToAction<?> other = (Action0ToAction<?>) obj;
		if (action == null)
		{
			if (other.action != null) return false;
		}
		else if (!action.equals(other.action)) return false;
		return true;
	}
	
}
