package net.objectzoo.ebc.split;

import net.objectzoo.ebc.impl.ProcessAndResultBase;

/**
 * The {@code SplitProcess} distributes the process and its parameter to multiple subscribers.
 * 
 * @author tilmann
 * 
 * @param <Parameter>
 *        the type of the process parameter
 */
public class SplitProcess<Parameter> extends ProcessAndResultBase<Parameter, Parameter>
{
	@Override
	protected void process(Parameter parameter)
	{
		sendResult(parameter);
	}
}
