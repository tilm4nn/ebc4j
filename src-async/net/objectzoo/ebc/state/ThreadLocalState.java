package net.objectzoo.ebc.state;

import net.objectzoo.ebc.state.State;
import net.objectzoo.ebc.state.ValueFactory;

class ThreadLocalState<Value> implements State<Value>
{
	private ValueFactory<Value> valueFactory;
	
	ThreadLocalState(ValueFactory<Value> valueFactory)
	{
		this.valueFactory = valueFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Value get()
	{
		Value value = (Value) ThreadLocalStateStore.get().get(this);
		
		if (value == null && valueFactory != null)
		{
			value = valueFactory.create();
			set(value);
		}
		
		return value;
	}
	
	@Override
	public void set(Value value)
	{
		ThreadLocalStateStore.get().put(this, value);
	}
	
}
