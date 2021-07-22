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
    private static  Map<String, Session> clients = new ConcurrentHashMap<>();
    private static  Map<String, GameStatus> allPlayers = new ConcurrentHashMap<>();
    public static  Map<Integer, Game> allGames = new ConcurrentHashMap<>();
    public static  Map<Integer, Room> allRooms = new ConcurrentHashMap<>();

    public static int currentGameId = 0;

    @OnOpen
    public void onOpen(Session session){
        int cnt = onlineCount.incrementAndGet();
        clients.put(session.getId(),session);
        log.info("有新连接加入：{}，当前在线人数为：{}", session.getId(), cnt);

        GameStatus player = new GameStatus();
        player.setPlayerId(session.getId());
        //TODO:登陆后在此保存玩家昵称
        player.setPlayerName("玩家名字");
        allPlayers.put(session.getId(), player);
    }

    @OnClose
    public void onClose(Session session){
        String sessionid = session.getId();
        int cnt = onlineCount.decrementAndGet(); // 在线数减1
        clients.remove(sessionid);
        log.info("有一连接关闭：{}，当前在线人数为：{}", session.getId(), cnt);
    }


    private void createRoom(Session session) throws ExceptionMessage {
        log.info("创建房间");
        CardController cardController = new CardController();
        System.out.print(cardController);
        GameStatus gameStatus = allPlayers.get(session.getId());

        if(gameStatus.isInRoom()) {
            sendMessageBack("玩家已在房间内",session);
            throw new ExceptionMessage("error");
        }

        Room room = new Room();
        int rid = currentGameId++;
        room.setOwner(rid,gameStatus);
        allRooms.put(rid, room);
        sendMessageBack("success",session);
        return ;
    }

    private void exitRoom(int rid,Session session) throws ExceptionMessage {
        log.info("退出房间");
        Room room = allRooms.get(rid);
        if(room.roomsize > 1 || room.player[0].getPlayerId() != session.getId())
            throw new ExceptionMessage("error");
        allRooms.remove(rid);
        sendMessageBack("success",session);
        return;
    }

    private void searchRoom(Session session) {
        log.info("查询房间");
        String roomInfo = "allRoom$";
        for(Map.Entry<Integer, Room> roomMap:allRooms.entrySet()){
            Room room = roomMap.getValue();
            if(room.getRoomsize() == 1) {
                roomInfo += room.player[0].getPlayerName();
                roomInfo += "#";
                roomInfo += Integer.toString(room.getGameId());
                roomInfo += "$";
            }
        }
        sendMessageBack(roomInfo,session);
        return ;
    }

    private void joinRoom(int rid,Session session) throws ExceptionMessage {
        log.info("加入房间");
        Room room = allRooms.get(rid);

        if(room == null){
            sendMessageBack("房间不存在",session);
            throw new ExceptionMessage("error");
        }
        if(room.getRoomsize() > 1 ){
            sendMessageBack("玩家已在房间内",session);
            throw new ExceptionMessage("error");
        }

        GameStatus gameStatus = allPlayers.get(session.getId());
        gameStatus.setGameId(rid);
        room.player[1] = gameStatus;
        room.roomsize++;
        startGame(room);
//        sendMessageBack("success",session);
        return ;
    }

    private Game startGame(Room room) {

        int rid = room.getGameId();
//        int r = (int) (Math.random ()*(352324 +1)) % 2 ;
        int r = 0;

        Game game = new Game();
        if( r == 1)
            game.setPlayer(room.player);
        else{
            game.player[0] = room.player[1];
            game.player[1] = room.player[0];
        }
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
        game.start(rid);
>>>>>>> 87f1811 (14:30)
        allGames.put(rid,game);

        Session toSession0 = clients.get(game.getPlayer()[0].getPlayerId());
        Session toSession1 = clients.get(game.getPlayer()[1].getPlayerId());

        String status0 = "gameStart#" + rid ;
        String status1 = "gameStart#" + rid;

        status0 += game.packStat(0);
        status1 += game.packStat(1);

        sendMessageBack(status0,toSession0);
        sendMessageBack(status1,toSession1);

//        gameRun(rid,toSession1,1,1);

        if(r == 1) {
            game.getCard(0);                         //后手抽卡
            gameRun(rid, toSession1, 1, 0);
        }
        else{
            game.getCard(1);                         //后手抽卡
            gameRun(rid, toSession0, 0, 0);
        }

>>>>>>> 3a4695c (7.21 15:56)
        return game;
    }


    private void gameRun(int rid,Session curSession,int seat,int type) {

        Game game = allGames.get(rid);
        int enemy = (seat ^ 1);

        Session seatSession = clients.get(game.getPlayer()[seat].getPlayerId());
        Session enemySession = clients.get(game.getPlayer()[enemy].getPlayerId());
        String curStatus = "",enemyStatus = "";
        switch (type){
            case 0:{                                            //回合开始
                curStatus   = "yourTurn#" + rid  ;
                enemyStatus = "waitTurn#" + rid ;
            }
                break;
            case 1:{
                curStatus = "getCard#" + rid + "#current#";
                enemyStatus = "getCard#" + rid + "#enemy#";
                game.getCard(seat);
            }
                break;
            case 2:{
                int cardOrder = seat;
                seat = allPlayers.get(curSession.getId()).getSeat();
                game.useCard(seat,cardOrder);
            }
                break;
            case 3:{
//                curStatus   = "yourTurn#" + rid  ;
//                enemyStatus = "waitTurn#" + rid ;
                game.endTurn(seat);
                gameRun(rid,enemySession,enemy,0);        // 敌方回合开始
                return ;
            }
            default:
                break;
        }

        curStatus += game.packStat(seat);
        enemyStatus += game.packStat(enemy);
        sendMessageBack(curStatus,curSession);
        sendMessageBack(enemyStatus,enemySession);

        if(!game.isRunning())
        {                                                     //判断输赢
            String winner = "youWin";
            String loser = "enemyWin";
            if(game.getPlayer()[seat].getHp() <= 0){
                sendMessageBack(winner,curSession);
                sendMessageBack(loser,enemySession);
            }
            else{
                sendMessageBack(winner,enemySession);
                sendMessageBack(loser,curSession);
            }
            allRooms.remove(rid);
            allGames.remove(rid);
            return ;
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
        int rid,seat,argu1 = 0;
        rid = allPlayers.get(session.getId()).getGameId();
        seat = allPlayers.get(session.getId()).getSeat();
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
                case "getCard":     gameRun(rid,session,seat,1);
                    return;
                case "useCard":     gameRun(rid,session,argu1,2); //这里不传位置传卡号
                    return;
                case "endTurn":     gameRun(rid,session,seat,3);
                    return;
                default:
                    break;
            }
        }catch (Exception e){
            log.info("发生错误");
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