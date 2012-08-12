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

import java.util.Map;
import java.util.WeakHashMap;

/**
 * The {@code ThreadLocalStateStore} has the responsibility to save the state values per
 * {@link ThreadLocalState} and per {@link Thread}. It does so by using a {@link ThreadLocal} that
 * stores a {@link WeakHashMap} mapping between the {@link ThreadLocalState}s and their values. The
 * {@link WeakHashMap} is choosen so that EBCs/{@link ThreadLocalState}s that are garbage collected
 * automatically free their state values and do not leave memory leaking.
 * 
 * @author tilmann
 */
public final class ThreadLocalStateStore
{
	private static final ThreadLocal<Map<ThreadLocalState<?>, Object>> stateStores = new ThreadLocal<Map<ThreadLocalState<?>, Object>>();
	
	private ThreadLocalStateStore()
	{
		// No instances will be made
	}
	
	static Map<ThreadLocalState<?>, Object> get()
	{
		Map<ThreadLocalState<?>, Object> stateStore = stateStores.get();
		if (stateStore == null)
		{
			stateStore = new WeakHashMap<ThreadLocalState<?>, Object>();
			stateStores.set(stateStore);
		}
		return stateStore;
	}
	
	/**
	 * Clears all state saved in this store bound to the current executing thread
	 */
	public static void clear()
	{
		stateStores.remove();
	}
}
