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

import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.SendsSignal;
import net.objectzoo.ebc.adapters.EventToEvent0;
import net.objectzoo.ebc.state.StateFactory;
import net.objectzoo.events.Event0;

/**
 * This is a special flow EBC implementation that joins together two signal flows.
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
 */
public class JoinSignals implements SendsSignal
{
	private final Join<Void, Void, Void> join;
	
	private EventToEvent0<Void> signalEvent;
	
	/**
	 * Creates a new {@code JoinSignals}
	 */
	public JoinSignals()
	{
		join = new Join<Void, Void, Void>();
		init();
	}
	
	/**
	 * Creates a new {@code JoinSignals}
	 * 
	 * @param resetAfterSignalEvent
	 *        if set to {@code true} the EBC is automatically reset after each result event
	 */
	public JoinSignals(boolean resetAfterSignalEvent)
	{
		join = new Join<Void, Void, Void>(resetAfterSignalEvent);
		init();
	}
	
	/**
	 * Creates a new {@code JoinSignals}
	 * 
	 * @param stateFactory
	 *        the {@link StateFactory} to be used by this EBC. If {@code null} is given then the
	 *        {@link Join#DEFAULT_STATE_FACTORY} is used.
	 */
	public JoinSignals(StateFactory stateFactory)
	{
		join = new Join<Void, Void, Void>(stateFactory);
		init();
	}
	
	/**
	 * Creates a new {@code JoinSignals}
	 * 
	 * @param stateFactory
	 *        the {@link StateFactory} to be used by this EBC. If {@code null} is given then the
	 *        {@link Join#DEFAULT_STATE_FACTORY} is used.
	 * @param resetAfterSignalEvent
	 *        if set to {@code true} the EBC is automatically reset after each result event
	 */
	public JoinSignals(StateFactory stateFactory, boolean resetAfterSignalEvent)
	{
		join = new Join<Void, Void, Void>(stateFactory, resetAfterSignalEvent);
		init();
	}
	
	private void init()
	{
		initOutputCreator();
		initResultEvent();
	}
	
	private void initOutputCreator()
	{
		join.setOutputCreator((i1, i2) -> null);
	}
	
	private void initResultEvent()
	{
		signalEvent = new EventToEvent0<Void>(join.resultEvent());
	}
	
	private final Action0 startAction1 = () -> join.input1Action().accept(null);
	
	/**
	 * Provides an {@link Action0} that is used to set this EBCs first input
	 * 
	 * @return the first start action of this EBC
	 */
	public Action0 startAction1()
	{
		return startAction1;
	}
	
	private final Action0 startAction2 = () -> join.input2Action().accept(null);
	
	/**
	 * Provides an {@link Action0} that is used to start this EBCs second input
	 * 
	 * @return the second start action of this EBC
	 */
	public Action0 startAction2()
	{
		return startAction2;
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
	
	@Override
	public Event0 signalEvent()
	{
		return signalEvent;
	}
}
