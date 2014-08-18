package net.objectzoo.ebc.join;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import net.objectzoo.delegates.Action;

@SuppressWarnings({ "rawtypes", "unchecked", "javadoc" })
public class JoinProcessAndSignalTest
{
	private JoinProcessAndSignal sut = new JoinProcessAndSignal();
	
	@Test
	public void sends_result_for_both_start_and_process()
	{
		Action mockAction = mock(Action.class);
		sut.resultEvent().subscribe(mockAction);
		
		sut.startAction().start();
		sut.processAction().accept("input");
		
		verify(mockAction).accept("input");
	}
}