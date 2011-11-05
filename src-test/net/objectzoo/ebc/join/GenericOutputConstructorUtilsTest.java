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

import java.lang.reflect.Constructor;

import org.junit.Assert;
import org.junit.Test;

public class GenericOutputConstructorUtilsTest
{
	static class TestGenericJoinType<Input1, Input2, Output>
	{
		
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void findOutputConsctructor_finds_correct_constructor_from_type_arguments()
	{
		Class<?> input = new TestGenericJoinType<String, Integer, TestObject>()
		{
		}.getClass();
		
		Constructor<?> result = GenericOutputConstructorUtils.findOutputConstructor(input);
		
		Assert.assertThat(result, is((Constructor) TestObject.class.getConstructors()[0]));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void findOutputConsctructor_finds_correct_constructor_from_type_arguments_and_output_type()
	{
		Class<?> input = new TestGenericJoinType<String, Integer, TestObject>()
		{
		}.getClass();
		
		Constructor<?> result = GenericOutputConstructorUtils.findOutputConstructor(input, SubTestObject.class);
		
		Assert.assertThat(result, is((Constructor) SubTestObject.class.getConstructors()[0]));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void findOutputConsctructor_finds_correct_constructor_from_given_types()
	{
		Constructor<?> result = GenericOutputConstructorUtils.findOutputConstructor(String.class, Integer.class,
			TestObject.class);
		
		Assert.assertThat(result, is((Constructor) TestObject.class.getConstructors()[0]));
	}
}
