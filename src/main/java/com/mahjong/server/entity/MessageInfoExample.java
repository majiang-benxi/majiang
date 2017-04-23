package com.mahjong.server.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MessageInfoExample() {
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

        public Criteria andMesTypeIsNull() {
            addCriterion("mes_type is null");
            return (Criteria) this;
        }

        public Criteria andMesTypeIsNotNull() {
            addCriterion("mes_type is not null");
            return (Criteria) this;
        }

        public Criteria andMesTypeEqualTo(Byte value) {
            addCriterion("mes_type =", value, "mesType");
            return (Criteria) this;
        }

        public Criteria andMesTypeNotEqualTo(Byte value) {
            addCriterion("mes_type <>", value, "mesType");
            return (Criteria) this;
        }

        public Criteria andMesTypeGreaterThan(Byte value) {
            addCriterion("mes_type >", value, "mesType");
            return (Criteria) this;
        }

        public Criteria andMesTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("mes_type >=", value, "mesType");
            return (Criteria) this;
        }

        public Criteria andMesTypeLessThan(Byte value) {
            addCriterion("mes_type <", value, "mesType");
            return (Criteria) this;
        }

        public Criteria andMesTypeLessThanOrEqualTo(Byte value) {
            addCriterion("mes_type <=", value, "mesType");
            return (Criteria) this;
        }

        public Criteria andMesTypeIn(List<Byte> values) {
            addCriterion("mes_type in", values, "mesType");
            return (Criteria) this;
        }

        public Criteria andMesTypeNotIn(List<Byte> values) {
            addCriterion("mes_type not in", values, "mesType");
            return (Criteria) this;
        }

        public Criteria andMesTypeBetween(Byte value1, Byte value2) {
            addCriterion("mes_type between", value1, value2, "mesType");
            return (Criteria) this;
        }

        public Criteria andMesTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("mes_type not between", value1, value2, "mesType");
            return (Criteria) this;
        }

        public Criteria andMesPositionIsNull() {
            addCriterion("mes_position is null");
            return (Criteria) this;
        }

        public Criteria andMesPositionIsNotNull() {
            addCriterion("mes_position is not null");
            return (Criteria) this;
        }

        public Criteria andMesPositionEqualTo(Byte value) {
            addCriterion("mes_position =", value, "mesPosition");
            return (Criteria) this;
        }

        public Criteria andMesPositionNotEqualTo(Byte value) {
            addCriterion("mes_position <>", value, "mesPosition");
            return (Criteria) this;
        }

        public Criteria andMesPositionGreaterThan(Byte value) {
            addCriterion("mes_position >", value, "mesPosition");
            return (Criteria) this;
        }

        public Criteria andMesPositionGreaterThanOrEqualTo(Byte value) {
            addCriterion("mes_position >=", value, "mesPosition");
            return (Criteria) this;
        }

        public Criteria andMesPositionLessThan(Byte value) {
            addCriterion("mes_position <", value, "mesPosition");
            return (Criteria) this;
        }

        public Criteria andMesPositionLessThanOrEqualTo(Byte value) {
            addCriterion("mes_position <=", value, "mesPosition");
            return (Criteria) this;
        }

        public Criteria andMesPositionIn(List<Byte> values) {
            addCriterion("mes_position in", values, "mesPosition");
            return (Criteria) this;
        }

        public Criteria andMesPositionNotIn(List<Byte> values) {
            addCriterion("mes_position not in", values, "mesPosition");
            return (Criteria) this;
        }

        public Criteria andMesPositionBetween(Byte value1, Byte value2) {
            addCriterion("mes_position between", value1, value2, "mesPosition");
            return (Criteria) this;
        }

        public Criteria andMesPositionNotBetween(Byte value1, Byte value2) {
            addCriterion("mes_position not between", value1, value2, "mesPosition");
            return (Criteria) this;
        }

        public Criteria andMesTitleIsNull() {
            addCriterion("mes_title is null");
            return (Criteria) this;
        }

        public Criteria andMesTitleIsNotNull() {
            addCriterion("mes_title is not null");
            return (Criteria) this;
        }

        public Criteria andMesTitleEqualTo(String value) {
            addCriterion("mes_title =", value, "mesTitle");
            return (Criteria) this;
        }

        public Criteria andMesTitleNotEqualTo(String value) {
            addCriterion("mes_title <>", value, "mesTitle");
            return (Criteria) this;
        }

        public Criteria andMesTitleGreaterThan(String value) {
            addCriterion("mes_title >", value, "mesTitle");
            return (Criteria) this;
        }

        public Criteria andMesTitleGreaterThanOrEqualTo(String value) {
            addCriterion("mes_title >=", value, "mesTitle");
            return (Criteria) this;
        }

        public Criteria andMesTitleLessThan(String value) {
            addCriterion("mes_title <", value, "mesTitle");
            return (Criteria) this;
        }

        public Criteria andMesTitleLessThanOrEqualTo(String value) {
            addCriterion("mes_title <=", value, "mesTitle");
            return (Criteria) this;
        }

        public Criteria andMesTitleLike(String value) {
            addCriterion("mes_title like", value, "mesTitle");
            return (Criteria) this;
        }

        public Criteria andMesTitleNotLike(String value) {
            addCriterion("mes_title not like", value, "mesTitle");
            return (Criteria) this;
        }

        public Criteria andMesTitleIn(List<String> values) {
            addCriterion("mes_title in", values, "mesTitle");
            return (Criteria) this;
        }

        public Criteria andMesTitleNotIn(List<String> values) {
            addCriterion("mes_title not in", values, "mesTitle");
            return (Criteria) this;
        }

        public Criteria andMesTitleBetween(String value1, String value2) {
            addCriterion("mes_title between", value1, value2, "mesTitle");
            return (Criteria) this;
        }

        public Criteria andMesTitleNotBetween(String value1, String value2) {
            addCriterion("mes_title not between", value1, value2, "mesTitle");
            return (Criteria) this;
        }

        public Criteria andMessageContentIsNull() {
            addCriterion("message_content is null");
            return (Criteria) this;
        }

        public Criteria andMessageContentIsNotNull() {
            addCriterion("message_content is not null");
            return (Criteria) this;
        }

        public Criteria andMessageContentEqualTo(String value) {
            addCriterion("message_content =", value, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentNotEqualTo(String value) {
            addCriterion("message_content <>", value, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentGreaterThan(String value) {
            addCriterion("message_content >", value, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentGreaterThanOrEqualTo(String value) {
            addCriterion("message_content >=", value, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentLessThan(String value) {
            addCriterion("message_content <", value, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentLessThanOrEqualTo(String value) {
            addCriterion("message_content <=", value, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentLike(String value) {
            addCriterion("message_content like", value, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentNotLike(String value) {
            addCriterion("message_content not like", value, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentIn(List<String> values) {
            addCriterion("message_content in", values, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentNotIn(List<String> values) {
            addCriterion("message_content not in", values, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentBetween(String value1, String value2) {
            addCriterion("message_content between", value1, value2, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentNotBetween(String value1, String value2) {
            addCriterion("message_content not between", value1, value2, "messageContent");
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

        public Criteria andIntervalTimeIsNull() {
            addCriterion("interval_time is null");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeIsNotNull() {
            addCriterion("interval_time is not null");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeEqualTo(Integer value) {
            addCriterion("interval_time =", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeNotEqualTo(Integer value) {
            addCriterion("interval_time <>", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeGreaterThan(Integer value) {
            addCriterion("interval_time >", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("interval_time >=", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeLessThan(Integer value) {
            addCriterion("interval_time <", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeLessThanOrEqualTo(Integer value) {
            addCriterion("interval_time <=", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeIn(List<Integer> values) {
            addCriterion("interval_time in", values, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeNotIn(List<Integer> values) {
            addCriterion("interval_time not in", values, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeBetween(Integer value1, Integer value2) {
            addCriterion("interval_time between", value1, value2, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("interval_time not between", value1, value2, "intervalTime");
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