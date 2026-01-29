package com.kashkina.portfolio.repository.about;

import com.kashkina.portfolio.entity.about.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {
    List<Certificate> findAllByOrderByDisplayOrderAsc();
}
