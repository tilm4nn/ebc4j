package net.objectzoo.ebc.executor;

import net.objectzoo.ebc.CanProcess;
import net.objectzoo.ebc.CanStart;
import net.objectzoo.ebc.ProcessAndResultFlow;
import net.objectzoo.ebc.SendsResult;
import net.objectzoo.ebc.StartAndResultFlow;
import net.objectzoo.ebc.test.MockAction;

public class FlowExecutor
{
	private final FlowCreator ebcFlowCreator;
	
	public FlowExecutor(FlowCreator theEbcFlowCreator)
	{
		ebcFlowCreator = theEbcFlowCreator;
	}
	
	public <InputParameter, FlowType extends CanProcess<InputParameter>> void process(Class<FlowType> flowClass,
																					  InputParameter input)
	{
		FlowType flow = ebcFlowCreator.createFlow(flowClass);
		
		process(flow, input);
	}
	
	public <InputParameter, OutputParameter, FlowType extends ProcessAndResultFlow<InputParameter, OutputParameter>> OutputParameter processAndReturnResult(Class<FlowType> flowClass,
																																							InputParameter input)
	{
		FlowType flow = ebcFlowCreator.createFlow(flowClass);
		
		return processAndReturnResult(flow, input);
	}
	
	public <FlowType extends CanStart> void start(Class<FlowType> flowClass)
	{
		FlowType flow = ebcFlowCreator.createFlow(flowClass);
		
		start(flow);
	}
	
	public <OutputParameter, FlowType extends StartAndResultFlow<OutputParameter>> OutputParameter startAndReturnResult(Class<FlowType> flowClass)
	{
		FlowType flow = ebcFlowCreator.createFlow(flowClass);
		
		return startAndReturnResult(flow);
	}
	
	public static <InputParameter, FlowType extends CanProcess<InputParameter>> void process(FlowType flow,
																							 InputParameter input)
	{
		flow.processAction().invoke(input);
	}
	
	public static <InputParameter, OutputParameter, FlowType extends ProcessAndResultFlow<InputParameter, OutputParameter>> OutputParameter processAndReturnResult(FlowType flow,
																																								   InputParameter input)
	{
		MockAction<OutputParameter> resultContainer = appendResultContainer(flow);
		
		flow.processAction().invoke(input);
		
		return resultContainer.getLastResult();
	}
	
	public static <FlowType extends CanStart> void start(FlowType flow)
	{
		flow.startAction().invoke();
	}
	
	public static <OutputParameter, FlowType extends StartAndResultFlow<OutputParameter>> OutputParameter startAndReturnResult(FlowType flow)
	{
		MockAction<OutputParameter> resultContainer = appendResultContainer(flow);
		
		flow.startAction().invoke();
		
		return resultContainer.getLastResult();
	}
	
	private static <OutputParameter> MockAction<OutputParameter> appendResultContainer(SendsResult<OutputParameter> flow)
	{
		SendsResult<OutputParameter> resultSender = (SendsResult<OutputParameter>) flow;
		MockAction<OutputParameter> resultContainer = new MockAction<OutputParameter>();
		resultSender.resultEvent().subscribe(resultContainer);
		return resultContainer;
	}
}
