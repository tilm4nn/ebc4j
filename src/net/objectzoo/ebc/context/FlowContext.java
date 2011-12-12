package net.objectzoo.ebc.context;

import java.util.HashMap;
import java.util.Map;

public class FlowContext
{
	private Map<Object, Object> attributes = new HashMap<Object, Object>();
	
	public void putAttribute(Object key, Object value)
	{
		attributes.put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Object key)
	{
		return (T) attributes.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Object key, T initialValue)
	{
		T attribute = (T) attributes.get(key);
		if (attribute == null && initialValue != null)
		{
			attribute = initialValue;
			attributes.put(key, attribute);
		}
		return attribute;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T removeAttribute(Object key)
	{
		return (T) attributes.remove(key);
	}
	
}
