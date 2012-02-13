package net.objectzoo.ebc.executor;

public interface FlowCreator
{
	<FlowType> FlowType createFlow(Class<? extends FlowType> flowClass);
}
