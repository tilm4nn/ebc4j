package net.objectzoo.ebc.join;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import net.objectzoo.delegates.Action0;

@SuppressWarnings("javadoc")
public class JoinSignalsTest
{
	private JoinSignals sut = new JoinSignals();
	
	@Test
	public void sends_signal_for_both_starts()
	{
		Action0 mockAction = mock(Action0.class);
		sut.signalEvent().subscribe(mockAction);
		
		sut.startAction1().invoke();
		sut.startAction2().invoke();
		
		verify(mockAction).invoke();
	}
}