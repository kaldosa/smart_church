package com.laonsys.smartchurch.service.impl;

import static org.springframework.util.Assert.notNull;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.mapper.ReplyMapper;
import com.laonsys.smartchurch.service.ReplyService;

@Service("replyService")
@Transactional
public class ReplyServiceImpl implements ReplyService {

    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired ReplyMapper replyMapper;
    
    @Override
    public BaseComments selectOne(int id, String refTable) {
        notNull(id);
        return replyMapper.selectOne(id, refTable);
    }

    @Override
    public List<BaseComments> selectList(int refId, String refTable) {
        notNull(refTable);
        notNull(refId);
        
        List<BaseComments> list = replyMapper.selectList(refId, refTable);
        return (list == null || list.isEmpty()) ? null : list;
    }

    @Override
    public void insert(BaseComments reply, String refTable) {
        notNull(reply);
        notNull(refTable);
        notNull(reply.getPostingsId());
        notNull(reply.getUser());
        
        logger.debug("Insert Reply : [ {} ]", reply);
        logger.debug("Insert Reply reference table : [ {} ]", refTable);
        
        replyMapper.insert(reply);
        replyMapper.insertRefTable(refTable, reply.getId(), reply.getPostingsId());
    }

    @Override
    public void update(BaseComments reply, User activeUser) {
        notNull(reply);
        notNull(reply.getId());
        notNull(activeUser);
        
        logger.debug("Update Reply : [ {} ]", reply.getId());
        
        if(hasPermission(reply.getId(), activeUser)) {
            reply.setUser(activeUser);
            replyMapper.update(reply);
        }
    }

    @Override
    public void delete(int id, String refTable, User activeUser) {
        notNull(id);
        notNull(refTable);
        notNull(activeUser);
        
        logger.debug("Delete Reply : [ {} ]", id);
        logger.debug("Delete Reply reference table : [ {} ]", refTable);

        replyMapper.delete(id);
        replyMapper.deleteRefTable(refTable, id);
        
//        if(hasPermission(id, activeUser)) {
//            replyMapper.delete(id);
//            replyMapper.deleteRefTable(refTable, id);
//        }
    }

    @Override
    public void deleteAll(int refId, String refTable) {
        replyMapper.deleteAll(refId, refTable);
        replyMapper.deleteRefAll(refId, refTable);
    }
    
    @Override
    public void deleteAll(int churchId) {
        replyMapper.deleteAllByChurchId(churchId);
    }
    
    @Override
    public boolean hasPermission(int id, User user) {
        notNull(id);
        notNull(user);
        
        BaseComments reply = replyMapper.selectOne(id, null);
        if(reply == null) {
            logger.debug("Not found reply : id [ {} ]", id);
            return false;
        }
        
        String roleName = user.getRoleName();
        
        if("관리자".equals(roleName) || "교회관리자".equals(roleName)) {
            logger.debug("user have addmin role");
            return true;
        }
        
        if(reply.getUser().getId() == user.getId()) {
            logger.debug("user is reply [{}] author", id);
            return true;
        }
        
        logger.debug("Reply user id [{}] : access user id [{}]", reply.getUser().getId(), user.getId());
        
        return false;
    }

}
