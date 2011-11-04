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

import java.util.logging.Logger;

/**
 * This class provides some convenience methods to create {@link Logger} instances for classes or
 * objects.
 * 
 * @author tilmann
 */
public class LoggerFactory
{
	private LoggerFactory()
	{
		
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
}
