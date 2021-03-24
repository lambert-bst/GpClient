package bst.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * 功能说明：封装http请求：GET/POST/PUT/DELETE等方法
 * 修改说明：
 * @author 郑立兵
 * @date 2017年10月17日 上午8:42:30
 * @version V0.1
 * @param <T>
 */
public class HttpRequestUtil {
    /**
     * 定义全局默认编码格式
     */
    private static final String CHARSET_NAME = "UTF-8";
    /**
     * 定义全局OkHttpClient对象
     */
    private static final OkHttpClient httpClient = new OkHttpClient();

    /**
     * 功能说明：同步调用
     * 修改说明：
     * @author zhenglibing
     * @date 2018年1月8日 上午10:20:55
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException {
        return httpClient.newCall(request).execute();
    }

    /**
     * 功能说明：开启异步线程调用
     * 修改说明：
     * @author zhenglibing
     * @date 2018年1月8日 上午10:23:00
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, Callback responseCallback) {
        httpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     * 功能说明：开启异步线程调用，且不在意返回结果（实现空callback）
     * 修改说明：
     * @author zhenglibing
     * @date 2018年1月8日 上午10:24:53
     * @param request
     */
    public static void enqueue(Request request) {
        httpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }

            @Override
            public void onResponse(Call arg0, Response arg1) throws IOException {

            }

        });
    }

    /**
     * 功能说明：向指定URL发送GET方法的请求
     * 修改说明：
     * @author zhenglibing
     * @date 2018年1月8日 上午10:19:11
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL所代表远程资源的响应结果
     * @throws IOException
     */
    public static String sendGet(String url, String param) throws IOException {
        String result = "";
        String urlNameString = url + "?" + param;

        Request req = new Request.Builder().url(urlNameString).build();
        Response response = httpClient.newCall(req).execute();
        if (!response.isSuccessful())
        {
            throw new IOException("Unexpected code " + response);
        }
        result = response.body().string();

        return result;
    }

    /**
     * 功能说明：向指定URL发送GET方法的请求
     * 修改说明：
     * @author zhenglibing
     * @date 2018年1月8日 上午10:54:55
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param encoding 设置响应信息的编码格式，如utf-8
     * @return URL所代表远程资源的响应结果
     * @throws IOException
     */
    public static String sendGet(String url, String param, String encoding) throws IOException {
        String result = "";
        String urlNameString = url + "?" + param;

        Request req = new Request.Builder().url(urlNameString).build();
        Response response = httpClient.newCall(req).execute();
        if (!response.isSuccessful())
        {
            throw new IOException("Unexpected code " + response);
        }
        result = response.body().string();

        if (null == encoding || encoding.equals("")) {
            return result;
        }
        byte[] bresult = result.getBytes();
        result = new String(bresult, encoding);

        return result;
    }

    /**
     * 功能说明：向指定URL发送POST方法的请求
     * 修改说明：
     * @author zhenglibing
     * @date 2018年1月8日 上午10:54:55
     * @param url 发送请求的URL
     * @param jsonData 请求参数，请求参数应该是Json格式字符串的形式。
     * @return URL所代表远程资源的响应结果
     * @throws IOException
     */
    public static String sendPost(String url, String jsonData) throws IOException {
        String result = "";
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonData);
        Request req = new Request.Builder().url(url).header("Content-Type", "application/json").post(body).build();
        Response response = httpClient.newCall(req).execute();
        if (!response.isSuccessful())
        {
            throw new IOException("Unexpected code " + response);
        }
        result = response.body().string();

        return result;
    }

    /**
     * 功能说明：向指定URL发送POST方法的请求
     * 修改说明：
     * @author zhenglibing
     * @date 2018年1月8日 上午10:54:55
     * @param url 发送请求的URL
     * @param jsonData 请求参数，请求参数应该是Json格式字符串的形式。
     * @param encoding 设置响应信息的编码格式，如utf-8
     * @param authorization 授权
     * @param postmanToken 票证
     * @return URL所代表远程资源的响应结果
     * @throws IOException
     */
    public static String sendPost(String url, String jsonData, String encoding, String authorization, String postmanToken) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection con = realUrl.openConnection();
            HttpURLConnection conn = (HttpURLConnection) con;
            // 设置通用的请求属性
            conn.setRequestMethod("POST"); // 设置Post请求
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded"); // 设置内容类型
            conn.setRequestProperty("authorization", authorization);
            conn.setRequestProperty("postman-token", postmanToken);

            // conn.setRequestProperty("Content-Length",
            // String.valueOf(param.length())); //设置长度
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(
                    conn.getOutputStream(), encoding));
            // 发送请求参数
            // out.print(param);
            out.write(jsonData);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            byte[] bresult = result.getBytes();
            result = new String(bresult, encoding);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 功能说明：向指定 URL 发送POST方法的请求
     * 修改说明：
     * @author zhenglibing
     * @date 2018年1月8日 下午2:17:06
     * @param url 发送请求的 URL
     * @param jsonData 请求参数，请求参数应该是Json格式字符串的形式。
     * @param encoding 设置响应信息的编码格式，如utf-8
     * @return url所代表远程资源的响应结果
     * @throws IOException
     */
    public static String sendPost(String url, String jsonData, String encoding) throws IOException {
        String result = "";
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonData);
        Request req = new Request.Builder().url(url).header("Content-Type", "application/json").post(body).build();
        Response response = httpClient.newCall(req).execute();
        if (!response.isSuccessful())
        {
            throw new IOException("Unexpected code " + response);
        }
        result = response.body().string();

        if (null == encoding || encoding.equals("")) {
            return result;
        }
        byte[] bresult = result.getBytes();
        result = new String(bresult, encoding);

        return result;
    }

    /**
     * 功能说明：上传文件
     * 修改说明：
     * @author zhenglibing
     * @date 2018年1月8日 下午2:15:51
     * @param url 上传url
     * @param file 要上传的文件对象
     * @return 返回上传的结果
     */
    public static String uploadPost(String url, File file) {
        DataOutputStream dos = null;
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader in = null;
        String result = "";
        String end = "\r\n";
        String twoHyphens = "--"; // 用于拼接
        String boundary = "*****"; // 用于拼接 可自定义
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection con = realUrl.openConnection();
            HttpURLConnection conn = (HttpURLConnection) con;
            // 设置通用的请求属性
            conn.setRequestMethod("POST"); // 设置Post请求
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary); // 设置内容类型

            // 获取URLConnection对象对应的输出流
            dos = new DataOutputStream(conn.getOutputStream());
            //1、写入媒体头部分
            StringBuilder sb = new StringBuilder();
            sb.append(twoHyphens).append(boundary).append(end);
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"").append(end);
            sb.append("Content-Type:application/octet-stream").append(end).append(end);
            byte[] head = sb.toString().getBytes("utf-8");
            dos.write(head);

            //2、写入媒体正文部分， 对文件进行传输
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);
            byte[] buffer = new byte[8192]; // 8K
            int count = 0;
            while ((count = dis.read(buffer)) != -1) {
                dos.write(buffer, 0, count);
            }

            //3、写入媒体结尾部分。
            byte[] foot = (end + twoHyphens + boundary + twoHyphens + end).getBytes("utf-8");
            dos.write(foot);
            dos.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            byte[] bresult = result.getBytes();
            result = new String(bresult, "utf-8");
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (dos != null) {
                    dos.close();
                }
                if (dis != null) {
                    dis.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 功能说明：下载素材文件
     * 修改说明：
     * @author zhenglibing
     * @date 2018年1月9日 下午2:06:56
     * @param url 下载的接口地址
     * @param param 参数
     * @param outFileName 输出文件
     * @return 成功返回true，失败返回false
     * @throws IOException
     */
    public static boolean downloadFile(String url, String param, String outFileName) throws IOException
    {
        boolean result = false;
        String urlNameString = url + "?" + param;
        Request req = new Request.Builder().url(url).build();
        Response response = httpClient.newCall(req).execute();
        if (!response.isSuccessful())
        {
            throw new IOException("Unexpected code " + response);
        }
        if (response.body().contentType().toString().toLowerCase().contains("application/json") || response.body().contentType().toString().toLowerCase().contains("text/plain")) {
            throw new IOException("下载资源失败,下载地址为=" + urlNameString);
        }
        else
        {
            InputStream in = response.body().byteStream();
            FileOutputStream out = new FileOutputStream(outFileName);
            int bufferSize = 2048;
            byte[] data = new byte[bufferSize];
            int length = 0;
            while ((length = in.read(data, 0, bufferSize)) > 0)
            {
                out.write(data, 0, length);
            }
            out.close();
            in.close();
            result = true;
        }
        return result;
    }
}
