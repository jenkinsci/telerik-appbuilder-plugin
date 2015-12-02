package com.telerik.plugins.appbuilderci;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;

public class PathFilteringService {
	private List<PathMatcher> ignoreFilesRules;

	public PathFilteringService(Path ignoreRulesFilePath) throws IOException {
		if (Files.exists(ignoreRulesFilePath)) {
			FileSystem fs = FileSystems.getDefault();	
			this.ignoreFilesRules = new ArrayList<PathMatcher>();
			List<String> lines = Files.readAllLines(ignoreRulesFilePath, Charset.defaultCharset());			
			for(String line : lines){
				this.ignoreFilesRules.add(fs.getPathMatcher("glob:" + line));
			}
		}
	}

	public boolean isFileExcluded(Path path) {
		if(this.ignoreFilesRules != null){
			for(PathMatcher rule : this.ignoreFilesRules){
				if(rule.matches(path)){
					return true;
				}
			}
		}
		return false;
	}
}