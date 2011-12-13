package net.objectzoo.ebc.join;

import net.objectzoo.ebc.context.ThreadFlowContextProvider;

import org.junit.After;
import org.junit.Before;

public class JoinWithThreadLocalFlowContextProviderTest extends JoinTest
{
	private ThreadFlowContextProvider threadLocalFlowContextProvider;
	
	@Before
	public void setupFlowContext()
	{
		threadLocalFlowContextProvider = new ThreadFlowContextProvider();
		threadLocalFlowContextProvider.setupContextForCurrentThread();
		this.flowContextProvider = threadLocalFlowContextProvider;
	}
	
	@After
	public void clearFlowContext()
	{
		threadLocalFlowContextProvider.removeContextForCurrentThread();
	}
}
