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
package net.objectzoo.ebc.impl;

import static net.objectzoo.ebc.builder.Flow.await;

import net.objectzoo.ebc.CanStart;

/**
 * A base class for an EBC that {@link CanStart}.
 * 
 * The implementation contains the boilerplate code required to implement the interface and provides
 * trace logging of action invocations.
 * 
 * @author tilmann
 */
public abstract class StartBase extends StartBoard
{
	/**
	 * Creates a new {@code StartBase}
	 */
	public StartBase()
	{
		await(startAction).then(this::receiveStart);
	}
	
	private void receiveStart()
	{
		logger.log(logLevel, "receiving start");
		
		start();
	}
	
	/**
	 * This method is to be provided by subclasses to actually implement what's taking place when
	 * the start action is invoked.
	 */
	protected abstract void start();
	
}
