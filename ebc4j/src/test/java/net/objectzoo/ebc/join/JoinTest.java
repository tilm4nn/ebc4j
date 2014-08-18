package net.objectzoo.ebc.join;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import net.objectzoo.delegates.Action;
import net.objectzoo.ebc.state.BasicStateFactory;
import net.objectzoo.ebc.state.StateFactory;
import net.objectzoo.ebc.state.ValueFactory;

@SuppressWarnings({ "rawtypes", "unchecked", "javadoc" })
public class JoinTest
{
	private JoinOutputCreator mockOutputCreator = mock(JoinOutputCreator.class);
	private Join sut = new Join(mockOutputCreator);
	
	@Test
	public void waits_for_input1_to_continue()
	{
		sut.input2Action().accept(21);
		sut.input2Action().accept(22);
		sut.input2Action().accept(23);
		
		verify(mockOutputCreator, never()).createOutput(anyObject(), anyObject());
	}
	
	@Test
	public void waits_for_input2_to_continue()
	{
		sut.input1Action().accept(11);
		sut.input1Action().accept(12);
		sut.input1Action().accept(13);
		
		verify(mockOutputCreator, never()).createOutput(anyObject(), anyObject());
	}
	
	@Test
	public void creates_output_for_last_input1()
	{
		sut.input1Action().accept(11);
		sut.input1Action().accept(12);
		sut.input2Action().accept(21);
		
		verify(mockOutputCreator).createOutput(eq(12), anyObject());
	}
	
	@Test
	public void creates_output_for_last_input2()
	{
		sut.input2Action().accept(21);
		sut.input2Action().accept(22);
		sut.input1Action().accept(12);
		
		verify(mockOutputCreator).createOutput(anyObject(), eq(22));
	}
	
	@Test
	public void sends_created_output()
	{
		Action resultAction = mock(Action.class);
		sut.resultEvent().subscribe(resultAction);
		when(mockOutputCreator.createOutput(anyObject(), anyObject())).thenReturn("out");
		
		sut.input2Action().accept(21);
		sut.input1Action().accept(11);
		
		verify(resultAction).accept("out");
	}
	
	@Test
	public void with_resetAfterResultEvent_resets_input1_after_result()
	{
		sut.input1Action().accept(11);
		sut.input2Action().accept(21);
		sut.input2Action().accept(22);
		
		verify(mockOutputCreator, times(1)).createOutput(anyObject(), anyObject());
	}
	
	@Test
	public void with_resetAfterResultEvent_resets_input2_after_result()
	{
		sut.input2Action().accept(21);
		sut.input1Action().accept(11);
		sut.input1Action().accept(12);
		
		verify(mockOutputCreator, times(1)).createOutput(anyObject(), anyObject());
	}
	
	@Test
	public void without_resetAfterResultEvent_sends_new_result_for_changing_input2()
	{
		sut = new Join(mockOutputCreator, false);
		
		sut.input1Action().accept(11);
		sut.input2Action().accept(21);
		sut.input2Action().accept(22);
		sut.input2Action().accept(23);
		
		verify(mockOutputCreator, times(3)).createOutput(anyObject(), anyObject());
	}
	
	@Test
	public void without_resetAfterResultEvent_sends_new_result_for_changing_input1()
	{
		sut = new Join(mockOutputCreator, false);
		
		sut.input2Action().accept(21);
		sut.input1Action().accept(11);
		sut.input1Action().accept(12);
		sut.input1Action().accept(13);
		
		verify(mockOutputCreator, times(3)).createOutput(anyObject(), anyObject());
	}
	
	@Test
	public void resetAction_resets_input1()
	{
		sut.input1Action().accept(11);
		sut.resetAction().start();
		sut.input2Action().accept(21);
		
		verify(mockOutputCreator, never()).createOutput(anyObject(), anyObject());
	}
	
	@Test
	public void resetAction_resets_input2()
	{
		sut.input2Action().accept(21);
		sut.resetAction().start();
		sut.input1Action().accept(11);
		
		verify(mockOutputCreator, never()).createOutput(anyObject(), anyObject());
	}
	
	@Test
	public void setOutputCreator_sets_a_new_JoinOutputCreator()
	{
		sut = new Join();
		JoinOutputCreator mockOutputCreator = mock(JoinOutputCreator.class);
		
		sut.setOutputCreator(mockOutputCreator);
		sut.input2Action().accept(21);
		sut.input1Action().accept(11);
		
		verify(mockOutputCreator).createOutput(anyObject(), anyObject());
	}
	
	@Test(expected = IllegalStateException.class)
	public void setOutputCreator_throws_exception_if_already_set()
	{
		sut.setOutputCreator(mockOutputCreator);
	}
	
	@Test
	public void constructors_throw_exceptions_for_null_stateFactory()
	{
		Join.setDefaultStateFactory(null);
		try
		{
			try
			{
				new Join();
				fail("Expected IllegalArgumentException has not been thrown");
			}
			catch (IllegalArgumentException e)
			{
			}
			
			try
			{
				new Join(true);
				fail("Expected IllegalArgumentException has not been thrown");
			}
			catch (IllegalArgumentException e)
			{
			}
			
			try
			{
				new Join((StateFactory) null);
				fail("Expected IllegalArgumentException has not been thrown");
			}
			catch (IllegalArgumentException e)
			{
			}
			
			try
			{
				new Join((StateFactory) null, true);
				fail("Expected IllegalArgumentException has not been thrown");
			}
			catch (IllegalArgumentException e)
			{
			}
			try
			{
				new Join(mockOutputCreator);
				fail("Expected IllegalArgumentException has not been thrown");
			}
			catch (IllegalArgumentException e)
			{
			}
			try
			{
				new Join(mockOutputCreator, true);
				fail("Expected IllegalArgumentException has not been thrown");
			}
			catch (IllegalArgumentException e)
			{
			}
			try
			{
				new Join(mockOutputCreator, (StateFactory) null);
				fail("Expected IllegalArgumentException has not been thrown");
			}
			catch (IllegalArgumentException e)
			{
			}
			try
			{
				new Join(mockOutputCreator, (StateFactory) null, true);
				fail("Expected IllegalArgumentException has not been thrown");
			}
			catch (IllegalArgumentException e)
			{
			}
		}
		finally
		{
			Join.setDefaultStateFactory(new BasicStateFactory());
		}
	}
	
	@Test
	public void constructors_throw_exceptions_for_null_outputCreator()
	{
		StateFactory stateFactory = mock(StateFactory.class);
		try
		{
			new Join((JoinOutputCreator) null);
			fail("Expected IllegalArgumentException has not been thrown");
		}
		catch (IllegalArgumentException e)
		{
		}
		try
		{
			new Join((JoinOutputCreator) null, true);
			fail("Expected IllegalArgumentException has not been thrown");
		}
		catch (IllegalArgumentException e)
		{
		}
		try
		{
			new Join((JoinOutputCreator) null, stateFactory);
			fail("Expected IllegalArgumentException has not been thrown");
		}
		catch (IllegalArgumentException e)
		{
		}
		try
		{
			new Join((JoinOutputCreator) null, stateFactory, true);
			fail("Expected IllegalArgumentException has not been thrown");
		}
		catch (IllegalArgumentException e)
		{
		}
	}
	
	@Test
	public void constructors_use_given_stateFactory()
	{
		StateFactory stateFactory = mock(StateFactory.class);
		new Join(stateFactory);
		new Join(stateFactory, true);
		new Join(mockOutputCreator, stateFactory);
		new Join(mockOutputCreator, stateFactory, true);
		
		verify(stateFactory, times(4)).create((ValueFactory) any());
	}
}