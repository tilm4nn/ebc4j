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

import static net.objectzoo.ebc.builder.Flow.*;

import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.ProcessAndResultFlow;
import net.objectzoo.ebc.StartAndResultFlow;
import net.objectzoo.ebc.impl.ProcessAndResultBase;
import net.objectzoo.ebc.state.StateFactory;

/**
 * This is a special flow EBC implementation that joins together a process and a signal flow and
 * thus implements the interfaces {@link ProcessAndResultFlow} and {@link StartAndResultFlow}.
 * 
 * The EBC waits for every input to be set at least once before creating a result event. Once both
 * inputs have been set an output is created and sent. After that there are two different modes of
 * operation depending on the {@code resetAfterResultEvent} parameter setting.
 * 
 * If {@code resetAfterResultEvent} is set to {@code true} (which is the default) then after each
 * result event the two input values are reset and the procedure to wait for both inputs starts from
 * the beginning.
 * 
 * If {@code resetAfterResultEvent} is set to {@code false} then for each following single input
 * invocation a new output is created and sent until the EBC is manually reset again. If reset the
 * procedure to wait for both inputs starts from the beginning.
 * 
 * To manually reset the EBC the {@link #resetAction()} can be invoked.
 * 
 * @author tilmann
 * 
 * @param <T>
 *        the in and output type of the process flow that is joined
 */
public class JoinProcessAndSignal<T> extends ProcessAndResultBase<T, T> implements StartAndResultFlow<T>
{
	private final Join<T, Void, T> join;
	
	/**
	 * Creates a new {@code JoinProcessAndSignal}
	 */
	public JoinProcessAndSignal()
	{
		join = new Join<T, Void, T>();
		init();
	}
	
	/**
	 * Creates a new {@code JoinProcessAndSignal}
	 * 
	 * @param resetAfterResultEvent
	 *        if set to {@code true} the EBC is automatically reset after each result event
	 */
	public JoinProcessAndSignal(boolean resetAfterResultEvent)
	{
		join = new Join<T, Void, T>(resetAfterResultEvent);
		init();
	}
	
	/**
	 * Creates a new {@code JoinProcessAndSignal}
	 * 
	 * @param stateFactory
	 *        the {@link StateFactory} to be used by this EBC. If {@code null} is given then the
	 *        {@link Join#DEFAULT_STATE_FACTORY} is used.
	 */
	public JoinProcessAndSignal(StateFactory stateFactory)
	{
		join = new Join<T, Void, T>(stateFactory);
		init();
	}
	
	/**
	 * Creates a new {@code JoinProcessAndSignal}
	 * 
	 * @param stateFactory
	 *        the {@link StateFactory} to be used by this EBC. If {@code null} is given then the
	 *        {@link Join#DEFAULT_STATE_FACTORY} is used.
	 * @param resetAfterResultEvent
	 *        if set to {@code true} the EBC is automatically reset after each result event
	 */
	public JoinProcessAndSignal(StateFactory stateFactory, boolean resetAfterResultEvent)
	{
		join = new Join<T, Void, T>(stateFactory, resetAfterResultEvent);
		init();
	}
	
	private void init()
	{
		initOutputCreator();
		initFlow();
	}
	
	private void initOutputCreator()
	{
		join.setOutputCreator(new JoinOutputCreator<T, Void, T>()
		{
			@Override
			public T createOutput(T input1, Void input2)
			{
				return input1;
			}
		});
	}
	
	private void initFlow()
	{
		await(join).then(resultEvent);
	}
	
	private Action0 startAction = new Action0()
	{
		@Override
		public void invoke()
		{
			join.input2Action().invoke(null);
		}
	};
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Action0 startAction()
	{
		return startAction;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void process(T parameter)
	{
		join.input1Action().invoke(parameter);
	}
	
	/**
	 * Provides an {@link Action0} that is used to reset this EBC
	 * 
	 * @return the reset action of this EBC
	 */
	public Action0 resetAction()
	{
		return join.resetAction();
	}
}
