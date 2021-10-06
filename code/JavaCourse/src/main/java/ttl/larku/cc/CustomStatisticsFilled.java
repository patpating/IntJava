package ttl.larku.cc;

public class CustomStatisticsFilled {
	private int maxX = Integer.MIN_VALUE;
	private int maxY = Integer.MIN_VALUE;
	private int minX = Integer.MAX_VALUE;
	private int minY = Integer.MAX_VALUE;

	private double averageX;
	private double averageY;
	private double sumX;
	private double sumY;
	
	private int count = 0;
	
	/**
	 * Add data to this CustomStatics object
	 * @param d
	 */
	public void accumulate(Data d) {
		this.maxX = Math.max(this.maxX,  d.xValue);
		this.maxY = Math.max(this.maxY,  d.yValue);

		this.minX = Math.min(this.minX, d.xValue);
		this.minY = Math.min(this.minY, d.yValue);

		sumX += d.xValue;
		sumY += d.yValue;
		
		this.count++;
	}
	
	/**
	 * Combine this CustomStatistics object with c2.
	 * Only done if using a parallel Stream
	 * @param c2
	 */
	public void combine(CustomStatisticsFilled c2) {
		this.maxX = Math.max(this.maxX, c2.maxX);
		this.maxY = Math.max(this.maxY, c2.maxY);

		this.minX = Math.min(this.minX, c2.minX);
		this.minY = Math.min(this.minY, c2.minY);

		this.sumX += c2.sumX;
		this.sumY += c2.sumY;

		this.count += c2.count;

		//This is a bomb waiting to go off.  The combiner
		//is called only for parallel streams, so this
		//code will *not* be called for serial streams.
		//This belongs either in the accumulator,
		//or in the 4 function collector implemented
		//in CustomStatisticsWithFinisher
		this.averageX = this.sumX / this.count;
		this.averageY = this.sumY / this.count;

	}

	/**
	 * A final opportunity to massage the result.
	 * Only called with using the 4 arg Collectors call
	 * Collectors.of(supplier, accum, combine, finish)
	 * and, you would have to change the accum, combine etc.
	 * methods to be static, with two arguments.
	 * @param almostFinal
	 * @return
	 */
	public CustomStatisticsFilled finisher(CustomStatisticsFilled almostFinal) {
		//Might calculate averages and deviations here and add them to the result
		
		return almostFinal;
	}

	@Override
	public String toString() {
		return "CustomStatisticsFilled{" +
				"maxX=" + maxX +
				", maxY=" + maxY +
				", minX=" + minX +
				", minY=" + minY +
				", averageX=" + averageX +
				", averageY=" + averageY +
				", sumX=" + sumX +
				", sumY=" + sumY +
				", count=" + count +
				'}';
	}
}
