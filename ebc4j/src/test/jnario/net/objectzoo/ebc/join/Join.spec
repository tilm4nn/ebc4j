package net.objectzoo.ebc.join

import static org.junit.Assert.*
import static org.mockito.Mockito.*
import static org.hamcrest.Matcher.*
import net.objectzoo.ebc.state.StateFactory
import net.objectzoo.ebc.state.BasicStateFactory
import net.objectzoo.delegates.Action

import static extension org.jnario.lib.Should.*

describe Join
{
    val mockOutputCreator = mock(typeof(JoinOutputCreator))
    val subject = new Join<Object, Object, Object>(mockOutputCreator)
	
	fact "waits for input1 to continue"
	{
		subject.input2Action.invoke(21)
		subject.input2Action.invoke(22)
		subject.input2Action.invoke(23)
		
		verify(mockOutputCreator, never).createOutput(anyObject, anyObject)
	}
	
	fact "waits for input2 to continue"
	{
		subject.input1Action.invoke(11)
		subject.input1Action.invoke(12)
		subject.input1Action.invoke(13)
		
		verify(mockOutputCreator, never).createOutput(anyObject, anyObject)
	}
	
	fact "creates output for last input1"
	{
		subject.input1Action.invoke(11)
		subject.input1Action.invoke(12)
		subject.input2Action.invoke(21)
		
		verify(mockOutputCreator).createOutput(eq(12), anyObject)
	}
	
	fact "creates output for last input2"
	{
		subject.input2Action.invoke(21)
		subject.input2Action.invoke(22)
		subject.input1Action.invoke(12)
		
		verify(mockOutputCreator).createOutput(anyObject, eq(22))
	}
	
	fact "sends created output"
	{
		val resultAction = mock(typeof(Action))
		subject.resultEvent.subscribe(resultAction)
		when(mockOutputCreator.createOutput(anyObject, anyObject))
			.thenReturn("out")

		subject.input2Action.invoke(21)
		subject.input1Action.invoke(11)
		
		verify(resultAction).invoke("out")
	}
	
	context "with resetAfterResultEvent"
	{
		fact "resets input1 after result"
		{
			subject.input1Action.invoke(11)
			subject.input2Action.invoke(21)
			subject.input2Action.invoke(22)
			
			verify(mockOutputCreator, times(1)).createOutput(anyObject, anyObject)
		}
		
		fact "resets input2 after result"
		{
			subject.input2Action.invoke(21)
			subject.input1Action.invoke(11)
			subject.input1Action.invoke(12)
			
			verify(mockOutputCreator, times(1)).createOutput(anyObject, anyObject)
		}
	}
	
	context "without resetAfterResultEvent"
	{
		val subject = new Join<Object, Object, Object>(mockOutputCreator, false)
			
		fact "sends new result for changing input2"
		{ 
			subject.input1Action.invoke(11)
			subject.input2Action.invoke(21)
			subject.input2Action.invoke(22)
			subject.input2Action.invoke(23)
			
			verify(mockOutputCreator, times(3)).createOutput(anyObject, anyObject)
		}
		
		fact "sends new result for changing input1"
		{
			subject.input2Action.invoke(21)
			subject.input1Action.invoke(11)
			subject.input1Action.invoke(12)
			subject.input1Action.invoke(13)
			
			verify(mockOutputCreator, times(3)).createOutput(anyObject, anyObject)
		}
	}
	
	context "resetAction"
	{
		fact "resets input1"
		{
			subject.input1Action.invoke(11)
			subject.resetAction.invoke
			subject.input2Action.invoke(21)
			
			verify(mockOutputCreator, never).createOutput(anyObject, anyObject)
		}
		
		fact "resets input2"
		{
			subject.input2Action.invoke(21)
			subject.resetAction.invoke
			subject.input1Action.invoke(11)
			
			verify(mockOutputCreator, never).createOutput(anyObject, anyObject)
		}
	}
	
	context "setOutputCreator"
	{
		fact "sets a new JoinOutputCreator"
		{
			val subject = new Join<Object, Object, Object>()
			val mockOutputCreator = mock(typeof(JoinOutputCreator))
			
			subject.setOutputCreator(mockOutputCreator)
			subject.input2Action.invoke(21)
			subject.input1Action.invoke(11)
			
			verify(mockOutputCreator).createOutput(anyObject, anyObject)
		}
		
		fact "throws exception if already set"
		{
			subject.setOutputCreator(mockOutputCreator) should throw IllegalStateException
		}
	}
	
    context "constructors"
    {
        val stateFactory = mock(typeof(StateFactory))
        
        fact "throw exceptions for null stateFactory"
        {
// Does not compile
//          Join::DEFAULT_STATE_FACTORY = null
            
            Join::setDefaultStateFactory(null)
            try
            {
                // Does not compile
                // new Join() should throw IllegalArgumentException
                new Join(true as Boolean) should throw IllegalArgumentException
                new Join(null as StateFactory) should throw IllegalArgumentException
                new Join(null as StateFactory, true) should throw IllegalArgumentException
                new Join(mockOutputCreator) should throw IllegalArgumentException
                new Join(mockOutputCreator, true) should throw IllegalArgumentException
                new Join(mockOutputCreator, null as StateFactory) should throw IllegalArgumentException
                new Join(mockOutputCreator, null as StateFactory, true) should throw IllegalArgumentException
            }
            finally
            {
// Does not compile
//          Join::DEFAULT_STATE_FACTORY = new BasicStateFactory         
                
                Join::setDefaultStateFactory(new BasicStateFactory)
            }
        }
        
        fact "throw exceptions for null outputCreator"
        {
            new Join(null as JoinOutputCreator) should throw IllegalArgumentException
            new Join(null as JoinOutputCreator, true) should throw IllegalArgumentException
            new Join(null as JoinOutputCreator, stateFactory) should throw IllegalArgumentException
            new Join(null as JoinOutputCreator, stateFactory, true) should throw IllegalArgumentException
        }
        
        fact "use given stateFactory"
        {
            new Join(stateFactory)
            new Join(stateFactory, true)
            new Join(mockOutputCreator, stateFactory)
            new Join(mockOutputCreator, stateFactory, true)
            
            verify(stateFactory, times(4)).create(anyObject)
        }
    }
}