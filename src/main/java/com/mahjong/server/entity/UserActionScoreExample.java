package com.mahjong.server.entity;

import java.util.ArrayList;
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

        public Criteria andUserRoomRecordIdIsNull() {
            addCriterion("user_room_record_id is null");
            return (Criteria) this;
        }

        public Criteria andUserRoomRecordIdIsNotNull() {
            addCriterion("user_room_record_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserRoomRecordIdEqualTo(Integer value) {
            addCriterion("user_room_record_id =", value, "userRoomRecordId");
            return (Criteria) this;
        }

        public Criteria andUserRoomRecordIdNotEqualTo(Integer value) {
            addCriterion("user_room_record_id <>", value, "userRoomRecordId");
            return (Criteria) this;
        }

        public Criteria andUserRoomRecordIdGreaterThan(Integer value) {
            addCriterion("user_room_record_id >", value, "userRoomRecordId");
            return (Criteria) this;
        }

        public Criteria andUserRoomRecordIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_room_record_id >=", value, "userRoomRecordId");
            return (Criteria) this;
        }

        public Criteria andUserRoomRecordIdLessThan(Integer value) {
            addCriterion("user_room_record_id <", value, "userRoomRecordId");
            return (Criteria) this;
        }

        public Criteria andUserRoomRecordIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_room_record_id <=", value, "userRoomRecordId");
            return (Criteria) this;
        }

        public Criteria andUserRoomRecordIdIn(List<Integer> values) {
            addCriterion("user_room_record_id in", values, "userRoomRecordId");
            return (Criteria) this;
        }

        public Criteria andUserRoomRecordIdNotIn(List<Integer> values) {
            addCriterion("user_room_record_id not in", values, "userRoomRecordId");
            return (Criteria) this;
        }

        public Criteria andUserRoomRecordIdBetween(Integer value1, Integer value2) {
            addCriterion("user_room_record_id between", value1, value2, "userRoomRecordId");
            return (Criteria) this;
        }

        public Criteria andUserRoomRecordIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_room_record_id not between", value1, value2, "userRoomRecordId");
            return (Criteria) this;
        }

        public Criteria andActionTypeIsNull() {
            addCriterion("action_type is null");
            return (Criteria) this;
        }

        public Criteria andActionTypeIsNotNull() {
            addCriterion("action_type is not null");
            return (Criteria) this;
        }

        public Criteria andActionTypeEqualTo(Integer value) {
            addCriterion("action_type =", value, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeNotEqualTo(Integer value) {
            addCriterion("action_type <>", value, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeGreaterThan(Integer value) {
            addCriterion("action_type >", value, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("action_type >=", value, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeLessThan(Integer value) {
            addCriterion("action_type <", value, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeLessThanOrEqualTo(Integer value) {
            addCriterion("action_type <=", value, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeIn(List<Integer> values) {
            addCriterion("action_type in", values, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeNotIn(List<Integer> values) {
            addCriterion("action_type not in", values, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeBetween(Integer value1, Integer value2) {
            addCriterion("action_type between", value1, value2, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("action_type not between", value1, value2, "actionType");
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