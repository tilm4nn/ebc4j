package net.objectzoo.ebc;

/**
 * An EBC that is capable of being invoked with a possible outcome of a result being sent.
 * 
 * @author tilmann
 * 
 * @param <ResultParameter>
 *        the type of output of this EBC
 */
public interface StartAndResultFlow<ResultParameter> extends CanStart, SendsResult<ResultParameter>
{
	
}
