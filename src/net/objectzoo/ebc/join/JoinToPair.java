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

import net.objectzoo.ebc.state.StateFactory;
import net.objectzoo.ebc.util.Pair;

/**
 * This is a Join implementation that joins the two input values to a {@link Pair}.
 * 
 * @author tilmann
 * 
 * @param <Input1>
 *        the type of input one of this Join
 * @param <Input2>
 *        the type of input two of this Join
 */
public class JoinToPair<Input1, Input2> extends Join<Input1, Input2, Pair<Input1, Input2>>
{
	/**
	 * Crates a new {@code JoinToPair}.
	 * 
	 * @param resetAfterResultEvent
	 *        if set to {@code true} the {@code Join} is automatically reset after each result event
	 */
	public JoinToPair(boolean resetAfterResultEvent)
	{
		super(new PairOutputCreator<Input1, Input2>(), null, resetAfterResultEvent);
	}
	
	/**
	 * Crates a new {@code JoinToPair}.
	 * 
	 * @param inputStorageProvider
	 *        the {@link JoinInputStorageProvider} to be used by this {@code Join}. If {@code null}
	 *        is given then the {@link #DEFAULT_INPUT_STORAGE_PROVIDER} is used or if that is also
	 *        {@code null} a {@link BasicInputStorageProvider} is created.
	 * @param resetAfterResultEvent
	 *        if set to {@code true} the {@code Join} is automatically reset after each result event
	 */
	public JoinToPair(StateFactory stateFactory, boolean resetAfterResultEvent)
	{
		super(new PairOutputCreator<Input1, Input2>(), stateFactory, resetAfterResultEvent);
	}
	
}
