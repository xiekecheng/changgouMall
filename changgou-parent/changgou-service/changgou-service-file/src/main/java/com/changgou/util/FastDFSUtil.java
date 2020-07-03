package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @description:
 * @author: Kecheng Xie
 * @since: 2020-04-02 21:56
 **/
public class FastDFSUtil {

    static {
        //获取文件路径
        String path = new ClassPathResource("fdfs_client.conf").getPath();
        // String path = "/Users/xiekc/JavaProject/changgou/changgou-parent/changgou-service/changgou-service-file/src/main/resources/fdfs_client.conf";
        //加载tracker连接信息
        try {
            ClientGlobal.init(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    public static void upload(FastDFSFile file) throws Exception {
        NameValuePair[] meta_list= new NameValuePair[1];
        meta_list[0] = new NameValuePair("书名", "三国演义");
        //建立TrackerClient客户端
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        //上传文件
        storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
        // storageClient.upload_file(file.getContent(), file.getExt(), null);

        //执行文件上传
        // String[] jpgs = storageClient.upload_file("/Users/xiekc/Desktop/WechatIMG60679.jpeg", "jpeg", null);

        // for (String jpg : jpgs) {
        //
        //     System.out.println(jpg);
        // }
    }
}
