package corn.controllers;

import corn.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Álvaro Rungue.
 * @since 03/02/15.
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save( @RequestBody MultipartFile file ){
        Map<String,Object> map = new HashMap<String, Object>();
        uploadService.saveFile(file);
        return map;
    }//fim save()

}//fim class UploadController
