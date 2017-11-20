package examples;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.json.JSONObject;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Basic Client Socket
 */
@WebSocket(maxBinaryMessageSize = 60 * 1024)
public class SimpleSocket
{
    private final CountDownLatch closeLatch;
    @SuppressWarnings("unused")
    private Session session;

    private Map map = new HashMap<String,String>(16);


    public SimpleSocket()
    {
        this.closeLatch = new CountDownLatch(1);
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException
    {
        return this.closeLatch.await(duration,unit);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason)
    {
        System.out.printf("Connection closed: %d - %s%n",statusCode,reason);
        this.session = null;
        this.closeLatch.countDown(); // trigger latch
    }

    @OnWebSocketConnect
    public void onConnect(Session session)
    {
        System.out.printf("Got connect: %s%n",session);
        this.session = session;
        try
        {
            Future<Void> fut;

            //向服务端传送指令
            map.put("ACTION","REMIND");

            JSONObject dataJson=new JSONObject(map);

            fut = session.getRemote().sendStringByFuture(dataJson.toString());

            fut.get(2,TimeUnit.SECONDS); // wait for send to complete.

            session.close(StatusCode.NORMAL,"I'm done");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String msg)
    {
        System.out.println("2222222222");
        //接受服务端接受的json字符串
        JSONObject dataJson=new JSONObject(msg);

        //判断指令
        if ("REMIND".equals(dataJson.get("ACTION"))) {

            //// TODO: 2017/11/20
        }else{

            //// TODO: 2017/11/20
        }

    }

    public static void main(String[] args)
    {
        //项目WebSocket地址
        String destUri = "ws://localhost:8080/emonitor/api";
        WebSocketClient client = new WebSocketClient();
        SimpleSocket socket = new SimpleSocket();
        try
        {
            client.start();

            URI echoUri = new URI(destUri);

            ClientUpgradeRequest request = new ClientUpgradeRequest();

            client.connect(socket,echoUri,request);

            // wait for closed socket connection.
            socket.awaitClose(5,TimeUnit.SECONDS);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            try
            {
                client.stop();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}