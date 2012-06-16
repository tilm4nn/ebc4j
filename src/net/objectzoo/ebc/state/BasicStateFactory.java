package net.objectzoo.ebc.state;

public class BasicStateFactory implements StateFactory
{
	@Override
	public <Value> State<Value> create(ValueFactory<Value> valueFactory)
	{
		return new BasicState<Value>(valueFactory);
	}
}
