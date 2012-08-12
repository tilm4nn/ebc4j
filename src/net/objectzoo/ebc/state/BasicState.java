/*
 * The MIT License
 * 
 * Copyright (C) 2011 Tilmann Kuhn
 * 
 * http://www.object-zoo.net
 * 
 * mailto:ebc4j@object-zoo.net
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package net.objectzoo.ebc.state;

/**
 * A basic {@link State} implementation that holds its value in a simple instance attribute.
 * 
 * @author tilmann
 * 
 * @param <Value>
 *        the type of the state to be hold
 */
class BasicState<Value> implements State<Value>
{
	private ValueFactory<Value> valueFactory;
	
	BasicState(ValueFactory<Value> valueFactory)
	{
		this.valueFactory = valueFactory;
	}
	
	private Value value;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Value get()
	{
		if (value == null && valueFactory != null)
		{
			value = valueFactory.create();
		}
		
		return value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void set(Value value)
	{
		this.value = value;
	}
	
}
