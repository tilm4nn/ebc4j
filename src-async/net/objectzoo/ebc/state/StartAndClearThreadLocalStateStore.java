package net.objectzoo.ebc.state;

import net.objectzoo.ebc.impl.StartAndSignalBase;

public class StartAndClearThreadLocalStateStore extends StartAndSignalBase
{
	@Override
	protected void start()
	{
		try
		{
			sendSignal();
		}
		finally
		{
			ThreadLocalStateStore.clear();
		}
	}
}
