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

import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.CanStart;
import net.objectzoo.ebc.util.LoggingUtils;
import net.objectzoo.events.impl.Event0Delegate;
import net.objectzoo.events.impl.Event0Distributor;

public abstract class StartBoard implements CanStart
{
	/** The log level used for the trace logging. Defaults to {@link Level#FINEST} */
	protected Level logLevel = Level.FINEST;
	
	/** The logger that can be used for this EBC's logging activities */
	protected final Logger logger = LoggingUtils.getLogger(this);
	
	protected final Event0Delegate startAction = new Event0Distributor();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Action0 startAction()
	{
		return startAction;
	}
}
