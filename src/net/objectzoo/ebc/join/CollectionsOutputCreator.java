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
import java.util.Iterator;
import java.util.List;

/**
 * This {@link JoinOutputCreator} implementation takes two input {@link Collection}s and creates an
 * output {@link List} with one output value for every pair of values in the input collections.
 * 
 * @author tilmann
 * 
 * @param <Input1Element>
 *        the element type of the input collection one of this Join output creator
 * @param <Input2Element>
 *        the element type of the input collection two of this Join output creator
 * @param <OutputElement>
 *        the element type of the output list of this Join output creator
 */
public class CollectionsOutputCreator<Input1Element, Input2Element, OutputElement> implements
	JoinOutputCreator<Collection<Input1Element>, Collection<Input2Element>, List<OutputElement>>
{
	private final JoinOutputCreator<? super Input1Element, ? super Input2Element, ? extends OutputElement> elementOutputCreator;
	
	/**
	 * Creates a new {@code CollectionsOutputCreator} that uses the given {@link JoinOutputCreator}
	 * to join the given input element types to the output element type.
	 * 
	 * @param elementOutputCreator
	 *        the Join output creator used to create the output elements
	 */
	public CollectionsOutputCreator(JoinOutputCreator<? super Input1Element, ? super Input2Element, ? extends OutputElement> elementOutputCreator)
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
	public List<OutputElement> createOutput(Collection<Input1Element> input1, Collection<Input2Element> input2)
	{
		List<OutputElement> output = new ArrayList<OutputElement>(Math.max(input1.size(), input2.size()));
		
		Iterator<Input1Element> input1Iter = input1.iterator();
		Iterator<Input2Element> input2Iter = input2.iterator();
		
		while (input1Iter.hasNext() && input2Iter.hasNext())
		{
			output.add(elementOutputCreator.createOutput(input1Iter.next(), input2Iter.next()));
		}
		
		return output;
	}
	
}
