package com.tools.repo;

import com.tools.model.Tool;
import com.tools.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolRepo extends JpaRepository<Tool, Long> {
    List<Tool> findAllByCategory(Category category);
}
