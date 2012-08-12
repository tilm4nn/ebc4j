package net.objectzoo.ebc.join;

import org.junit.After;
import org.junit.Before;

import net.objectzoo.ebc.state.ThreadLocalStateFactory;
import net.objectzoo.ebc.state.ThreadLocalStateStore;

@SuppressWarnings("javadoc")
public class JoinWithThreadLocalStateFactoryTest extends JoinTest
{
	@Before
	public void setupStateStore()
	{
		this.stateFactory = new ThreadLocalStateFactory();
	}
	
	@After
	public void clearStateStore()
	{
		ThreadLocalStateStore.clear();
	}
}
