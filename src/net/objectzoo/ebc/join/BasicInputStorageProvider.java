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
 * A basic implementation of {@link JoinInputStorageProvider} that is used as a fall back when no
 * other provider is given during {@link Join} construction. Instances of this implementation are
 * associated with a single {@link Join} each and provide it with an instance of
 * {@link BasicInputStorage}.
 * 
 * @author tilmann
 * 
 * @param <Input1>
 *        the type of input one of the Join
 * @param <Input2>
 *        the type of input two of the Join
 */
public class BasicInputStorageProvider<Input1, Input2> implements JoinInputStorageProvider<Input1, Input2>
{
	private final Join<Input1, Input2, ?> join;
	
	private final JoinInputStorage<Input1, Input2> inputStorage = new BasicInputStorage<Input1, Input2>();
	
	/**
	 * Creates a new {@code BasicInputStorageProvider} associated with the given {@link Join}
	 * 
	 * @param join
	 *        the {@code Join} the {@code BasicInputStorageProvider} should belong to
	 */
	public BasicInputStorageProvider(Join<Input1, Input2, ?> join)
	{
		if (join == null)
		{
			throw new IllegalArgumentException(
				"This BasicInputStorageProvider must be associated with a Join object.");
		}
		this.join = join;
	}
	
	/**
	 * @see net.objectzoo.ebc.join.JoinInputStorageProvider#getInputStorage(net.objectzoo.ebc.join.Join)
	 * @throws IllegalArgumentException
	 *         if the {@link Join} given is not the {@code Join} this
	 *         {@code BasicInputStorageProvider} has been associated with during construction.
	 */
	@Override
	public JoinInputStorage<Input1, Input2> getInputStorage(Join<Input1, Input2, ?> join)
		throws IllegalArgumentException
	{
		if (this.join != join)
		{
			throw new IllegalArgumentException(
				"Cannot provide JoinInputStorage for Join that is not associated with this BasicInputStorageProvider");
		}
		return inputStorage;
	}
	
}
