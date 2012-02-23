package net.objectzoo.ebc.executor;

import static net.objectzoo.ebc.executor.FlowExecutorHelpers.appendResultContainer;
import static net.objectzoo.ebc.executor.FlowExecutorHelpers.removeResultContainer;
import net.objectzoo.delegates.Func;
import net.objectzoo.ebc.ProcessAndResultFlow;
import net.objectzoo.ebc.test.MockAction;

public class ProcessAndResultFlowToFuncAdapter<ProcessParameter, ResultParameter> implements
	Func<ProcessParameter, ResultParameter>
{
	private final ProcessAndResultFlow<ProcessParameter, ResultParameter> flow;
	
	public ProcessAndResultFlowToFuncAdapter(ProcessAndResultFlow<ProcessParameter, ResultParameter> theFlow)
	{
		flow = theFlow;
	}
	
	@Override
	public ResultParameter invoke(ProcessParameter input)
	{
		MockAction<ResultParameter> resultContainer = appendResultContainer(flow);
		
		flow.processAction().invoke(input);
		
		removeResultContainer(resultContainer, flow);
		
		return resultContainer.getLastResult();
	}
}
