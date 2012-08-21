package net.objectzoo.ebc.join

import static org.junit.Assert.*;
import static org.mockito.Mockito.*
import static org.hamcrest.Matcher.*
import net.objectzoo.ebc.state.StateFactory
import net.objectzoo.ebc.state.BasicStateFactory
import net.objectzoo.delegates.Action

import static extension org.jnario.lib.Should.*

describe Join
{
	val outputCreator = mock(typeof(JoinOutputCreator))
	val sut = new Join<Object, Object, Object>(outputCreator, null)
	
	fact "waits for input1 to continue"
	{
		sut.input2Action.invoke(new Object)
		sut.input2Action.invoke(new Object)
		sut.input2Action.invoke(new Object)
		
		verify(outputCreator, never).createOutput(anyObject, anyObject)
	}
	
	fact "waits for input2 to continue"
	{
		sut.input1Action.invoke(new Object)
		sut.input1Action.invoke(new Object)
		sut.input1Action.invoke(new Object)
		
		verify(outputCreator, never).createOutput(anyObject, anyObject)
	}
	
	fact "creates output for last input1"
	{
		val input1 = new Object;
		
		sut.input1Action.invoke(new Object)
		sut.input1Action.invoke(input1)
		sut.input2Action.invoke(new Object)
		
		verify(outputCreator).createOutput(same(input1), anyObject)
	}
	
	fact "creates output for last input2"
	{
		val input2 = new Object;
		
		sut.input2Action.invoke(new Object)
		sut.input2Action.invoke(input2)
		sut.input1Action.invoke(new Object)
		
		verify(outputCreator).createOutput(anyObject, same(input2))
	}
	
	fact "sends created output"
	{
		val output = new Object
		val resultAction = mock(typeof(Action))
		sut.resultEvent.subscribe(resultAction)
		when(outputCreator.createOutput(anyObject, anyObject))
			.thenReturn(output)

		sut.input2Action.invoke(new Object)
		sut.input1Action.invoke(new Object)
		
		verify(resultAction).invoke(output)
	}
	
	context "with resetAfterResultEvent"
	{
		fact "resets input1 after result"
		{
			sut.input1Action.invoke(new Object)
			sut.input2Action.invoke(new Object)
			sut.input2Action.invoke(new Object)
			
			verify(outputCreator).createOutput(anyObject, anyObject)
		}
		
		fact "resets input2 after result"
		{
			sut.input2Action.invoke(new Object)
			sut.input1Action.invoke(new Object)
			sut.input1Action.invoke(new Object)
			
			verify(outputCreator).createOutput(anyObject, anyObject)
		}
	}
	
	context "without resetAfterResultEvent"
	{
		val sut = new Join<Object, Object, Object>(outputCreator, null, false)
			
		fact "sends new result for changing input2"
		{ 
			sut.input1Action.invoke(new Object)
			sut.input2Action.invoke(new Object)
			sut.input2Action.invoke(new Object)
			sut.input2Action.invoke(new Object)
			
			verify(outputCreator, times(3)).createOutput(anyObject, anyObject)
		}
		
		fact "sends new result for changing input1"
		{
			sut.input2Action.invoke(new Object)
			sut.input1Action.invoke(new Object)
			sut.input1Action.invoke(new Object)
			sut.input1Action.invoke(new Object)
			
			verify(outputCreator, times(3)).createOutput(anyObject, anyObject)
		}
	}
	
	context "resetAction"
	{
		fact "resets input1"
		{
			sut.input1Action.invoke(new Object)
			sut.resetAction.invoke;
			sut.input2Action.invoke(new Object)
			
			verify(outputCreator, never).createOutput(anyObject, anyObject)
		}
		
		fact "resets input2"
		{
			sut.input2Action.invoke(new Object)
			sut.resetAction.invoke;
			sut.input1Action.invoke(new Object)
			
			verify(outputCreator, never).createOutput(anyObject, anyObject)
		}
	}
	
	context "setOutputCreator"
	{
		fact "sets a new JoinOutputCreator"
		{
			val sut = new Join<Object, Object, Object>(null)
			val outputCreator = mock(typeof(JoinOutputCreator))
			
			sut.setOutputCreator(outputCreator)
			sut.input2Action.invoke(new Object)
			sut.input1Action.invoke(new Object)
			
			verify(outputCreator).createOutput(anyObject, anyObject)
		}
		
		fact "throws exception if already set"
		{
			sut.setOutputCreator(outputCreator) should throw IllegalStateException
		}
	}
	
	context "constructors"
	{
		val stateFactory = mock(typeof(StateFactory))
		
		fact "throw exceptions for null stateFactory"
		{
// Does not compile
//			Join::DEFAULT_STATE_FACTORY = null
			
			Join::setDefaultStateFactory(null)
			try
			{
				new Join(null as StateFactory) should throw IllegalArgumentException
				new Join(null as StateFactory, true) should throw IllegalArgumentException
				new Join(outputCreator, null) should throw IllegalArgumentException
				new Join(outputCreator, null, true) should throw IllegalArgumentException
			}
			finally
			{
// Does not compile
//			Join::DEFAULT_STATE_FACTORY = new BasicStateFactory			
				
				Join::setDefaultStateFactory(new BasicStateFactory)
			}
		}
		
		fact "throw exceptions for null outputCreator"
		{
			new Join(null, stateFactory) should throw IllegalArgumentException
			new Join(null, stateFactory, true) should throw IllegalArgumentException
		}
		
		fact "use given stateFactory"
		{
			new Join(stateFactory)
			new Join(stateFactory, true)
			new Join(outputCreator, stateFactory)
			new Join(outputCreator, stateFactory, true)
			
			verify(stateFactory, times(4)).create(anyObject)
		}
	}
}