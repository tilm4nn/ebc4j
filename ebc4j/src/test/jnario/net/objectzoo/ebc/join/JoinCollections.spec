package net.objectzoo.ebc.join

import static org.junit.Assert.*
import static org.mockito.Mockito.*
import static org.hamcrest.Matcher.*
import net.objectzoo.delegates.Action
import java.util.Collection

import static extension org.jnario.lib.Should.*

describe JoinCollections
{
	fact "sends result after both inputs"
	{
		val sut = new JoinCollections<Integer, String, Object>()
		val mockAction = mock(typeof(Action)) as Action<Collection<Object>>
		val mockOutputCreator = mock(typeof(JoinOutputCreator))
		sut.resultEvent.subscribe(mockAction)
		sut.initOutputElementCreator(mockOutputCreator)
		when(mockOutputCreator.createOutput(1, "A")).thenReturn("out1")
		when(mockOutputCreator.createOutput(2, "B")).thenReturn("out2")
		
		sut.input1Action.invoke(list(1, 2))
		sut.input2Action.invoke(list("A", "B"))
		
		verify(mockAction).invoke(list("out1", "out2") as Collection)
	}
}