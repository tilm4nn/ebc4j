package net.objectzoo.ebc.join

import static org.junit.Assert.*
import static org.mockito.Mockito.*
import static org.hamcrest.Matcher.*
import net.objectzoo.delegates.Action0

import static extension org.jnario.lib.Should.*

describe JoinSignals
{
	val mockAction = mock(typeof(Action0))
	before subject.signalEvent.subscribe(mockAction)
	
	fact "sends signal for both starts"
	{
		subject.startAction1.invoke
		subject.startAction2.invoke
		
		verify(mockAction).invoke
	}
}