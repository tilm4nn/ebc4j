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
		flow.processAction().invoke(input);
	}
	
	public <InputParameter, OutputParameter, FlowType extends ProcessAndResultFlow<InputParameter, OutputParameter>> OutputParameter processAndReturnResult(Class<FlowType> flowClass,
																																							InputParameter input)
	{
		FlowType flow = ebcFlowCreator.createFlow(flowClass);
		
		MockAction<OutputParameter> resultContainer = appendResultContainer(flow);
		
		flow.processAction().invoke(input);
		
		return resultContainer.getLastResult();
	}
	
	public <FlowType extends CanStart> void start(Class<FlowType> flowClass)
	{
		FlowType flow = ebcFlowCreator.createFlow(flowClass);
		flow.startAction().invoke();
	}
	
	public <OutputParameter, FlowType extends StartAndResultFlow<OutputParameter>> OutputParameter startAndReturnResult(Class<FlowType> flowClass)
	{
		FlowType flow = ebcFlowCreator.createFlow(flowClass);
		
		MockAction<OutputParameter> resultContainer = appendResultContainer(flow);
		
		flow.startAction().invoke();
		
		return resultContainer.getLastResult();
	}
	
	private <OutputParameter> MockAction<OutputParameter> appendResultContainer(SendsResult<OutputParameter> flow)
	{
		SendsResult<OutputParameter> resultSender = (SendsResult<OutputParameter>) flow;
		MockAction<OutputParameter> resultContainer = new MockAction<OutputParameter>();
		resultSender.resultEvent().subscribe(resultContainer);
		return resultContainer;
	}
	
}
