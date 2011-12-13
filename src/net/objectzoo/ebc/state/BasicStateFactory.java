package net.objectzoo.ebc.state;

public class BasicStateFactory extends StateFactory
{
	@Override
	public <Value> State<Value> create(ValueFactory<Value> valueFactory)
	{
		return new BasicState<Value>(valueFactory);
	}
}
