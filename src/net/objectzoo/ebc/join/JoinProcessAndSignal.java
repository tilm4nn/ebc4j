package net.objectzoo.ebc.join;

import static net.objectzoo.ebc.builder.Flow.await;

import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.StartAndResultFlow;
import net.objectzoo.ebc.impl.ProcessAndResultBase;

public class JoinProcessAndSignal<T> extends ProcessAndResultBase<T, T> implements StartAndResultFlow<T>
{
	private final Join<T, Void, T> join;
	
	public JoinProcessAndSignal(boolean resetAfterResultEvent)
	{
		join = new Join<T, Void, T>(new JoinOutputCreator<T, Void, T>()
		{
			@Override
			public T createOutput(T input1, Void input2)
			{
				return input1;
			}
		}, null, resetAfterResultEvent);
		
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
	
	@Override
	public Action0 startAction()
	{
		return startAction;
	}
	
	@Override
	protected void process(T parameter)
	{
		join.input1Action().invoke(parameter);
	}
	
	public Action0 resetAction()
	{
		return join.resetAction();
	}
}
