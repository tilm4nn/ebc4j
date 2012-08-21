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

import java.lang.reflect.Constructor;

import net.objectzoo.ebc.state.StateFactory;

/**
 * This Join base class joins two input values to an output value that has a constructor taking the
 * two input values as parameters. The class implements the boilerplate code required to provide two
 * input actions and a result event as well as trace logging of value input and send results.
 * 
 * The Join waits for every input to be set at least once before creating a result event. Once both
 * inputs have been set an output is created and sent. After that there are two different modes of
 * operation depending on the {@code resetAfterResultEvent} parameter setting.
 * 
 * If {@code resetAfterResultEvent} is set to {@code true} (which is the default) then after each
 * result event the two input values are reset and the procedure to wait for both inputs starts from
 * the beginning.
 * 
 * If {@code resetAfterResultEvent} is set to {@code false} then for each following single input
 * invocation a new output is created and sent until the Join is manually reset again. If reset the
 * procedure to wait for both inputs starts from the beginning.
 * 
 * To manually reset the Join the {@link #resetAction()} can be invoked.
 * 
 * To use this Join create a (possibly anonymous) subclass that specifies concrete type parameters.
 * 
 * @author tilmann
 * 
 * @param <Input1>
 *        the type of input one of this Join
 * @param <Input2>
 *        the type of input two of this Join
 * @param <Output>
 *        the type of output of this Join
 */
public abstract class GenericJoin<Input1, Input2, Output> extends Join<Input1, Input2, Output>
{
	/**
	 * Initializes this {@code Join} with a constructor for the output determined from this Join's
	 * output type by taking a constructor that has the fitting parameter types for this Join's
	 * input types.
	 * 
	 * @throws IllegalArgumentException
	 *         if the output type does not have a fitting constructor
	 */
	public GenericJoin()
	{
		super(null);
		
		initOutputCreator();
	}
	
	/**
	 * Initializes this {@code Join} with a constructor for the output determined from this Join's
	 * output type by taking a constructor that has the fitting parameter types for this Join's
	 * input types.
	 * 
	 * @param resetAfterResultEvent
	 *        if set to {@code true} the {@code Join} is automatically reset after each result event
	 * @throws IllegalArgumentException
	 *         if the output type does not have a fitting constructor
	 */
	public GenericJoin(boolean resetAfterResultEvent)
	{
		super(null, resetAfterResultEvent);
		
		initOutputCreator();
	}
	
	/**
	 * Initializes this {@code Join} with a constructor for the output determined from this Join's
	 * output type by taking a constructor that has the fitting parameter types for this Join's
	 * input types.
	 * 
	 * 
	 * @param stateFactory
	 *        the {@link StateFactory} to be used by this {@code Join}. If {@code null} is given
	 *        then the {@link #DEFAULT_STATE_FACTORY} is used.
	 * @throws IllegalArgumentException
	 *         if the output type does not have a fitting constructor
	 */
	public GenericJoin(StateFactory stateFactory)
	{
		super(stateFactory);
		
		initOutputCreator();
	}
	
	/**
	 * Initializes this {@code Join} with a constructor for the output determined from this Join's
	 * output type by taking a constructor that has the fitting parameter types for this Join's
	 * input types.
	 * 
	 * 
	 * @param stateFactory
	 *        the {@link StateFactory} to be used by this {@code Join}. If {@code null} is given
	 *        then the {@link #DEFAULT_STATE_FACTORY} is used.
	 * @param resetAfterResultEvent
	 *        if set to {@code true} the {@code Join} is automatically reset after each result event
	 * @throws IllegalArgumentException
	 *         if the output type does not have a fitting constructor
	 */
	public GenericJoin(StateFactory stateFactory, boolean resetAfterResultEvent)
	{
		super(stateFactory, resetAfterResultEvent);
		
		initOutputCreator();
	}
	
	/**
	 * Initializes this {@code Join} with a constructor for the output determined from the given
	 * type by taking a constructor that has the fitting parameter types for this Join's input
	 * types.
	 * 
	 * @param outputType
	 *        the type of the output actually constructed in this Join
	 * @throws IllegalArgumentException
	 *         if the output type is {@code null} or does not have a fitting constructor
	 */
	public GenericJoin(Class<? extends Output> outputType)
	{
		super(null);
		
		initOutputCreator(outputType);
	}
	
	/**
	 * Initializes this {@code Join} with a constructor for the output determined from the given
	 * type by taking a constructor that has the fitting parameter types for this Join's input
	 * types.
	 * 
	 * @param outputType
	 *        the type of the output actually constructed in this Join
	 * @param resetAfterResultEvent
	 *        if set to {@code true} the {@code Join} is automatically reset after each result event
	 * @throws IllegalArgumentException
	 *         if the output type is {@code null} or does not have a fitting constructor
	 */
	public GenericJoin(Class<? extends Output> outputType, boolean resetAfterResultEvent)
	{
		super(null, resetAfterResultEvent);
		
		initOutputCreator(outputType);
	}
	
	/**
	 * Initializes this {@code Join} with a constructor for the output determined from the given
	 * type by taking a constructor that has the fitting parameter types for this Join's input
	 * types.
	 * 
	 * @param outputType
	 *        the type of the output actually constructed in this Join
	 * @param stateFactory
	 *        the {@link StateFactory} to be used by this {@code Join}. If {@code null} is given
	 *        then the {@link #DEFAULT_STATE_FACTORY} is used.
	 * @throws IllegalArgumentException
	 *         if the output type is {@code null} or does not have a fitting constructor
	 */
	public GenericJoin(Class<? extends Output> outputType, StateFactory stateFactory)
	{
		super(stateFactory);
		
		initOutputCreator(outputType);
	}
	
	/**
	 * Initializes this {@code Join} with a constructor for the output determined from the given
	 * type by taking a constructor that has the fitting parameter types for this Join's input
	 * types.
	 * 
	 * @param outputType
	 *        the type of the output actually constructed in this Join
	 * @param stateFactory
	 *        the {@link StateFactory} to be used by this {@code Join}. If {@code null} is given
	 *        then the {@link #DEFAULT_STATE_FACTORY} is used.
	 * @param resetAfterResultEvent
	 *        if set to {@code true} the {@code Join} is automatically reset after each result event
	 * @throws IllegalArgumentException
	 *         if the output type is {@code null} or does not have a fitting constructor
	 */
	public GenericJoin(Class<? extends Output> outputType, StateFactory stateFactory, boolean resetAfterResultEvent)
	{
		super(stateFactory, resetAfterResultEvent);
		
		initOutputCreator(outputType);
	}
	
	private void initOutputCreator(Class<? extends Output> outputType)
	{
		Constructor<? extends Output> outputConstructor;
		outputConstructor = GenericOutputConstructorUtils.<Output> findOutputConstructor(getClass(), outputType);
		
		initOutputCreator(outputConstructor);
	}
	
	private void initOutputCreator()
	{
		Constructor<? extends Output> outputConstructor;
		outputConstructor = GenericOutputConstructorUtils.<Output> findOutputConstructor(getClass());
		
		initOutputCreator(outputConstructor);
	}
	
	private void initOutputCreator(Constructor<? extends Output> outputConstructor)
	{
		JoinOutputCreator<? super Input1, ? super Input2, ? extends Output> outputCreator;
		outputCreator = new ConstructableOutputCreator<Input1, Input2, Output>(outputConstructor);
		
		setOutputCreator(outputCreator);
	}
}
