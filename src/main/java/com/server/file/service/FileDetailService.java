package com.server.file.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.server.file.DTO.AccountDTO;
import com.server.file.DTO.TypeDTO;
import com.server.file.dao.FileDetailMapper;
import com.server.file.dao.UserMapper;
import com.server.file.exception.CommonException;
import com.server.file.model.FileDetail;
import com.server.file.model.FileDetailExample;
import com.server.file.model.User;
import com.server.file.util.RedisUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: Leo
 * @Date: 2019/1/12
 * @Description:
 */
@Service
public class FileDetailService {

    private final static Logger logger = LoggerFactory.getLogger(FileDetailService.class);

    @Autowired
    private FileDetailMapper fileDetailMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    public PageInfo getFileList(int currentPage, int pageSize) {

        FileDetailExample example = new FileDetailExample();
        example.setOrderByClause(" create_time desc");
        PageHelper.startPage(currentPage,pageSize);
        List<FileDetail> list = fileDetailMapper.selectByExample(example);
        PageInfo<FileDetail> pageInfo = new PageInfo<>(formatTime(list));
        return pageInfo;
    }

    public void deleteFile(String id) {
        FileDetail fileDetail = fileDetailMapper.selectByPrimaryKey(id);
        if (fileDetail != null){
            File file = new File(fileDetail.getFileUrl());
            if(file.exists()&&file.isFile()){
                file.delete();
            }
            fileDetailMapper.deleteByPrimaryKey(id);
        }
    }

    public void addFile(MultipartFile file) throws Exception {// 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);

        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);

        // 文件上传路径
        String filePath = "/static/file/";

        // 解决中文问题，liunx下中文路径，图片显示问题
         fileName = UUID.randomUUID() + suffixName;

        File dest = new File(filePath + fileName);

        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);
        FileDetail detail = new FileDetail();
        detail.setId(UUID.randomUUID().toString());
        detail.setCount(0);
        detail.setCreateTime(new Date());
        detail.setFileName(file.getOriginalFilename());
        detail.setFileUrl(filePath + fileName);
        detail.setFileSuffix(suffixName);
        fileDetailMapper.insertSelective(detail);
    }

    public FileDetail getFileById(String id){
        FileDetail detail = fileDetailMapper.selectByPrimaryKey(id);
        return detail;
    }

    public void updateDownloadCount(FileDetail detail){
        detail.setCount(detail.getCount()+1);
        fileDetailMapper.updateByPrimaryKeySelective(detail);
    }

    public PageInfo getIndexList(String keywords,int currentPage,int pageSize){
        FileDetailExample example = new FileDetailExample();
        PageHelper.startPage(currentPage, pageSize);
        example.createCriteria().andFileNameLike("%"+keywords+"%");
        List<FileDetail> list = fileDetailMapper.selectByExample(example);
        PageInfo<FileDetail> pageInfo = new PageInfo<>(formatTime(list));
        return pageInfo;
    }

    private List<FileDetail> formatTime(List<FileDetail> list){
        if (CollectionUtils.isNotEmpty(list)){
            for (FileDetail detail : list) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                detail.setCreateTimeStr(sdf.format(detail.getCreateTime()));
            }
        }
        return list;
    }

    public List<FileDetail> getFileDetailList(String keyWords,String fileSuffix) {

        FileDetailExample example = new FileDetailExample();
        example.setOrderByClause(" create_time desc");
        FileDetailExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(keyWords)){
            criteria.andFileNameLike("%"+keyWords+"%");
        }
        if (StringUtils.isNotEmpty(fileSuffix)){
            criteria.andFileSuffixEqualTo(fileSuffix);
        }
        criteria.andUserIdEqualTo("");
        List<FileDetail> list = fileDetailMapper.selectByExample(example);
        return formatTime(list);
    }

    public List<TypeDTO> getFileSuffix(String userId){
        List<String> list = fileDetailMapper.getFileSuffix(userId);
        List<TypeDTO> result = new ArrayList<>();
        TypeDTO typeDTO;
        typeDTO = new TypeDTO();
        typeDTO.setId(0);
        typeDTO.setText("全部");
        result.add(typeDTO);
        int i = 1;
        if (CollectionUtils.isNotEmpty(list)){
            for (String s : list){
                typeDTO = new TypeDTO();
                typeDTO.setId(i);
                typeDTO.setText(s);
                result.add(typeDTO);
                i++;
            }
        }
        return result;
    }

    public void addUserFile(String token,MultipartFile file) throws IOException {

        if (redisUtil.hasKey(token)) {
            AccountDTO accountDTO = JSON.parseObject(redisUtil.get(token).toString(), AccountDTO.class);
            User user = userMapper.findUserByUserName(accountDTO.getUsername());
            String fileName = file.getOriginalFilename();
            logger.info("上传的文件名为：" + fileName);

            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            logger.info("上传的后缀名为：" + suffixName);

            // 文件上传路径
            String filePath = "/static/file/";

            // 解决中文问题，liunx下中文路径，图片显示问题
            fileName = UUID.randomUUID() + suffixName;

            File dest = new File(filePath + fileName);

            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);
            FileDetail detail = new FileDetail();
            detail.setId(UUID.randomUUID().toString());
            detail.setCount(0);
            detail.setCreateTime(new Date());
            detail.setFileName(file.getOriginalFilename());
            detail.setFileUrl(filePath + fileName);
            detail.setFileSuffix(suffixName);
            detail.setUserId(user.getId());
            fileDetailMapper.insertSelective(detail);
        }else {
            throw new CommonException("登录信息无效，请重新登录！");
        }
    }

    public List<FileDetail> getUserFileDetailList(String keyWords,String fileSuffix,String token) {

        if (redisUtil.hasKey(token)) {
            AccountDTO accountDTO = JSON.parseObject(redisUtil.get(token).toString(), AccountDTO.class);
            User user = userMapper.findUserByUserName(accountDTO.getUsername());
            FileDetailExample example = new FileDetailExample();
            example.setOrderByClause(" create_time desc");
            FileDetailExample.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotEmpty(keyWords)) {
                criteria.andFileNameLike("%" + keyWords + "%");
            }
            if (StringUtils.isNotEmpty(fileSuffix)) {
                criteria.andFileSuffixEqualTo(fileSuffix);
            }
            criteria.andUserIdEqualTo(user.getId());
            criteria.andUserIdEqualTo("");
            List<FileDetail> list = fileDetailMapper.selectByExample(example);
            return formatTime(list);
        }else {
            throw new CommonException("登录信息无效，请重新登录！");
        }
    }

    public List<TypeDTO> getUserFileSuffix(String token){
        if (redisUtil.hasKey(token)) {
            AccountDTO accountDTO = JSON.parseObject(redisUtil.get(token).toString(), AccountDTO.class);
            User user = userMapper.findUserByUserName(accountDTO.getUsername());
            List<String> list = fileDetailMapper.getFileSuffix(user.getId());
            List<TypeDTO> result = new ArrayList<>();
            TypeDTO typeDTO;
            typeDTO = new TypeDTO();
            typeDTO.setId(0);
            typeDTO.setText("全部");
            result.add(typeDTO);
            int i = 1;
            if (CollectionUtils.isNotEmpty(list)) {
                for (String s : list) {
                    typeDTO = new TypeDTO();
                    typeDTO.setText(s);
                    typeDTO.setId(i);
                    result.add(typeDTO);
                    i++;
                }
            }
            return result;
        }else {
            throw new CommonException("登录信息无效，请重新登录！");
        }
    }

}
