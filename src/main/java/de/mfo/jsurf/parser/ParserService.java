
package de.mfo.jsurf.parser;

import de.mfo.jsurf.algebra.PolynomialOperation;

public class ParserService
{
    public PolynomialOperation parse(String s) throws Exception
    {
	return AlgebraicExpressionParser.parse(s);
    }
}
