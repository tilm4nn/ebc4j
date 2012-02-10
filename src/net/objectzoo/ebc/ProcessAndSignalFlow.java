package net.objectzoo.ebc;

/**
 * An EBC that is capable of processing input with a possible outcome of a signal being sent.
 * 
 * @author tilmann
 * 
 * @param <ProcessParameter>
 *        the type of input processed by this EBC
 */
public interface ProcessAndSignalFlow<ProcessParameter> extends CanProcess<ProcessParameter>, SendsSignal
{
	
}
