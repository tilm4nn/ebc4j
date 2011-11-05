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
package net.objectzoo.ebc.util;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

/**
 * This class defines some Reflection utility methods used by the generic {@code Join} EBCs.
 * 
 * @author tilmann
 */
public class ReflectionUtils
{
	private ReflectionUtils()
	{
		// No instances will be made
	}
	
	/**
	 * Tries to find a {@link Constructor} in the object type that takes the two parameter types as
	 * parameters.
	 * 
	 * @param objectType
	 *        the object type to find the constructor in
	 * @param parameter1Type
	 *        the first parameter type
	 * @param parameter2Type
	 *        the second parameter type
	 * @return the found constructor
	 * @throws IllegalArgumentException
	 *         if no fitting constructor can be found
	 */
	@SuppressWarnings("unchecked")
	public static <T> Constructor<T> findConstructorWithParameters(Class<T> objectType, Class<?> parameter1Type,
																   Class<?> parameter2Type)
	{
		for (Constructor<?> constructor : objectType.getConstructors())
		{
			Class<?>[] parameterTypes = constructor.getParameterTypes();
			if (parameterTypes.length == 2 && parameterTypes[0].isAssignableFrom(parameter1Type)
				&& parameterTypes[1].isAssignableFrom(parameter2Type))
			{
				constructor.setAccessible(true);
				return (Constructor<T>) constructor;
			}
		}
		throw new IllegalArgumentException("The object type " + objectType.getName()
			+ " does not have a constructor taking " + parameter1Type.getName() + " and "
			+ parameter2Type.getName() + " as arguments.");
	}
	
	/**
	 * Retrieve the raw type of the generic super class' type argument. This will only work
	 * correctly when the given concrete generic type specifies concrete classes for the type
	 * parameters of it's super class. For type variables or wildcard type the raw type of the upper
	 * bound will be returned.
	 * 
	 * @param concreteGenericType
	 *        the class of the implementation of a generic type
	 * @param numberOfArgument
	 *        the number of the generic super class' type argument
	 * @return the raw type of the generic super class' type argument
	 * @throws IllegalArgumentException
	 *         if no raw type can be determined or the super class is not a parameterized type
	 */
	public static Class<?> getRawGenericSuperTypeArgument(Class<?> concreteGenericType, int numberOfArgument)
	{
		ParameterizedType genericType = getGenericSuperType(concreteGenericType);
		return getRawTypeArgument(genericType, numberOfArgument);
	}
	
	/**
	 * Retrieve the generic super class type of the given concrete generic type.
	 * 
	 * @param concreteGenericType
	 *        the class of the implementation of a generic type
	 * @return the generic super class type
	 * @throws IllegalArgumentException
	 *         if the super class is not a parameterized type
	 */
	public static ParameterizedType getGenericSuperType(Class<?> concreteGenericType)
	{
		Type genericSuperclass = concreteGenericType.getGenericSuperclass();
		if (!(genericSuperclass instanceof ParameterizedType))
		{
			throw new IllegalArgumentException("The type " + concreteGenericType.getClass().getName()
				+ " is not a concrete implementation of a generic type.");
		}
		return (ParameterizedType) genericSuperclass;
	}
	
	/**
	 * Retrieve the raw type of a given parameterized type's type argument.
	 * 
	 * @param parameterizedType
	 *        the parameterized type
	 * @param numberOfArgument
	 *        the number of the parameterized type's type argument
	 * @return the raw type of the type argument
	 * @throws IllegalArgumentException
	 *         if no raw type can be determined
	 */
	public static Class<?> getRawTypeArgument(ParameterizedType parameterizedType, int numberOfArgument)
	{
		Type typeArgument = parameterizedType.getActualTypeArguments()[numberOfArgument];
		
		return getRawType(typeArgument);
	}
	
	/**
	 * Determine the raw type for a given type
	 * 
	 * @param type
	 *        the type to determine the raw type for
	 * @return the raw type of the type
	 * @throws IllegalArgumentException
	 *         if no raw type can be determined
	 */
	public static Class<?> getRawType(Type type)
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
}
