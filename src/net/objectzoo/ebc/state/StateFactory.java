package net.objectzoo.ebc.state;

public interface StateFactory
{
	<Value> State<Value> create(ValueFactory<Value> valueFactory);
}
