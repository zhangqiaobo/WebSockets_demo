package examples;

/**
 * Example of a simple Echo Client.
 */
public class SimpleClient
{
//    public static void main(String[] args)
//    {
//        //项目WebSocket地址
//        String destUri = "ws://localhost:8080/emonitor/api";
//
//        if (args.length > 0)
//        {
//            destUri = args[0];
//        }
//
//        WebSocketClient client = new WebSocketClient();
//
//        SimpleSocket socket = new SimpleSocket();
//
//        try
//        {
//            client.start();
//
//            URI echoUri = new URI(destUri);
//
//            ClientUpgradeRequest request = new ClientUpgradeRequest();
//
//            client.connect(socket,echoUri,request);
//
//            // wait for closed socket connection.
//            socket.awaitClose(5,TimeUnit.SECONDS);
//        }
//        catch (Throwable t)
//        {
//            t.printStackTrace();
//        }
//        finally
//        {
//            try
//            {
//                client.stop();
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
//    }
}