package com.server.file.controller;

import com.github.pagehelper.PageInfo;
import com.server.file.model.FileDetail;
import com.server.file.service.FileDetailService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @Auther: Leo
 * @Date: 2019/1/8
 * @Description:
 */
@Controller
@RequestMapping("/server/file")
public class FileController {

    private final static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileDetailService fileDetailService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/index/search",method = RequestMethod.GET)
    public String search(ModelMap map,
                         @RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
                         @RequestParam(value = "keywords",required = false) String keywords){
        PageInfo pageInfo = fileDetailService.getIndexList(keywords,page,10);
        map.put("pageInfo", pageInfo);
        return "index1";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap map,
                       @RequestParam(value = "page", defaultValue = "1", required = false) Integer page) {
        PageInfo pageInfo = fileDetailService.getFileList(page, 10);
        map.put("pageInfo", pageInfo);
        return "index1";
    }

    @RequestMapping(value = "/refresh/list", method = RequestMethod.GET)
    public String refreshList(ModelMap map,
                              @RequestParam(value = "page", defaultValue = "1", required = false) Integer page) {

        PageInfo pageInfo = fileDetailService.getFileList(page, 10);
        map.put("pageInfo", pageInfo);
        return "index::table_file";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public void deleteFIle(@RequestParam("id") String id) {
        fileDetailService.deleteFile(id);
    }

    @RequestMapping(value = "/upload/page", method = RequestMethod.GET)
    public String uploadPage() {
        return "uploadFile";
    }

    @RequestMapping(value = "/upload/file", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("files") List<MultipartFile> files) {
        if (CollectionUtils.isEmpty(files)) {
            return "redirect:/server/file/upload/page";
        }
        try {
            for (MultipartFile file : files) {
                fileDetailService.addFile(file);
            }
            return "redirect:/server/file/list";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/server/file/upload/page";
    }

    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, @PathVariable("id") String id) throws Exception {

        FileDetail fileDetail = fileDetailService.getFileById(id);
        String fileName = new String(fileDetail.getFileName().getBytes("UTF-8"), "ISO-8859-1");
        String path = fileDetail.getFileUrl();

        File file = new File(path);
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        os = response.getOutputStream();
        bis = new BufferedInputStream(new FileInputStream(file));
        int i = 0;
        while ((i = bis.read(buff)) != -1){
            os.write(buff, 0, i);
            os.flush();
        }
        bis.close();
        fileDetailService.updateDownloadCount(fileDetail);
    }
}
