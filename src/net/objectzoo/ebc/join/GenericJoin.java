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


/**
 * This Join base class joins two input values to an output value that has a constructor taking the
 * two input values as parameters. The class implements the boilerplate code required to provide two
 * input actions and a result event as well as trace logging of value input and send results. The
 * output of this Join is created and sent for each input invocation, when both input values have
 * been set to a non {@code null} value at this time.
 * 
 * To use this Join create a (possibly anonymous) subclass that specifies concrete type parameters.
 * 
 * @author tilmann
 * 
 * @param <Input1>
 *        the type of input one of this Join
 * @param <Input2>
 *        the type of input two of this Join
 * @param <Output>
 *        the type of output of this Join
 */
public abstract class GenericJoin<Input1, Input2, Output> extends Join<Input1, Input2, Output>
{
	
	/**
	 * Initializes this {@code Join} with a constructor for the output determined from this Join's
	 * output type by taking a constructor that has the fitting parameter types for this Join's
	 * input types.
	 * 
	 * @throws IllegalArgumentException
	 *         if the output type does not have a fitting constructor
	 */
	public GenericJoin()
	{
		setOutputCreator(new ConstructableOutputCreator<Input1, Input2, Output>(
			GenericOutputConstructorUtils.<Output> findOutputConstructor(getClass())));
	}
	
	/**
	 * Initializes this {@code Join} with a constructor for the output determined from the given
	 * type by taking a constructor that has the fitting parameter types for this Join's input
	 * types.
	 * 
	 * @param outputType
	 *        the type of the output actually constructed in this Join
	 * @throws IllegalArgumentException
	 *         if the output type is {@code null} or does not have a fitting constructor
	 */
	public GenericJoin(Class<? extends Output> outputType)
	{
		setOutputCreator(new ConstructableOutputCreator<Input1, Input2, Output>(
			GenericOutputConstructorUtils.<Output> findOutputConstructor(getClass(), outputType)));
	}
	
}
