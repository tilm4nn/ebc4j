package net.objectzoo.ebc.split;

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;

/**
 * Adapts an {@link Action0} to provide the {@link Action} interface. The implementations just
 * forwards any calls ignoring the given call parameter value.
 * 
 * @author tilmann
 * 
 * @param <T>
 *        The {@link Action}'s parameter type
 */
public class Action0ToAction<T> implements Action<T>
{
	private final Action0 action;
	
	/**
	 * Create a new {@code Action0ToAction} forwarding the incoming calls to the given
	 * {@link Action0} instance.
	 * 
	 * @param action
	 */
	public Action0ToAction(Action0 action)
	{
		this.action = action;
	}
	
	@Override
	public void invoke(T parameter)
	{
		action.invoke();
	}
	
}
