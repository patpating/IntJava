package ttl.larku.streams.split;

import static java.util.stream.Collectors.toList;
import static ttl.larku.streams.split.GoTill.makeGoTill;

import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vavr.control.Try;

/**
 * @author whynot
 */
public class GoTillTestVavrTry {
	private static Logger logger = LoggerFactory.getLogger(GoTillTestVavrTry.class);

	public static void main(String[] args) {
		List<String> fileNames = Arrays.asList(".gitignore", ".classpath", "doesNotExist", "pom.xml");
//       callFileTheOldWay(fileNames);
		callFilesWithWrappedExceptions(fileNames);
	}

	public static void callFilesWithWrappedExceptions(List<String> fileNames) {
		List<Try<Character>> firstChars = filesOnlyTillFirstException(fileNames);

		firstChars.forEach(tw -> {
			tw.onSuccess(ch -> {
				// send to DB
				System.out.println(ch);
			}).onFailure(e -> {
				logger.error(e.toString());
			});
		});
	}

	public static List<Try<Character>> filesOnlyTillFirstException(List<String> fileNames) {
		// List<Try<Character>> firstChars = fileNames.stream()
		Stream<Try<Character>> firstCharsStream = fileNames.stream().map(fileName -> {
			return Try.of(() -> {
				try (FileReader reader = new FileReader(fileName)) {
					int ch = reader.read();
					return (char) ch;
				}
			});
		});

		List<Try<Character>> firstChars = makeGoTill(firstCharsStream, tw -> tw.isFailure()).collect(toList());

		return firstChars;
	}
}
