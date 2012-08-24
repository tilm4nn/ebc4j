package net.objectzoo.ebc.join

import static org.junit.Assert.*
import static org.mockito.Mockito.*
import static org.hamcrest.Matcher.*
import net.objectzoo.delegates.Action

import static extension org.jnario.lib.Should.*

describe JoinProcessAndSignal
{
	val mockAction = mock(typeof(Action)) as Action<Object>
	before subject.resultEvent.subscribe(mockAction)
	
	fact "sends result for both start and process"
	{
		subject.startAction.invoke
		subject.processAction.invoke("input")
		
		verify(mockAction).invoke("input")
	}
}