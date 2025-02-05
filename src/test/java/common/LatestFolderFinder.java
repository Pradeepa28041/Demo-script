package common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LatestFolderFinder {

	private static final Pattern FOLDER_PATTERN = Pattern.compile("SparkReport (\\d+-\\w+-\\d+ \\d+-\\d+-\\d+)");
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d-MMM-yy H-mm-ss");

	public static String GetLatestFolderName(String path) {
		Path directoryPath = Paths.get(path);
		String latestFolder = null;
		try {
			latestFolder = getLatestFolderName(directoryPath);
			System.out.println("Latest Folder: " + latestFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return latestFolder;
	}

	private static String getLatestFolderName(Path directory) throws IOException {
		return Files.list(directory).filter(Files::isDirectory).map(Path::getFileName).map(Path::toString)
				.filter(name -> FOLDER_PATTERN.matcher(name).matches())
				.max(Comparator.comparing(LatestFolderFinder::extractDateTime))
				.orElseThrow(() -> new IllegalArgumentException("No valid folders found"));
	}

	private static LocalDateTime extractDateTime(String folderName) {
		Matcher matcher = FOLDER_PATTERN.matcher(folderName);
		if (matcher.find()) {
			String dateTimeString = matcher.group(1);
			return LocalDateTime.parse(dateTimeString, FORMATTER);
		}
		throw new IllegalArgumentException("Invalid folder name: " + folderName);
	}
}
