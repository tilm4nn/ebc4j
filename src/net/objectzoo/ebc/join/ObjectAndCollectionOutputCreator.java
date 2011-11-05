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
 * This {@link JoinOutputCreator} implementation takes a first input and a {@link Collection} of
 * input elements and creates a {@link List} of output elements that contains an output element for
 * each element present in the second input constructed together with the given first input.
 * 
 * @author tilmann
 * 
 * @param <Input1>
 *        the type of the first input of this Join output creator
 * @param <Input2Element>
 *        the element type of the second input collection two of this Join output creator
 * @param <OutputElement>
 *        the element type of the output list of this Join output creator
 */
public class ObjectAndCollectionOutputCreator<Input1, Input2Element, OutputElement> implements
	JoinOutputCreator<Input1, Collection<Input2Element>, List<OutputElement>>
{
	private final JoinOutputCreator<? super Input1, ? super Input2Element, ? extends OutputElement> elementOutputCreator;
	
	/**
	 * Creates a new {@code ObjectAndCollectionOutputCreator} that uses the given
	 * {@link JoinOutputCreator} to join the given first input type and second input element type to
	 * the output element type.
	 * 
	 * @param elementOutputCreator
	 *        the Join output creator used to create the output elements
	 */
	public ObjectAndCollectionOutputCreator(JoinOutputCreator<? super Input1, ? super Input2Element, ? extends OutputElement> elementOutputCreator)
	{
		if (elementOutputCreator == null)
		{
			throw new IllegalArgumentException("elementOutputCreator=null");
		}
		this.elementOutputCreator = elementOutputCreator;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<OutputElement> createOutput(Input1 input1, Collection<Input2Element> input2)
	{
		List<OutputElement> output = new ArrayList<OutputElement>(input2.size());
		for (Input2Element input2Element : input2)
		{
			output.add(elementOutputCreator.createOutput(input1, input2Element));
		}
		return output;
	}
}
