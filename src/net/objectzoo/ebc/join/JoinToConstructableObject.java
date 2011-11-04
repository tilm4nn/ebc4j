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

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

/**
 * This is a base class that can be used to implement Join EBCs that create their output elements by
 * calling a constructor that takes the two input values as parameters.
 * 
 * @author tilmann
 * 
 * @param <Input1>
 *        the type of input one of this Join
 * @param <Input2>
 *        the type of input two of this Join
 * @param <Output>
 *        the type of output of this Join
 * @param <OutputElement>
 *        the element type of this Join, which might be the same as the output type but can also
 *        differ. For example if the output is a collection of elements.
 */
public abstract class JoinToConstructableObject<Input1, Input2, Output, OutputElement> extends
	JoinBase<Input1, Input2, Output>
{
	/** Holds a reference to the constructor that is used to create the Join's output elements */
	protected final Constructor<? extends OutputElement> outputElementConstructor;
	
	/**
	 * Initializes this {@code JoinToConstructableObject} with a constructor for the output elements
	 * determined from the {@code OutputElement} type parameter by taking a constructor that has the
	 * fitting parameter types for this Join's input types.
	 * 
	 * The types of the generic type parameters are used to determine a fitting constructor using
	 * {@link Class#getGenericSuperclass()}. This approach will only work if the super class has
	 * defined type parameters. This must be ensured by making all subclasses that do not specify
	 * concrete classes for the type parameters abstract.
	 * 
	 * @throws IllegalArgumentException
	 *         if the output element type does not have a fitting constructor
	 */
	protected JoinToConstructableObject()
	{
		outputElementConstructor = findOutputElementConstructor();
	}
	
	/**
	 * Initializes this {@code JoinToConstructableObject} with the given constructor.
	 * 
	 * @param outputElementConstructor
	 *        the constructor that is used to create the Join's output elements
	 */
	protected JoinToConstructableObject(Constructor<? extends OutputElement> outputElementConstructor)
	{
		this.outputElementConstructor = outputElementConstructor;
	}
	
	/**
	 * Initializes this {@code JoinToConstructableObject} with a constructor for the output elements
	 * determined from the given type by taking a constructor that has the fitting parameter types
	 * for this Join's input types.
	 * 
	 * The types of the generic type parameters are used to determine a fitting constructor using
	 * {@link Class#getGenericSuperclass()}. This approach will only work if the super class has
	 * defined type parameters. This must be ensured by making all subclasses that do not specify
	 * concrete classes for the type parameters abstract.
	 * 
	 * @param outputElementType
	 *        the type of the output element actually constructed in this Join
	 * @throws IllegalArgumentException
	 *         if the output element type is {@code null} or does not have a fitting constructor
	 */
	protected JoinToConstructableObject(Class<? extends OutputElement> outputElementType)
	{
		if (outputElementType == null)
		{
			throw new IllegalArgumentException("outputElementType=null");
		}
		outputElementConstructor = findOutputElementConstructor(outputElementType);
	}
	
	private Constructor<? extends OutputElement> findOutputElementConstructor()
	{
		Class<?> outputElementType = getTypeArgument(2);
		return findOutputElementConstructor(outputElementType);
	}
	
	@SuppressWarnings("unchecked")
	private Constructor<? extends OutputElement> findOutputElementConstructor(Class<?> outputElementType)
	{
		Class<?> input1Type = getTypeArgument(0);
		Class<?> input2Type = getTypeArgument(1);
		
		Constructor<?> outputElementConstructor = findConstructorWithParameters(outputElementType, input1Type,
			input2Type);
		return (Constructor<? extends OutputElement>) outputElementConstructor;
	}
	
	static Constructor<?> findConstructorWithParameters(Class<?> objectType, Class<?> parameter1Type,
														Class<?> parameter2Type)
	{
		for (Constructor<?> constructor : objectType.getConstructors())
		{
			Class<?>[] parameterTypes = constructor.getParameterTypes();
			if (parameterTypes.length == 2 && parameterTypes[0].isAssignableFrom(parameter1Type)
				&& parameterTypes[1].isAssignableFrom(parameter2Type))
			{
				constructor.setAccessible(true);
				return constructor;
			}
		}
		throw new IllegalArgumentException("The output type " + objectType.getName()
			+ " does not have a constructor taking " + parameter1Type.getName() + " and "
			+ parameter2Type.getName() + " as arguments.");
	}
	
	private Class<?> getTypeArgument(int numberOfArgument)
	{
		ParameterizedType genericType = getGenericSuperType(getClass());
		return getTypeArgument(genericType, numberOfArgument);
	}
	
	static ParameterizedType getGenericSuperType(Class<?> clazz)
	{
		Type genericSuperclass = clazz.getGenericSuperclass();
		if (!(genericSuperclass instanceof ParameterizedType))
		{
			throw new IllegalArgumentException(
				"The join cannot be used as raw type. Please give the type parameters.");
		}
		return (ParameterizedType) genericSuperclass;
	}
	
	static Class<?> getTypeArgument(ParameterizedType genericType, int numberOfArgument)
	{
		Type typeArgument = genericType.getActualTypeArguments()[numberOfArgument];
		
		return getRawType(typeArgument);
	}
	
	static Class<?> getRawType(Type type)
	{
		if (type instanceof Class<?>)
		{
			return (Class<?>) type;
		}
		if (type instanceof ParameterizedType)
		{
			return (Class<?>) ((ParameterizedType) type).getRawType();
		}
		if (type instanceof GenericArrayType)
		{
			Class<?> elementType = getRawType(((GenericArrayType) type).getGenericComponentType());
			return Array.newInstance(elementType, 0).getClass();
		}
		if (type instanceof TypeVariable)
		{
			return getRawType(((TypeVariable<?>) type).getBounds()[0]);
		}
		if (type instanceof WildcardType)
		{
			return getRawType(((WildcardType) type).getUpperBounds()[0]);
		}
		throw new IllegalArgumentException("The type " + type
			+ " given as type parameter cannot be converted into a valid Java Class.");
	}
	
	/**
	 * This method can be called to create an output element by invoking the output element
	 * constructor created at construction time with the given parameter values.
	 * 
	 * @param input1
	 *        the input1 parameter for the constructor call
	 * @param input2
	 *        the input2 parameter for the constructor call
	 * @return the newly created output element
	 * @throws IllegalArgumentException
	 *         if the construction of the output element fails
	 */
	protected OutputElement createOutputElement(Object input1, Object input2)
	{
		if (outputElementConstructor == null)
		{
			throw new IllegalStateException(
				"This method can only be used if an output element constructor is set.");
		}
		try
		{
			return outputElementConstructor.newInstance(input1, input2);
		}
		catch (IllegalArgumentException e)
		{
			throw new IllegalArgumentException("Could not create output object using constructor "
				+ outputElementConstructor, e);
		}
		catch (InstantiationException e)
		{
			throw new IllegalArgumentException("Could not create output object using constructor "
				+ outputElementConstructor, e);
		}
		catch (IllegalAccessException e)
		{
			throw new IllegalArgumentException("Could not create output object using constructor "
				+ outputElementConstructor, e);
		}
		catch (InvocationTargetException e)
		{
			throw new IllegalArgumentException("Could not create output object using constructor "
				+ outputElementConstructor, e);
		}
	}
}
