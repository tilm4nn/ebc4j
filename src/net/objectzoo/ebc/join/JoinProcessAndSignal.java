package net.objectzoo.ebc.join;

import static net.objectzoo.ebc.builder.Flow.await;

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
		this(null);
	}
	
	/**
	 * Creates a new {@code JoinProcessAndSignal}
	 * 
	 * @param resetAfterResultEvent
	 *        if set to {@code true} the EBC is automatically reset after each result event
	 */
	public JoinProcessAndSignal(boolean resetAfterResultEvent)
	{
		this(null, resetAfterResultEvent);
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
		this(stateFactory, true);
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
		join = new Join<T, Void, T>(new JoinOutputCreator<T, Void, T>()
		{
			@Override
			public T createOutput(T input1, Void input2)
			{
				return input1;
			}
		}, stateFactory, resetAfterResultEvent);
		
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
