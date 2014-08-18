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

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.impl.ResultBase;
import net.objectzoo.ebc.state.BasicStateFactory;
import net.objectzoo.ebc.state.State;
import net.objectzoo.ebc.state.StateFactory;
import net.objectzoo.ebc.state.ValueFactory;
import net.objectzoo.ebc.util.LoggingUtils;

/**
 * This is a class that can be used to implement Join EBCs. The class implements the boilerplate
 * code required to provide two input actions and a result event as well as trace logging of value
 * input and send results.
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
	 * The {@link StateFactory} to be used by {@link Join}s that are not configured with their own
	 */
	public static StateFactory DEFAULT_STATE_FACTORY = new BasicStateFactory();
	
	static final boolean DEFAULT_RESET_AFTER_RESULT_EVENT = true;
	
	@SuppressWarnings("rawtypes")
	private static final ValueFactory<JoinInputStorage> inputStorageFactory = new ValueFactory<JoinInputStorage>()
	{
		@Override
		public JoinInputStorage create()
		{
			return new JoinInputStorage();
		}
	};
	
	private final State<JoinInputStorage<Input1, Input2>> inputStorage;
	
	private final boolean resetAfterResultEvent;
	
	private JoinOutputCreator<? super Input1, ? super Input2, ? extends Output> outputCreator;
	
	private final Action<Input1> input1Action = new Action<Input1>()
	{
		@Override
		public void accept(Input1 input)
		{
			LoggingUtils.log(logger, logLevel, "receiving input1: ", input);
			
			processInput1(input);
		}
	};
	
	private final Action<Input2> input2Action = new Action<Input2>()
	{
		@Override
		public void accept(Input2 input)
		{
			LoggingUtils.log(logger, logLevel, "receiving input2: ", input);
			
			processInput2(input);
		}
	};
	
	private final Action0 resetAction = new Action0()
	{
		@Override
		public void start()
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
	 */
	public Join(JoinOutputCreator<? super Input1, ? super Input2, ? extends Output> outputCreator)
	{
		this(outputCreator, (StateFactory) null, (Boolean) null);
	}
	
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
		this(outputCreator, (StateFactory) null, resetAfterResultEvent);
	}
	
	/**
	 * Creates a new {@code Join} using the given {@link JoinOutputCreator} to create the output of
	 * the {@code Join}.
	 * 
	 * @param outputCreator
	 *        the output creator to be used
	 * @param stateFactory
	 *        the {@link StateFactory} to be used by this {@code Join}. If {@code null} is given
	 *        then the {@link #DEFAULT_STATE_FACTORY} is used.
	 */
	public Join(JoinOutputCreator<? super Input1, ? super Input2, ? extends Output> outputCreator,
				StateFactory stateFactory)
	{
		this(outputCreator, stateFactory, (Boolean) null);
	}
	
	/**
	 * Creates a new {@code Join} using the given {@link JoinOutputCreator} to create the output of
	 * the {@code Join}.
	 * 
	 * @param outputCreator
	 *        the output creator to be used
	 * @param stateFactory
	 *        the {@link StateFactory} to be used by this {@code Join}. If {@code null} is given
	 *        then the {@link #DEFAULT_STATE_FACTORY} is used.
	 * @param resetAfterResultEvent
	 *        if set to {@code true} the {@code Join} is automatically reset after each result event
	 */
	public Join(JoinOutputCreator<? super Input1, ? super Input2, ? extends Output> outputCreator,
				StateFactory stateFactory, Boolean resetAfterResultEvent)
	{
		this(stateFactory, resetAfterResultEvent);
		
		if (outputCreator == null)
		{
			throw new IllegalArgumentException("outputCreator=null");
		}
		this.outputCreator = outputCreator;
	}
	
	Join()
	{
		this((StateFactory) null, (Boolean) null);
	}
	
	Join(boolean resetAfterResultEvent)
	{
		this((StateFactory) null, resetAfterResultEvent);
	}
	
	Join(StateFactory stateFactory)
	{
		this(stateFactory, (Boolean) null);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	Join(StateFactory stateFactory, Boolean resetAfterResultEvent)
	{
		if (resetAfterResultEvent == null)
		{
			this.resetAfterResultEvent = true;
		}
		else
		{
			this.resetAfterResultEvent = resetAfterResultEvent;
		}
		
		if (stateFactory == null)
		{
			if (DEFAULT_STATE_FACTORY == null)
			{
				throw new IllegalArgumentException(
					"Either stateFactory must be given or Join.DEFAULT_STATE_FACTORY must be set.");
			}
			stateFactory = DEFAULT_STATE_FACTORY;
		}
		this.inputStorage = (State) stateFactory.create(inputStorageFactory);
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
		clearInput(inputStorage.get());
	}
	
	private void processInput1(Input1 input)
	{
		JoinInputStorage<Input1, Input2> storage = inputStorage.get();
		
		storage.setInput1(input);
		sendResultIfComplete(storage);
	}
	
	private void processInput2(Input2 input)
	{
		JoinInputStorage<Input1, Input2> storage = inputStorage.get();
		
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
	
	/* Just for the test */
	static void setDefaultStateFactory(StateFactory stateFactory)
	{
		DEFAULT_STATE_FACTORY = stateFactory;
	}
	
}
