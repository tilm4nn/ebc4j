package net.objectzoo.ebc.join;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Constructor;

import org.junit.Test;

@SuppressWarnings({ "rawtypes", "unchecked", "javadoc" })
public class ConstructableOutputCreatorTest
{
	@Test
	public void creates_TestObject_with_given_constructor()
	{
		ConstructableOutputCreator sut = new ConstructableOutputCreator<String, Integer, TestObject>(
			(Constructor) TestObject.class.getConstructors()[0]);
		
		Object result = sut.createOutput("1", 1);
		
		assertThat(result, instanceOf(TestObject.class));
		TestObject result2 = (TestObject) result;
		assertThat(result2.s, is("1"));
		assertThat(result2.i, is(1));
	}
}