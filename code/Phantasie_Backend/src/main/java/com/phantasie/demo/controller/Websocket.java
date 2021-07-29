package com.phantasie.demo.controller;

<<<<<<< HEAD
import com.phantasie.demo.utils.msgutils.Msg;
import com.phantasie.demo.utils.msgutils.MsgUtil;
=======


import com.phantasie.demo.config.config.WebSocketConfig;
<<<<<<< HEAD
>>>>>>> e24ea9e (session)
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
=======
import com.phantasie.demo.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
>>>>>>> 1a79dbb (token)
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ServerEndpoint(value = "/game/test/{token}",configurator = WebSocketConfig.class)
@Component
public class Websocket {
    /**
     * 记录当前在线连接数
     */
    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    private CloseReason closeReason = new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR,
            "未经允许的连接");

    /**
     * 存放所有在线的客户端
     */

    private static  Map<String, Session> clients = new ConcurrentHashMap<>();
    private static Map<Session,HttpSession> sessionMap = new ConcurrentHashMap<>();
    private static  Map<String, GameStatus> allPlayers = new ConcurrentHashMap<>();
    public static  Map<Integer, Game> allGames = new ConcurrentHashMap<>();
    public static  Map<Integer, Room> allRooms = new ConcurrentHashMap<>();
    public static List<Room> waitRooms = new LinkedList<>();

    public static int currentGameId = 0;

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        if(!token.equals("1"))
        session.close(closeReason);
        //HttpSession httpSession = (HttpSession) endpointConfig.getUserProperties().get(HttpSession.class.getName());
        int cnt = onlineCount.incrementAndGet();
        clients.put(session.getId(),session);
        log.info("有新连接加入：{}，当前在线人数为：{}", session.getId(), cnt);

        GameStatus player = new GameStatus();
        player.setPlayerId(session.getId());
        //TODO:登陆后在此保存玩家昵称
        player.setPlayerName("玩家名字");
        //String id = httpSession.getId();
        log.info(session.getId()+"\n");
        //log.info(id);
        //sessionMap.put(session,httpSession);
        allPlayers.put(session.getId(), player);
    }

    @OnClose
    public void onClose(Session session){
        String sessionid = session.getId();
        int cnt = onlineCount.decrementAndGet(); // 在线数减1
        GameStatus player = allPlayers.get(sessionid);
        if(player.isInRoom()){
            allRooms.remove(player.getGameId());
            allGames.remove(player.getGameId());
        }
        clients.remove(sessionid);
        allPlayers.remove(sessionid);
        sessionMap.remove(sessionid);
        log.info("有一连接关闭：{}，当前在线人数为：{}", session.getId(), cnt);
    }


    private void createRoom(Session session) throws ExceptionMessage {
        log.info("创建房间");
        GameStatus gameStatus = allPlayers.get(session.getId());

        if(gameStatus.isInRoom()) {
            sendMessageBack(MsgUtil.makeMsg(-100,"创建房间失败"),session);
            throw new ExceptionMessage("error");
        }

        Room room = new Room();
        int rid = currentGameId++;
        room.setOwner(rid,gameStatus);
        allRooms.put(rid, room);
        waitRooms.add(room);

        JSONObject data = new JSONObject();
        data.put("rid",rid);
        sendMessageBack(MsgUtil.makeMsg(101,"successCreate",data),session);
        return ;
    }

    private void exitRoom(int rid,Session session) throws ExceptionMessage {
        log.info("退出房间");
        Room room = allRooms.get(rid);
        if(room.roomsize > 1 || room.player[0].getPlayerId() != session.getId()) {
            sendMessageBack(MsgUtil.makeMsg(113,"退出房间失败"),session);
            throw new ExceptionMessage("error");
        }

        allRooms.remove(rid);
        waitRooms.remove(room);
        allPlayers.get(session.getId()).setInRoom(false);
        sendMessageBack(MsgUtil.makeMsg(112,"successExit"),session);
        return;
    }

    private void searchRoom(Session session) {
        log.info("查询房间");
//        String roomInfo = "allRoom";
//        for(Map.Entry<Integer, Room> roomMap:allRooms.entrySet()){
//            Room room = roomMap.getValue();
//            if(room.getRoomsize() == 1) {
//                roomInfo += "$";
//                roomInfo += room.player[0].getPlayerName();
//                roomInfo += "#";
//                roomInfo += Integer.toString(room.getGameId());
//            }
//        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"player"});
        JSONArray list = JSONArray.fromObject(waitRooms,jsonConfig);

        sendMessageBack(MsgUtil.makeMsg(100,"allRoom",list),session);
        return ;
    }

    private void joinRoom(int rid,Session session) throws ExceptionMessage {
        log.info("加入房间");
        Room room = allRooms.get(rid);

        if(room == null){
            sendMessageBack(MsgUtil.makeMsg(-100,"加入房间失败"),session);
            throw new ExceptionMessage("error");
        }
        if(room.getRoomsize() > 1 ){
            sendMessageBack(MsgUtil.makeMsg(-100,"加入房间失败"),session);
            throw new ExceptionMessage("error");
        }

        GameStatus gameStatus = allPlayers.get(session.getId());
        gameStatus.setGameId(rid);
        gameStatus.setInRoom(true);
        room.player[1] = gameStatus;
        room.roomsize++;
        sendMessageBack(MsgUtil.makeMsg(102,"successJoin"),session);

        Session toSession = clients.get(room.getPlayer()[0].getPlayerId());
        sendMessageBack(MsgUtil.makeMsg(102,"successJoin"),toSession);

        startGame(room);
        return ;
    }

    private Game startGame(Room room) {

        Game game = new Game();
        int rid = room.getRoomId();
        int r = (int) (Math.random ()*(352324 +1)) % 2;

        if( r == 1)
            game.setPlayer(room.player);
        else{
            game.player[0] = room.player[1];
            game.player[1] = room.player[0];
        }
<<<<<<< HEAD
<<<<<<< HEAD
        game.start();
        game.setGameId(rid);
<<<<<<< HEAD
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
=======
=======
=======

>>>>>>> d96d41d (15:17)
        game.start(rid);
<<<<<<< HEAD
>>>>>>> 87f1811 (14:30)
=======
        waitRooms.remove(room);
>>>>>>> 18b44a7 (11:29)
        allGames.put(rid,game);

        Session toSession0 = clients.get(game.getPlayer()[0].getPlayerId());
        Session toSession1 = clients.get(game.getPlayer()[1].getPlayerId());


        sendMessageBack(MsgUtil.makeMsg(103,"gameStart",game.packStat(0)),toSession0);
        sendMessageBack(MsgUtil.makeMsg(103,"gameStart",game.packStat(1)),toSession1);

        gameRun(rid, toSession1, 1, 1);                         //后手抽卡
        gameRun(rid, toSession0, 0, 0);

>>>>>>> 3a4695c (7.21 15:56)
        return game;
    }

    private void gameRun(int rid,Session curSession,int seat,int type) {

        Game game = allGames.get(rid);
        int enemy = (seat ^ 1),cardOrder = 0;
        int timeStamp = game.getTimeStamp();

        int msgCount = game.getMsgCount() + 1 ;
        game.setMsgCount(msgCount);

        Session seatSession = clients.get(game.getPlayer()[seat].getPlayerId());
        Session enemySession = clients.get(game.getPlayer()[enemy].getPlayerId());
        
        if(type >= 200) {
            cardOrder = type - 200;
            type = 2;
        }
        switch (type){
            case 0:{
                //回合开始
                sendMessageBack(MsgUtil.makeMsg(104,"yourTurn",game.packStat(seat),msgCount),seatSession);
                sendMessageBack(MsgUtil.makeMsg(105,"waitTurn",game.packStat(enemy),msgCount),enemySession);
            }
                break;
            case 1:{
                game.getCard(seat);
                sendMessageBack(MsgUtil.makeMsg(106,"getCard",game.cardMsg(true),msgCount),seatSession);
                sendMessageBack(MsgUtil.makeMsg(107,"getCard",game.cardMsg(false),msgCount),enemySession);

            }
                break;
            case 2:{
                JSONObject data = new JSONObject();
                data.put("cardId",game.useCard(seat,cardOrder));
                sendMessageBack(MsgUtil.makeMsg(108,"useCard",data,msgCount),seatSession);
                sendMessageBack(MsgUtil.makeMsg(109,"useCard",data,msgCount),enemySession);
            }
                break;
            case 3:{
                game.endTurn(seat);
                gameRun(rid,enemySession,enemy,0);        // 敌方回合开始
                game.setPlayerNow(enemy);
                return ;
            }
            case 4:{
                game.getCard(seat,1);
                sendMessageBack(MsgUtil.makeMsg(106,"getCard",game.cardMsg(true),msgCount),seatSession);
                sendMessageBack(MsgUtil.makeMsg(107,"getCard",game.cardMsg(false),msgCount),enemySession);
            }
                break;
            default:
                break;
        }


        if(game.stageChange(timeStamp) != null) {
            JSONArray data = game.stageChange(timeStamp);
            int msgCnt = game.getMsgCount() + 1 ;
            game.setMsgCount(msgCnt);
            sendMessageBack(MsgUtil.makeMsg(110, "newState", data,msgCnt), seatSession);
            sendMessageBack(MsgUtil.makeMsg(110, "newState", data,msgCnt), enemySession);
        }

        if(!game.isRunning())
        {                                                     //判断输赢

            if(game.getPlayer()[enemy].getHp() <= 0){
                sendMessageBack(MsgUtil.makeMsg(120,"youWin"),seatSession);
                sendMessageBack(MsgUtil.makeMsg(130,"enemyWin"),enemySession);
            }
            else{
                sendMessageBack(MsgUtil.makeMsg(120,"youWin"),enemySession);
                sendMessageBack(MsgUtil.makeMsg(130,"enemyWin"),seatSession);
            }
            game.player[0].setInRoom(false);
            game.player[1].setInRoom(false);
            allRooms.remove(rid);
            allGames.remove(rid);


            return ;
        }
        if(type == 0){
            gameRun(rid,curSession,seat,1);
        }
        if(game.allState.size()>0 && game.allState.get(timeStamp).getSpecial() == 1){

            gameRun(rid,curSession,seat,4);
        }
        return ;
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
        int rid = allPlayers.get(session.getId()).getGameId();
        int seat = allPlayers.get(session.getId()).getSeat();
        int playerNow = 0;
        int argu1 = 0;
        if(splitMessage.length > 1)
            argu1 = Integer.parseInt(splitMessage[1]);
        try {
            switch (splitMessage[0]){
                case "createRoom":  createRoom(session);
                    return;
                case "searchRoom":  searchRoom(session);
                    return;
                case "exitRoom":    exitRoom(argu1,session);
                    return;
                case "joinRoom":    joinRoom(argu1,session);
                    return;
//                case "getCard":     gameRun(rid,session,seat,1);
//                    return;
                case "useCard": {
                    playerNow = allGames.get(rid).getPlayerNow();
                    if (seat != playerNow){
                        sendMessageBack(MsgUtil.makeMsg(-100,"操作失败"),session);
                        break;
                    }
                    gameRun(rid, session, seat, 200 + argu1);
                    return;
                }
                case "endTurn": {
                    playerNow = allGames.get(rid).getPlayerNow();
                    if (seat != playerNow){
                        sendMessageBack(MsgUtil.makeMsg(-100,"操作失败"),session);
                        break;
                    }
                    gameRun(rid, session, seat, 3);
                    return;
                }
                case "update":
//                    gameRun(rid,session,seat,3);
                    return;
                default:
                    break;
            }
        }catch (Exception e){
            log.info("发生错误");
//            e.printStackTrace();
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
    private void sendMessageBack(Msg message, Session fromSession) {
        try {
            JSONObject data = JSONObject.fromObject(message);
            fromSession.getBasicRemote().sendText(data.toString());
            log.info("服务端给客户端[{}]发送消息:{}", fromSession.getId(), data.toString());
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败：", e);
        }
    }
//    private void sendMessageBack(String  message, Session fromSession) {
//        try {
//            log.info("服务端给客户端[{}]发送消息:{}", fromSession.getId(), message);
//            fromSession.getBasicRemote().sendText(message);
//        } catch (Exception e) {
//            log.error("服务端发送消息给客户端失败：", e);
//        }
//    }

    /**
     *
<<<<<<< HEAD
     *
=======
//     * @param fromSession 会话号
>>>>>>> 3a4695c (7.21 15:56)
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
                    toSession.getBasicRemote().sendText(message + currentroomid);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public class ExceptionMessage  extends Exception {
        public ExceptionMessage(String message) {
            super(message);
        }
    }
}