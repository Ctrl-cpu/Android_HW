package com.bytedance.practice5.socket;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocketThread extends Thread {
    public ClientSocketThread(SocketActivity.SocketCallback callback) {
        this.callback = callback;
    }
    private boolean stopFlag = false;

    private SocketActivity.SocketCallback callback;


    //head请求内容
    private static String content = "HEAD / HTTP/1.1\r\nHost:www.zju.edu.cn\r\n\r\n";
    public void disconnect() {
        stopFlag = true;
    }
    private synchronized void clearCot() {
        this.content = "";
    }
    private Activity activity;

    @Override
    public void run() {
        // TODO 6 用socket实现简单的HEAD请求（发送content）
        //  将返回结果用callback.onresponse(result)进行展示
        try{
            Socket socket = new Socket("localhost", 30000);
            BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());
            BufferedInputStream is = new BufferedInputStream(socket.getInputStream());

            double n = 1;
            byte[] data = new byte[1024*5];
            int len = -1;
            while(!stopFlag && socket.isConnected()){
                Log.d("socket","Connect!");
                if(!content.isEmpty()){
                    Log.d("socket","client send " + content);
                    os.write(content.getBytes());
                    os.flush();
                    clearCot();
                    int reciveLen = is.read(data);
                    if(reciveLen != -1){
                        String receive = new String(data,0,reciveLen);
                        Log.d("socket","client recieve " + receive);
//                        activity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                callback.onResponse(receive);
//                            }
//                        });
                        callback.onResponse(receive);
                    }
                    else{
                        Log.d("socket","client receive -1");
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}