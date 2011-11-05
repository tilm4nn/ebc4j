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

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;

import net.objectzoo.ebc.test.MockAction;

public class GenericJoinCollectionsTest
{
	@SuppressWarnings("rawtypes")
	@Test
	public void sends_collection_of_TestObject_with_values_from_invocations()
	{
		MockAction<Collection<TestObject>> result = new MockAction<Collection<TestObject>>();
		
		GenericJoinCollections<String, Integer, TestObject> sut = new GenericJoinCollections<String, Integer, TestObject>()
		{
		};
		sut.resultEvent().subscribe(result);
		
		sut.input1Action().invoke(asList("String1", "String2"));
		sut.input2Action().invoke(asList(1, 2));
		
		assertThat(result.getLastResult(),
			is((Collection) asList(new TestObject("String1", 1), new TestObject("String2", 2))));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void sends_collection_of_SubTestObject_with_values_from_invocations()
	{
		MockAction<Collection<TestObject>> result = new MockAction<Collection<TestObject>>();
		
		GenericJoinCollections<String, Integer, TestObject> sut = new GenericJoinCollections<String, Integer, TestObject>(
			SubTestObject.class)
		{
		};
		sut.resultEvent().subscribe(result);
		
		sut.input1Action().invoke(asList("String1", "String2"));
		sut.input2Action().invoke(asList(1, 2));
		
		assertThat(result.getLastResult(),
			is((Collection) asList(new SubTestObject("String1", 1), new SubTestObject("String2", 2))));
	}
}
