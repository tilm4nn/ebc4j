package net.objectzoo.ebc.context;

public class ThreadFlowContextProvider implements FlowContextProvider
{
	private ThreadLocal<FlowContext> flowContext = new ThreadLocal<FlowContext>();
	
	public void setupContextForCurrentThread()
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
	
	FlowContext replaceContextForCurrentThread()
	{
		FlowContext currentFlowContext = flowContext.get();
		flowContext.set(new FlowContext());
		return currentFlowContext;
	}
	
	void restoreContextForCurrentThread(FlowContext previousContext)
	{
		if (previousContext == null)
		{
			flowContext.remove();
		}
		else
		{
			flowContext.set(previousContext);
		}
	}
}
