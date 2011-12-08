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
 * The {@code JoinInputStorageProvider} is used by a {@link Join} to retrieve its associated
 * {@link JoinInputStorage} each time the storage is required.
 * 
 * @author tilmann
 * 
 * @param <Input1>
 *        the type of input one of the Join
 * @param <Input2>
 *        the type of input two of the Join
 */
public interface JoinInputStorageProvider<Input1, Input2>
{
	/**
	 * Retrieve the {@link JoinInputStorage} for the given {@link Join}.
	 * 
	 * @param join
	 *        the {@code Join} to retrieve the storage for
	 * @return the {@code JoinInputStorage} of the {@code Join}
	 */
	JoinInputStorage<Input1, Input2> getInputStorage(Join<Input1, Input2, ?> join);
}
