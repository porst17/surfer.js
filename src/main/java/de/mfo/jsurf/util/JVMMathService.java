
package de.mfo.jsurf.util;

public final class JVMMathService implements MathService
{
    public double nextPowerOfTwo(double d) // computes the next power of two with respect to outward rounding
    {
	long bits = java.lang.Double.doubleToLongBits(d);
	if ((bits & 0x000fffffffffffffL) != 0L)
	{
	    bits = java.lang.Double.doubleToLongBits(2.0 * d); // "round up" -> increase exponent by one
	    bits &= 0xfff0000000000000L; // remove mantissa bits
	}
	return java.lang.Double.longBitsToDouble(bits);
    }
}