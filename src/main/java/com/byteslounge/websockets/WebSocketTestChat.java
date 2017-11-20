package com.byteslounge.websockets;

import org.json.JSONObject;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;

@ServerEndpoint("/api")
public class WebSocketTestChat {
    Set<Session> session_list =null;
	@OnMessage
    public void onMessage(String message, Session session) 
    	throws IOException, InterruptedException {

        JSONObject dataJson=new JSONObject(message);

        String action = (String) dataJson.get("ACTION");
        if(action.equals("REMIND")){
            //// TODO: 2017/11/20

        }else{
            //// TODO: 2017/11/20
        }
        session_list =session.getOpenSessions();
        for(Session s:session_list){
            s.getBasicRemote().sendText(dataJson.toString());
        }
    }
	@OnOpen
    public void onOpen () {
    }

    @OnClose
    public void onClose () {
    }
}
