package net.objectzoo.ebc.state;

public abstract class StateFactory
{
	public <Value> State<Value> create()
	{
		return create((ValueFactory<Value>) null);
	}
	
	public <Value> State<Value> create(final Class<? extends Value> clazz)
	{
		if (clazz == null)
		{
			throw new IllegalArgumentException("clazz=null");
		}
		
		return create(new ValueFactory<Value>()
		{
			@Override
			public Value create()
			{
				try
				{
					return clazz.newInstance();
				}
				catch (Exception e)
				{
					throw new RuntimeException("Could not instantiate state value of type " + clazz.getName(), e);
				}
			}
		});
	}
	
	public abstract <Value> State<Value> create(ValueFactory<Value> valueFactory);
}
