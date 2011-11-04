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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This Join base class joins a single first input value with a second collection of input elements
 * to a collection of output element by using a constructor of the output element type taking the
 * first input type and the second input element type value as parameters. The class implements the
 * boilerplate code required to provide two input actions and a result event as well as trace
 * logging of value input and send results. The output of this Join is created and sent for each
 * input invocation, when both input values have been set to a non {@code null} value at this time.
 * 
 * To use this Join create a (possibly anonymous) subclass that specifies concrete type parameters.
 * 
 * @author tilmann
 * 
 * @param <Input1>
 *        the type of input one of this Join
 * @param <Input2Element>
 *        the type of input two's elements
 * @param <OutputElement>
 *        the type of the output's elements
 */
public abstract class JoinObjectAndCollection<Input1, Input2Element, OutputElement> extends
	JoinToConstructableObject<Input1, Collection<Input2Element>, List<OutputElement>, OutputElement>
{
	/**
	 * Initializes this {@code JoinObjectAndCollection} with a constructor for the output element
	 * determined from this Join's output element type by taking a constructor that has the fitting
	 * parameter types for this Join's first input type and second input element type.
	 * 
	 * @throws IllegalArgumentException
	 *         if the output element type does not have a fitting constructor
	 */
	public JoinObjectAndCollection()
	{
		super();
	}
	
	/**
	 * Initializes this {@code JoinObjectAndCollection} with a constructor for the output elements
	 * determined from the given type by taking a constructor that has the fitting parameter types
	 * for this Join's first input type and second input element type.
	 * 
	 * @param outputElementType
	 *        the type of the output elements actually constructed in this Join
	 * @throws IllegalArgumentException
	 *         if the output element type is {@code null} or does not have a fitting constructor
	 */
	public JoinObjectAndCollection(Class<? extends OutputElement> outputElementType)
	{
		super(outputElementType);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * This implementation creates a list of output elements that contains an output element for
	 * each element present in the last input 2 constructed together with the given last input 1.
	 */
	@Override
	protected List<OutputElement> createOutput(Input1 lastInput1, Collection<Input2Element> lastInput2)
	{
		List<OutputElement> output = new ArrayList<OutputElement>(lastInput2.size());
		for (Input2Element input2Element : lastInput2)
		{
			output.add(createOutputElement(lastInput1, input2Element));
		}
		return output;
	}
}
