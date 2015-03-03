package corn.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author Álvaro Rungue.
 * @since 03/02/15.
 */
@Service
public class UploadService {

    @Value(value = "upload.path")
    private String dirPath;

    public void saveFile( MultipartFile multipartFile ){

        //Diretorio para salvar o arquivo
        String path = "/var/xrayData/";

        String fileName = multipartFile.getOriginalFilename();
        String fileExtension = fileName.split("\\.")[1];

        //Verificar se o diretorio existe
        if( !(new File(path).exists()) ){
            new File(path).mkdirs();
        }//fim if(check directory exists)

        String newFileName = "file" + "." + fileExtension;
        File file = new File(path +  newFileName );

        try{
            multipartFile.transferTo(file);
        }catch( IOException e ){
            e.printStackTrace();
        }

    }//fim saveFile()

}//fim class UploadService
