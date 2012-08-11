package net.objectzoo.ebc.executor;

import net.objectzoo.ebc.SendsResult;
import net.objectzoo.ebc.test.MockAction;

public abstract class FlowExecutorHelpers
{
	private FlowExecutorHelpers()
	{
		
	}
	
	public static <FlowType> FlowType createFlow(Class<FlowType> flowClass, FlowFactory flowCreator)
	{
		FlowType flow = flowCreator.createFlow(flowClass);
		
		if (flow == null)
		{
			throw new IllegalStateException("Could not instantiate flow of type " + flowClass.getName());
		}
		
		return flow;
	}
	
	public static <OutputParameter> MockAction<OutputParameter> appendResultContainer(SendsResult<OutputParameter> flow)
	{
		SendsResult<OutputParameter> resultSender = (SendsResult<OutputParameter>) flow;
		MockAction<OutputParameter> resultContainer = new MockAction<OutputParameter>();
		resultSender.resultEvent().subscribe(resultContainer);
		return resultContainer;
	}
	
	public static <OutputParameter> void removeResultContainer(MockAction<OutputParameter> resultContainer,
															   SendsResult<OutputParameter> flow)
	{
		flow.resultEvent().unsubscribe(resultContainer);
	}
}
