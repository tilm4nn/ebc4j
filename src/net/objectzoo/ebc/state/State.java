package net.objectzoo.ebc.state;

public interface State<Value>
{
	Value get();
	
	void set(Value value);
}
