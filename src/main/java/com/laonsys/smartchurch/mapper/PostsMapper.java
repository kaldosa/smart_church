package com.laonsys.smartchurch.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.laonsys.smartchurch.domain.Comments;
import com.laonsys.smartchurch.domain.Posts;

public interface PostsMapper {
	public int totalRow();
	
	public String maxValueForSortKey(@Param("postsId") int postsId, @Param("parentId") int parentId, @Param("level") int level);
	
	public List<Posts> selectAll(@Param("index") int index, @Param("offset") int offset);
	
	public List<Comments> selectAllCommentsByPostsId(int postsId);
	
	public Posts selectPostsById(int id);
	
	public Comments selectCommentsById(int id);
	
	public int selectPostsWriterById(int id);
	
	public Posts previousPostsById(int id);
	
	public Posts nextPostsById(int id);
	
	public int savePosts(Posts posts);
	
	public int updatePosts(Posts posts);
	
	public int saveComments(Comments comments);
	
	public int deletePostsById(int id);
	
	public int deleteCommentsById(int id);
	
	public int deleteCommentsByParentId(int parentId);
	
	public int deleteCommentsByPostsId(int postsId);
}