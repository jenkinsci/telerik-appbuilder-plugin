package com.telerik.plugins.appbuilderci;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class ProjectFilesManager {	
	 public Path getProjectDir(String sourceDirPath) throws IOException {
        final List<Path> projectFilePaths = new ArrayList<Path>();
     	Files.walkFileTree(Paths.get(sourceDirPath), new SimpleFileVisitor<Path>()
     	{
     	   @Override
     	   public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs)
     	      throws IOException
     	   {
              if(filePath.toString().contains(Constants.ABProjectFileName)){
            	  projectFilePaths.add(filePath);
            	  return FileVisitResult.TERMINATE;
              }
     	      return FileVisitResult.CONTINUE;
     	   }
     	});
     	return new File(projectFilePaths.get(0).toString()).getParentFile().toPath();
    }
}
