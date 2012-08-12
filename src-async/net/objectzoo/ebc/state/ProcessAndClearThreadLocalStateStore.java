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
package net.objectzoo.ebc.state;

import net.objectzoo.ebc.impl.ProcessAndResultBase;

/**
 * This class implements a decorator EBC that simply forwards the processing to the next component
 * in the flow and clears the {@link ThreadLocalStateStore} after the flows execution. This only
 * works for the current synchronously executing part of the flow and thus must be introduced in the
 * beginning of every part of the flow that is executed asynchronously.
 * 
 * @author tilmann
 * 
 * @param <T>
 *        the process parameter type of the flow
 */
public class ProcessAndClearThreadLocalStateStore<T> extends ProcessAndResultBase<T, T>
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void process(T parameter)
	{
		try
		{
			sendResult(parameter);
		}
		finally
		{
			ThreadLocalStateStore.clear();
		}
	}
	
}
