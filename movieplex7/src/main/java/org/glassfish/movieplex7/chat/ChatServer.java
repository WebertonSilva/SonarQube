package org.glassfish.movieplex7.chat;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class ChatServer {

    private static final Set<Session> peers
            = Collections.synchronizedSet(new HashSet<Session>());
    
    private static final Logger LOGGER = Logger.getLogger(ChatServer.class.getName());

    @OnOpen
    public void onOpen(Session peer) {
        peers.add(peer);
    }

    @OnClose
    public void onClose(Session peer) {
        peers.remove(peer);
    }

    @OnMessage
    public void message(String message) throws MessageException {
    	
        try {
			for (Session peer : peers) {
			    peer.getBasicRemote().sendObject(message);
			}
		} catch (IOException | EncodeException e) {
			
			LOGGER.log(Level.SEVERE, "Falha ao enviar mensagem", e);
			
			throw new MessageException(e);
		}
    }
}
