package ttl.larku.cc;

public class CustomStatisticsWithFinisher {
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
	public static void accumulate(CustomStatisticsWithFinisher cs, Data d) {
		cs.maxX = Math.max(cs.maxX,  d.xValue);
		cs.maxY = Math.max(cs.maxY,  d.yValue);

		cs.minX = Math.min(cs.minX, d.xValue);
		cs.minY = Math.min(cs.minY, d.yValue);

		cs.sumX += d.xValue;
		cs.sumY += d.yValue;
		
		cs.count++;
	}
	
	/**
	 * Combine this CustomStatistics object with c2.
	 * Only done if using a parallel Stream
	 * @param c2
	 */
	public  static CustomStatisticsWithFinisher combine(CustomStatisticsWithFinisher cs1, CustomStatisticsWithFinisher c2) {
		cs1.maxX = Math.max(cs1.maxX, c2.maxX);
		cs1.maxY = Math.max(cs1.maxY, c2.maxY);

		cs1.minX = Math.min(cs1.minX, c2.minX);
		cs1.minY = Math.min(cs1.minY, c2.minY);

		cs1.sumX += c2.sumX;
		cs1.sumY += c2.sumY;

		cs1.count += c2.count;


		return cs1;
	}

	/**
	 * A final opportunity to massage the result.
	 * Only called when using the 4 arg Collectors call
	 * Collectors.of(supplier, accum, combine, finish)
	 * and, you would have to change the accum, combine etc.
	 * methods to be static, with two arguments.
	 * @param almostFinal
	 * @return
	 */
	public static CustomStatisticsWithFinisher finisher(CustomStatisticsWithFinisher almostFinal) {
		//Might calculate averages and deviations here and add them to the result
		almostFinal.averageX = almostFinal.sumX / almostFinal.count;
		almostFinal.averageY = almostFinal.sumY / almostFinal.count;

		return almostFinal;
	}

	@Override
	public String toString() {
		return "CustomStatisticsFinisher{" +
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
