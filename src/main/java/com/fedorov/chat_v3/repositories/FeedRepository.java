package com.fedorov.chat_v3.repositories;

import com.fedorov.chat_v3.models.FeedMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedRepository extends JpaRepository<FeedMessage, Integer> {

    List<FeedMessage> findAllByOrderByIdDesc();
}
