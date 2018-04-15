import java.util.HashMap;
import java.util.Map;

public class ArgumentUtils {

	public static final String PASSWORD = "PASSWORD";
	public static final String EMAIL = "EMAIL";

	private Map<String, String> argumentsMap;

	ArgumentUtils(String[] args) {
		argumentsMap = parseArgumentList(args);
	}

	public boolean areCredentialsPresent() {
		return argumentsMap.containsKey(PASSWORD) && argumentsMap.containsKey(EMAIL);
	}

	public String getPassword() {
		return argumentsMap.get(PASSWORD);
	}

	public String getEmail() {
		return argumentsMap.get(EMAIL);
	}

	private Map<String, String> parseArgumentList(String[] args) {
		Map<String, String> argsMap = new HashMap<>();
		for (int i = 0; i < args.length - 1; i += 2) {
			String key = args[i];
			String value = args[i + 1];
			switch (key) {
				case "-p":
					argsMap.put(PASSWORD, value);
					break;
				case "-e":
					argsMap.put(EMAIL, value);
					break;
			}
		}
		return argsMap;
	}
}
