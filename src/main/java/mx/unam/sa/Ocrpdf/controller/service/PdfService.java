/*
 * (c) UNAM, 2023
 */
package mx.unam.sa.Ocrpdf.controller.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import mx.unam.sa.Ocrpdf.controller.models.OcrPDF;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author israel1971
 */
@Service
public class PdfService {

    public String getContenido(MultipartFile file) throws Exception {

        try {
            File entrada = convertMultipartFileToFile(file);
            OcrPDF ocrPDF = new OcrPDF();
            String result = ocrPDF.getText(entrada);
            
            
            return result;
        } catch (Exception e) {
            throw new Exception (e);
        }

    }

    public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        // Obtiene el nombre original del archivo
        String fileName = multipartFile.getOriginalFilename();
        // Crea un objeto Archivo en memoria
        File file = Files.createTempFile(Paths.get(""), fileName, null).toFile();

        // Copia el contenido del objeto MultipartFile al objeto Archivo en memoria
        try (InputStream inputStream = multipartFile.getInputStream(); FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        file.deleteOnExit();
        return file;
    }

}
