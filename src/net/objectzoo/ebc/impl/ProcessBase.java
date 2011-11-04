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

import java.util.logging.Level;
import java.util.logging.Logger;

import net.objectzoo.delegates.Action;
import net.objectzoo.ebc.CanProcess;
import net.objectzoo.ebc.util.LoggerFactory;

/**
 * A base class for an EBC that {@link CanProcess}.
 * 
 * The implementation contains the boilerplate code required to implement the interface and provides
 * trace logging of action invocations.
 * 
 * @author tilmann
 * 
 * @param <ProcessParameter>
 *        the type of input processed by this EBC
 */
public abstract class ProcessBase<ProcessParameter> implements CanProcess<ProcessParameter>
{
	/** The log level used for the trace logging. Defaults to {@link Level#FINEST} */
	protected Level logLevel = Level.FINEST;
	
	/** The logger that can be used for this EBC's logging activities */
	protected final Logger logger = LoggerFactory.getLogger(this);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Action<ProcessParameter> processAction()
	{
		return processAction;
	}
	
	private final Action<ProcessParameter> processAction = new Action<ProcessParameter>()
	{
		@Override
		public void invoke(ProcessParameter parameter)
		{
			receiveProcess(parameter);
		}
	};
	
	private void receiveProcess(ProcessParameter parameter)
	{
		logger.log(logLevel, "receiving parameter to process: {0}", parameter);
		
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
	
}
