package com.laonsys.smartchurch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.laonsys.smartchurch.domain.Comments;
import com.laonsys.smartchurch.domain.Posts;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.mapper.PostsMapper;
import com.laonsys.smartchurch.service.PostsService;
import com.laonsys.smartchurch.utiles.SessionUtils;
import com.laonsys.springmvc.extensions.utils.StringUtils;

@Service("postsService")
public class PostsServiceImpl implements PostsService {

	@Autowired
	private PostsMapper postsMapper;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int totalRow() {
		return postsMapper.totalRow();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Posts> selectAll(int index, int offset) {
		return postsMapper.selectAll(index, offset);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Comments> selectAllCommentsByPostsId(int postsId) {
		return postsMapper.selectAllCommentsByPostsId(postsId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Posts selectPostsById(int id) {
		return postsMapper.selectPostsById(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Comments selectCommentsById(int id) {
		return postsMapper.selectCommentsById(id);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)	
	public int selectPostsWriterById(int id) {
		return postsMapper.selectPostsWriterById(id);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int savePosts(Posts posts) {
		return postsMapper.savePosts(posts);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int updatePosts(Posts posts) {
		return postsMapper.updatePosts(posts);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int saveComments(Comments comments) {
		User user = SessionUtils.getUser();
		
		comments.setWriterId(user.getId());
		
		int level = comments.getLevel();
		String maxValue = postsMapper.maxValueForSortKey(comments.getPostsId(), comments.getParentId(), level);
		double increment = Math.pow(0.1, level);
		
		double sortKey = 0;
		if(maxValue == null) {
			sortKey = Double.parseDouble(comments.getSortKey()) + increment;
		} else {
			sortKey = Double.parseDouble(maxValue) + increment;
		}

		comments.setSortKey(StringUtils.doubleToString(sortKey, level));
		
		return postsMapper.saveComments(comments);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int deletePosts(Posts posts) {
		int result = 0;
		
		result += postsMapper.deleteCommentsByPostsId(posts.getId());
		result += postsMapper.deletePostsById(posts.getId());
		
		return result;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int deleteComments(Comments comments) {
		int result = 0;

		result = postsMapper.deleteCommentsByParentId(comments.getId());
		result += postsMapper.deleteCommentsById(comments.getId());

		return result;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Posts nextPostsById(int id) {
		return postsMapper.nextPostsById(id);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Posts previousPostsById(int id) {
		return postsMapper.previousPostsById(id);
	}
}