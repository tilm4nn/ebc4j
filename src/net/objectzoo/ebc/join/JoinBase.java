package net.objectzoo.ebc.join;

import net.objectzoo.ebc.state.StateFactory;

public abstract class JoinBase<Input1, Input2, Output> extends Join<Input1, Input2, Output>
{
	protected JoinBase(boolean resetAfterResultEvent)
	{
		this(null, resetAfterResultEvent);
	}
	
	protected JoinBase(StateFactory stateFactory, boolean resetAfterResultEvent)
	{
		super(stateFactory, resetAfterResultEvent);
		setOutputCreator(new OutputCreator());
	}
	
	protected abstract Output createOutput(Input1 input1, Input2 input2);
	
	private class OutputCreator implements JoinOutputCreator<Input1, Input2, Output>
	{
		@Override
		public Output createOutput(Input1 input1, Input2 input2)
		{
			return JoinBase.this.createOutput(input1, input2);
		}
	}
}
