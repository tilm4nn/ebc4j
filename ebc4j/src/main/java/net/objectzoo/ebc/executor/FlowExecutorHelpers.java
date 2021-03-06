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

import net.objectzoo.ebc.SendsResult;
import net.objectzoo.ebc.test.MockAction;

final class FlowExecutorHelpers
{
	private FlowExecutorHelpers()
	{
		// No instances will be made
	}
	
	static <FlowType> FlowType createFlow(Class<FlowType> flowClass, FlowFactory flowCreator)
	{
		FlowType flow = flowCreator.createFlow(flowClass);
		
		if (flow == null)
		{
			throw new IllegalStateException("Could not instantiate flow of type " + flowClass.getName());
		}
		
		return flow;
	}
	
	static <OutputParameter> MockAction<OutputParameter> appendResultContainer(SendsResult<OutputParameter> flow)
	{
		MockAction<OutputParameter> resultContainer = new MockAction<OutputParameter>();
		flow.resultEvent().subscribe(resultContainer);
		return resultContainer;
	}
	
	static <OutputParameter> void removeResultContainer(MockAction<OutputParameter> resultContainer,
														SendsResult<OutputParameter> flow)
	{
		flow.resultEvent().unsubscribe(resultContainer);
	}
}
