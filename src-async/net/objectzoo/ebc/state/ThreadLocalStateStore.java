package net.objectzoo.ebc.state;

import java.util.Map;
import java.util.WeakHashMap;

public abstract class ThreadLocalStateStore
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
	
	public static void clear()
	{
		stateStores.remove();
	}
}
