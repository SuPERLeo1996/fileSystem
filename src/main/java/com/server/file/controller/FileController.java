package com.server.file.controller;

import com.github.pagehelper.PageInfo;
import com.server.file.DTO.ResultTO;
import com.server.file.service.FileDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
    public Map<String, Object> getFileList(@RequestParam(value = "offset",defaultValue = "1") Integer offset,
                                           @RequestParam(value = "limit",defaultValue = "10") Integer limit,
                                           @RequestParam(value = "keywords",required = false) String keywords){
        Integer page = offset/limit+1;
        PageInfo pageInfo = fileDetailService.getFileDetailList(page, limit,keywords);
        Map<String,Object> map = new HashMap<>();
        map.put("rows",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return map;
    }




}
