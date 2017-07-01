package majiang;

import static com.mahjong.server.game.action.standard.StandardActionType.DISCARD;
import static com.mahjong.server.game.action.standard.StandardActionType.DRAW;
import static com.mahjong.server.game.action.standard.StandardActionType.WIN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.exception.IllegalActionException;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.action.standard.DealActionType;
import com.mahjong.server.game.action.standard.DiscardActionType;
import com.mahjong.server.game.action.standard.DrawActionType;
import com.mahjong.server.game.action.standard.WinActionType;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.enums.PlayerLocation.Relation;
import com.mahjong.server.game.object.DisCardActionAndLocation;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileGroup;
import com.mahjong.server.game.object.TileGroupType;
import com.mahjong.server.netty.handler.HandlerHelper;
import com.mahjong.server.netty.model.EnterRoomRespModel;
import com.mahjong.server.netty.model.RoomRespModel;

public class PlayTest {
	public static void main(String[] args) throws IllegalActionException {
		UserInfo userInfo1 = new UserInfo();
		userInfo1.setWeixinMark("user1");
		RoomContext roomContex = HouseContext.addRoom(userInfo1, "11111", 1);
		UserInfo userInfo2 = new UserInfo();
		userInfo2.setWeixinMark("user2");
		UserInfo userInfo3 = new UserInfo();
		userInfo3.setWeixinMark("user3");
		UserInfo userInfo4 = new UserInfo();
		userInfo4.setWeixinMark("user4");
		roomContex.getGameContext().joinRoom(userInfo2);
		roomContex.getGameContext().joinRoom(userInfo3);
		roomContex.getGameContext().joinRoom(userInfo4);
		DealActionType dealActionType = new DealActionType();
		dealActionType.doAction(roomContex.getGameContext(), roomContex.getGameContext().getZhuangLocation(), null);
		//roomContex.getGameContext().getTable().printAllPlayTiles();

//		EnterRoomRespModel enterRoomRespModel = new EnterRoomRespModel("user1", true, "发牌", roomContex,
//				roomContex.getGameContext().getZhuangLocation());
//		System.out.println("zhuang  enterRoomRespModel:" + JSON.toJSONString(enterRoomRespModel));
//		EnterRoomRespModel nextEnterRoomRespModel = new EnterRoomRespModel("user1", true, "发牌", roomContex,
//				roomContex.getGameContext().getZhuangLocation().getLocationOf(Relation.NEXT));
//		System.out.println("zhuang.next  enterRoomRespModel:" + JSON.toJSONString(nextEnterRoomRespModel));
//		DiscardActionType discardActionType = new DiscardActionType();
//		discardActionType.doAction(roomContex.getGameContext(), roomContex.getGameContext().getZhuangLocation(),
//				new Action(DISCARD,
//						roomContex.getGameContext().getTable()
//								.getPlayerByLocation(roomContex.getGameContext().getZhuangLocation())
//								.getLastDrawedTile()));
//		roomContex.getGameContext().getTable().printAllPlayTiles();
	PlayerInfo playerInfo=	roomContex.getGameContext().getTable().getPlayerByLocation(PlayerLocation.EAST);
	playerInfo.setAliveTiles(new Tile(new byte[]{0x13,0x13,0x15,0x28,0x12}));
	List<TileGroup> tileGroups=new ArrayList<TileGroup>();
	tileGroups.add(new TileGroup(TileGroupType.CHI_GROUP, new Tile(new byte[]{0x01, 0x02, 0x03})));
	tileGroups.add(new TileGroup(TileGroupType.PENG_GROUP, new Tile(new byte[]{0x28,0x28,0x28})));
	tileGroups.add(new TileGroup(TileGroupType.PENG_GROUP, new Tile(new byte[]{0x37,0x37,0x37})));

	playerInfo.setTileGroups(tileGroups);
	roomContex.getGameContext().getTable().setFanhui((byte)0x12);
 	roomContex.getGameContext().getLocalDoneActions().add(new ActionAndLocation(new Action(DISCARD, new Tile(new byte[]{0x08})), PlayerLocation.WEST));
		roomContex.getGameContext().getTable().printAllPlayTiles();
		WinActionType winActionType = new WinActionType();
		System.out.println(winActionType.isLegalAction(roomContex.getGameContext(),
				roomContex.getGameContext().getZhuangLocation(), new Action(WIN)));
	}
}
