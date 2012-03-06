package net.objectzoo.ebc.split;

import net.objectzoo.ebc.impl.StartAndSignalBase;

/**
 * The {@code SplitSignal} distributes the signal to multiple subscribers.
 * 
 * @author tilmann
 */
public class SplitSignal extends StartAndSignalBase
{
	@Override
	protected void start()
	{
		sendSignal();
	}
}
