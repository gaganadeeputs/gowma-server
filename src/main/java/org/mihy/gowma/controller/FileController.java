package org.mihy.gowma.controller;

import io.swagger.annotations.ApiOperation;
import org.mihy.gowma.constants.EndPoints;
import org.mihy.gowma.model.File;
import org.mihy.gowma.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class FileController {

    @Autowired
    FileService fileService;


    @ApiOperation(value = "upload a list of files to aws s3")
    @PostMapping(EndPoints.File.ROOT)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public List<File> uploadFile(@RequestParam(EndPoints.RequestParam.FILES) MultipartFile[] files) {
        return fileService.upload(files);
    }
}
