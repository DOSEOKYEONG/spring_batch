package com.ll.exam.spring_batch.app.cash.repository;

import com.ll.exam.spring_batch.app.cash.entity.CashLog;
import com.ll.exam.spring_batch.app.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CashLogRepository extends JpaRepository<CashLog, Long> {
    Optional<CashLog> findByMember(Member member);
}
