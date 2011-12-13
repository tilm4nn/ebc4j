package net.objectzoo.ebc.state;


public class ThreadLocalStateFactory extends StateFactory
{
	@Override
	public <Value> State<Value> create(ValueFactory<Value> valueFactory)
	{
		return new ThreadLocalState<Value>(valueFactory);
	}
}
