package net.objectzoo.ebc.join;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import net.objectzoo.delegates.Action;

@SuppressWarnings({ "rawtypes", "unchecked", "javadoc" })
public class JoinBaseTest
{
	private JoinOutputCreator mockOutputCreator = mock(JoinOutputCreator.class);
	private JoinBaseImpl sut = new JoinBaseImpl(mockOutputCreator);
	
	@Test
	public void calls_createOutput_with_correct_values()
	{
		sut.input1Action().accept(11);
		sut.input2Action().accept(21);
		
		verify(mockOutputCreator).createOutput(11, 21);
	}
	
	@Test
	public void sends_created_output()
	{
		Action resultAction = mock(Action.class);
		sut.resultEvent().subscribe(resultAction);
		when(mockOutputCreator.createOutput(anyObject(), anyObject())).thenReturn("out");
		
		sut.input1Action().accept(11);
		sut.input2Action().accept(21);
		
		verify(resultAction).accept("out");
	}
}