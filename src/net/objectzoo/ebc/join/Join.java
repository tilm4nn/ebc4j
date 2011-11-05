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

import java.util.logging.Level;

import net.objectzoo.delegates.Action;
import net.objectzoo.ebc.impl.ResultBase;
import net.objectzoo.ebc.util.LoggingUtils;

/**
 * This is a class that can be used to implement Join EBCs. The class implements the boilerplate
 * code required to provide two input actions and a result event as well as trace logging of value
 * input and send results. The output of this Join is created and sent for each input invocation,
 * when both input values have been set to a non {@code null} value at this time. To actually create
 * the output an instance of {@link JoinOutputCreator} is needed.
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
public class Join<Input1, Input2, Output> extends ResultBase<Output>
{
	private Input1 lastInput1;
	
	private Input2 lastInput2;
	
	private JoinOutputCreator<? super Input1, ? super Input2, ? extends Output> outputCreator;
	
	/**
	 * Creates a new {@code Join} using the given {@link JoinOutputCreator} to create the output of
	 * the {@code Join}.
	 * 
	 * @param outputCreator
	 *        the output creator to be used
	 */
	public Join(JoinOutputCreator<? super Input1, ? super Input2, ? extends Output> outputCreator)
	{
		if (outputCreator == null)
		{
			throw new IllegalArgumentException("outputCreator=null");
		}
		this.outputCreator = outputCreator;
	}
	
	Join()
	{
		// only accessible to subclasses in same package
	}
	
	private final Action<Input1> input1Action = new Action<Input1>()
	{
		@Override
		public void invoke(Input1 input)
		{
			LoggingUtils.log(logger, Level.FINEST, "receiving input1: ", input);
			
			lastInput1 = input;
			sendResultIfComplete();
		}
	};
	
	private final Action<Input2> input2Action = new Action<Input2>()
	{
		@Override
		public void invoke(Input2 input)
		{
			LoggingUtils.log(logger, Level.FINEST, "receiving input2: ", input);
			
			lastInput2 = input;
			sendResultIfComplete();
		}
	};
	
	/**
	 * Provides an {@link Action} that is used to send input one to this Join
	 * 
	 * @return the input1 action of this Join
	 */
	public Action<Input1> input1Action()
	{
		return input1Action;
	}
	
	/**
	 * Provides an {@link Action} that is used to send input two to this Join
	 * 
	 * @return the input2 action of this Join
	 */
	public Action<Input2> input2Action()
	{
		return input2Action;
	}
	
	private void sendResultIfComplete()
	{
		if (lastInput1 != null && lastInput2 != null)
		{
			createAndSendOutput();
		}
	}
	
	private void createAndSendOutput()
	{
		Output output = outputCreator.createOutput(lastInput1, lastInput2);
		sendResult(output);
	}
	
	void setOutputCreator(JoinOutputCreator<? super Input1, ? super Input2, ? extends Output> outputCreator)
	{
		if (this.outputCreator != null)
		{
			throw new IllegalStateException("The outputCreator can only be set once");
		}
		this.outputCreator = outputCreator;
	}
	
}
