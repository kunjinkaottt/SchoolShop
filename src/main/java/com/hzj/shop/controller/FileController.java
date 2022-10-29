package com.hzj.shop.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
public class FileController {

    @Value("${server.port}")
    private String port;

    @Value("${files.upload.path}")
    private String fileUploadPath;

    private static final String ip = "http://localhost";

/*//文件上传接口
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();   //获取源文件名称
//        定义文件唯一标识，防止同名文件被覆盖
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir")+"/src/main/resources/files/" + flag + "_" + originalFilename;//System.getProperty("user.dir")获取项目路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);    //把文件写入到上传的路径
        return Result.success(ip+":"+port+"/files/"+flag);    //返回可以本地访问图片的url
    }*/

//文件下载接口
    @GetMapping("files/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response){
        OutputStream os;// 新建一个输出流对象
        String basePath = System.getProperty("user.dir") + "/src/main/resources/files/";    //定义文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  //获取所有的文件名称
        String photo = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");//找到跟参数一致的文件
        try{
            if(StrUtil.isNotEmpty(photo)){
                response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(photo,"UTF-8"));
                response.setContentType("application/octer-stream");
                byte[] bytes = FileUtil.readBytes(basePath+photo);//通过文件的路径读取文件字节流
                os = response.getOutputStream();    //通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        }catch (Exception e){
            System.out.println("文件下载失败");
        }
    }

    //    上传图片到本地磁盘
    @PostMapping("/uploadFile")
    public String uploadGoodsImg(@Param("file")MultipartFile file) throws IOException {
//        获取文件名
        String originalFilename = file.getOriginalFilename();
//        获取扩展名
        String type = FileUtil.extName(originalFilename);
//      判断磁盘要存储文件的路径有没有存在，没有就新建一个
        File uploadParentFile = new File(fileUploadPath);
        if (!uploadParentFile.exists()){
            uploadParentFile.mkdirs();  }
//        生成一个唯一标识码uuid，后面拼接".拓展名"      (拼接到原来的文件名前面)
        String uuid = IdUtil.fastSimpleUUID();
        String uuidFile = uuid+StrUtil.DOT+type;
//          把文件上传到磁盘
        File uploadFile = new File(fileUploadPath + uuidFile);
        file.transferTo(uploadFile);//抛出异常 IOException
//        返回文件路径
        String url = "http://localhost:8081/"+uuidFile;    //注意！！ 是后端的接口 8081,还有后面接的是这个下载接口的url名uploadGoodsImg
        System.out.println(url);
        return url;
    }

    //文件下载  新
    @GetMapping("/{uuidFile}")
    public void download(@PathVariable String uuidFile,
                         HttpServletResponse response) throws IOException {
        File uploadFile = new File(fileUploadPath + uuidFile);
        ServletOutputStream os = response.getOutputStream();
//       设置输出流的格式
        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(uuidFile,"UTF-8"));
        response.setContentType("application/octet-stream");
//        写出
//        读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

}





















