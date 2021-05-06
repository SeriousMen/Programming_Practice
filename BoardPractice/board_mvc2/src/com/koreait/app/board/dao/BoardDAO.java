package com.koreait.app.board.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.koreait.app.board.vo.BoardReplyVO;
import com.koreait.app.board.vo.BoardVO;
import com.koreait.mybatis.config.SqlMapConfig;

public class BoardDAO {
	private static final int KEY = 3;
	
	SqlSessionFactory sessionf = SqlMapConfig.getSqlMapInstance();
	SqlSession session;
	
	public BoardDAO() {
		//세션 팩토리로 세션을 열어주고 모든 쿼리문은 auto커밋으로 설정
		session = sessionf.openSession(true);
	}
	
	//페이지 별 게시글 목록
	public List<BoardVO> getBoardList(int startRow, int endRow) {
		HashMap<String,Integer> pageMap = new HashMap<>();
		
		//키:벨류 넣기 
		pageMap.put("startRow", startRow);
		pageMap.put("endRow", endRow);
		
		return session.selectList("Board.listAll", pageMap);
	}

	public int getBoardCnt() {
		return session.selectOne("Board.getBoardCnt"); //selectOne은 메소드 선언부의 리턴타입에 의해 자동으로 리턴타입이 바뀐다.
	}
	
	public void updateReadCount(int boardNum) {
		session.update("Board.updateReadCount", boardNum);
	}
	
	//게시글 정보만 담기 위해서는 boolean을 리턴할 필요가 없으나
	//첨부파일을 추가해야하는 장기계획에 따라서
	//트랜잭션에 맞게 설계해야한다.
	public boolean insertBoard(BoardVO board) {
		
		return session.insert("Board.insertBoard",board) ==1;
	}
	
	//게시글 가져오기
	public BoardVO getDetail(int boardNum) {
		return session.selectOne("Board.getDetail", boardNum);
	}
	//수정
	public void updateBoard(BoardVO b_vo) {
		 session.update("Board.updateBoard",b_vo);
	}
	
	//게시글 삭제
	public boolean deleteBoard(int boardNum) {
		return session.delete("Board.deleteBoard",boardNum)==1;
	}
	
	//현재 시퀀스 가져오기
	public int getBoardNum() {
		return session.selectOne("Board.getBoardNum");
	}
	
	//댓글관련 DAO
	
	//댓글 추가
	public boolean insertReply(BoardReplyVO r_vo) {
		return session.insert("Board.insertReply", r_vo) == 1;
	}
	
	//댓글 전체목록
	public List<BoardReplyVO> getReplyList(int boardNum){
		return session.selectList("Board.getReplyList",boardNum);
	}
	
	//댓글 삭제
	public boolean deleteReply(int replyNum) {
		return session.delete("Board.deleteReply",replyNum) == 1;
	}
	
	//댓글 수정
	public boolean updateReply(BoardReplyVO r_vo) {
		return session.update("Board.updateReply",r_vo) ==1;
	}
}
