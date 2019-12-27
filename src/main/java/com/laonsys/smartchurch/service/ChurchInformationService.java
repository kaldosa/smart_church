package com.laonsys.smartchurch.service;

import java.util.List;

import com.laonsys.smartchurch.domain.church.ChurchIntro;
import com.laonsys.smartchurch.domain.church.History;
import com.laonsys.smartchurch.domain.church.Server;
import com.laonsys.smartchurch.domain.church.Worship;

public interface ChurchInformationService {
    ChurchIntro getIntro(int ourChurchId);
    void addIntro(ChurchIntro churchIntro);
    void updateIntro(ChurchIntro churchIntro);
    
    void addHistory(History history);
    void deleteHistory(int historyId);
    List<History> getHistories(int ourChurchId);
    void updateHistories(List<History> histories);
    
    void addWorship(Worship worship);
    void deleteWorship(int worshipId);
    List<Worship> getWorships(int ourChurchId);
    void updateWorships(List<Worship> worships);
    
    void addServer(Server server);
    void deleteServer(int serverId);
    List<Server> getServers(int ourChurchId);
    void updateServers(List<Server> servers);
}
