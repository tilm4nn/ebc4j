package net.objectzoo.ebc.state;

class BasicState<Value> implements State<Value>
{
	private ValueFactory<Value> valueFactory;
	
	BasicState(ValueFactory<Value> valueFactory)
	{
		this.valueFactory = valueFactory;
	}
	
	private Value value;
	
	@Override
	public Value get()
	{
		if (value == null && valueFactory != null)
		{
			value = valueFactory.create();
		}
		
		return value;
	}
	
	@Override
	public void set(Value value)
	{
		this.value = value;
	}
	
}
