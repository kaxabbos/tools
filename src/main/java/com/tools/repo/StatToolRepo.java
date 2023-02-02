package com.tools.repo;

import com.tools.model.StatTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatToolRepo extends JpaRepository<StatTool, Long> {

}
