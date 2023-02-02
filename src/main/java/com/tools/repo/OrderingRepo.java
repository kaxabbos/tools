package com.tools.repo;

import com.tools.model.Ordering;
import com.tools.model.Tool;
import com.tools.model.User;
import com.tools.model.enums.OrderingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderingRepo extends JpaRepository<Ordering, Long> {
    public List<Ordering> findAllByUserAndOrderingStatus(User user, OrderingStatus orderingStatus);
}
