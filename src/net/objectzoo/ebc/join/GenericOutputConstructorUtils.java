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

import static net.objectzoo.ebc.util.ReflectionUtils.findConstructorWithParameters;
import static net.objectzoo.ebc.util.ReflectionUtils.getRawGenericSuperTypeArgument;

import java.lang.reflect.Constructor;

/**
 * This class contains some utitlity methods that are used by the generic {@code Join}
 * implementations to determine the output constructor for their generic type parameters.
 * 
 * The types of the generic type parameters are used to determine a fitting constructor using
 * {@link Class#getGenericSuperclass()}. This approach will only work if the implementing class has
 * defined concrete type parameters. This must be ensured by making all {@link Join} subclasses that
 * do not specify concrete classes for the type parameters abstract.
 * 
 * @author tilmann
 */
class GenericOutputConstructorUtils
{
	private GenericOutputConstructorUtils()
	{
		// No instances will be made
	}
	
	static <Output> Constructor<? extends Output> findOutputConstructor(Class<?> genericJoinType)
	{
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Class<Output> outputType = (Class) getRawGenericSuperTypeArgument(genericJoinType, 2);
		
		return findOutputConstructor(genericJoinType, outputType);
	}
	
	static <Output> Constructor<? extends Output> findOutputConstructor(Class<?> genericJoinType,
																		Class<? extends Output> outputType)
	{
		Class<?> input1Type = getRawGenericSuperTypeArgument(genericJoinType, 0);
		Class<?> input2Type = getRawGenericSuperTypeArgument(genericJoinType, 1);
		
		return findOutputConstructor(input1Type, input2Type, outputType);
	}
	
	static <Output> Constructor<? extends Output> findOutputConstructor(Class<?> input1Type, Class<?> input2Type,
																		Class<? extends Output> outputType)
	{
		return findConstructorWithParameters(outputType, input1Type, input2Type);
	}
	
}
