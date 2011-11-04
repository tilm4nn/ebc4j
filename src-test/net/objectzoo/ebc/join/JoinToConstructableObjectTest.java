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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class JoinToConstructableObjectTest
{
	private static class Types<T extends Number>
	{
		@SuppressWarnings("unused")
		public List<String> listOfString;
		
		@SuppressWarnings("unused")
		public List<String>[] listOfStringArray;
		
		@SuppressWarnings("unused")
		public T numberBoundedTypeVariable;
		
		@SuppressWarnings("unused")
		public List<? extends String> listOfStringBoundedWildcardType;
		
		@SuppressWarnings("unused")
		public Map<Number, String> numberToStringMap;
		
		Type get(String fieldName)
		{
			try
			{
				return getClass().getField(fieldName).getGenericType();
			}
			catch (Exception e)
			{
				throw new RuntimeException("Could not get test type.", e);
			}
		}
	}
	
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
	public void createOutputElement_creates_TestObject_with_given_constructor()
	{
		JoinToConstructableObject<String, Integer, TestObject, TestObject> sut = new JoinToConstructableObject<String, Integer, TestObject, TestObject>(
			(Constructor<TestObject>) TestObject.class.getConstructors()[0])
		{
			@Override
			protected TestObject createOutput(String lastInput1, Integer lastInput2)
			{
				return null; // We do not test this
			}
		};
		
		TestObject result = sut.createOutputElement("String", 1);
		
		assertThat(result.s, is("String"));
		assertThat(result.i, is(1));
	}
	
	@Test
	public void createOutputElement_creates_TestObject_with_given_class()
	{
		JoinToConstructableObject<Integer, Date, TestObject, TestObject> sut = new JoinToConstructableObject<Integer, Date, TestObject, TestObject>(
			TestObject.class)
		{
			@Override
			protected TestObject createOutput(Integer lastInput1, Date lastInput2)
			{
				return null; // We do not test this
			}
		};
		
		TestObject result = sut.createOutputElement(42, new Date(0));
		
		assertThat(result.d, is(new Date(0)));
		assertThat(result.i, is(42));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void findControctorWithParameters_returns_correct_contsructor()
	{
		Constructor result = JoinToConstructableObject.findConstructorWithParameters(TestObject.class,
			String.class, Integer.class);
		
		assertThat(result, is(equalTo((Constructor) TestObject.class.getConstructors()[0])));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void findControctorWithParameters_throws_exception_for_missing_constructor()
	{
		JoinToConstructableObject.findConstructorWithParameters(TestObject.class, String.class, String.class);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void getRawType_returns_class_for_class_type()
	{
		Class<?> result = JoinToConstructableObject.getRawType(String.class);
		
		assertThat(result, is(equalTo(((Class) String.class))));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void getRawType_returns_list_class_for_list_of_string_type()
	{
		Class<?> result = JoinToConstructableObject.getRawType(new Types().get("listOfString"));
		
		assertThat(result, is(equalTo((Class) List.class)));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void getRawType_returns_array_class_for_generic_array_type()
	{
		Class<?> result = JoinToConstructableObject.getRawType(new Types().get("listOfStringArray"));
		
		assertThat(result, is(equalTo((Class) List[].class)));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void getRawType_returns_upper_bound_class_for_type_variable_type()
	{
		Class<?> result = JoinToConstructableObject.getRawType(new Types().get("numberBoundedTypeVariable"));
		
		assertThat(result, is(equalTo((Class) Number.class)));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void getRawType_returns_upper_bound_class_for_wildcard_type()
	{
		Class<?> result = JoinToConstructableObject.getRawType(((ParameterizedType) new Types().get("listOfStringBoundedWildcardType")).getActualTypeArguments()[0]);
		
		assertThat(result, is(equalTo((Class) String.class)));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void getTypeArgument_returns_type_of_first_type_parameter()
	{
		Class<?> result = JoinToConstructableObject.getTypeArgument(
			(ParameterizedType) new Types().get("numberToStringMap"), 0);
		
		assertThat(result, is(equalTo((Class) Number.class)));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void getTypeArgument_returns_type_of_second_type_parameter()
	{
		Class<?> result = JoinToConstructableObject.getTypeArgument(
			(ParameterizedType) new Types().get("numberToStringMap"), 1);
		
		assertThat(result, is(equalTo((Class) String.class)));
	}
	
	@Test
	public void getGenericSuperType_returns_generic_type_of_super_class()
	{
		ParameterizedType result = JoinToConstructableObject.getGenericSuperType(new ArrayList<String>().getClass());
		
		assertThat(result.getRawType(), is(equalTo((Type) AbstractList.class)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getGenericSuperType_throws_exception_for_non_generic_type()
	{
		JoinToConstructableObject.getGenericSuperType(String.class);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getGenericSuperType_throws_exception_for_non_generic_super_type()
	{
		JoinToConstructableObject.getGenericSuperType(Types.class);
	}
}
