package net.objectzoo.ebc.executor;

import static net.objectzoo.ebc.executor.FlowExecutorHelpers.appendResultContainer;
import static net.objectzoo.ebc.executor.FlowExecutorHelpers.removeResultContainer;
import net.objectzoo.delegates.Func0;
import net.objectzoo.ebc.StartAndResultFlow;
import net.objectzoo.ebc.test.MockAction;

public class StartAndResultFlowToFunc0Adapter<ResultParameter> implements Func0<ResultParameter>
{
	private final StartAndResultFlow<ResultParameter> flow;
	
	public StartAndResultFlowToFunc0Adapter(StartAndResultFlow<ResultParameter> theFlow)
	{
		flow = theFlow;
	}
	
	@Override
	public ResultParameter invoke()
	{
		MockAction<ResultParameter> resultContainer = appendResultContainer(flow);
		
		flow.startAction().invoke();
		
		removeResultContainer(resultContainer, flow);
		
		return resultContainer.getLastResult();
	}
}
