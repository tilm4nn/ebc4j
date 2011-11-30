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
import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.impl.ResultBase;
import net.objectzoo.ebc.util.LoggingUtils;

/**
 * This is a class that can be used to implement Join EBCs. The class implements the boilerplate
 * code required to provide two input actions and a result event as well as trace logging of value
 * input and send results.
 * 
 * The Join waits for every input to be set at least once before creating a result event. Once both
 * inputs have been set an output is created and sent for each single input invocation until the
 * Join is reset again. If reset the procedure to wait for both inputs starts from the beginning.
 * 
 * To reset the Join the {@link #resetAction()} can be invoked. If the {@code resetAfterResultEvent}
 * parameter is set at construction time the Join is automatically reset after each result event.
 * 
 * To actually create the output an instance of {@link JoinOutputCreator} is used.
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
	private Input1 input1;
	
	private Input2 input2;
	
	private boolean input1Received;
	
	private boolean input2Received;
	
	private final boolean resetAfterResultEvent;
	
	private JoinOutputCreator<? super Input1, ? super Input2, ? extends Output> outputCreator;
	
	/**
	 * Creates a new {@code Join} using the given {@link JoinOutputCreator} to create the output of
	 * the {@code Join}.
	 * 
	 * @param outputCreator
	 *        the output creator to be used
	 * @param resetAfterResultEvent
	 *        if set to {@code true} the {@code Join} is automatically reset after each result event
	 */
	public Join(JoinOutputCreator<? super Input1, ? super Input2, ? extends Output> outputCreator,
				boolean resetAfterResultEvent)
	{
		this(resetAfterResultEvent);
		
		if (outputCreator == null)
		{
			throw new IllegalArgumentException("outputCreator=null");
		}
		this.outputCreator = outputCreator;
	}
	
	Join(boolean resetAfterResultEvent)
	{
		this.resetAfterResultEvent = resetAfterResultEvent;
	}
	
	private final Action<Input1> input1Action = new Action<Input1>()
	{
		@Override
		public void invoke(Input1 input)
		{
			LoggingUtils.log(logger, Level.FINEST, "receiving input1: ", input);
			
			input1 = input;
			input1Received = true;
			sendResultIfComplete();
		}
	};
	
	private final Action<Input2> input2Action = new Action<Input2>()
	{
		@Override
		public void invoke(Input2 input)
		{
			LoggingUtils.log(logger, Level.FINEST, "receiving input2: ", input);
			
			input2 = input;
			input2Received = true;
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
		if (input1Received && input2Received)
		{
			createAndSendResult();
			
			if (resetAfterResultEvent)
			{
				clearInput();
			}
		}
	}
	
	private void createAndSendResult()
	{
		Output output = outputCreator.createOutput(input1, input2);
		sendResult(output);
	}
	
	private void clearInput()
	{
		input1 = null;
		input1Received = false;
		input2 = null;
		input2Received = false;
	}
	
	void setOutputCreator(JoinOutputCreator<? super Input1, ? super Input2, ? extends Output> outputCreator)
	{
		if (this.outputCreator != null)
		{
			throw new IllegalStateException("The outputCreator can only be set once");
		}
		this.outputCreator = outputCreator;
	}
	
	/**
	 * Provides an {@link Action0} that is used to reset this Join
	 * 
	 * @return the reset action of this Join
	 */
	public Action0 resetAction()
	{
		return new Action0()
		{
			@Override
			public void invoke()
			{
				logger.log(logLevel, "receiving resetInput");
				
				clearInput();
			}
		};
	}
	
}
