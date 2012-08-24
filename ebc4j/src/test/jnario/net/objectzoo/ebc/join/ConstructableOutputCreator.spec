package net.objectzoo.ebc.join

import static org.junit.Assert.*;
import static org.mockito.Mockito.*
import static org.hamcrest.Matcher.*

import static extension org.jnario.lib.Should.*
import java.lang.reflect.Constructor

describe ConstructableOutputCreator
{
	fact "creates TestObject with given constructor"
	{
		val sut = new ConstructableOutputCreator<String, Integer, TestObject>(
				typeof(TestObject).getConstructors().first as Constructor);
		
		val result = sut.createOutput("1", 1);
		
		result should be typeof(TestObject)
		result.s should be "1"
		result.i should be 1
	}
}