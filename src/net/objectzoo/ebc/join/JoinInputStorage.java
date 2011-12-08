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
 * This interface defines a storage that is used by {@link Join} implementations to store their
 * input values. A {@link Join} is not directly associated with a {@code JoinInputStorage}. Instead
 * it acquires its provider using its {@link JoinInputStorageProvider} each time the storage is
 * required.
 * 
 * @author tilmann
 * 
 * @param <Input1>
 *        the type of input one of the Join
 * @param <Input2>
 *        the type of input two of the Join
 */
public interface JoinInputStorage<Input1, Input2>
{
	/**
	 * Store the input1 in this storage.
	 * 
	 * @param input1
	 *        the new value for input1
	 */
	void setInput1(Input1 input1);
	
	/**
	 * Store the input2 in this storage.
	 * 
	 * @param input2
	 *        the new value for input2
	 */
	void setInput2(Input2 input2);
	
	/**
	 * Clears both inputs in this storage.
	 */
	void clearInput();
	
	/**
	 * Check whether both inputs have been set at least one time each since the input as been
	 * cleared the last time.
	 * 
	 * @return {@code} true if both inputs have been set.
	 */
	boolean isInputComplete();
	
	/**
	 * Retrieves the input1 from this storage.
	 * 
	 * @return the previously set input1
	 * @throws IllegalStateException
	 *         if the input1 has not been set
	 */
	Input1 getInput1() throws IllegalStateException;
	
	/**
	 * Retrieves the input2 from this storage.
	 * 
	 * @return the previously set input2
	 * @throws IllegalStateException
	 *         if the input2 has not been set
	 */
	Input2 getInput2();
	
}
