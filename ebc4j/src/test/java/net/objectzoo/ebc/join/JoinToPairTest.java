package net.objectzoo.ebc.join;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import net.objectzoo.delegates.Action;
import net.objectzoo.ebc.util.Pair;

@SuppressWarnings({ "rawtypes", "unchecked", "javadoc" })
public class JoinToPairTest
{
	private JoinToPair sut = new JoinToPair();
	
	@Test
	public void sends_Pair_with_values_from_invocations()
	{
		Action mockAction = mock(Action.class);
		sut.resultEvent().subscribe(mockAction);
		
		sut.input1Action().accept(1);
		sut.input2Action().accept(2);
		
		verify(mockAction).accept(new Pair(1, 2));
	}
}