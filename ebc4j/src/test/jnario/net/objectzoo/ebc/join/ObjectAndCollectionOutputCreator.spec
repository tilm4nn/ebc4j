package net.objectzoo.ebc.join

import static org.junit.Assert.*;
import static org.mockito.Mockito.*
import static org.hamcrest.Matcher.*

import static extension org.jnario.lib.Should.*

describe ObjectAndCollectionOutputCreator
{
	val mockOutputCreator = mock(typeof(JoinOutputCreator))
			as JoinOutputCreator<String, Integer, Object>
	val subject = new ObjectAndCollectionOutputCreator(mockOutputCreator)
	
	fact "calls output creator with correct values"
	{
		subject.createOutput("1", list(1, 2))
		
		verify(mockOutputCreator).createOutput("1", 1)
		verify(mockOutputCreator).createOutput("1", 2)
	}
	
	fact "creates List with returned values"
	{
		when(mockOutputCreator.createOutput(anyString, anyInt)).thenReturn("out1", "out2")
		
		val result = subject.createOutput("1", list(1, 2))
		
		result should be list("out1", "out2")
	}
}