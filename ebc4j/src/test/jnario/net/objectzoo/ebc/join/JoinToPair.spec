package net.objectzoo.ebc.join

import static org.junit.Assert.*;
import static org.mockito.Mockito.*
import static org.hamcrest.Matcher.*
import net.objectzoo.delegates.Action
import net.objectzoo.ebc.util.Pair

import static extension org.jnario.lib.Should.*

describe JoinToPair
{
	fact "sends Pair with values from invocations"
	{
		val mockAction = mock(typeof(Action)) as Action<Pair<Object, Object>>
		subject.resultEvent.subscribe(mockAction)
		
		subject.input1Action.invoke(1)
		subject.input2Action.invoke(2)
		
		verify(mockAction).invoke(new Pair(1, 2) as Pair)
	}
}