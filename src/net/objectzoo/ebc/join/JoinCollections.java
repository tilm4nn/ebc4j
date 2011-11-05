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

import java.util.Collection;
import java.util.List;

/**
 * This Join class joins two input collections of the input element types to a collection of the
 * output element type by using a {@link JoinOutputCreator} to join the input element types to the
 * output element type. The class implements the boilerplate code required to provide two input
 * actions and a result event as well as trace logging of value input and send results. The output
 * of this Join is created and sent for each input invocation, when both input values have been set
 * to a non {@code null} value at this time. If one of the two collections given as input has more
 * items than the other the exceeding items are ignored while producing the output.
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
public class JoinCollections<Input1Element, Input2Element, OutputElement> extends
	Join<Collection<Input1Element>, Collection<Input2Element>, List<OutputElement>>
{
	/**
	 * Creates a new {@code JoinCollections} using the given {@link JoinOutputCreator} to join the
	 * input elements to the output element.
	 * 
	 * @param elementOutputCreator
	 *        the output element creator to be used
	 */
	public JoinCollections(JoinOutputCreator<? super Input1Element, ? super Input2Element, ? extends OutputElement> elementOutputCreator)
	{
		super(new CollectionsOutputCreator<Input1Element, Input2Element, OutputElement>(elementOutputCreator));
	}
	
	JoinCollections()
	{
		// only accessible to subclasses in same package
	}
	
	void setOutputElementCreator(JoinOutputCreator<? super Input1Element, ? super Input2Element, ? extends OutputElement> elementOutputCreator)
	{
		setOutputCreator(new CollectionsOutputCreator<Input1Element, Input2Element, OutputElement>(
			elementOutputCreator));
	}
	
}
