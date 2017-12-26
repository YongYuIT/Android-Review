package com.thinking.http_client_test;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yu Yong on 2017/12/26.
 */

public class FileTools {
    private final static String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/thinking_cache/";

    public static boolean saveFile(String path) {
        //准备本地文件
        String[] path_strs = path.split("/");
        String fileName = path_strs[path_strs.length - 1];
        File file = new File(storePath + fileName);
        if (file.exists()) {
            return true;
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        File tmp_file = new File(storePath + fileName + ".tmp");
        if (tmp_file.exists()) {
            File tmp_file_del = new File(tmp_file + ".del");
            tmp_file.renameTo(tmp_file_del);
            tmp_file_del.delete();
        }
        try {
            tmp_file.createNewFile();
        } catch (Exception e) {
            return false;
        }
        //准备对输出文件的输出流
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(tmp_file);
        } catch (Exception e) {
            return false;
        }
        //下载流程
        URL url = null;
        try {
            url = new URL(convertToUtf8(path));
        } catch (Exception e) {
            return false;
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (Exception e) {
            return false;
        }
        InputStream inputStream = null;
        try {
            inputStream = urlConnection.getInputStream();
        } catch (Exception e) {
            return false;
        }
        byte[] cache = new byte[1024];
        boolean isKeep = false;
        do {
            try {
                int length = inputStream.read(cache);
                if (length == -1) {
                    isKeep = false;
                } else {
                    isKeep = true;
                    //保存文件
                    fileOutputStream.write(cache, 0, length);
                }
            } catch (Exception e) {
                return false;
            }
        } while (isKeep);
        tmp_file.renameTo(file);
        try {
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        urlConnection.disconnect();
        return true;
    }

    public static void saveFileAsync(final List<String> paths, final Handler handler) {
        new AsyncTask<String, String, Boolean>() {
            @Override
            protected Boolean doInBackground(String... strings) {
                for (int i = 0; i < strings.length; i++) {
                    boolean result = saveFile(strings[i]);
                    this.onProgressUpdate(strings[i] + "-->" + result);
                }
                return true;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                Log.i("yuyong_download", values[0]);
                Message msg = new Message();
                msg.what = 1001;
                handler.sendMessage(msg);
            }
        }.execute((String[]) paths.toArray());
    }

    private static final Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");

    private static String convertToUtf8(String path) throws Exception {
        while (true) {
            Matcher m = pattern.matcher(path);
            if (m.find()) {
                String chinese = m.group();
                path = path.replace(chinese, URLEncoder.encode(chinese, "utf-8"));
            } else {
                break;
            }
        }
        return path;
    }

}
