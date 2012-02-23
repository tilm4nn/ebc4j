package net.objectzoo.ebc.executor;

import net.objectzoo.ebc.CanProcess;
import net.objectzoo.ebc.CanStart;
import net.objectzoo.ebc.ProcessAndResultFlow;
import net.objectzoo.ebc.StartAndResultFlow;

public class FlowExecutor
{
	private final FlowCreator flowCreator;
	
	public FlowExecutor(FlowCreator theFlowCreator)
	{
		flowCreator = theFlowCreator;
	}
	
	public <ProcessParameter, FlowType extends CanProcess<ProcessParameter>> void process(Class<FlowType> flowClass,
																						  ProcessParameter input)
	{
		FlowType flow = FlowExecutorHelpers.createFlow(flowClass, flowCreator);
		
		process(flow, input);
	}
	
	public <ProcessParameter, ResultParameter, FlowType extends ProcessAndResultFlow<ProcessParameter, ResultParameter>> ResultParameter processAndReturnResult(Class<FlowType> flowClass,
																																								ProcessParameter input)
	{
		FlowType flow = FlowExecutorHelpers.createFlow(flowClass, flowCreator);
		
		return processAndReturnResult(flow, input);
	}
	
	public <FlowType extends CanStart> void start(Class<FlowType> flowClass)
	{
		FlowType flow = FlowExecutorHelpers.createFlow(flowClass, flowCreator);
		
		start(flow);
	}
	
	public <ResultParameter, FlowType extends StartAndResultFlow<ResultParameter>> ResultParameter startAndReturnResult(Class<FlowType> flowClass)
	{
		FlowType flow = FlowExecutorHelpers.createFlow(flowClass, flowCreator);
		
		return startAndReturnResult(flow);
	}
	
	public static <ProcessParameter, FlowType extends CanProcess<ProcessParameter>> void process(FlowType flow,
																								 ProcessParameter input)
	{
		flow.processAction().invoke(input);
	}
	
	public static <ProcessParameter, ResultParameter, FlowType extends ProcessAndResultFlow<ProcessParameter, ResultParameter>> ResultParameter processAndReturnResult(FlowType flow,
																																									   
																																									   ProcessParameter input)
	{
		return new ProcessAndResultFlowToFuncAdapter<ProcessParameter, ResultParameter>(flow).invoke(input);
	}
	
	public static <FlowType extends CanStart> void start(FlowType flow)
	{
		flow.startAction().invoke();
	}
	
	public static <ResultParameter, FlowType extends StartAndResultFlow<ResultParameter>> ResultParameter startAndReturnResult(FlowType flow)
	{
		return new StartAndResultFlowToFunc0Adapter<ResultParameter>(flow).invoke();
	}
	
}
