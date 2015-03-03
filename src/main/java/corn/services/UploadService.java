package corn.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Álvaro Rungue.
 * @since 03/02/15.
 */
@Service
public class UploadService {

    @Value("${upload.path}")
    private String dirPath;

    public void saveFile( MultipartFile multipartFile ){

        String fileName = multipartFile.getOriginalFilename();
        String fileExtension = fileName.split("\\.")[1];

        //Verificar se o diretorio existe
        if( !(new File(dirPath).exists()) ){
            new File(dirPath).mkdirs();
        }//fim if(check directory exists)

        String newFileName = "file" + "." + fileExtension;
        File file = new File(dirPath +  newFileName );

        try{
            multipartFile.transferTo(file);
        }catch( IOException e ){
            e.printStackTrace();
        }

    }//fim saveFile()

}//fim class UploadService
