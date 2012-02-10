package net.objectzoo.ebc;

/**
 * An EBC that is capable of processing input with a possible outcome of a result being sent.
 * 
 * @author tilmann
 * 
 * @param <ProcessParameter>
 *        the type of input processed by this EBC
 * @param <ResultParameter>
 *        the type of output of this EBC
 */
public interface ProcessAndResultFlow<ProcessParameter, ResultParameter> extends CanProcess<ProcessParameter>,
	SendsResult<ResultParameter>
{
	
}
