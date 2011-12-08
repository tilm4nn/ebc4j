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
package net.objectzoo.ebc.join;

/**
 * This is a basic implementation of {@link JoinInputStorage} that stores the input in instance
 * fields.
 * 
 * @author tilmann
 * 
 * @param <Input1>
 *        the type of input one of the Join
 * @param <Input2>
 *        the type of input two of the Join
 */
public class BasicInputStorage<Input1, Input2> implements JoinInputStorage<Input1, Input2>
{
	private Input1 input1;
	
	private Input2 input2;
	
	private boolean input1Set;
	
	private boolean input2Set;
	
	/**
	 * @see net.objectzoo.ebc.join.JoinInputStorage#setInput1(java.lang.Object)
	 */
	@Override
	public void setInput1(Input1 input)
	{
		input1 = input;
		input1Set = true;
	}
	
	/**
	 * @see net.objectzoo.ebc.join.JoinInputStorage#setInput2(java.lang.Object)
	 */
	@Override
	public void setInput2(Input2 input)
	{
		input2 = input;
		input2Set = true;
	}
	
	/**
	 * @see net.objectzoo.ebc.join.JoinInputStorage#clearInput()
	 */
	@Override
	public void clearInput()
	{
		input1 = null;
		input1Set = false;
		input2 = null;
		input2Set = false;
	}
	
	/**
	 * @see net.objectzoo.ebc.join.JoinInputStorage#isInputComplete()
	 */
	@Override
	public boolean isInputComplete()
	{
		return input1Set && input2Set;
	}
	
	/**
	 * @see net.objectzoo.ebc.join.JoinInputStorage#getInput1()
	 */
	@Override
	public Input1 getInput1()
	{
		if (!input1Set)
		{
			throw new IllegalStateException("Input1 has not been set.");
		}
		return input1;
	}
	
	/**
	 * @see net.objectzoo.ebc.join.JoinInputStorage#getInput2()
	 */
	@Override
	public Input2 getInput2()
	{
		if (!input2Set)
		{
			throw new IllegalStateException("Input2 has not been set.");
		}
		return input2;
	}
	
}
