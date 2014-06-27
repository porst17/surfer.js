
package de.mfo.jsurf;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class BufferedImageGenerator implements ImageGenerator
{
    protected Timer timer;
    protected int size;
    private String jsurf_filename;
    private String output_filename;

    public void setSize(int size)
    {
	this.size = size;
    }

    public BufferedImageGenerator(int size, String jsurf_filename, String output_filename)
    {
	this.size = size;
	this.jsurf_filename = jsurf_filename;
	this.output_filename = output_filename;
    }

    public BufferedImageGenerator(int size)
    {
	this.size = size;
    }

    public BufferedImage createBufferedImageFromRGB(ImgBuffer ib)
    {
	int w = ib.width;
	int h = ib.height;

	DirectColorModel colormodel = new DirectColorModel(24, 0xff0000, 0xff00, 0xff);
	SampleModel sampleModel = colormodel.createCompatibleSampleModel(w, h);
	DataBufferInt data = new DataBufferInt(ib.rgbBuffer, w * h);
	WritableRaster raster = WritableRaster.createWritableRaster(sampleModel, data, new Point(0, 0));
	return new BufferedImage(colormodel, raster, false, null);
    }

    public static BufferedImage flipV(BufferedImage bi)
    {
	AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
	tx.translate(0, -bi.getHeight(null));
	AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	return op.filter(bi, null);
    }

    public static void saveToPNG(OutputStream os, BufferedImage bufferedImage, String output_filename) throws java.io.IOException
    {
	try
	{
	    bufferedImage = flipV(bufferedImage);
	    javax.imageio.ImageIO.write(bufferedImage, "png", os);
	    System.exit(0);
	}
	catch (Exception e)
	{
	    System.err.println("Unable to save to file  " + output_filename);
	    e.printStackTrace();
	    System.exit(-4);
	}
    }

    public void draw(ImgBuffer imgBuffer, int aSize)
    {
	try
	{
	    BufferedImage bufferedImage = createBufferedImageFromRGB(imgBuffer);

	    if (output_filename != null)
	    {
		OutputStream os;
		if (output_filename.equals("-"))
		    os = System.out;
		else
		    os = new FileOutputStream(new File(output_filename));

		saveToPNG(os, bufferedImage, output_filename);
	    }
	    else
	    {
		// display the image in a window 
		final String window_title = "jsurf: " + jsurf_filename;
		final BufferedImage window_image = bufferedImage;
		SwingUtilities.invokeLater(new Runnable()
		{
		    public void run()
		    {
			JFrame f = new JFrame(window_title);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.getContentPane().add(new JLabel(new ImageIcon(window_image)));
			f.pack();
			f.setResizable(false);
			f.setVisible(true);
		    }
		});
	    }

	}
	catch (Exception e)
	{
	    System.err.println("An error occurred during rendering.");
	    e.printStackTrace();
	    System.exit(-3);
	}
    }

    public void startTimerPeriodically(final Runnable runnable, int milliseconds)
    {
	timer = new Timer();
	TimerTask timerTask = new TimerTask()
	{
	    public void run()
	    {
		runnable.run();
	    }
	};
	timer.schedule(timerTask, 0, 10);
    }

    public void cancelTimer()
    {
	timer.cancel();
    }
}