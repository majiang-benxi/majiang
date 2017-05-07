package com.mahjong.server.game.action.standard;

import static com.mahjong.server.game.action.standard.StandardActionType.DEAL;
import static com.mahjong.server.game.action.standard.StandardActionType.DISCARD;
import static com.mahjong.server.game.action.standard.StandardActionType.DRAW;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import com.mahjong.server.exception.IllegalActionException;
import com.mahjong.server.game.action.AbstractActionType;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.GameResult;
import com.mahjong.server.game.object.HuType;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.WinInfo;
import com.mahjong.server.game.rule.RuleInfo;
import com.mahjong.server.game.rule.win.StandardHuType;

//TODO  胡牌的时候再去判断是否可以穷胡和飘(直接调用对应封装的方法即可)，以及对应分数的统计
public class WinActionType extends AbstractActionType {

	@Override
	public boolean canPass(GameContext context, PlayerLocation location) {
		return true;
	}

	@Override
	protected int getActionTilesSize() {
		return 0;
	}

	@Override
	protected boolean checkLastActionCondition(ActionAndLocation al, PlayerLocation playerLocation) {
		// 必须是发牌、自己摸牌，或别人打牌后
		if (DEAL.matchBy(al.getActionType()) || (al.getLocation() == playerLocation ? DRAW.matchBy(al.getActionType())
				: DISCARD.matchBy(al.getActionType()))) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void doLegalAction(GameContext context, PlayerLocation playerLocation, Tile tile)
			throws IllegalActionException {
		Action lastAction = context.getLastAction();
		Tile lastTile = lastAction.getTile();
		boolean ziMo = !DISCARD.matchBy(context.getLastAction().getType());
		PlayerInfo playerInfo = context.getTable().getPlayerByLocation(playerLocation);
		if (!ziMo) {
			playerInfo.getAliveTiles().addTile(lastTile);// 非自摸情况，把别人的牌加进来判断，自摸的时候牌已经在自摸的动作中添加了
		}
		boolean isZhuang = false;
		if (context.getZhuangLocation() == playerLocation) {
			isZhuang = false;
		}
		WinInfo winInfo = WinInfo.fromPlayerTiles(playerInfo, context.getTable().getFanhui(), ziMo, isZhuang);
		HuType huType = chooseBestWinType(winInfo, context.getGameStrategy().getRuleInfo());
		if (huType == null) {
			throw new IllegalActionException(context, playerLocation, new Action(this));
		} else {
			GameResult result = new GameResult(context.getTable().getPlayerInfos(), context.getZhuangLocation());
			result.setWinnerLocation(playerLocation);
			result.setWinTile(winInfo.getWinTile());
			if (!ziMo) {
				result.setPaoerLocation(context.getLastActionLocation());
			}
			context.setGameResult(result);// TODO 赢牌之后的分数，和牌【穷胡，飘胡】判断和写入
		}


	}

	// TODO 把玩家能赢牌的类型写入到context中
	@Override
	protected boolean isLegalActionWithPreconition(GameContext context, PlayerLocation playerLocation, Tile tiles) {
		Action lastAction = context.getLastAction();
		Tile lastTile = lastAction.getTile();
		boolean ziMo = !DISCARD.matchBy(context.getLastAction().getType());
		PlayerInfo playerInfo = context.getTable().getPlayerByLocation(playerLocation);
		if (!ziMo) {
			playerInfo.getAliveTiles().addTile(lastTile);// 非自摸情况，把别人的牌加进来判断，自摸的时候牌已经在自摸的动作中添加了
		}
		boolean isZhuang = false;
		if (context.getZhuangLocation() == playerLocation) {
			isZhuang = false;
		}
		WinInfo winInfo = WinInfo.fromPlayerTiles(playerInfo, context.getTable().getFanhui(), ziMo, isZhuang);
		HuType huType = chooseBestWinType(winInfo, context.getGameStrategy().getRuleInfo());
		if (!ziMo) {
			playerInfo.getAliveTiles().removeAll(lastTile);// 把之前加入判断的牌给去掉。
		}
		if (huType == null) {
			return false;
		} else {
			return true;
		}
	}

	// TODO 此处顺序需要在做处理
	private HuType chooseBestWinType(final WinInfo winInfo, final RuleInfo ruleInfo) {
		HuType[] allHuTypes = StandardHuType.values();
		// 按照可能的分数从大到小排序
		Collections.sort(Arrays.asList(allHuTypes), new Comparator<HuType>() {
			@Override
			public int compare(HuType o1, HuType o2) {
				if (o1.getZhuangScore(winInfo, ruleInfo) < o1.getZhuangScore(winInfo, ruleInfo)) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		for (HuType huType : allHuTypes) {
			boolean canHu = huType.canHU(winInfo, ruleInfo);
			if (canHu) {
				return huType;
			}
		}
		return null;
	}
}
