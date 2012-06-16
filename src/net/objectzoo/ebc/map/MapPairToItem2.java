package net.objectzoo.ebc.map;

import net.objectzoo.ebc.impl.ProcessAndResultBase;
import net.objectzoo.ebc.util.Pair;

public class MapPairToItem2<T> extends ProcessAndResultBase<Pair<?, T>, T>
{
	@Override
	protected void process(Pair<?, T> pair)
	{
		sendResult(pair.getItem2());
	}
}
