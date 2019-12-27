package com.laonsys.smartchurch.service;

import java.util.List;

import com.laonsys.smartchurch.domain.Comments;
import com.laonsys.smartchurch.domain.Posts;

public interface PostsService {
	public int totalRow();
	
	public List<Posts> selectAll(int index, int offset);

	public List<Comments> selectAllCommentsByPostsId(int postsId);
	
	public Posts selectPostsById(int id);

	public Comments selectCommentsById(int id);
	
	public int selectPostsWriterById(int id);
	
	public int savePosts(Posts posts);

	public int updatePosts(Posts posts);
	
	public int saveComments(Comments comments);
	
	public int deletePosts(Posts posts);

	public int deleteComments(Comments comments);
	
	public Posts previousPostsById(int id);

	public Posts nextPostsById(int id);
}