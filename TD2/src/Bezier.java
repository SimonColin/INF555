import java.util.LinkedList;

public class Bezier extends Curve {

	Transformation_2 transformation;

	public Bezier(DrawCurve frame, LinkedList<Point_2> p) {
		super(frame, p);
		transformation=null;
	}

	public Bezier(DrawCurve frame, LinkedList<Point_2> p, Transformation_2 transformation) {
		super(frame, p);
		this.transformation=transformation;
	}
	
	public Bezier(DrawCurve frame, Point_2[] points, Transformation_2 transformation) {
		super(frame, points);
		this.transformation=transformation;
	}

	/**
	 * Draw the control polygon
	 */
	public void drawControlPolygon() {
		this.frame.stroke(0, 0, 255);
	    for(int i=1;i<this.points.length;i++) {
	    	drawSegment(this.points[i], this.points[i-1]);
	    	//drawSegment(transformation.transform(points[i]), transformation.transform(points[i-1]));
	    }
		this.frame.stroke(0, 0, 0);
	}

	/**
	 * Evaluate the curve for parameter t
	 * Return point (x(t), y(t))
	 */
	public Point_2 evaluate(double t) {
		//return recursiveDeCasteljau(this.points.length-1, 0, t);
		return iterativeDeCasteljau(t);
		//return bernsteinBezier(t);
	}

	/**
	 * Perform the subdivision (once) of the Bezier curve (with parameter t)
	 * Return two Bezier curves (with n control points each)
	 */
	public Bezier[] subdivide(double t) {
		Point_2[] controlPolygon=this.points;
		int n=this.points.length-1; // degree and number of edges of the control polygon

		Point_2[] b0=new Point_2[n+1]; // first control polygon
		Point_2[] b1=new Point_2[n+1]; // second control polygon
		Bezier[] result=new Bezier[2]; // the pair of Bezier curves to return as result

		Point_2[] array = controlPolygon.clone();
		
		b0[0] = array[0];
		b1[0] = array[n];
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < n - i; j++)
			{
				Point_2 a = array[j].multiplyByScalar(1 - t);
				Point_2 b = array[j + 1].multiplyByScalar(t);
				Vector_2 v = new Vector_2(b.x, b.y);
				array[j] = a.sum(v);
			}
			b0[i + 1] = array[0];
			b1[i + 1] = array[n - i - 1];
		}
/*		Point_2[] array = this.points.clone();
		for(int i = 0; i < array.length - 1; i++)
		{
			for(int j = 0; j < array.length - 1 - i; j++)
			{
				Point_2 a = array[j + 1].multiplyByScalar(t);
				Point_2 b = array[j].multiplyByScalar(1 - t);
				Vector_2 v = new Vector_2(b.x, b.y);
				array[j] = a.sum(v);
			}
			b0[i] = array[0];
			b1[i] = array[n - i];
		} */
/* 		Point_2 a = b0[n - 1].multiplyByScalar(t);
		Point_2 b = b1[n - 1].multiplyByScalar(1 - t);
		b0[n] = a.sum(new Vector_2(b.x, b.y));
		b1[n] = b0[n]; */
		result[0] = new Bezier(this.frame, b0, this.transformation);
		result[1] = new Bezier(this.frame, b1, this.transformation);
		
		return result;
		
		//throw new Error("To be completed: TD2");
	}

	/**
	 * Plot the curve (in the frame), for t=0..1, with step dt
	 */
	public void plotCurve(double dt) {
	//	this.frame.stroke(255, 255, 255);
		this.drawControlPolygon();
		this.drawControlPoints();
		Point_2 a = evaluate(0);
		Point_2 b;
		for(double i = dt; i < 1.; i += dt)
		{
			b = evaluate(i);
			drawSegment(a, b);
			a = b;
		}
		Bezier[] sub = subdivide(0.5);
		sub[0].frame.stroke(255, 0, 0);
		sub[0].drawControlPolygon();
		sub[1].drawControlPolygon();
		
		// to be completed TD2
	}

	/**
	 * Perform the rendering of the curve using subdivision approach
	 * Perform the subdivision n times
	 */
	public void subdivisionRendering(int n) {
		this.drawControlPolygon(); // draw original control polygon
		this.drawControlPoints(); // draw original control points
		LinkedList<Bezier> subCurves=new LinkedList<Bezier>();
		
		// to be completed TD2
		if(this.points.length<3) return;

	}

	public Point_2 recursiveDeCasteljau(int r, int i, double t) {
		if(r == 0)
			return this.points[i];
		else
		{
			Point_2 a = recursiveDeCasteljau(r - 1, i, t).multiplyByScalar(1 - t);
			Point_2 b = recursiveDeCasteljau(r - 1, i + 1, t).multiplyByScalar(t);
			Vector_2 v = new Vector_2(a.x, a.y);
			return b.sum(v);
		}
		//throw new Error("TD2 to be completed");
	}

	/**
	 * Perform the (iterative) De Casteljau algorithm to evaluate b(t)
	 */
	public Point_2 iterativeDeCasteljau(double t) {
		Point_2 [] array = this.points.clone();
		for(int i = 0; i < array.length - 1; i++)
		{
			for(int j = 0; j < array.length - 1 - i; j++)
			{
				Point_2 a = array[j + 1].multiplyByScalar(t);
				Point_2 b = array[j].multiplyByScalar(1 - t);
				Vector_2 v = new Vector_2(b.x, b.y);
				array[j] = a.sum(v);
			}
		}
		return array[0];
		//throw new Error("TD2 to be completed");
	}
	
	public int binco(int n, int i)
	{
		if(i == 0 || i == n)
				return n;
		else
			return (n - i + 1) * binco(n, i - 1) / i;
	}
	
/*	public Point_2 bernsteinBezier(double dt)
	{
		int n = this.points.length;
		int[] bincos = new int[n];
		bincos[0] = 1;
		for(int i = 1; i < n; i++)
			bincos[i] = bincos[i - 1] * (n - i + 1) / i;
		for(double i = dt; i < 1; i += dt)
			
	} */
	
	public Point_2 bernsteinBezier(double dt)
	{
		int n = this.points.length;
		double s = 1 - dt;
		double t = dt;
		Point_2 pt;
		Vector_2 v;
		Point_2 out = this.points[0].multiplyByScalar(binco(n, 0) * s);
		for(int i = 1; i < n; i++)
		{
			pt = this.points[i].multiplyByScalar(binco(n, i) * t);
			v = new Vector_2(pt.x, pt.y);
			out = out.multiplyByScalar(s).sum(v);
			t = t * dt;
		}
		return out;
	}

}
