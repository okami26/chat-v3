package com.fedorov.chat_v3.services;

import com.fedorov.chat_v3.models.FeedMessage;
import com.fedorov.chat_v3.repositories.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {

    @Autowired
    private FeedRepository feedRepository;

    public List<FeedMessage> getFeeds() {
        return feedRepository.findAllByOrderByIdDesc();
    }

    public FeedMessage createFeed(FeedMessage feed) {
        return feedRepository.save(feed);
    }
}
