package com.mahjong.server.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserActionScoreExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserActionScoreExample() {
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

        public Criteria andRoundNumIsNull() {
            addCriterion("round_num is null");
            return (Criteria) this;
        }

        public Criteria andRoundNumIsNotNull() {
            addCriterion("round_num is not null");
            return (Criteria) this;
        }

        public Criteria andRoundNumEqualTo(Integer value) {
            addCriterion("round_num =", value, "roundNum");
            return (Criteria) this;
        }

        public Criteria andRoundNumNotEqualTo(Integer value) {
            addCriterion("round_num <>", value, "roundNum");
            return (Criteria) this;
        }

        public Criteria andRoundNumGreaterThan(Integer value) {
            addCriterion("round_num >", value, "roundNum");
            return (Criteria) this;
        }

        public Criteria andRoundNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("round_num >=", value, "roundNum");
            return (Criteria) this;
        }

        public Criteria andRoundNumLessThan(Integer value) {
            addCriterion("round_num <", value, "roundNum");
            return (Criteria) this;
        }

        public Criteria andRoundNumLessThanOrEqualTo(Integer value) {
            addCriterion("round_num <=", value, "roundNum");
            return (Criteria) this;
        }

        public Criteria andRoundNumIn(List<Integer> values) {
            addCriterion("round_num in", values, "roundNum");
            return (Criteria) this;
        }

        public Criteria andRoundNumNotIn(List<Integer> values) {
            addCriterion("round_num not in", values, "roundNum");
            return (Criteria) this;
        }

        public Criteria andRoundNumBetween(Integer value1, Integer value2) {
            addCriterion("round_num between", value1, value2, "roundNum");
            return (Criteria) this;
        }

        public Criteria andRoundNumNotBetween(Integer value1, Integer value2) {
            addCriterion("round_num not between", value1, value2, "roundNum");
            return (Criteria) this;
        }

        public Criteria andWinActionTypesIsNull() {
            addCriterion("win_action_types is null");
            return (Criteria) this;
        }

        public Criteria andWinActionTypesIsNotNull() {
            addCriterion("win_action_types is not null");
            return (Criteria) this;
        }

        public Criteria andWinActionTypesEqualTo(String value) {
            addCriterion("win_action_types =", value, "winActionTypes");
            return (Criteria) this;
        }

        public Criteria andWinActionTypesNotEqualTo(String value) {
            addCriterion("win_action_types <>", value, "winActionTypes");
            return (Criteria) this;
        }

        public Criteria andWinActionTypesGreaterThan(String value) {
            addCriterion("win_action_types >", value, "winActionTypes");
            return (Criteria) this;
        }

        public Criteria andWinActionTypesGreaterThanOrEqualTo(String value) {
            addCriterion("win_action_types >=", value, "winActionTypes");
            return (Criteria) this;
        }

        public Criteria andWinActionTypesLessThan(String value) {
            addCriterion("win_action_types <", value, "winActionTypes");
            return (Criteria) this;
        }

        public Criteria andWinActionTypesLessThanOrEqualTo(String value) {
            addCriterion("win_action_types <=", value, "winActionTypes");
            return (Criteria) this;
        }

        public Criteria andWinActionTypesLike(String value) {
            addCriterion("win_action_types like", value, "winActionTypes");
            return (Criteria) this;
        }

        public Criteria andWinActionTypesNotLike(String value) {
            addCriterion("win_action_types not like", value, "winActionTypes");
            return (Criteria) this;
        }

        public Criteria andWinActionTypesIn(List<String> values) {
            addCriterion("win_action_types in", values, "winActionTypes");
            return (Criteria) this;
        }

        public Criteria andWinActionTypesNotIn(List<String> values) {
            addCriterion("win_action_types not in", values, "winActionTypes");
            return (Criteria) this;
        }

        public Criteria andWinActionTypesBetween(String value1, String value2) {
            addCriterion("win_action_types between", value1, value2, "winActionTypes");
            return (Criteria) this;
        }

        public Criteria andWinActionTypesNotBetween(String value1, String value2) {
            addCriterion("win_action_types not between", value1, value2, "winActionTypes");
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

        public Criteria andActionScoreIsNull() {
            addCriterion("action_score is null");
            return (Criteria) this;
        }

        public Criteria andActionScoreIsNotNull() {
            addCriterion("action_score is not null");
            return (Criteria) this;
        }

        public Criteria andActionScoreEqualTo(Integer value) {
            addCriterion("action_score =", value, "actionScore");
            return (Criteria) this;
        }

        public Criteria andActionScoreNotEqualTo(Integer value) {
            addCriterion("action_score <>", value, "actionScore");
            return (Criteria) this;
        }

        public Criteria andActionScoreGreaterThan(Integer value) {
            addCriterion("action_score >", value, "actionScore");
            return (Criteria) this;
        }

        public Criteria andActionScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("action_score >=", value, "actionScore");
            return (Criteria) this;
        }

        public Criteria andActionScoreLessThan(Integer value) {
            addCriterion("action_score <", value, "actionScore");
            return (Criteria) this;
        }

        public Criteria andActionScoreLessThanOrEqualTo(Integer value) {
            addCriterion("action_score <=", value, "actionScore");
            return (Criteria) this;
        }

        public Criteria andActionScoreIn(List<Integer> values) {
            addCriterion("action_score in", values, "actionScore");
            return (Criteria) this;
        }

        public Criteria andActionScoreNotIn(List<Integer> values) {
            addCriterion("action_score not in", values, "actionScore");
            return (Criteria) this;
        }

        public Criteria andActionScoreBetween(Integer value1, Integer value2) {
            addCriterion("action_score between", value1, value2, "actionScore");
            return (Criteria) this;
        }

        public Criteria andActionScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("action_score not between", value1, value2, "actionScore");
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