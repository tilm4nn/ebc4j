package net.objectzoo.ebc.adapters;

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
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Action0ToAction other = (Action0ToAction) obj;
		if (action == null)
		{
			if (other.action != null) return false;
		}
		else if (!action.equals(other.action)) return false;
		return true;
	}
	
}
