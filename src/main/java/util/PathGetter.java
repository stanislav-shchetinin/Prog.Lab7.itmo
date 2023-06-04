package util;

import lombok.extern.java.Log;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Scanner;

import static util.constants.ConstantsForFileBuilder.*;

@Log
public class PathGetter {
    public static Path getPathFileCollection(){
        Scanner scanner = new Scanner(System.in);
        Map<String, String> mapEnv = System.getenv();
        Path path = null;
        while (true){
            System.out.println(PROMPT_TO_ENTER);
            String nameOfGlobalVar = scanner.next();
            if (mapEnv.containsKey(nameOfGlobalVar)){
                String pathString = mapEnv.get(nameOfGlobalVar);
                path = Path.of(pathString);
                if (Files.isRegularFile(path)){
                    log.info(GETTING_FILE_SUCCESSFUL);
                    return path;
                } else {
                    log.warning(GETTING_FILE_WRONG);
                }
            } else {
                log.warning(NOT_FOUND_VAR_ENV);
            }
        }
    }

}
