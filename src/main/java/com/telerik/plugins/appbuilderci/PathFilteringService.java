package com.telerik.plugins.appbuilderci;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class PathFilteringService {
	private List<PathMatcher> ignoreFilesRules = new ArrayList<PathMatcher>();

	public PathFilteringService(Path ignoreRulesFilePath) throws IOException {
		if (Files.exists(ignoreRulesFilePath)) {
			final FileSystem fs = FileSystems.getDefault();		
			Object[] list = Files.lines(ignoreRulesFilePath)
					.map(new Function<String, PathMatcher>() {
						@Override
						public PathMatcher apply(String path) {
							return fs.getPathMatcher("glob:" + path);
						}
					}).toArray();
			for (Object pathMatcher : list) {
				ignoreFilesRules.add((PathMatcher)pathMatcher);
			}
		}
	}

	public boolean isFileExcluded(final Path path) {
		if(this.ignoreFilesRules != null){
			return this.ignoreFilesRules.stream().anyMatch(new Predicate<PathMatcher>() {
				@Override
				public boolean test(PathMatcher rule) {
					return rule.matches(path);
				}
			});
		}
		return false;
	}
}