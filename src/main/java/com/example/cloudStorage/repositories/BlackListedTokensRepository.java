package com.example.cloudStorage.repositories;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Repository
public class BlackListedTokensRepository {

    //    private ConcurrentHashMap<Integer, String> blacklistedTokens = new ConcurrentHashMap<>();
    private Set<String> blacklistedTokens = Collections.synchronizedSet(new HashSet<>());

    public BlackListedTokensRepository() {}

    public void addBlacklistedHeaderToken(String headerToken) {
        blacklistedTokens.add(headerToken);
    }

    public Set<String> getBlacklistedHeaderTokens() {
        return blacklistedTokens;
    }

}
