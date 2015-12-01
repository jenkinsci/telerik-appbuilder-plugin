package com.telerik.plugins.appbuilderci;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

public class ProjectFilesManager {	
	 public Path getProjectDir(String sourceDirPath) throws IOException {
         Path paths = Paths.get(sourceDirPath);
         Path projectFilePath = Files.walk(paths)
                                    .filter(new Predicate<Path>() {
										@Override
										public boolean test(Path path) {
										    return path.toString().contains(".abproject");
										}
									})
                                    .findFirst().get();
                                    
         return new File(projectFilePath.toString()).getParentFile().toPath();
    }
}
