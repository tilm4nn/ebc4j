package net.objectzoo.ebc.state;

import net.objectzoo.ebc.impl.ProcessAndResultBase;

public class ProcessAndClearThreadLocalStateStore<T> extends ProcessAndResultBase<T, T>
{
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
