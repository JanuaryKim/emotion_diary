package emotion.diary.server.util;

import emotion.diary.server.exception.BusinessException;
import emotion.diary.server.exception.ExceptionCode;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUtil {
    public static String saveFile(String uploadDir, String originalFileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        System.out.println(uploadPath.toAbsolutePath());
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String newFileName = generateFileName() + "_" + originalFileName;

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return newFileName;
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + newFileName, ioe);
        }
    }

    public static void deleteFile(String deleteDir) throws IOException {
        Path fileDir = Paths.get(deleteDir);

        if (!Files.exists(fileDir)) {
            return;
        }

        try{
            Files.delete(fileDir);
        } catch (IOException ioe) {
            throw new BusinessException(ExceptionCode.NOT_EXISTS_DIARY_IMAGE_FILE,ExceptionCode.NOT_EXISTS_DIARY_IMAGE_FILE.getSentence());
        }

    }

    private static String generateFileName(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
