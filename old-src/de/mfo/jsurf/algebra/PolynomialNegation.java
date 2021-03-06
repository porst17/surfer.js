/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.mfo.jsurf.algebra;

/**
 *
 * @author Christian Stussak <christian at knorf.de>
 */
public class PolynomialNegation implements PolynomialOperation
{
    public PolynomialOperation operand;

    public PolynomialNegation()
    {
    }

    public PolynomialNegation(PolynomialOperation operand)
    {
	this.operand= operand;
    }

    public <RETURN_TYPE, PARAM_TYPE> RETURN_TYPE accept(Visitor<RETURN_TYPE, PARAM_TYPE> visitor, PARAM_TYPE arg)
    {
	return visitor.visit(this, arg);
    }
}
