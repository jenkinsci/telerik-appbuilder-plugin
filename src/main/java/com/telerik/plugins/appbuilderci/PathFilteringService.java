package com.telerik.plugins.appbuilderci;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.stream.Collectors;

public class PathFilteringService {
	private List<PathMatcher> ignoreFilesRules;

	public PathFilteringService(Path ignoreRulesFilePath) throws IOException {
		if (Files.exists(ignoreRulesFilePath)) {
			FileSystem fs = FileSystems.getDefault();			
			this.ignoreFilesRules = Files.lines(ignoreRulesFilePath)
					.map(path -> fs.getPathMatcher("glob:" + path))
					.collect(Collectors.toList());
		}
	}

	public boolean isFileExcluded(Path path) {
		if(this.ignoreFilesRules != null){
			return this.ignoreFilesRules.stream().anyMatch(rule -> rule.matches(path));
		}
		return false;
	}
}