package net.objectzoo.ebc.join;

@SuppressWarnings("javadoc")
public class JoinBaseImpl extends JoinBase<Object, Object, Object>
{
	public JoinBaseImpl(JoinOutputCreator<Object, Object, Object> outputCreator)
	{
		this.outputCreator = outputCreator;
	}
	
	private final JoinOutputCreator<Object, Object, Object> outputCreator;
	
	protected Object createOutput(Object input1, Object input2)
	{
		return outputCreator.createOutput(input1, input2);
	}
}
