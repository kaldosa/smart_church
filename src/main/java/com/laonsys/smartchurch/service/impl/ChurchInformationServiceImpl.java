package com.laonsys.smartchurch.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.domain.church.ChurchIntro;
import com.laonsys.smartchurch.domain.church.History;
import com.laonsys.smartchurch.domain.church.Server;
import com.laonsys.smartchurch.domain.church.Worship;
import com.laonsys.smartchurch.mapper.ChurchHistoryMapper;
import com.laonsys.smartchurch.mapper.ChurchIntroMapper;
import com.laonsys.smartchurch.mapper.ChurchServerMapper;
import com.laonsys.smartchurch.mapper.ChurchWorshipMapper;
import com.laonsys.smartchurch.service.AttachService;
import com.laonsys.smartchurch.service.ChurchInformationService;
import com.laonsys.springmvc.extensions.exception.ServiceException;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.utils.ResourceProcessingEngine;

@Service("churchInformationService")
@Transactional
public class ChurchInformationServiceImpl implements ChurchInformationService {
    @Autowired private ChurchIntroMapper introMapper;
    @Autowired private ChurchHistoryMapper historyMapper;
    @Autowired private ChurchServerMapper serverMapper;
    @Autowired private ChurchWorshipMapper worshipMapper;
    @Autowired private AttachService attachService;
    @Autowired private ResourceProcessingEngine resourceProcessingEngine;
    
    @Override
    public ChurchIntro getIntro(int ourChurchId) {
        return introMapper.selectOne(ourChurchId);
    }

    @Override
    public void addIntro(ChurchIntro churchIntro) {
        introMapper.insert(churchIntro);
        MultipartFile file = churchIntro.getFile();
        
        if(file != null && !file.isEmpty()) {
            saveAttach(churchIntro.getId(), churchIntro.getPath(), file, "church_intro_attach");
        }
    }

    @Override
    public void updateIntro(ChurchIntro churchIntro) {
        introMapper.update(churchIntro);

        MultipartFile file = churchIntro.getFile();

        if (file != null && !file.isEmpty()) {
            attachService.deleteAll(churchIntro.getId(), "church_intro_attach");
            saveAttach(churchIntro.getId(), churchIntro.getPath(), file, "church_intro_attach");
        }
    }

    @Override
    public void addHistory(History history) {
        historyMapper.insert(history);
    }

    @Override
    public void deleteHistory(int historyId) {
        historyMapper.delete(historyId);
    }

    @Override
    public List<History> getHistories(int ourChurchId) {
        return historyMapper.selectList(ourChurchId);
    }

    @Override
    public void updateHistories(List<History> histories) {
        for (History history : histories) {
            historyMapper.update(history);
        }
    }

    @Override
    public void addWorship(Worship worship) {
        worshipMapper.insert(worship);
    }

    @Override
    public void deleteWorship(int worshipId) {
        worshipMapper.delete(worshipId);
    }

    @Override
    public List<Worship> getWorships(int ourChurchId) {
        return worshipMapper.selectList(ourChurchId);
    }

    @Override
    public void updateWorships(List<Worship> worships) {
        for (Worship worship : worships) {
            worshipMapper.update(worship);
        }
    }

    @Override
    public void addServer(Server server) {
        serverMapper.insert(server);
        Attachment attachment = saveAttach(server.getId(), server.getPath(), server.getUpload(), "church_server_attach");
        server.setPhoto(attachment);
    }

    @Override
    public void deleteServer(int serverId) {
        attachService.deleteAll(serverId, "church_server_attach");
        serverMapper.delete(serverId);
    }

    @Override
    public List<Server> getServers(int ourChurchId) {
        return serverMapper.selectList(ourChurchId);
    }

    @Override
    public void updateServers(List<Server> servers) {
        for(Server server : servers) {
            serverMapper.update(server);
        }
    }

    private Attachment saveAttach(int refId, String path, MultipartFile file, String tableName) {
        Attachment attachment = null;
        try {
            File output = (File) resourceProcessingEngine.processing(file.getOriginalFilename(), file.getContentType(), file.getSize(),
                    file.getInputStream());
            
            if(output != null) {
                attachment = attachService.insert("/" + path, output, tableName, refId);
            }
        }
        catch (IOException e) {
            throw new ServiceException("failed to save image in [" + tableName + "].");
        }
        return attachment;
    }
}
