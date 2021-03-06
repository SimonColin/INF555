import java.util.*;

/**
 * Simple scheme implementing linear interpolation
 * 
 * @author Luca Castelli Aleardi (INF555, 2012)
 *
 */
public class LinearInterpolation extends Interpolation {
	
	public LinearInterpolation(Draw frame) {
		super(frame);
	}
	
	public void interpolate() {	
		for(int i=0;i<this.points.length;i++)
		{
			Arrays.sort(this.points);
			if(i > 0)
				drawSegment(this.points[i - 1], this.points[i]);
		}
		//throw new Error("To be completed: TD1");
	}
	
}
