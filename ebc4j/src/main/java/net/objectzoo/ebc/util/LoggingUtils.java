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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides some convenience methods to used for logging in the EBCs.
 * 
 * @author tilmann
 */
public class LoggingUtils
{
	/** This is the default log level used by the EBC implementations and base classes */
	public static final Level DEFAULT_EBC_LOG_LEVEL = Level.FINEST;
	
	private LoggingUtils()
	{
		// No instances will be made
	}
	
	/**
	 * Retrieves a {@link Logger} for the given class' name
	 * 
	 * @param clazz
	 *        the class to create the logger for
	 * @return a logger with class' name
	 */
	public static Logger getLogger(Class<?> clazz)
	{
		if (clazz == null)
		{
			throw new IllegalArgumentException("Cannot create Logger for null class.");
		}
		return Logger.getLogger(clazz.getName());
	}
	
	/**
	 * Retrieves a {@link Logger} for the given object's class' name
	 * 
	 * @param object
	 *        the object to create the logger for
	 * @return a logger with object's class' name
	 */
	public static Logger getLogger(Object object)
	{
		if (object == null)
		{
			throw new IllegalArgumentException("Cannot create Logger for null object.");
		}
		return getLogger(object.getClass());
	}
	
	/**
	 * This method logs the given message and a {@link String} representation of the given parameter
	 * obtained by {@link String#valueOf(Object)}.
	 * 
	 * This is provided for logging as a workaround so that this library can be used in GWT projects
	 * because GWT's {@link Logger} implementations does not provide the method
	 * {@link Logger#log(Level, String, Object)}.
	 * 
	 * @param logger
	 *        the {@link Logger} to log to
	 * @param level
	 *        the {@link Level} to use
	 * @param message
	 *        the message to log
	 * @param parameter
	 *        the parameter to log
	 */
	public static void log(Logger logger, Level level, String message, Object parameter)
	{
		if (logger.isLoggable(level))
		{
			logger.log(level, message + parameter);
		}
	}
}
