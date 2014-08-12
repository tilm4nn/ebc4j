package net.objectzoo.ebc.join;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hamcrest.Matcher;
import org.junit.Test;

@SuppressWarnings({ "rawtypes", "unchecked", "javadoc" })
public class ObjectAndCollectionOutputCreatorTest
{
	private JoinOutputCreator mockOutputCreator = mock(JoinOutputCreator.class);
	private ObjectAndCollectionOutputCreator sut = new ObjectAndCollectionOutputCreator(mockOutputCreator);
	
	@Test
	public void calls_output_creator_with_correct_values()
	{
		sut.createOutput("1", asList(1, 2));
		
		verify(mockOutputCreator).createOutput("1", 1);
		verify(mockOutputCreator).createOutput("1", 2);
	}
	
	@Test
	public void creates_List_with_returned_values()
	{
		when(mockOutputCreator.createOutput(anyString(), anyInt())).thenReturn("out1", "out2");
		
		List result = sut.createOutput("1", asList(1, 2));
		
		assertThat(result, (Matcher) is(asList("out1", "out2")));
	}
}