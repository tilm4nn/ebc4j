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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * This {@link JoinOutputCreator} implementation creates the output by dynamically invoking the
 * given {@link Constructor}.
 * 
 * @author tilmann
 * 
 * @param <Input1>
 *        the type of input one of this Join output creator
 * @param <Input2>
 *        the type of input two of this Join output creator
 * @param <Output>
 *        the type of output of this Join output creator
 */
public class ConstructableOutputCreator<Input1, Input2, Output> implements
	JoinOutputCreator<Input1, Input2, Output>
{
	private final Constructor<? extends Output> outputConstructor;
	
	/**
	 * Crates a new {@code ConstructableOutputCreator} using the given {@link Constructor} to create
	 * output values. The given constructor's parameter values are not checked against the input
	 * value types of this {@link JoinOutputCreator}. If given input values do not fit the
	 * constructor signature an {@link IllegalArgumentException} is thrown during the invocation of
	 * {@link #createOutput(Object, Object)}.
	 * 
	 * @param outputConstructor
	 *        the constructor used to create the output values
	 */
	public ConstructableOutputCreator(Constructor<? extends Output> outputConstructor)
	{
		if (outputConstructor == null)
		{
			throw new IllegalArgumentException("outputConstructor=null");
		}
		
		this.outputConstructor = outputConstructor;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * This method can be called to create an output element by invoking the output element
	 * constructor given at construction time with the given input parameter values.
	 * 
	 * @param input1
	 *        the input1 parameter for the constructor call
	 * @param input2
	 *        the input2 parameter for the constructor call
	 * @return the newly created output element
	 * @throws IllegalArgumentException
	 *         if the construction of the output element fails
	 */
	@Override
	public Output createOutput(Input1 input1, Input2 input2)
	{
		try
		{
			return outputConstructor.newInstance(input1, input2);
		}
		catch (IllegalArgumentException e)
		{
			throw new IllegalArgumentException("Could not create output object using constructor "
				+ outputConstructor, e);
		}
		catch (InstantiationException e)
		{
			throw new IllegalArgumentException("Could not create output object using constructor "
				+ outputConstructor, e);
		}
		catch (IllegalAccessException e)
		{
			throw new IllegalArgumentException("Could not create output object using constructor "
				+ outputConstructor, e);
		}
		catch (InvocationTargetException e)
		{
			throw new IllegalArgumentException("Could not create output object using constructor "
				+ outputConstructor, e);
		}
	}
	
}
