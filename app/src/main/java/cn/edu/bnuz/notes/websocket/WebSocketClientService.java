package cn.edu.bnuz.notes.websocket;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import org.java_websocket.client.WebSocketClient;

import java.net.URI;

public class WebSocketClientService extends Service {

    private URI uri;
    public WebSocketClient client;
    private WebSocketClientBinder mBinder = new WebSocketClientBinder();

    //用于Activity和service通讯
    public class WebSocketClientBinder extends Binder {
        public WebSocketClientService getService() {
            return WebSocketClientService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
