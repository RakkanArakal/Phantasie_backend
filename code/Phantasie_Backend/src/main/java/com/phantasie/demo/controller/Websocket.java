package com.phantasie.demo.controller;



import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ServerEndpoint(value = "/game/test")
@Component
public class Websocket {
    /**
     * 记录当前在线连接数
     */
    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    private Session session;


    /**
     * 存放所有在线的客户端
     */
    private static final Map<String, Session> clients = new ConcurrentHashMap<>();
    private static final Map<String, GameStatus> allPlayers = new ConcurrentHashMap<>();
    public static final Map<Integer, Game> allGames = new ConcurrentHashMap<>();
    public static final Map<Integer, Room> allRooms = new ConcurrentHashMap<>();

    public static int currentGameId = 0;

    @OnOpen
    public void onOpen(Session session){
        int cnt = onlineCount.incrementAndGet();
        clients.put(session.getId(),session);
        log.info("有新连接加入：{}，当前在线人数为：{}", session.getId(), cnt);

        GameStatus player = new GameStatus();
        player.setPlayerId(session.getId());
        allPlayers.put(session.getId(), player);
    }

    @OnClose
    public void onClose(Session session){
        String sessionid = session.getId();
        int cnt = onlineCount.decrementAndGet(); // 在线数减1
        clients.remove(sessionid);
        log.info("有一连接关闭：{}，当前在线人数为：{}", session.getId(), cnt);
    }


    private Room createRoom(Session session) {
        log.info("创建房间");
        Room room = new Room();
        int rid = currentGameId++;
        room.setGameId(rid);
        GameStatus gameStatus = allPlayers.get(session.getId());
        room.player[0] = gameStatus;
        allRooms.put(rid, room);
        sendMessageBack("success",session);
        return room;
    }

    private Room exitRoom(int rid,Session session) {
        log.info("退出房间");
        Room room = allRooms.get(rid);
        if(room.roomsize > 1 || room.player[0].getPlayerId() != session.getId())
            throw new NullPointerException("error");
        allRooms.remove(rid);
        sendMessageBack("success",session);
        return room;
    }

    private void searchRoom(Session session) {
        log.info("查询房间");
        String roomInfo = "allRoom$";
        for(Map.Entry<Integer, Room> roomMap:allRooms.entrySet()){
            Room room = roomMap.getValue();
            if(room.getRoomsize() == 1) {
                roomInfo += room.player[0].getPlayerId();
                roomInfo += "#";
                roomInfo += Integer.toString(room.getGameId());
                roomInfo += "$";
            }
        }
        sendMessageBack(roomInfo,session);
        return ;
    }

    private void joinRoom(int rid,Session session) {
        log.info("加入房间");
        Room room = allRooms.get(rid);
        GameStatus gameStatus = allPlayers.get(session.getId());
        room.player[1] = gameStatus;
        room.roomsize++;
        startGame(room);
        sendMessageBack("success",session);
        return ;
    }

    private Game startGame(Room room) {
        Game game = new Game();
        int rid = room.getGameId();
        game.setGameId(rid);
        allGames.put(rid, game);
<<<<<<< HEAD
<<<<<<< HEAD
        sendMessageToRoom("gameStart",rid);
=======
        //sendGameMessageToRoom(rid);
>>>>>>> b75f259 (9.42)
=======
        sendMessageToRoom("gameStart",rid);
>>>>>>> 663c87b (9.42)
        return game;
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("服务端收到客户端[{}]的消息:{}", session.getId(), message);
        String[] splitMessage=message.split("#");
        int rid = 0;
        try {
            if(splitMessage.length > 1 )
                rid = Integer.parseInt(splitMessage[1]);
            switch (splitMessage[0]){
                case "createRoom":  createRoom(session);
                    return;
                case "searchRoom":  searchRoom(session);
                    return;
                case "exitRoom":    exitRoom(rid,session);
                    return;
                case "joinRoom":    joinRoom(rid,session);
                    return;
                case "getCard":     joinRoom(rid,session);
                    return;
                case "useCard":     joinRoom(rid,session);
                    return;
                default:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }



    /**
     * 服务端单独返回消息消息给请求的客户端
     */
    private void sendMessageBack(String message, Session fromSession) {
        try {
            log.info("服务端给客户端[{}]发送消息:{}", fromSession.getId(), message);
            fromSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败：", e);
        }
    }

    /**
     *
     *
     * @param message 消息
     * @param currentroomid 向那个房间发送
     *
     */
    private void sendMessageToRoom(String message, int currentroomid) {
        for (Map.Entry<String, GameStatus> gameStatusEntry : allPlayers.entrySet()) {
            GameStatus gamestatus = gameStatusEntry.getValue();
            if (gamestatus.getGameId() == currentroomid) {
                Session toSession = clients.get(gamestatus.getPlayerId());
                log.info("服务端发给客户端[{}]消息:{}", toSession.getId(), message);
                try {
                    toSession.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}