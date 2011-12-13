package net.objectzoo.ebc.context;

import net.objectzoo.ebc.impl.ProcessAndResultBase;

public class ProcessWithNewThreadFlowContext<T> extends ProcessAndResultBase<T, T>
{
	private final ThreadFlowContextProvider threadFlowContextProvider;
	
	public ProcessWithNewThreadFlowContext(ThreadFlowContextProvider flowContextProvider)
	{
		this.threadFlowContextProvider = flowContextProvider;
	}
	
	@Override
	protected void process(T parameter)
	{
		FlowContext previousContext = threadFlowContextProvider.replaceContextForCurrentThread();
		try
		{
			sendResult(parameter);
		}
		finally
		{
			threadFlowContextProvider.restoreContextForCurrentThread(previousContext);
		}
	}
	
}
