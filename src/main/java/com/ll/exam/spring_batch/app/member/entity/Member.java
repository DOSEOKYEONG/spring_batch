package com.ll.exam.spring_batch.app.member.entity;

import com.ll.exam.spring_batch.app.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.aspectj.weaver.ast.Call;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class Member extends BaseEntity {

    private String username;

    private String password;

    private String email;

    private long restCash;
}
