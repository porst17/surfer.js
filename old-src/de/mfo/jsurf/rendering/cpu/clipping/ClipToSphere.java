/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.mfo.jsurf.rendering.cpu.clipping;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3d;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import de.mfo.jsurf.rendering.cpu.Ray;

/**
 *
 * @author stussak
 */
public class ClipToSphere extends Clipper
{
    double radius;

    public ClipToSphere()
    {
	this(1.0);
    }
    public ClipToSphere(double radius)
    {
	super();
	this.radius= radius;
    }

    @Override
    public List<Vector2d> clipRay(Ray r)
    {
	Vector3d my_o= new Vector3d(r.o);
	Vector3d my_d= new Vector3d(r.d);
	double length= my_d.length();
	my_d.scale(1.0f / length);

	// solve algebraic
	double B= -my_o.dot(my_d);
	double C= my_o.dot(my_o) - radius * radius;
	double D= B * B - C;

	List<Vector2d> intervals= new ArrayList<Vector2d>();
	if (D >= 0.0)
	{
	    double sqrtD= Math.sqrt(D);
	    Vector2d result= new Vector2d(B - sqrtD, B + sqrtD);
	    result.scale(1.0 / length);
	    intervals.add(result);
	}
	return intervals;
    }

    @Override
    public boolean clipPoint(Point3d p)
    {
	return p.x * p.x + p.y * p.y + p.z * p.z <= radius * radius;
    }

    @Override
    public boolean pointClippingNecessary()
    {
	return false;
    }
}
