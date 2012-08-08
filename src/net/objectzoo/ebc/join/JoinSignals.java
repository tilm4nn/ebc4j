package net.objectzoo.ebc.join;

import static net.objectzoo.ebc.builder.Flow.await;

import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.impl.SignalBoard;

public class JoinSignals extends SignalBoard
{
	private final Join<Void, Void, Void> join;
	
	public JoinSignals(boolean resetAfterSignalEvent)
	{
		join = new Join<Void, Void, Void>(new JoinOutputCreator<Void, Void, Void>()
		{
			@Override
			public Void createOutput(Void input1, Void input2)
			{
				return null;
			}
		}, null, resetAfterSignalEvent);
		
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
	
	public Action0 startAction2()
	{
		return startAction2;
	}
	
	public Action0 resetAction()
	{
		return join.resetAction();
	}
}
