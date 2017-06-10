package com.mahjong.server.game.rule;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class RuleInfo {
	private Set<PlayRule> playRules;
	private FangKa fangKa;

	public Set<PlayRule> getPlayRules() {
		return playRules;
	}

	public void setPlayRules(Set<PlayRule> playRules) {
		this.playRules = playRules;
	}

	public FangKa getFangKa() {
		return fangKa;
	}

	public void setFangKa(FangKa fangKa) {
		this.fangKa = fangKa;
	}

	public String getMysqlRule() {
		if (CollectionUtils.isEmpty(playRules)) {
			return StringUtils.EMPTY;
		}
		BitSet bitSet = new BitSet(PlayRule.values().length);
		for (PlayRule playRule : playRules) {
			bitSet.set(playRule.id-1);
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < PlayRule.values().length; i++) {
			boolean flag = bitSet.get(i);
			if (flag) {
				sb.append("1");
			} else {
				sb.append("0");
			}
		}
		return sb.toString();
	}

	public static Set<PlayRule> parseRuleFromBitString(String bitRule) {
		Set<PlayRule> set = new HashSet<PlayRule>();
		if (StringUtils.isEmpty(bitRule)) {
			return set;
		}
		char[] ruleStates = bitRule.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < ruleStates.length; i++) {
			char ruleState = ruleStates[i];
			if (ruleState == '1') {
				PlayRule playRule = PlayRule.findPlayRuleById(i);
				if (playRule != null) {
					set.add(playRule);
				}
			}
		}
		return set;
	}
}
