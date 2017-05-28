package majiang;

import static com.mahjong.server.game.action.standard.StandardActionType.DISCARD;
import static com.mahjong.server.game.action.standard.StandardActionType.DRAW;

import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.exception.IllegalActionException;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.standard.DealActionType;
import com.mahjong.server.game.action.standard.DiscardActionType;
import com.mahjong.server.game.action.standard.DrawActionType;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.PlayerLocation.Relation;

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
		DiscardActionType discardActionType = new DiscardActionType();
		discardActionType.doAction(roomContex.getGameContext(), roomContex.getGameContext().getZhuangLocation(),
				new Action(DISCARD,
						roomContex.getGameContext().getTable()
								.getPlayerByLocation(roomContex.getGameContext().getZhuangLocation())
								.getLastDrawedTile()));
		roomContex.getGameContext().getTable().printAllPlayTiles();
		DrawActionType drawActionType = new DrawActionType();
		drawActionType.doAction(roomContex.getGameContext(),
				roomContex.getGameContext().getZhuangLocation().getLocationOf(Relation.NEXT),
				new Action(DRAW));
		roomContex.getGameContext().getTable().printAllPlayTiles();

	}
}
