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

import net.objectzoo.ebc.CanProcess;
import net.objectzoo.ebc.SendsSignal;
import net.objectzoo.ebc.util.LoggingUtils;

/**
 * A base class for an EBC that {@link CanProcess} and {@link SendsSignal}.
 * 
 * The implementation contains the boilerplate code required to implement the interfaces and
 * provides trace logging of action and event invocations.
 * 
 * @author tilmann
 * 
 * @param <ProcessParameter>
 *        the type of input processed by this EBC
 */
public abstract class ProcessAndSignalBase<ProcessParameter> extends
	ProcessAndSignalBoard<ProcessParameter>
{
	/**
	 * Creates a new {@code ProcessAndSignalBase}
	 */
	public ProcessAndSignalBase()
	{
		await(processAction).then(this::receiveProcess);
	}
	
	private void receiveProcess(ProcessParameter parameter)
	{
		LoggingUtils.log(logger, logLevel, "receiving parameter to process: ", parameter);
		
		process(parameter);
	}
	
	/**
	 * This method is to be provided by subclasses to actually implement what's taking place when
	 * the process action is invoked.
	 * 
	 * @param parameter
	 *        the parameter value for the invocation
	 */
	protected abstract void process(ProcessParameter parameter);
	
	/**
	 * This method can be used by subclasses to send the signal.
	 */
	protected void sendSignal()
	{
		logger.log(logLevel, "sending singal");
		
		signalEvent.start();
	}
}
