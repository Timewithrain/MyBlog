package com.watermelon.DAO;

import com.watermelon.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type,Long> {

    Type findByName(String name);
}
