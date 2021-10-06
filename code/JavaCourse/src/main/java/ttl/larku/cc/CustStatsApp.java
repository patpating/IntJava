package ttl.larku.cc;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CustStatsApp {

	private static AtomicInteger supplier = new AtomicInteger(0);
	private static AtomicInteger accum = new AtomicInteger(0);
	private static AtomicInteger comb = new AtomicInteger(0);

	public static void main(String[] args) throws IOException {
//		foo();
		customStatsWithFinisher();

	}
	
	public static void foo() {
		List<Data> data = Arrays.asList(
				new Data(0,10),
				new Data(4,20),
				new Data(6,20),
				new Data(0,10)
				/*
				new Data(67,80),
				new Data(22,900),
				new Data(58,89),
				new Data(35,80),
				new Data(49,80),
				new Data(88,22),
				new Data(100,82),
				new Data(34,50),
				new Data(45,70),
				new Data(43,50),
				new Data(28,60),
				new Data(99,70),
				new Data(28,10)
				*/
		);
		
		CustomStatisticsFilled result = data.parallelStream()
				.collect(() -> {
					//System.out.println("supplier: " + supplier.getAndIncrement() + ", " + Thread.currentThread().getName());
					return new CustomStatisticsFilled();
				},
				(cs, d) -> {
					//System.out.println("accum: " + accum.getAndIncrement() + ", " + Thread.currentThread().getName());
					cs.accumulate(d);
				}, 
				(cs1, cs2) -> { 
					//System.out.println("comb: " + comb.getAndIncrement() + ", " + Thread.currentThread().getName());
					cs1.combine(cs2);
				});

		System.out.println("Result: " + result);
		//System.out.println(supplier.get() + ", " + accum.get() + ", " + comb.get());
		
		//Simpler way
		CustomStatistics result2 = data.stream()
				.parallel()
				.collect(CustomStatistics::new,
						CustomStatistics::accumulate,
						CustomStatistics::combine);
		
		
		System.out.println("result = " + result);

	}
	
	public void intStreams(List<Integer> someList) {
		int sum = IntStream.range(0, 10).parallel().reduce(0, (cs, i) -> cs + i);

		Optional<Integer> optResult = someList.stream().parallel().reduce((cs, i) -> cs + i);
		
		optResult.ifPresent((it) -> System.out.println(it)); 
	}
	
	public static void flatMapper() {
		String [][] sarr = { {"one", "two", "three" },
				{"four", "five", "six" }
		};
		
		/*
		for(int i =0; i < sarr.length; i++) {
			for(int j = 0; j < sarr[i].length; j++) {
				System.out.println(sarr[i][j]);
			}
		}
		
		List<String> ssa = Arrays.stream(sarr)
				.peek(sa -> System.out.println("Peek1 with " + sa))
				.flatMap(sa -> Arrays.stream(sa))
				.peek(str -> System.out.println("Peek 2 with " + str))
				.collect(Collectors.toList());
		*/

		List<Stream<String>> x = Arrays.stream(sarr)
				.peek(sa -> System.out.println("Peek1 with " + sa))
				.map(sa -> Arrays.stream(sa))
				.peek(str -> System.out.println("Peek 2 with " + str))
				.collect(Collectors.toList());

		
	}

	public static void customStatsWithFinisher() {
		List<Data> data = Arrays.asList(
				new Data(0,10),
				new Data(4,20),
				new Data(6,20),
				new Data(0,10)
				/*
				new Data(67,80),
				new Data(22,900),
				new Data(58,89),
				new Data(35,80),
				new Data(49,80),
				new Data(88,22),
				new Data(100,82),
				new Data(34,50),
				new Data(45,70),
				new Data(43,50),
				new Data(28,60),
				new Data(99,70),
				new Data(28,10)
				*/
		);

		CustomStatisticsWithFinisher cs = data.stream().collect(Collector.of(CustomStatisticsWithFinisher::new,
				CustomStatisticsWithFinisher::accumulate,
				CustomStatisticsWithFinisher::combine,
				CustomStatisticsWithFinisher::finisher
				));

		System.out.println("stats: " + cs);
	}
}
