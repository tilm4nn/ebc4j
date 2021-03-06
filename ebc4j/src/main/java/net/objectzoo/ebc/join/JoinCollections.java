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

import net.objectzoo.ebc.state.StateFactory;

/**
 * This Join class joins two input collections of the input element types to a collection of the
 * output element type by using a {@link JoinOutputCreator} to join the input element types to the
 * output element type. The class implements the boilerplate code required to provide two input
 * actions and a result event as well as trace logging of value input and send results. If one of
 * the two collections given as input has more items than the other the exceeding items are ignored
 * while producing the output.
 * 
 * The Join waits for every input to be set at least once before creating a result event. Once both
 * inputs have been set an output is created and sent. After that there are two different modes of
 * operation depending on the {@code resetAfterResultEvent} parameter setting.
 * 
 * If {@code resetAfterResultEvent} is set to {@code true} (which is the default) then after each
 * result event the two input values are reset and the procedure to wait for both inputs starts from
 * the beginning.
 * 
 * If {@code resetAfterResultEvent} is set to {@code false} then for each following single input
 * invocation a new output is created and sent until the Join is manually reset again. If reset the
 * procedure to wait for both inputs starts from the beginning.
 * 
 * To manually reset the Join the {@link #resetAction()} can be invoked.
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
		super();
		initOutputElementCreator(elementOutputCreator);
	}
	
	/**
	 * Creates a new {@code JoinCollections} using the given {@link JoinOutputCreator} to join the
	 * input elements to the output element.
	 * 
	 * @param elementOutputCreator
	 *        the output element creator to be used
	 * @param resetAfterResultEvent
	 *        if set to {@code true} the {@code Join} is automatically reset after each result event
	 */
	public JoinCollections(JoinOutputCreator<? super Input1Element, ? super Input2Element, ? extends OutputElement> elementOutputCreator,
						   boolean resetAfterResultEvent)
	{
		super(resetAfterResultEvent);
		initOutputElementCreator(elementOutputCreator);
	}
	
	/**
	 * Creates a new {@code JoinCollections} using the given {@link JoinOutputCreator} to join the
	 * input elements to the output element.
	 * 
	 * @param elementOutputCreator
	 *        the output element creator to be used
	 * @param stateFactory
	 *        the {@link StateFactory} to be used by this {@code Join}. If {@code null} is given
	 *        then the {@link #DEFAULT_STATE_FACTORY} is used.
	 */
	public JoinCollections(JoinOutputCreator<? super Input1Element, ? super Input2Element, ? extends OutputElement> elementOutputCreator,
						   StateFactory stateFactory)
	{
		super(stateFactory);
		initOutputElementCreator(elementOutputCreator);
	}
	
	/**
	 * Creates a new {@code JoinCollections} using the given {@link JoinOutputCreator} to join the
	 * input elements to the output element.
	 * 
	 * @param elementOutputCreator
	 *        the output element creator to be used
	 * @param stateFactory
	 *        the {@link StateFactory} to be used by this {@code Join}. If {@code null} is given
	 *        then the {@link #DEFAULT_STATE_FACTORY} is used.
	 * @param resetAfterResultEvent
	 *        if set to {@code true} the {@code Join} is automatically reset after each result event
	 */
	public JoinCollections(JoinOutputCreator<? super Input1Element, ? super Input2Element, ? extends OutputElement> elementOutputCreator,
						   StateFactory stateFactory, boolean resetAfterResultEvent)
	{
		super(stateFactory, resetAfterResultEvent);
		initOutputElementCreator(elementOutputCreator);
	}
	
	JoinCollections()
	{
		super();
	}
	
	JoinCollections(boolean resetAfterResultEvent)
	{
		super(resetAfterResultEvent);
	}
	
	JoinCollections(StateFactory stateFactoryr)
	{
		super(stateFactoryr);
	}
	
	JoinCollections(StateFactory stateFactoryr, boolean resetAfterResultEvent)
	{
		super(stateFactoryr, resetAfterResultEvent);
	}
	
	void initOutputElementCreator(JoinOutputCreator<? super Input1Element, ? super Input2Element, ? extends OutputElement> elementOutputCreator)
	{
		setOutputCreator(new CollectionsOutputCreator<Input1Element, Input2Element, OutputElement>(
			elementOutputCreator));
	}
	
}
