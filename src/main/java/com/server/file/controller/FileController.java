package com.server.file.controller;

import com.server.file.DTO.ResultTO;
import com.server.file.exception.CommonException;
import com.server.file.model.FileDetail;
import com.server.file.service.FileDetailService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Auther: Leo
 * @Date: 2019/1/8
 * @Description:
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private final static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileDetailService fileDetailService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResultTO getFileList(@RequestParam(value = "keywords",required = false) String keywords,
                                @RequestParam(value = "fileSuffix",required = false) String fileSuffix){
        List<FileDetail> list = fileDetailService.getFileDetailList(keywords,fileSuffix);
        ResultTO resultTO = new ResultTO();
        resultTO.setResult(list);
        return resultTO;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResultTO deleteFIle(@RequestParam("id") String id) {
        ResultTO resultTO = new ResultTO();
        fileDetailService.deleteFile(id);
        return resultTO;
    }

    @RequestMapping(value = "/suffix/get",method = RequestMethod.GET)
    public ResultTO getSuffixList(@RequestParam(value = "userId",required = false) String userId){
        ResultTO resultTO = new ResultTO();
        resultTO.setResult(fileDetailService.getFileSuffix(userId));
        return resultTO;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResultTO uploadFile(@RequestParam("files") List<MultipartFile> files) {
        ResultTO resultTO = new ResultTO();
        if (CollectionUtils.isEmpty(files)) {
            throw new CommonException("没有上传文件!");
        }
        try {
            for (MultipartFile file : files) {
                fileDetailService.addFile(file);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return resultTO;
    }

    @RequestMapping(value = "/user/upload", method = RequestMethod.POST)
    public ResultTO uploadFile(@RequestParam("token") String token,@RequestParam("files") List<MultipartFile> files) {
        ResultTO resultTO = new ResultTO();
        if (CollectionUtils.isEmpty(files)) {
            throw new CommonException("没有上传文件!");
        }
        try {
            for (MultipartFile file : files) {
                fileDetailService.addUserFile(token,file);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return resultTO;
    }


    @RequestMapping(value = "/user/list",method = RequestMethod.GET)
    public ResultTO getFileList(@RequestParam(value = "keywords",required = false) String keywords,
                                @RequestParam(value = "fileSuffix",required = false) String fileSuffix,
                                @RequestParam("token") String token){
        List<FileDetail> list = fileDetailService.getUserFileDetailList(keywords,fileSuffix,token);
        ResultTO resultTO = new ResultTO();
        resultTO.setResult(list);
        return resultTO;
    }

    @RequestMapping(value = "/user/suffix/get",method = RequestMethod.GET)
    public ResultTO getUserSuffixList(@RequestParam("token") String token){
        ResultTO resultTO = new ResultTO();
        resultTO.setResult(fileDetailService.getUserFileSuffix(token));
        return resultTO;
    }

}
