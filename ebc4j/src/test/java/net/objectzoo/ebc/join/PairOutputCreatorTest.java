package net.objectzoo.ebc.join;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import net.objectzoo.ebc.util.Pair;

@SuppressWarnings({ "rawtypes", "unchecked", "javadoc" })
public class PairOutputCreatorTest
{
	private PairOutputCreator sut = new PairOutputCreator();
	
	@Test
	public void creates_pair_with_given_values()
	{
		Pair result = sut.createOutput(1, 2);
		
		assertThat(result, is(new Pair(1, 2)));
	}
}