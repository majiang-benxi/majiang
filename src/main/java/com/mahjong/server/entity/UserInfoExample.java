package com.mahjong.server.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNickNameIsNull() {
            addCriterion("nick_name is null");
            return (Criteria) this;
        }

        public Criteria andNickNameIsNotNull() {
            addCriterion("nick_name is not null");
            return (Criteria) this;
        }

        public Criteria andNickNameEqualTo(String value) {
            addCriterion("nick_name =", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotEqualTo(String value) {
            addCriterion("nick_name <>", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameGreaterThan(String value) {
            addCriterion("nick_name >", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameGreaterThanOrEqualTo(String value) {
            addCriterion("nick_name >=", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLessThan(String value) {
            addCriterion("nick_name <", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLessThanOrEqualTo(String value) {
            addCriterion("nick_name <=", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLike(String value) {
            addCriterion("nick_name like", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotLike(String value) {
            addCriterion("nick_name not like", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameIn(List<String> values) {
            addCriterion("nick_name in", values, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotIn(List<String> values) {
            addCriterion("nick_name not in", values, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameBetween(String value1, String value2) {
            addCriterion("nick_name between", value1, value2, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotBetween(String value1, String value2) {
            addCriterion("nick_name not between", value1, value2, "nickName");
            return (Criteria) this;
        }

        public Criteria andWeixinMarkIsNull() {
            addCriterion("weixin_mark is null");
            return (Criteria) this;
        }

        public Criteria andWeixinMarkIsNotNull() {
            addCriterion("weixin_mark is not null");
            return (Criteria) this;
        }

        public Criteria andWeixinMarkEqualTo(String value) {
            addCriterion("weixin_mark =", value, "weixinMark");
            return (Criteria) this;
        }

        public Criteria andWeixinMarkNotEqualTo(String value) {
            addCriterion("weixin_mark <>", value, "weixinMark");
            return (Criteria) this;
        }

        public Criteria andWeixinMarkGreaterThan(String value) {
            addCriterion("weixin_mark >", value, "weixinMark");
            return (Criteria) this;
        }

        public Criteria andWeixinMarkGreaterThanOrEqualTo(String value) {
            addCriterion("weixin_mark >=", value, "weixinMark");
            return (Criteria) this;
        }

        public Criteria andWeixinMarkLessThan(String value) {
            addCriterion("weixin_mark <", value, "weixinMark");
            return (Criteria) this;
        }

        public Criteria andWeixinMarkLessThanOrEqualTo(String value) {
            addCriterion("weixin_mark <=", value, "weixinMark");
            return (Criteria) this;
        }

        public Criteria andWeixinMarkLike(String value) {
            addCriterion("weixin_mark like", value, "weixinMark");
            return (Criteria) this;
        }

        public Criteria andWeixinMarkNotLike(String value) {
            addCriterion("weixin_mark not like", value, "weixinMark");
            return (Criteria) this;
        }

        public Criteria andWeixinMarkIn(List<String> values) {
            addCriterion("weixin_mark in", values, "weixinMark");
            return (Criteria) this;
        }

        public Criteria andWeixinMarkNotIn(List<String> values) {
            addCriterion("weixin_mark not in", values, "weixinMark");
            return (Criteria) this;
        }

        public Criteria andWeixinMarkBetween(String value1, String value2) {
            addCriterion("weixin_mark between", value1, value2, "weixinMark");
            return (Criteria) this;
        }

        public Criteria andWeixinMarkNotBetween(String value1, String value2) {
            addCriterion("weixin_mark not between", value1, value2, "weixinMark");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(Byte value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(Byte value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(Byte value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(Byte value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(Byte value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(Byte value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<Byte> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<Byte> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(Byte value1, Byte value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(Byte value1, Byte value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andHeadImgurlIsNull() {
            addCriterion("head_imgurl is null");
            return (Criteria) this;
        }

        public Criteria andHeadImgurlIsNotNull() {
            addCriterion("head_imgurl is not null");
            return (Criteria) this;
        }

        public Criteria andHeadImgurlEqualTo(String value) {
            addCriterion("head_imgurl =", value, "headImgurl");
            return (Criteria) this;
        }

        public Criteria andHeadImgurlNotEqualTo(String value) {
            addCriterion("head_imgurl <>", value, "headImgurl");
            return (Criteria) this;
        }

        public Criteria andHeadImgurlGreaterThan(String value) {
            addCriterion("head_imgurl >", value, "headImgurl");
            return (Criteria) this;
        }

        public Criteria andHeadImgurlGreaterThanOrEqualTo(String value) {
            addCriterion("head_imgurl >=", value, "headImgurl");
            return (Criteria) this;
        }

        public Criteria andHeadImgurlLessThan(String value) {
            addCriterion("head_imgurl <", value, "headImgurl");
            return (Criteria) this;
        }

        public Criteria andHeadImgurlLessThanOrEqualTo(String value) {
            addCriterion("head_imgurl <=", value, "headImgurl");
            return (Criteria) this;
        }

        public Criteria andHeadImgurlLike(String value) {
            addCriterion("head_imgurl like", value, "headImgurl");
            return (Criteria) this;
        }

        public Criteria andHeadImgurlNotLike(String value) {
            addCriterion("head_imgurl not like", value, "headImgurl");
            return (Criteria) this;
        }

        public Criteria andHeadImgurlIn(List<String> values) {
            addCriterion("head_imgurl in", values, "headImgurl");
            return (Criteria) this;
        }

        public Criteria andHeadImgurlNotIn(List<String> values) {
            addCriterion("head_imgurl not in", values, "headImgurl");
            return (Criteria) this;
        }

        public Criteria andHeadImgurlBetween(String value1, String value2) {
            addCriterion("head_imgurl between", value1, value2, "headImgurl");
            return (Criteria) this;
        }

        public Criteria andHeadImgurlNotBetween(String value1, String value2) {
            addCriterion("head_imgurl not between", value1, value2, "headImgurl");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andRoomCartNumIsNull() {
            addCriterion("room_cart_num is null");
            return (Criteria) this;
        }

        public Criteria andRoomCartNumIsNotNull() {
            addCriterion("room_cart_num is not null");
            return (Criteria) this;
        }

        public Criteria andRoomCartNumEqualTo(Integer value) {
            addCriterion("room_cart_num =", value, "roomCartNum");
            return (Criteria) this;
        }

        public Criteria andRoomCartNumNotEqualTo(Integer value) {
            addCriterion("room_cart_num <>", value, "roomCartNum");
            return (Criteria) this;
        }

        public Criteria andRoomCartNumGreaterThan(Integer value) {
            addCriterion("room_cart_num >", value, "roomCartNum");
            return (Criteria) this;
        }

        public Criteria andRoomCartNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("room_cart_num >=", value, "roomCartNum");
            return (Criteria) this;
        }

        public Criteria andRoomCartNumLessThan(Integer value) {
            addCriterion("room_cart_num <", value, "roomCartNum");
            return (Criteria) this;
        }

        public Criteria andRoomCartNumLessThanOrEqualTo(Integer value) {
            addCriterion("room_cart_num <=", value, "roomCartNum");
            return (Criteria) this;
        }

        public Criteria andRoomCartNumIn(List<Integer> values) {
            addCriterion("room_cart_num in", values, "roomCartNum");
            return (Criteria) this;
        }

        public Criteria andRoomCartNumNotIn(List<Integer> values) {
            addCriterion("room_cart_num not in", values, "roomCartNum");
            return (Criteria) this;
        }

        public Criteria andRoomCartNumBetween(Integer value1, Integer value2) {
            addCriterion("room_cart_num between", value1, value2, "roomCartNum");
            return (Criteria) this;
        }

        public Criteria andRoomCartNumNotBetween(Integer value1, Integer value2) {
            addCriterion("room_cart_num not between", value1, value2, "roomCartNum");
            return (Criteria) this;
        }

        public Criteria andScoreNumIsNull() {
            addCriterion("score_num is null");
            return (Criteria) this;
        }

        public Criteria andScoreNumIsNotNull() {
            addCriterion("score_num is not null");
            return (Criteria) this;
        }

        public Criteria andScoreNumEqualTo(Integer value) {
            addCriterion("score_num =", value, "scoreNum");
            return (Criteria) this;
        }

        public Criteria andScoreNumNotEqualTo(Integer value) {
            addCriterion("score_num <>", value, "scoreNum");
            return (Criteria) this;
        }

        public Criteria andScoreNumGreaterThan(Integer value) {
            addCriterion("score_num >", value, "scoreNum");
            return (Criteria) this;
        }

        public Criteria andScoreNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("score_num >=", value, "scoreNum");
            return (Criteria) this;
        }

        public Criteria andScoreNumLessThan(Integer value) {
            addCriterion("score_num <", value, "scoreNum");
            return (Criteria) this;
        }

        public Criteria andScoreNumLessThanOrEqualTo(Integer value) {
            addCriterion("score_num <=", value, "scoreNum");
            return (Criteria) this;
        }

        public Criteria andScoreNumIn(List<Integer> values) {
            addCriterion("score_num in", values, "scoreNum");
            return (Criteria) this;
        }

        public Criteria andScoreNumNotIn(List<Integer> values) {
            addCriterion("score_num not in", values, "scoreNum");
            return (Criteria) this;
        }

        public Criteria andScoreNumBetween(Integer value1, Integer value2) {
            addCriterion("score_num between", value1, value2, "scoreNum");
            return (Criteria) this;
        }

        public Criteria andScoreNumNotBetween(Integer value1, Integer value2) {
            addCriterion("score_num not between", value1, value2, "scoreNum");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpIsNull() {
            addCriterion("last_login_ip is null");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpIsNotNull() {
            addCriterion("last_login_ip is not null");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpEqualTo(String value) {
            addCriterion("last_login_ip =", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpNotEqualTo(String value) {
            addCriterion("last_login_ip <>", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpGreaterThan(String value) {
            addCriterion("last_login_ip >", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpGreaterThanOrEqualTo(String value) {
            addCriterion("last_login_ip >=", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpLessThan(String value) {
            addCriterion("last_login_ip <", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpLessThanOrEqualTo(String value) {
            addCriterion("last_login_ip <=", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpLike(String value) {
            addCriterion("last_login_ip like", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpNotLike(String value) {
            addCriterion("last_login_ip not like", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpIn(List<String> values) {
            addCriterion("last_login_ip in", values, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpNotIn(List<String> values) {
            addCriterion("last_login_ip not in", values, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpBetween(String value1, String value2) {
            addCriterion("last_login_ip between", value1, value2, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpNotBetween(String value1, String value2) {
            addCriterion("last_login_ip not between", value1, value2, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIsNull() {
            addCriterion("last_login_time is null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIsNotNull() {
            addCriterion("last_login_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeEqualTo(Date value) {
            addCriterion("last_login_time =", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotEqualTo(Date value) {
            addCriterion("last_login_time <>", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThan(Date value) {
            addCriterion("last_login_time >", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_login_time >=", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThan(Date value) {
            addCriterion("last_login_time <", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_login_time <=", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIn(List<Date> values) {
            addCriterion("last_login_time in", values, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotIn(List<Date> values) {
            addCriterion("last_login_time not in", values, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeBetween(Date value1, Date value2) {
            addCriterion("last_login_time between", value1, value2, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_login_time not between", value1, value2, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andCurrRoomIsNull() {
            addCriterion("curr_room is null");
            return (Criteria) this;
        }

        public Criteria andCurrRoomIsNotNull() {
            addCriterion("curr_room is not null");
            return (Criteria) this;
        }

        public Criteria andCurrRoomEqualTo(Integer value) {
            addCriterion("curr_room =", value, "currRoom");
            return (Criteria) this;
        }

        public Criteria andCurrRoomNotEqualTo(Integer value) {
            addCriterion("curr_room <>", value, "currRoom");
            return (Criteria) this;
        }

        public Criteria andCurrRoomGreaterThan(Integer value) {
            addCriterion("curr_room >", value, "currRoom");
            return (Criteria) this;
        }

        public Criteria andCurrRoomGreaterThanOrEqualTo(Integer value) {
            addCriterion("curr_room >=", value, "currRoom");
            return (Criteria) this;
        }

        public Criteria andCurrRoomLessThan(Integer value) {
            addCriterion("curr_room <", value, "currRoom");
            return (Criteria) this;
        }

        public Criteria andCurrRoomLessThanOrEqualTo(Integer value) {
            addCriterion("curr_room <=", value, "currRoom");
            return (Criteria) this;
        }

        public Criteria andCurrRoomIn(List<Integer> values) {
            addCriterion("curr_room in", values, "currRoom");
            return (Criteria) this;
        }

        public Criteria andCurrRoomNotIn(List<Integer> values) {
            addCriterion("curr_room not in", values, "currRoom");
            return (Criteria) this;
        }

        public Criteria andCurrRoomBetween(Integer value1, Integer value2) {
            addCriterion("curr_room between", value1, value2, "currRoom");
            return (Criteria) this;
        }

        public Criteria andCurrRoomNotBetween(Integer value1, Integer value2) {
            addCriterion("curr_room not between", value1, value2, "currRoom");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Byte value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Byte value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Byte value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Byte value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Byte value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Byte value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Byte> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Byte> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Byte value1, Byte value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Byte value1, Byte value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}