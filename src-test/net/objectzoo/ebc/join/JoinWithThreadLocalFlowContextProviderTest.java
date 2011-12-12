package net.objectzoo.ebc.join;

import net.objectzoo.ebc.context.ThreadLocalFlowContextProvider;

import org.junit.After;
import org.junit.Before;

public class JoinWithThreadLocalFlowContextProviderTest extends JoinTest
{
	private ThreadLocalFlowContextProvider threadLocalFlowContextProvider;
	
	@Before
	public void setupFlowContext()
	{
		threadLocalFlowContextProvider = new ThreadLocalFlowContextProvider();
		threadLocalFlowContextProvider.createContextForCurrentThread();
		this.flowContextProvider = threadLocalFlowContextProvider;
	}
	
	@After
	public void clearFlowContext()
	{
		threadLocalFlowContextProvider.removeContextForCurrentThread();
	}
}
