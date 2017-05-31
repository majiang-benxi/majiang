package com.mahjong.server.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRoomRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserRoomRecordExample() {
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

        public Criteria andRoomRecordIdIsNull() {
            addCriterion("room_record_id is null");
            return (Criteria) this;
        }

        public Criteria andRoomRecordIdIsNotNull() {
            addCriterion("room_record_id is not null");
            return (Criteria) this;
        }

        public Criteria andRoomRecordIdEqualTo(Integer value) {
            addCriterion("room_record_id =", value, "roomRecordId");
            return (Criteria) this;
        }

        public Criteria andRoomRecordIdNotEqualTo(Integer value) {
            addCriterion("room_record_id <>", value, "roomRecordId");
            return (Criteria) this;
        }

        public Criteria andRoomRecordIdGreaterThan(Integer value) {
            addCriterion("room_record_id >", value, "roomRecordId");
            return (Criteria) this;
        }

        public Criteria andRoomRecordIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("room_record_id >=", value, "roomRecordId");
            return (Criteria) this;
        }

        public Criteria andRoomRecordIdLessThan(Integer value) {
            addCriterion("room_record_id <", value, "roomRecordId");
            return (Criteria) this;
        }

        public Criteria andRoomRecordIdLessThanOrEqualTo(Integer value) {
            addCriterion("room_record_id <=", value, "roomRecordId");
            return (Criteria) this;
        }

        public Criteria andRoomRecordIdIn(List<Integer> values) {
            addCriterion("room_record_id in", values, "roomRecordId");
            return (Criteria) this;
        }

        public Criteria andRoomRecordIdNotIn(List<Integer> values) {
            addCriterion("room_record_id not in", values, "roomRecordId");
            return (Criteria) this;
        }

        public Criteria andRoomRecordIdBetween(Integer value1, Integer value2) {
            addCriterion("room_record_id between", value1, value2, "roomRecordId");
            return (Criteria) this;
        }

        public Criteria andRoomRecordIdNotBetween(Integer value1, Integer value2) {
            addCriterion("room_record_id not between", value1, value2, "roomRecordId");
            return (Criteria) this;
        }

        public Criteria andRoomNumIsNull() {
            addCriterion("room_num is null");
            return (Criteria) this;
        }

        public Criteria andRoomNumIsNotNull() {
            addCriterion("room_num is not null");
            return (Criteria) this;
        }

        public Criteria andRoomNumEqualTo(Integer value) {
            addCriterion("room_num =", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumNotEqualTo(Integer value) {
            addCriterion("room_num <>", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumGreaterThan(Integer value) {
            addCriterion("room_num >", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("room_num >=", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumLessThan(Integer value) {
            addCriterion("room_num <", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumLessThanOrEqualTo(Integer value) {
            addCriterion("room_num <=", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumIn(List<Integer> values) {
            addCriterion("room_num in", values, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumNotIn(List<Integer> values) {
            addCriterion("room_num not in", values, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumBetween(Integer value1, Integer value2) {
            addCriterion("room_num between", value1, value2, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumNotBetween(Integer value1, Integer value2) {
            addCriterion("room_num not between", value1, value2, "roomNum");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserDirectionIsNull() {
            addCriterion("user_direction is null");
            return (Criteria) this;
        }

        public Criteria andUserDirectionIsNotNull() {
            addCriterion("user_direction is not null");
            return (Criteria) this;
        }

        public Criteria andUserDirectionEqualTo(Byte value) {
            addCriterion("user_direction =", value, "userDirection");
            return (Criteria) this;
        }

        public Criteria andUserDirectionNotEqualTo(Byte value) {
            addCriterion("user_direction <>", value, "userDirection");
            return (Criteria) this;
        }

        public Criteria andUserDirectionGreaterThan(Byte value) {
            addCriterion("user_direction >", value, "userDirection");
            return (Criteria) this;
        }

        public Criteria andUserDirectionGreaterThanOrEqualTo(Byte value) {
            addCriterion("user_direction >=", value, "userDirection");
            return (Criteria) this;
        }

        public Criteria andUserDirectionLessThan(Byte value) {
            addCriterion("user_direction <", value, "userDirection");
            return (Criteria) this;
        }

        public Criteria andUserDirectionLessThanOrEqualTo(Byte value) {
            addCriterion("user_direction <=", value, "userDirection");
            return (Criteria) this;
        }

        public Criteria andUserDirectionIn(List<Byte> values) {
            addCriterion("user_direction in", values, "userDirection");
            return (Criteria) this;
        }

        public Criteria andUserDirectionNotIn(List<Byte> values) {
            addCriterion("user_direction not in", values, "userDirection");
            return (Criteria) this;
        }

        public Criteria andUserDirectionBetween(Byte value1, Byte value2) {
            addCriterion("user_direction between", value1, value2, "userDirection");
            return (Criteria) this;
        }

        public Criteria andUserDirectionNotBetween(Byte value1, Byte value2) {
            addCriterion("user_direction not between", value1, value2, "userDirection");
            return (Criteria) this;
        }

        public Criteria andOperateTimeIsNull() {
            addCriterion("operate_time is null");
            return (Criteria) this;
        }

        public Criteria andOperateTimeIsNotNull() {
            addCriterion("operate_time is not null");
            return (Criteria) this;
        }

        public Criteria andOperateTimeEqualTo(Date value) {
            addCriterion("operate_time =", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeNotEqualTo(Date value) {
            addCriterion("operate_time <>", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeGreaterThan(Date value) {
            addCriterion("operate_time >", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("operate_time >=", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeLessThan(Date value) {
            addCriterion("operate_time <", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeLessThanOrEqualTo(Date value) {
            addCriterion("operate_time <=", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeIn(List<Date> values) {
            addCriterion("operate_time in", values, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeNotIn(List<Date> values) {
            addCriterion("operate_time not in", values, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeBetween(Date value1, Date value2) {
            addCriterion("operate_time between", value1, value2, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeNotBetween(Date value1, Date value2) {
            addCriterion("operate_time not between", value1, value2, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTypeIsNull() {
            addCriterion("operate_type is null");
            return (Criteria) this;
        }

        public Criteria andOperateTypeIsNotNull() {
            addCriterion("operate_type is not null");
            return (Criteria) this;
        }

        public Criteria andOperateTypeEqualTo(Byte value) {
            addCriterion("operate_type =", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeNotEqualTo(Byte value) {
            addCriterion("operate_type <>", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeGreaterThan(Byte value) {
            addCriterion("operate_type >", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("operate_type >=", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeLessThan(Byte value) {
            addCriterion("operate_type <", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeLessThanOrEqualTo(Byte value) {
            addCriterion("operate_type <=", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeIn(List<Byte> values) {
            addCriterion("operate_type in", values, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeNotIn(List<Byte> values) {
            addCriterion("operate_type not in", values, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeBetween(Byte value1, Byte value2) {
            addCriterion("operate_type between", value1, value2, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("operate_type not between", value1, value2, "operateType");
            return (Criteria) this;
        }

        public Criteria andUserIpIsNull() {
            addCriterion("user_ip is null");
            return (Criteria) this;
        }

        public Criteria andUserIpIsNotNull() {
            addCriterion("user_ip is not null");
            return (Criteria) this;
        }

        public Criteria andUserIpEqualTo(String value) {
            addCriterion("user_ip =", value, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpNotEqualTo(String value) {
            addCriterion("user_ip <>", value, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpGreaterThan(String value) {
            addCriterion("user_ip >", value, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpGreaterThanOrEqualTo(String value) {
            addCriterion("user_ip >=", value, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpLessThan(String value) {
            addCriterion("user_ip <", value, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpLessThanOrEqualTo(String value) {
            addCriterion("user_ip <=", value, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpLike(String value) {
            addCriterion("user_ip like", value, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpNotLike(String value) {
            addCriterion("user_ip not like", value, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpIn(List<String> values) {
            addCriterion("user_ip in", values, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpNotIn(List<String> values) {
            addCriterion("user_ip not in", values, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpBetween(String value1, String value2) {
            addCriterion("user_ip between", value1, value2, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpNotBetween(String value1, String value2) {
            addCriterion("user_ip not between", value1, value2, "userIp");
            return (Criteria) this;
        }

        public Criteria andOperateCauseIsNull() {
            addCriterion("operate_cause is null");
            return (Criteria) this;
        }

        public Criteria andOperateCauseIsNotNull() {
            addCriterion("operate_cause is not null");
            return (Criteria) this;
        }

        public Criteria andOperateCauseEqualTo(Byte value) {
            addCriterion("operate_cause =", value, "operateCause");
            return (Criteria) this;
        }

        public Criteria andOperateCauseNotEqualTo(Byte value) {
            addCriterion("operate_cause <>", value, "operateCause");
            return (Criteria) this;
        }

        public Criteria andOperateCauseGreaterThan(Byte value) {
            addCriterion("operate_cause >", value, "operateCause");
            return (Criteria) this;
        }

        public Criteria andOperateCauseGreaterThanOrEqualTo(Byte value) {
            addCriterion("operate_cause >=", value, "operateCause");
            return (Criteria) this;
        }

        public Criteria andOperateCauseLessThan(Byte value) {
            addCriterion("operate_cause <", value, "operateCause");
            return (Criteria) this;
        }

        public Criteria andOperateCauseLessThanOrEqualTo(Byte value) {
            addCriterion("operate_cause <=", value, "operateCause");
            return (Criteria) this;
        }

        public Criteria andOperateCauseIn(List<Byte> values) {
            addCriterion("operate_cause in", values, "operateCause");
            return (Criteria) this;
        }

        public Criteria andOperateCauseNotIn(List<Byte> values) {
            addCriterion("operate_cause not in", values, "operateCause");
            return (Criteria) this;
        }

        public Criteria andOperateCauseBetween(Byte value1, Byte value2) {
            addCriterion("operate_cause between", value1, value2, "operateCause");
            return (Criteria) this;
        }

        public Criteria andOperateCauseNotBetween(Byte value1, Byte value2) {
            addCriterion("operate_cause not between", value1, value2, "operateCause");
            return (Criteria) this;
        }

        public Criteria andWinTimesIsNull() {
            addCriterion("win_times is null");
            return (Criteria) this;
        }

        public Criteria andWinTimesIsNotNull() {
            addCriterion("win_times is not null");
            return (Criteria) this;
        }

        public Criteria andWinTimesEqualTo(Integer value) {
            addCriterion("win_times =", value, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesNotEqualTo(Integer value) {
            addCriterion("win_times <>", value, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesGreaterThan(Integer value) {
            addCriterion("win_times >", value, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("win_times >=", value, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesLessThan(Integer value) {
            addCriterion("win_times <", value, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesLessThanOrEqualTo(Integer value) {
            addCriterion("win_times <=", value, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesIn(List<Integer> values) {
            addCriterion("win_times in", values, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesNotIn(List<Integer> values) {
            addCriterion("win_times not in", values, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesBetween(Integer value1, Integer value2) {
            addCriterion("win_times between", value1, value2, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("win_times not between", value1, value2, "winTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesIsNull() {
            addCriterion("lose_times is null");
            return (Criteria) this;
        }

        public Criteria andLoseTimesIsNotNull() {
            addCriterion("lose_times is not null");
            return (Criteria) this;
        }

        public Criteria andLoseTimesEqualTo(Integer value) {
            addCriterion("lose_times =", value, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesNotEqualTo(Integer value) {
            addCriterion("lose_times <>", value, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesGreaterThan(Integer value) {
            addCriterion("lose_times >", value, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("lose_times >=", value, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesLessThan(Integer value) {
            addCriterion("lose_times <", value, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesLessThanOrEqualTo(Integer value) {
            addCriterion("lose_times <=", value, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesIn(List<Integer> values) {
            addCriterion("lose_times in", values, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesNotIn(List<Integer> values) {
            addCriterion("lose_times not in", values, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesBetween(Integer value1, Integer value2) {
            addCriterion("lose_times between", value1, value2, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("lose_times not between", value1, value2, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andHuTimesIsNull() {
            addCriterion("hu_times is null");
            return (Criteria) this;
        }

        public Criteria andHuTimesIsNotNull() {
            addCriterion("hu_times is not null");
            return (Criteria) this;
        }

        public Criteria andHuTimesEqualTo(Integer value) {
            addCriterion("hu_times =", value, "huTimes");
            return (Criteria) this;
        }

        public Criteria andHuTimesNotEqualTo(Integer value) {
            addCriterion("hu_times <>", value, "huTimes");
            return (Criteria) this;
        }

        public Criteria andHuTimesGreaterThan(Integer value) {
            addCriterion("hu_times >", value, "huTimes");
            return (Criteria) this;
        }

        public Criteria andHuTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("hu_times >=", value, "huTimes");
            return (Criteria) this;
        }

        public Criteria andHuTimesLessThan(Integer value) {
            addCriterion("hu_times <", value, "huTimes");
            return (Criteria) this;
        }

        public Criteria andHuTimesLessThanOrEqualTo(Integer value) {
            addCriterion("hu_times <=", value, "huTimes");
            return (Criteria) this;
        }

        public Criteria andHuTimesIn(List<Integer> values) {
            addCriterion("hu_times in", values, "huTimes");
            return (Criteria) this;
        }

        public Criteria andHuTimesNotIn(List<Integer> values) {
            addCriterion("hu_times not in", values, "huTimes");
            return (Criteria) this;
        }

        public Criteria andHuTimesBetween(Integer value1, Integer value2) {
            addCriterion("hu_times between", value1, value2, "huTimes");
            return (Criteria) this;
        }

        public Criteria andHuTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("hu_times not between", value1, value2, "huTimes");
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