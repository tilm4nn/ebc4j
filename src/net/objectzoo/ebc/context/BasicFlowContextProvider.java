package net.objectzoo.ebc.context;

public class BasicFlowContextProvider implements FlowContextProvider
{
	private final FlowContext context = new FlowContext();
	
	@Override
	public FlowContext getContext()
	{
		return context;
	}
	
}
