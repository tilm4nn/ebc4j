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
	/**
	 * The default {@link JoinInputStorageProvider} to be used when no provider is given during
	 * construction of a {@code Join}. I this default is not set (is {@code null}) then a
	 * {@link BasicInputStorageProvider} is created for each {@code Join}. The initial value of this
	 * default is not set ({@code null}).
	 */
	public static JoinInputStorageProvider<?, ?> DEFAULT_INPUT_STORAGE_PROVIDER = null;
	
	private final JoinInputStorageProvider<Input1, Input2> inputStorageProvider;
	
	private final boolean resetAfterResultEvent;
	
	private JoinOutputCreator<? super Input1, ? super Input2, ? extends Output> outputCreator;
	
	private final Action<Input1> input1Action = new Action<Input1>()
	{
		@Override
		public void invoke(Input1 input)
		{
			LoggingUtils.log(logger, Level.FINEST, "receiving input1: ", input);
			
			processInput1(input);
		}
	};
	
	private final Action<Input2> input2Action = new Action<Input2>()
	{
		@Override
		public void invoke(Input2 input)
		{
			LoggingUtils.log(logger, Level.FINEST, "receiving input2: ", input);
			
			processInput2(input);
		}
	};
	
	private final Action0 resetAction = new Action0()
	{
		@Override
		public void invoke()
		{
			logger.log(logLevel, "receiving reset");
			
			processReset();
		}
	};
	
	/**
	 * Creates a new {@code Join} using the given {@link JoinOutputCreator} to create the output of
	 * the {@code Join}.
	 * 
	 * @param outputCreator
	 *        the output creator to be used
	 * @param inputStorageProvider
	 *        the {@link JoinInputStorageProvider} to be used by this {@code Join}. If {@code null}
	 *        is given then the {@link #DEFAULT_INPUT_STORAGE_PROVIDER} is used or if that is also
	 *        {@code null} a {@link BasicInputStorageProvider} is created.
	 * @param resetAfterResultEvent
	 *        if set to {@code true} the {@code Join} is automatically reset after each result event
	 */
	public Join(JoinOutputCreator<? super Input1, ? super Input2, ? extends Output> outputCreator,
				JoinInputStorageProvider<Input1, Input2> inputStorageProvider, boolean resetAfterResultEvent)
	{
		this(inputStorageProvider, resetAfterResultEvent);
		
		if (outputCreator == null)
		{
			throw new IllegalArgumentException("outputCreator=null");
		}
		this.outputCreator = outputCreator;
	}
	
	@SuppressWarnings("unchecked")
	Join(JoinInputStorageProvider<Input1, Input2> inputStorageProvider, boolean resetAfterResultEvent)
	{
		this.resetAfterResultEvent = resetAfterResultEvent;
		if (inputStorageProvider != null)
		{
			this.inputStorageProvider = inputStorageProvider;
		}
		else if (DEFAULT_INPUT_STORAGE_PROVIDER != null)
		{
			this.inputStorageProvider = (JoinInputStorageProvider<Input1, Input2>) DEFAULT_INPUT_STORAGE_PROVIDER;
		}
		else
		{
			this.inputStorageProvider = new BasicInputStorageProvider<Input1, Input2>(this);
		}
	}
	
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
	
	/**
	 * Provides an {@link Action0} that is used to reset this Join
	 * 
	 * @return the reset action of this Join
	 */
	public Action0 resetAction()
	{
		return resetAction;
	}
	
	private void processReset()
	{
		JoinInputStorage<Input1, Input2> storage = getInputStorage();
		
		clearInput(storage);
	}
	
	private void processInput1(Input1 input)
	{
		JoinInputStorage<Input1, Input2> storage = getInputStorage();
		
		storage.setInput1(input);
		sendResultIfComplete(storage);
	}
	
	private void processInput2(Input2 input)
	{
		JoinInputStorage<Input1, Input2> storage = getInputStorage();
		
		storage.setInput2(input);
		sendResultIfComplete(storage);
	}
	
	private void sendResultIfComplete(JoinInputStorage<Input1, Input2> storage)
	{
		if (storage.isInputComplete())
		{
			createAndSendResult(storage);
			
			if (resetAfterResultEvent)
			{
				clearInput(storage);
			}
		}
	}
	
	private void createAndSendResult(JoinInputStorage<Input1, Input2> storage)
	{
		Output output = outputCreator.createOutput(storage.getInput1(), storage.getInput2());
		sendResult(output);
	}
	
	private void clearInput(JoinInputStorage<Input1, Input2> storage)
	{
		storage.clearInput();
	}
	
	void setOutputCreator(JoinOutputCreator<? super Input1, ? super Input2, ? extends Output> outputCreator)
	{
		if (this.outputCreator != null)
		{
			throw new IllegalStateException("The outputCreator can only be set once");
		}
		this.outputCreator = outputCreator;
	}
	
	private JoinInputStorage<Input1, Input2> getInputStorage()
	{
		return inputStorageProvider.getInputStorage(Join.this);
	}
	
}
