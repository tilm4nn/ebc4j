package net.objectzoo.ebc.join;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import net.objectzoo.delegates.Action;

@SuppressWarnings({ "rawtypes", "unchecked", "javadoc" })
public class JoinObjectAndCollectionTest
{
	private JoinObjectAndCollection sut = new JoinObjectAndCollection();
	
	@Test
	public void sends_result_after_both_inputs()
	{
		Action mockAction = mock(Action.class);
		JoinOutputCreator mockOutputCreator = mock(JoinOutputCreator.class);
		sut.resultEvent().subscribe(mockAction);
		sut.initOutputElementCreator(mockOutputCreator);
		when(mockOutputCreator.createOutput(1, "A")).thenReturn("out1");
		when(mockOutputCreator.createOutput(1, "B")).thenReturn("out2");
		
		sut.input1Action().invoke(1);
		sut.input2Action().invoke(asList("A", "B"));
		
		verify(mockAction).invoke(asList("out1", "out2"));
	}
}