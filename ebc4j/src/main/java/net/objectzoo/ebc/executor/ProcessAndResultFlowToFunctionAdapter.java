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

import static net.objectzoo.ebc.executor.FlowExecutorHelpers.appendResultContainer;
import static net.objectzoo.ebc.executor.FlowExecutorHelpers.removeResultContainer;

import java.util.function.Function;

import net.objectzoo.ebc.ProcessAndResultFlow;
import net.objectzoo.ebc.test.MockAction;

/**
 * This adapter adapts an {@link ProcessAndResultFlow} to a {@link Function}. It does this by
 * subscribing to the final result event for each single {@link Function} invocation and returning
 * the last result send after the flow execution before finally unsubscribing from the event again
 * and returning to the caller. Doing so this implementation is inherently not thread save regarding
 * multiple simultaneous execution of the flow and flows that contain asynchronous execution chains.
 * 
 * @author tilmann
 * 
 * @param <ProcessParameter>
 *        the process parameter type of the flow
 * @param <ResultParameter>
 *        the result parameter type of the flow
 */
public class ProcessAndResultFlowToFunctionAdapter<ProcessParameter, ResultParameter> implements
	Function<ProcessParameter, ResultParameter>
{
	private final ProcessAndResultFlow<ProcessParameter, ResultParameter> flow;
	
	/**
	 * Creates a new {@code ProcessAndResultFlowToFuncAdapter}
	 * 
	 * @param theFlow
	 *        the flow to be adapted
	 */
	public ProcessAndResultFlowToFunctionAdapter(ProcessAndResultFlow<ProcessParameter, ResultParameter> theFlow)
	{
		flow = theFlow;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResultParameter apply(ProcessParameter input)
	{
		MockAction<ResultParameter> resultContainer = appendResultContainer(flow);
		
		flow.processAction().accept(input);
		
		removeResultContainer(resultContainer, flow);
		
		return resultContainer.getLastResult();
	}
}
