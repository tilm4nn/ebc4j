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
package net.objectzoo.ebc.executor;

import net.objectzoo.ebc.CanProcess;
import net.objectzoo.ebc.CanStart;
import net.objectzoo.ebc.ProcessAndResultFlow;
import net.objectzoo.ebc.StartAndResultFlow;

/**
 * The {@code FlowExecuter} supports both: Execution of existing flows with one of it's
 * {@code static} methods and the execution of flows created per execution using the given
 * {@link FlowFactory}.
 * 
 * @author tilmann
 */
public class FlowExecutor
{
	private final FlowFactory flowCreator;
	
	/**
	 * Creates a new {@code FlwoExecutor} using the given {@link FlowFactory} to create new flow
	 * instances
	 * 
	 * @param theFlowCreator
	 *        the {@link FlowFactory} used to create flow instances
	 */
	public FlowExecutor(FlowFactory theFlowCreator)
	{
		flowCreator = theFlowCreator;
	}
	
	/**
	 * Creates the flow determined by the given flow class using this executor's {@link FlowFactory}
	 * instance and then executes the flow as described by {@link #process(CanProcess, Object)}
	 * 
	 * @param flowClass
	 *        the {@link Class} of the flow to be created
	 * @param input
	 *        the input parameter to be processed by the flow
	 */
	public <ProcessParameter, FlowType extends CanProcess<ProcessParameter>> void process(Class<FlowType> flowClass,
																						  ProcessParameter input)
	{
		FlowType flow = FlowExecutorHelpers.createFlow(flowClass, flowCreator);
		
		process(flow, input);
	}
	
	/**
	 * Creates the flow determined by the given flow class using this executor's {@link FlowFactory}
	 * instance and then executes the flow as described by
	 * {@link #processAndReturnResult(ProcessAndResultFlow, Object)}
	 * 
	 * @param flowClass
	 *        the {@link Class} of the flow to be created
	 * @param input
	 *        the input parameter to be processed by the flow
	 * @return the final result sent by the flow
	 */
	public <ProcessParameter, ResultParameter, FlowType extends ProcessAndResultFlow<ProcessParameter, ResultParameter>> ResultParameter processAndReturnResult(Class<FlowType> flowClass,
																																								ProcessParameter input)
	{
		FlowType flow = FlowExecutorHelpers.createFlow(flowClass, flowCreator);
		
		return processAndReturnResult(flow, input);
	}
	
	/**
	 * Creates the flow determined by the given flow class using this executor's {@link FlowFactory}
	 * instance and then executes the flow as described by {@link #start(CanStart)}
	 * 
	 * @param flowClass
	 *        the {@link Class} of the flow to be created
	 */
	public <FlowType extends CanStart> void start(Class<FlowType> flowClass)
	{
		FlowType flow = FlowExecutorHelpers.createFlow(flowClass, flowCreator);
		
		start(flow);
	}
	
	/**
	 * Creates the flow determined by the given flow class using this executor's {@link FlowFactory}
	 * instance and then executes the flow as described by
	 * {@link #startAndReturnResult(StartAndResultFlow)}
	 * 
	 * @param flowClass
	 *        the {@link Class} of the flow to be created
	 * @return the final result sent by the flow
	 */
	public <ResultParameter, FlowType extends StartAndResultFlow<ResultParameter>> ResultParameter startAndReturnResult(Class<FlowType> flowClass)
	{
		FlowType flow = FlowExecutorHelpers.createFlow(flowClass, flowCreator);
		
		return startAndReturnResult(flow);
	}
	
	/**
	 * Executes the given {@link CanProcess} flow with the given input parameter
	 * 
	 * @param flow
	 *        the flow to be executed
	 * @param input
	 *        the input parameter provided to the flow
	 */
	public static <ProcessParameter, FlowType extends CanProcess<ProcessParameter>> void process(FlowType flow,
																								 ProcessParameter input)
	{
		flow.processAction().invoke(input);
	}
	
	/**
	 * Executes the given {@link ProcessAndResultFlow} flow with the given input parameter and
	 * returns the final result. This method is not thread save regarding simultaneous executions of
	 * the flow!
	 * 
	 * @param flow
	 *        the flow to be executed
	 * @param input
	 *        the input parameter to be processed by the flow
	 * @return the final result sent by the flow
	 */
	public static <ProcessParameter, ResultParameter, FlowType extends ProcessAndResultFlow<ProcessParameter, ResultParameter>> ResultParameter processAndReturnResult(FlowType flow,
																																									   
																																									   ProcessParameter input)
	{
		return new ProcessAndResultFlowToFuncAdapter<ProcessParameter, ResultParameter>(flow).invoke(input);
	}
	
	/**
	 * Executes the given {@link CanStart} flow
	 * 
	 * @param flow
	 *        the flow to be executed
	 */
	public static <FlowType extends CanStart> void start(FlowType flow)
	{
		flow.startAction().invoke();
	}
	
	/**
	 * Executes the given {@link StartAndResultFlow} flow and returns the final result. This method
	 * is not thread save regarding simultaneous executions of the flow!
	 * 
	 * @param flow
	 *        the flow to be executed
	 * @return the final result sent by the flow
	 */
	public static <ResultParameter, FlowType extends StartAndResultFlow<ResultParameter>> ResultParameter startAndReturnResult(FlowType flow)
	{
		return new StartAndResultFlowToFunc0Adapter<ResultParameter>(flow).invoke();
	}
	
}
