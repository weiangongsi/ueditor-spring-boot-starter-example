package com.example.ueditorspringbootstarterexample;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.spring.EditorUploader;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * 自定义上传
 * 返回指定格式即可
 *
 * @author lihy
 * @version 2018/12/25
 */
//@Service
public class MyEditorUploader implements EditorUploader {

    @Override
    public State binaryUpload(HttpServletRequest request, Map<String, Object> conf) {
        String fieldName = (String) conf.get("fieldName");
        MultiValueMap<String, MultipartFile> multiFileMap = ((MultipartHttpServletRequest) request).getMultiFileMap();
        MultipartFile file = multiFileMap.getFirst(fieldName);
        assert file != null;
        // 文件名
        String originFileName = file.getOriginalFilename();
        assert originFileName != null;
        // 文件扩展名
        String suffix = originFileName.substring(originFileName.lastIndexOf(".")).toLowerCase();
        // 不符合文件类型
        if (!Arrays.asList((String[]) conf.get("allowFiles")).contains(suffix)) {
            return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
        }
        long maxSize = (Long) conf.get("maxSize");
        // 文件大小超出限制
        if (maxSize < file.getSize()) {
            return new BaseState(false, AppInfo.MAX_SIZE);
        }
        // 根据config.json 中的 imagePathFormat 生成 路径+文件名
        String savePath = (String) conf.get("savePath");
        savePath = savePath + suffix;
        savePath = PathFormat.parse(savePath, originFileName);
        System.out.println(savePath);
        // TODO: 实现你自己的上传 比如ftp上传到服务器、上传到七牛云等


        BaseState baseState = new BaseState();
        // 必填项url，图片地址
        baseState.putInfo("url", "https://www.baidu.com/img/baidu_jgylogo3.gif");
        // 以下三项可以不填 都是 img 标签的属性
        baseState.putInfo("type", "gif");
        baseState.putInfo("original", "img-alt");
        baseState.putInfo("title", "img-title");
        return baseState;
    }

    @Override
    public State base64Upload(HttpServletRequest request, Map<String, Object> conf) {
        String filedName = (String) conf.get("fieldName");
        String content = request.getParameter(filedName);
        byte[] data = Base64.decodeBase64(content);
        long maxSize = (Long) conf.get("maxSize");
        // 文件大小超出限制
        if (data.length > maxSize) {
            return new BaseState(false, AppInfo.MAX_SIZE);
        }
        // 根据config.json 中的 imagePathFormat 生成 路径+文件名
        String savePath = PathFormat.parse((String) conf.get("savePath"), (String) conf.get("filename"));
        savePath = savePath + ".jpg";
        // TODO: 实现你自己的上传 比如ftp上传到服务器、上传到七牛云等

        BaseState baseState = new BaseState();
        // 必填项url，图片地址
        baseState.putInfo("url", "https://www.baidu.com/img/baidu_jgylogo3.gif");
        // 以下三项可以不填 都是 img 标签的属性
        baseState.putInfo("type", "gif");
        baseState.putInfo("original", "img-alt");
        baseState.putInfo("title", "img-title");
        return baseState;
    }


    @Override
    public MultiState listImage(int index, Map<String, Object> conf) {
        // 每次列出图片数量 config.json 中的 imageManagerListSize
        int count = (Integer) conf.get("count");

        BaseState imageFirst = new BaseState();
        imageFirst.putInfo("url", "https://www.baidu.com/img/baidu_jgylogo3.gif");
        BaseState imageSecond = new BaseState();
        imageSecond.putInfo("url", "https://www.baidu.com/img/bd_logo1.png");
        MultiState multiState = new MultiState(true);
        multiState.addState(imageFirst);

        multiState.addState(imageSecond);

        // 开始位置
        multiState.putInfo("start", index);
        // 总记录数
        multiState.putInfo("total", 1);
        return multiState;
    }

    @Override
    public MultiState listFile(int index, Map<String, Object> conf) {
        // 每次列出文件数量 config.json 中的 fileManagerListSize
        int count = (Integer) conf.get("count");

        BaseState baseState = new BaseState();
        baseState.putInfo("url", "https://www.baidu.com/img/baidu_jgylogo3.pdf");
        MultiState multiState = new MultiState(true);
        multiState.addState(baseState);

        // 开始位置
        multiState.putInfo("start", index);
        // 总记录数
        multiState.putInfo("total", 1);
        return multiState;
    }
}
