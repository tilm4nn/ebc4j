/*
 * The MIT License
 * 
 * Copyright (C) 2011 Tilmann Kuhn
 * 
 * http://www.object-zoo.net
 * 
 * mailto:ebc4j@object-zoo.net
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package net.objectzoo.ebc.join;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Constructor;
import java.util.Date;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ConstructableOutputCreatorTest
{
	static class TestObject
	{
		String s;
		Integer i;
		Date d;
		
		public TestObject(String s, Integer i)
		{
			this.s = s;
			this.i = i;
		}
		
		public TestObject(Integer i, Date d)
		{
			this.i = i;
			this.d = d;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void createOutput_creates_TestObject_with_given_constructor()
	{
		ConstructableOutputCreator<String, Integer, TestObject> sut = new ConstructableOutputCreator<String, Integer, TestObject>(
			(Constructor<TestObject>) TestObject.class.getConstructors()[0]);
		
		TestObject result = sut.createOutput("String", 1);
		
		assertThat(result.s, is("String"));
		assertThat(result.i, is(1));
	}
	
	//	@Test
	//	public void createOutputElement_creates_TestObject_with_given_class()
	//	{
	//		JoinToConstructableObject<Integer, Date, TestObject, TestObject> sut = new JoinToConstructableObject<Integer, Date, TestObject, TestObject>(
	//			TestObject.class)
	//		{
	//			@Override
	//			protected TestObject createOutput(Integer lastInput1, Date lastInput2)
	//			{
	//				return null; // We do not test this
	//			}
	//		};
	//		
	//		TestObject result = sut.createOutputElement(42, new Date(0));
	//		
	//		assertThat(result.d, is(new Date(0)));
	//		assertThat(result.i, is(42));
	//	}
}
