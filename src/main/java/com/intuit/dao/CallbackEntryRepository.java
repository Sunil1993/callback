package com.intuit.dao;

import com.intuit.dao.entities.CallbackEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sunil on 9/1/19.
 */
@Repository
public interface CallbackEntryRepository extends JpaRepository<CallbackEntry, Integer>  {
}
