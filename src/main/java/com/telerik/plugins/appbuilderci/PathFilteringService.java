package com.telerik.plugins.appbuilderci;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.stream.Stream;

public class PathFilteringService {
	private Stream<PathMatcher> ignoreFilesRules;

	public PathFilteringService(Path ignoreRulesFilePath) throws IOException {
		if (Files.exists(ignoreRulesFilePath)) {
			FileSystem fs = FileSystems.getDefault();			
			this.ignoreFilesRules = Files.lines(ignoreRulesFilePath).map(path -> fs.getPathMatcher("glob:" + path));
		}
	}

	public boolean isFileExcluded(Path path) {
		if(this.ignoreFilesRules != null){
			return this.ignoreFilesRules.anyMatch(rule -> rule.matches(path));
		}
		return false;
	}
}