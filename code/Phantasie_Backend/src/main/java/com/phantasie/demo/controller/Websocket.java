package com.phantasie.demo.controller;

<<<<<<< HEAD
<<<<<<< HEAD
import com.phantasie.demo.utils.msgutils.Msg;
import com.phantasie.demo.utils.msgutils.MsgUtil;
=======


import com.phantasie.demo.config.config.WebSocketConfig;
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> e24ea9e (session)
=======
=======

import com.phantasie.demo.config.config.WebSocketConfig;
import com.phantasie.demo.entity.User;
import com.phantasie.demo.service.UserService;
>>>>>>> d354314 (10:59)
import com.phantasie.demo.utils.msgutils.Msg;
import com.phantasie.demo.utils.msgutils.MsgUtil;
>>>>>>> 1887f3e (9:11)
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
<<<<<<< HEAD
=======
import com.phantasie.demo.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
>>>>>>> 1a79dbb (token)
=======
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
>>>>>>> d354314 (10:59)
import org.springframework.stereotype.Component;
=======
import org.springframework.web.bind.annotation.RestController;
>>>>>>> f98c6f2 (17：32)

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ServerEndpoint(value = "/game/test/{token}",configurator = WebSocketConfig.class)
@RestController
public class Websocket {

    private static UserService userService;
    @Autowired
    public void setUserService(UserService userService){
        Websocket.userService = userService;
    }
    /**
     * 记录当前在线连接数
     */
    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    private static CloseReason closeReason = new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR,
            "未经允许的连接");


    /**
     * 存放所有在线的客户端
     */

    public static Map<String,Integer> tokenMap = new ConcurrentHashMap<>();
    private static  Map<String, Session> clients = new ConcurrentHashMap<>();
    private static  Map<String, GameStatus> allPlayers = new ConcurrentHashMap<>();
    public static  Map<Integer, Game> allGames = new ConcurrentHashMap<>();
    public static  Map<Integer, Room> allRooms = new ConcurrentHashMap<>();
    public static List<Room> waitRooms = new LinkedList<>();

    public static int currentGameId = 0;

    static boolean insertToken(String token, int userid){
        tokenMap.put(token,userid);
        return true;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {

        if(tokenMap.get(token) == null){
            session.close(closeReason);
            return;
        }
        User user = userService.findUserById(tokenMap.get(token));
        int cnt = onlineCount.incrementAndGet();
        clients.put(session.getId(),session);
        log.info("有新连接加入：{}，当前在线人数为：{}", session.getId(), cnt);

        GameStatus player = new GameStatus();
        player.setUserId(user.getUserId());
        player.setPlayerId(session.getId());
        player.setCurJob(user.getJob());
        player.setPlayerName(user.getNickName());
        player.setJobInfo(user.getJobInfo());

        allPlayers.put(session.getId(), player);
    }

//    @OnOpen
//    public void onOpen(Session session) throws IOException {
//        int cnt = onlineCount.incrementAndGet();
//        clients.put(session.getId(),session);
//        log.info("有新连接加入：{}，当前在线人数为：{}", session.getId(), cnt);
//
//        GameStatus player = new GameStatus();
//        player.setPlayerId(session.getId());
//        User user = userService.findUserById(2);
//        player.setPlayerId(session.getId());
//        player.setCurJob(user.getJob());
//        player.setPlayerName(user.getNickName());
//        player.setJobInfo(user.getJobInfo());
//        allPlayers.put(session.getId(), player);
//    }

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
        log.info("有一连接关闭：{}，当前在线人数为：{}", session.getId(), cnt);
    }


    private void createRoom(Session session) throws ExceptionMessage {
        log.info("创建房间");
        GameStatus gameStatus = allPlayers.get(session.getId());

        if(gameStatus.isInRoom()) {
            sendMessageBack(MsgUtil.makeMsg(-100,"创建房间失败"),session);
            throw new ExceptionMessage("error");
        }

        gameStatus.setCurJob(userService.findUserById(gameStatus.getUserId()).getJob());
        Room room = new Room();
        int rid = currentGameId++;
        room.setOwner(rid,gameStatus);
        allRooms.put(rid, room);
        waitRooms.add(room);

        JSONObject data = new JSONObject();
        data.put("rid",rid);
        sendMessageBack(MsgUtil.makeMsg(101,"successCreate",data),session);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"player"});
        JSONObject roomInfo = JSONObject.fromObject(room,jsonConfig);
        Msg msg = MsgUtil.makeMsg(115,"新增房间",roomInfo);
        for(Map.Entry<String,GameStatus> playerMap:allPlayers.entrySet()){
            GameStatus gameStatus1 = playerMap.getValue();
            if(!gameStatus1.isInRoom()){
                sendMessageBack(msg,clients.get(playerMap.getKey()));
            }
        }
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

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"player"});
        JSONObject roomInfo = JSONObject.fromObject(room,jsonConfig);
        Msg msg = MsgUtil.makeMsg(116,"退出房间",roomInfo);
        for(Map.Entry<String,GameStatus> playerMap:allPlayers.entrySet()){
            GameStatus gameStatus1 = playerMap.getValue();
            if(!gameStatus1.isInRoom()){
                sendMessageBack(msg,clients.get(playerMap.getKey()));
            }
        }
        return;
    }

    private void searchRoom(Session session) {
        log.info("查询房间");
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"player"});
        waitRooms.removeIf(Room::isExpired);
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
        gameStatus.setCurJob(userService.findUserById(gameStatus.getUserId()).getJob());
        room.player[1] = gameStatus;
        room.roomsize++;
        sendMessageBack(MsgUtil.makeMsg(102,"successJoin"),session);

        Session toSession = clients.get(room.getPlayer()[0].getPlayerId());
        sendMessageBack(MsgUtil.makeMsg(102,"successJoin"),toSession);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"player"});
        JSONObject roomInfo = JSONObject.fromObject(room,jsonConfig);
        Msg msg = MsgUtil.makeMsg(112,"失去房间",roomInfo);
        for(Map.Entry<String,GameStatus> playerMap:allPlayers.entrySet()){
            GameStatus gameStatus1 = playerMap.getValue();
            if(!gameStatus1.isInRoom()){
                sendMessageBack(msg,clients.get(playerMap.getKey()));
            }
        }

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



    private void disCarded(int rid, Session session, int seat,List<Integer> discardList) {
        Game game = allGames.get(rid);
        int enemy = (seat ^ 1);
        int timeStamp = game.getTimeStamp();
        Session seatSession = clients.get(game.getPlayer()[seat].getPlayerId());
        Session enemySession = clients.get(game.getPlayer()[enemy].getPlayerId());

        game.disCarded(seat,discardList);

        if(game.stageChange(timeStamp) != null) {
            JSONArray data = game.stageChange(timeStamp);
            int msgCnt = game.getMsgCount() + 1 ;
            game.setMsgCount(msgCnt);
            sendMessageBack(MsgUtil.makeMsg(110, "newState", data,msgCnt), seatSession);
            sendMessageBack(MsgUtil.makeMsg(110, "newState", data,msgCnt), enemySession);
//            game.changeAble();
        }
        return;

    }

    private void useSkill(int rid, Session session, int seat) {
        Game game = allGames.get(rid);
        int enemy = (seat ^ 1);
        int timeStamp = game.getTimeStamp();

        Session seatSession = clients.get(game.getPlayer()[seat].getPlayerId());
        Session enemySession = clients.get(game.getPlayer()[enemy].getPlayerId());

        game.useSkill(seat);

        if(game.stageChange(timeStamp) != null) {
            JSONArray data = game.stageChange(timeStamp);
            int msgCnt = game.getMsgCount() + 1 ;
            game.setMsgCount(msgCnt);
            sendMessageBack(MsgUtil.makeMsg(110, "newState", data,msgCnt), seatSession);
            sendMessageBack(MsgUtil.makeMsg(110, "newState", data,msgCnt), enemySession);
//            game.changeAble();

        }

        game.getCard(seat,game.allState.get(timeStamp).getSpecial());

        timeStamp = game.getTimeStamp();
        if(game.stageChange(timeStamp) != null) {
            JSONArray data = game.stageChange(timeStamp);
            int msgCnt = game.getMsgCount() + 1 ;
            game.setMsgCount(msgCnt);
            sendMessageBack(MsgUtil.makeMsg(110, "newState", data,msgCnt), seatSession);
            sendMessageBack(MsgUtil.makeMsg(110, "newState", data,msgCnt), enemySession);
//            game.changeAble();
        }

        return;

    }

    private void gameRun(int rid,Session curSession,int seat,int type) {

        Game game = allGames.get(rid);
        int enemy = (seat ^ 1),cardOrder = 0,cardId = 0;
        int timeStamp = game.getTimeStamp();

        Session seatSession = clients.get(game.getPlayer()[seat].getPlayerId());
        Session enemySession = clients.get(game.getPlayer()[enemy].getPlayerId());

        if(type >= 200) {
            cardOrder = type - 200;
            type = 2;
        }

        if(!game.isRunning())
        {                                                     //判断输赢
            if(game.getPlayer()[enemy].getCurHp() <= 0){
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

        switch (type){
            case 0:{
                game.gameBegin(seat);
            }
                break;
            case 1:{
                game.getCard(seat);
            }
                break;
            case 2:{
                cardId = game.useCard(seat,cardOrder);
            }
                break;
            case 3:{
                game.endTurn(seat);
            }
                break;
            case 4:{
                game.getCard(seat,1);
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
//            game.changeAble();
        }

        int msgCount = game.getMsgCount() + 1 ;
        game.setMsgCount(msgCount);

        switch (type){
            case 0:{
                sendMessageBack(MsgUtil.makeMsg(104,"yourTurn",game.packStat(seat),msgCount),seatSession);
                sendMessageBack(MsgUtil.makeMsg(105,"waitTurn",game.packStat(enemy),msgCount),enemySession);
                gameRun(rid,curSession,seat,1);
                return;
            }
            case 1:
            case 4:{                                            //抽卡信息
                sendMessageBack(MsgUtil.makeMsg(106,"getCard",game.cardMsg(true),msgCount),seatSession);
                sendMessageBack(MsgUtil.makeMsg(107,"getCard",game.cardMsg(false),msgCount),enemySession);
            }
            break;
            case 2:{
                JSONObject cardData0 = game.cardMsg(true);
                JSONObject cardData1 = game.cardMsg(false);
                cardData0.put("cardId",cardId);
                cardData1.put("cardId",cardId);
                sendMessageBack(MsgUtil.makeMsg(108,"useCard",cardData0,msgCount),seatSession);
                sendMessageBack(MsgUtil.makeMsg(109,"useCard",cardData1,msgCount),enemySession);
            }
            break;
            case 3:{
//                sendMessageBack(MsgUtil.makeMsg(104,"endTurn",msgCount),seatSession);
//                sendMessageBack(MsgUtil.makeMsg(105,"endTurn",msgCount),enemySession);
                gameRun(rid,enemySession,enemy,0);        // 敌方回合开始
                game.setPlayerNow(enemy);
                return ;
            }

            default:
                break;
        }


        if(!game.isRunning())
        {                                                     //判断输赢
            if(game.getPlayer()[enemy].getCurHp() <= 0){
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

        if(game.allState.size()>0 ){
            if(game.allState.get(timeStamp).getSpecial() == 1)
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
        if(splitMessage.length > 1 && !splitMessage[0].equals("endTurn"))
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
                    if(splitMessage.length > 1){
                        List<Integer> discardList = com.alibaba.fastjson.JSONArray.parseArray(splitMessage[1],Integer.class);
                        disCarded(rid,session,seat,discardList);
                    }
                    if (seat != playerNow){
                        sendMessageBack(MsgUtil.makeMsg(-100,"操作失败"),session);
                        break;
                    }
                    gameRun(rid, session, seat, 3);
                    return;
                }
//                case "update":
//                    gameRun(rid,session,seat,3);
//                    return;
                case "useSkill": useSkill(rid,session,seat);
                    return;
                default:
                    break;
            }
        }catch (Exception e){
            log.info("发生错误");
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
    private void sendMessageBack(Msg message, Session fromSession) {
        try {
            JSONObject data = JSONObject.fromObject(message);
            fromSession.getBasicRemote().sendText(data.toString());
            log.info("服务端给客户端[{}]发送消息:{}", fromSession.getId(), data.toString());
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败：", e);
        }
    }
<<<<<<< HEAD
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
=======
>>>>>>> 1a9d63d (14:57)
    public class ExceptionMessage  extends Exception {
        public ExceptionMessage(String message) {
            super(message);
        }
    }
}