package net.objectzoo.ebc.join;

import static net.objectzoo.ebc.builder.Flow.await;

import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.impl.SignalBoard;
import net.objectzoo.ebc.state.StateFactory;

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
public class JoinSignals extends SignalBoard
{
	private final Join<Void, Void, Void> join;
	
	/**
	 * Creates a new {@code JoinSignals}
	 */
	public JoinSignals()
	{
		this(null);
	}
	
	/**
	 * Creates a new {@code JoinSignals}
	 * 
	 * @param resetAfterSignalEvent
	 *        if set to {@code true} the EBC is automatically reset after each result event
	 */
	public JoinSignals(boolean resetAfterSignalEvent)
	{
		this(null, resetAfterSignalEvent);
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
		this(stateFactory, true);
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
		join = new Join<Void, Void, Void>(new JoinOutputCreator<Void, Void, Void>()
		{
			@Override
			public Void createOutput(Void input1, Void input2)
			{
				return null;
			}
		}, stateFactory, resetAfterSignalEvent);
		
		await(join).then(signalEvent);
	}
	
	private Action0 startAction1 = new Action0()
	{
		@Override
		public void invoke()
		{
			join.input1Action().invoke(null);
		}
	};
	
	/**
	 * Provides an {@link Action0} that is used to set this EBCs first input
	 * 
	 * @return the first start action of this EBC
	 */
	public Action0 startAction1()
	{
		return startAction1;
	}
	
	private Action0 startAction2 = new Action0()
	{
		@Override
		public void invoke()
		{
			join.input2Action().invoke(null);
		}
	};
	
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
}
