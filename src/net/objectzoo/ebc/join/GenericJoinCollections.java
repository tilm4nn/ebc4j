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
 * This Join base class joins two input collections of the input element types to a collection of
 * the output element type by using a constructor of the output element type taking the two input
 * element type values as parameters. The class implements the boilerplate code required to provide
 * two input actions and a result event as well as trace logging of value input and send results.
 * The output of this Join is created and sent for each input invocation, when both input values
 * have been set to a non {@code null} value at this time. If one of the two collections given as
 * input has more items than the other the exceeding items are ignored while producing the output.
 * 
 * To use this Join create a (possibly anonymous) subclass that specifies concrete type parameters.
 * 
 * @author tilmann
 * 
 * @param <Input1Element>
 *        the type of input one's elements
 * @param <Input2Element>
 *        the type of input two's elements
 * @param <OutputElement>
 *        the type of the output's elements
 */
public abstract class GenericJoinCollections<Input1Element, Input2Element, OutputElement> extends
	JoinCollections<Input1Element, Input2Element, OutputElement>
{
	/**
	 * Initializes this {@code JoinCollections} with a constructor for the output element determined
	 * from this Join's output element type by taking a constructor that has the fitting parameter
	 * types for this Join's input element types.
	 * 
	 * @throws IllegalArgumentException
	 *         if the output element type does not have a fitting constructor
	 */
	public GenericJoinCollections()
	{
		setOutputElementCreator(new ConstructableOutputCreator<Input1Element, Input2Element, OutputElement>(
			GenericOutputConstructorUtils.<OutputElement> findOutputConstructor(getClass())));
	}
	
	/**
	 * Initializes this {@code JoinCollections} with a constructor for the output elements
	 * determined from the given type by taking a constructor that has the fitting parameter types
	 * for this Join's input element types.
	 * 
	 * @param outputElementType
	 *        the type of the output elements actually constructed in this Join
	 * @throws IllegalArgumentException
	 *         if the output element type is {@code null} or does not have a fitting constructor
	 */
	public GenericJoinCollections(Class<? extends OutputElement> outputElementType)
	{
		setOutputElementCreator(new ConstructableOutputCreator<Input1Element, Input2Element, OutputElement>(
			GenericOutputConstructorUtils.<OutputElement> findOutputConstructor(getClass(), outputElementType)));
	}
}