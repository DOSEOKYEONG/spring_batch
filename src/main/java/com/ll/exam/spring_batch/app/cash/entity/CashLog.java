package com.ll.exam.spring_batch.app.cash.entity;

import com.ll.exam.spring_batch.app.base.entity.BaseEntity;
import com.ll.exam.spring_batch.app.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class CashLog extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    private Member member;

    private Long price; //변동

    private String eventType;
}
