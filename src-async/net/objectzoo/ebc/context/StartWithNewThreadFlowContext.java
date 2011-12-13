package net.objectzoo.ebc.context;

import net.objectzoo.ebc.impl.StartAndSignalBase;

public class StartWithNewThreadFlowContext extends StartAndSignalBase
{
	private final ThreadFlowContextProvider threadFlowContextProvider;
	
	public StartWithNewThreadFlowContext(ThreadFlowContextProvider flowContextProvider)
	{
		this.threadFlowContextProvider = flowContextProvider;
	}
	
	@Override
	protected void start()
	{
		FlowContext previousContext = threadFlowContextProvider.replaceContextForCurrentThread();
		try
		{
			sendSignal();
		}
		finally
		{
			threadFlowContextProvider.restoreContextForCurrentThread(previousContext);
		}
	}
	
}
