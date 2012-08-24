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

import net.objectzoo.ebc.state.StateFactory;

/**
 * This is a base class that can be extended to easily implement custom {@link Join}s.
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
 * @param <Input1>
 *        the type of input one of this Join
 * @param <Input2>
 *        the type of input two of this Join
 * @param <Output>
 *        the type of output of this Join
 */
public abstract class JoinBase<Input1, Input2, Output> extends Join<Input1, Input2, Output>
{
	/**
	 * Creates a new {@code JoinBase} creating its output using the implementation of the abstract
	 * method {@link #createOutput(Object, Object)}.
	 * 
	 */
	protected JoinBase()
	{
		super();
		setOutputCreator();
	}
	
	/**
	 * Creates a new {@code JoinBase} creating its output using the implementation of the abstract
	 * method {@link #createOutput(Object, Object)}.
	 * 
	 * @param resetAfterResultEvent
	 *        if set to {@code true} the {@code Join} is automatically reset after each result event
	 */
	protected JoinBase(boolean resetAfterResultEvent)
	{
		super(resetAfterResultEvent);
		setOutputCreator();
	}
	
	/**
	 * Creates a new {@code JoinBase} creating its output using the implementation of the abstract
	 * method {@link #createOutput(Object, Object)}.
	 * 
	 * @param stateFactory
	 *        the {@link StateFactory} to be used by this {@code Join}. If {@code null} is given
	 *        then the {@link #DEFAULT_STATE_FACTORY} is used.
	 */
	protected JoinBase(StateFactory stateFactory)
	{
		super(stateFactory);
		setOutputCreator();
	}
	
	/**
	 * Creates a new {@code JoinBase} creating its output using the implementation of the abstract
	 * method {@link #createOutput(Object, Object)}.
	 * 
	 * @param stateFactory
	 *        the {@link StateFactory} to be used by this {@code Join}. If {@code null} is given
	 *        then the {@link #DEFAULT_STATE_FACTORY} is used.
	 * @param resetAfterResultEvent
	 *        if set to {@code true} the {@code Join} is automatically reset after each result event
	 */
	protected JoinBase(StateFactory stateFactory, boolean resetAfterResultEvent)
	{
		super(stateFactory, resetAfterResultEvent);
		setOutputCreator();
	}
	
	private void setOutputCreator()
	{
		setOutputCreator(new OutputCreator());
	}
	
	/**
	 * Creates a new output object that joins the two input values
	 * 
	 * @param input1
	 *        the input one value
	 * @param input2
	 *        the input two value
	 * @return the newly created output object
	 */
	protected abstract Output createOutput(Input1 input1, Input2 input2);
	
	private class OutputCreator implements JoinOutputCreator<Input1, Input2, Output>
	{
		@Override
		public Output createOutput(Input1 input1, Input2 input2)
		{
			return JoinBase.this.createOutput(input1, input2);
		}
	}
}
