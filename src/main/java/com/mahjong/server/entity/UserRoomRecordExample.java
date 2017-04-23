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

        public Criteria andJoinTimeIsNull() {
            addCriterion("join_time is null");
            return (Criteria) this;
        }

        public Criteria andJoinTimeIsNotNull() {
            addCriterion("join_time is not null");
            return (Criteria) this;
        }

        public Criteria andJoinTimeEqualTo(Date value) {
            addCriterion("join_time =", value, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeNotEqualTo(Date value) {
            addCriterion("join_time <>", value, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeGreaterThan(Date value) {
            addCriterion("join_time >", value, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("join_time >=", value, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeLessThan(Date value) {
            addCriterion("join_time <", value, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeLessThanOrEqualTo(Date value) {
            addCriterion("join_time <=", value, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeIn(List<Date> values) {
            addCriterion("join_time in", values, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeNotIn(List<Date> values) {
            addCriterion("join_time not in", values, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeBetween(Date value1, Date value2) {
            addCriterion("join_time between", value1, value2, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeNotBetween(Date value1, Date value2) {
            addCriterion("join_time not between", value1, value2, "joinTime");
            return (Criteria) this;
        }

        public Criteria andLastScoreIsNull() {
            addCriterion("last_score is null");
            return (Criteria) this;
        }

        public Criteria andLastScoreIsNotNull() {
            addCriterion("last_score is not null");
            return (Criteria) this;
        }

        public Criteria andLastScoreEqualTo(Integer value) {
            addCriterion("last_score =", value, "lastScore");
            return (Criteria) this;
        }

        public Criteria andLastScoreNotEqualTo(Integer value) {
            addCriterion("last_score <>", value, "lastScore");
            return (Criteria) this;
        }

        public Criteria andLastScoreGreaterThan(Integer value) {
            addCriterion("last_score >", value, "lastScore");
            return (Criteria) this;
        }

        public Criteria andLastScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("last_score >=", value, "lastScore");
            return (Criteria) this;
        }

        public Criteria andLastScoreLessThan(Integer value) {
            addCriterion("last_score <", value, "lastScore");
            return (Criteria) this;
        }

        public Criteria andLastScoreLessThanOrEqualTo(Integer value) {
            addCriterion("last_score <=", value, "lastScore");
            return (Criteria) this;
        }

        public Criteria andLastScoreIn(List<Integer> values) {
            addCriterion("last_score in", values, "lastScore");
            return (Criteria) this;
        }

        public Criteria andLastScoreNotIn(List<Integer> values) {
            addCriterion("last_score not in", values, "lastScore");
            return (Criteria) this;
        }

        public Criteria andLastScoreBetween(Integer value1, Integer value2) {
            addCriterion("last_score between", value1, value2, "lastScore");
            return (Criteria) this;
        }

        public Criteria andLastScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("last_score not between", value1, value2, "lastScore");
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