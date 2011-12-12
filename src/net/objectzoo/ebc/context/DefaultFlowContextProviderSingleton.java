package net.objectzoo.ebc.context;

public class DefaultFlowContextProviderSingleton
{
	private static FlowContextProvider flowContextProvider = new BasicFlowContextProvider();
	
	private static boolean requested = false;
	
	private DefaultFlowContextProviderSingleton()
	{
		// Make constructor private
	}
	
	public static final FlowContextProvider getInstance()
	{
		requested = true;
		return flowContextProvider;
	}
	
	public static final void setInstance(FlowContextProvider defaultProvider)
	{
		if (requested)
		{
			throw new IllegalStateException(
				"The default flow context provider can only be set before it has been retrieved.");
		}
		flowContextProvider = defaultProvider;
	}
}
