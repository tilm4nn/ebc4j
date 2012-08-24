package net.objectzoo.ebc.join

import static org.junit.Assert.*;
import static org.mockito.Mockito.*
import static org.hamcrest.Matcher.*

import static extension org.jnario.lib.Should.*
import net.objectzoo.delegates.Action

describe JoinBase
{
	val mockOutputCreator = mock(typeof(JoinOutputCreator)) as JoinOutputCreator<Object, Object, Object>
	val subject = new JoinBaseImpl(mockOutputCreator)
	
	fact "calls createOutput with correct values"
	{
		subject.input1Action.invoke(11)
		subject.input2Action.invoke(21)
		
		verify(mockOutputCreator).createOutput(11, 21)
	}
	
	fact "sends created output"
	{
		val resultAction = mock(typeof(Action))
		subject.resultEvent.subscribe(resultAction)
		when(mockOutputCreator.createOutput(anyObject, anyObject)).thenReturn("out")

		subject.input1Action.invoke(11)
		subject.input2Action.invoke(21)
		
		verify(resultAction).invoke("out")
	}
}