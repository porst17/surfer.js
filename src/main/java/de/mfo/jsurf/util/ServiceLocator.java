
package de.mfo.jsurf.util;

import de.mfo.jsurf.parser.ParserService;

public class ServiceLocator
{
    private static ServiceLocator instance;
    private MathService mathService = new JVMMathService();

    public ParserService getParserService()
    {
	return new ParserService();
    }

    public MathService getMathService()
    {
	return mathService;
    }

    public static ServiceLocator getInstance()
    {
	if (instance == null)
	    instance = new ServiceLocator();

	return instance;
    }

    public void setMathService(MathService mathService)
    {
        this.mathService = mathService;
    }

}
