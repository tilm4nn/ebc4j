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
package net.objectzoo.ebc.util;

/**
 * A {@code Pair} is a container combining two other objects of known types. The {@code Pair} can be
 * used as output of an EBC if more than a single object has to be the result.
 * 
 * @author tilmann
 * 
 * @param <T1>
 *        the type of the pairs first item
 * @param <T2>
 *        the type of the pairs second item
 */
public class Pair<T1, T2>
{
	/**
	 * {@inheritDoc}
	 * 
	 * The hash code of the pair is computed by the hash codes of its contained items.
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item1 == null) ? 0 : item1.hashCode());
		result = prime * result + ((item2 == null) ? 0 : item2.hashCode());
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * Two pairs are equal if both of their contained items are equal.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		@SuppressWarnings("rawtypes")
		Pair other = (Pair) obj;
		if (item1 == null)
		{
			if (other.item1 != null) return false;
		}
		else if (!item1.equals(other.item1)) return false;
		if (item2 == null)
		{
			if (other.item2 != null) return false;
		}
		else if (!item2.equals(other.item2)) return false;
		return true;
	}
	
	private T1 item1;
	
	private T2 item2;
	
	/**
	 * Creates a new {@code Pair} with {@code null} values as items.
	 */
	public Pair()
	{
		this(null, null);
	}
	
	/**
	 * Creates a new {@code Pair} with the given items.
	 * 
	 * @param item1
	 * @param item2
	 */
	public Pair(T1 item1, T2 item2)
	{
		this.item1 = item1;
		this.item2 = item2;
	}
	
	/**
	 * Retrieve the first item
	 * 
	 * @return the value of item 1
	 */
	public T1 getItem1()
	{
		return item1;
	}
	
	/**
	 * Set the first item
	 * 
	 * @param item1
	 *        the new value for item 1
	 */
	public void setItem1(T1 item1)
	{
		this.item1 = item1;
	}
	
	/**
	 * Retrieve the second item
	 * 
	 * @return the value of item 2
	 */
	public T2 getItem2()
	{
		return item2;
	}
	
	/**
	 * Set the second item
	 * 
	 * @param item2
	 *        the new value for item 2
	 */
	public void setItem2(T2 item2)
	{
		this.item2 = item2;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return "Pair [item1=" + item1 + ", item2=" + item2 + "]";
	}
}
