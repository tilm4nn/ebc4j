package net.objectzoo.ebc.join

import static org.junit.Assert.*;
import static org.mockito.Mockito.*
import static org.hamcrest.Matcher.*
import static extension org.jnario.lib.Should.*

import net.objectzoo.ebc.util.Pair 

describe PairOutputCreator
{
	fact "creates pair with given values"
	{
		val result = subject.createOutput(1, 2)
		
		result should be new Pair(1, 2)
	}
}