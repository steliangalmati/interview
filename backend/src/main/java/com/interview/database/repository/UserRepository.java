package com.interview.database.repository;

import com.interview.database.model.DbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<DbUser, Long>,
        JpaRepository<DbUser, Long> {
}
