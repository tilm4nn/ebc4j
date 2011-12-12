package net.objectzoo.ebc.context;

import net.objectzoo.ebc.context.FlowContext;
import net.objectzoo.ebc.context.FlowContextProvider;

public class ThreadLocalFlowContextProvider implements FlowContextProvider
{
	private ThreadLocal<FlowContext> flowContext = new ThreadLocal<FlowContext>();
	
	public void createContextForCurrentThread()
	{
		FlowContext context = flowContext.get();
		if (context != null)
		{
			throw new IllegalStateException("The current flow already has a context associated with it.");
		}
		flowContext.set(new FlowContext());
	}
	
	public void removeContextForCurrentThread()
	{
		flowContext.remove();
	}
	
	public FlowContext getContext()
	{
		FlowContext context = flowContext.get();
		if (context == null)
		{
			throw new IllegalStateException("The current flow has no context associated with it.");
		}
		return context;
	}
}
