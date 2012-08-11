package net.objectzoo.ebc.executor;

public interface FlowFactory
{
	<FlowType> FlowType createFlow(Class<? extends FlowType> flowClass);
}
