package com.koreait.app.board.dao;

import java.util.Enumeration;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.koreait.app.board.vo.FilesVO;
import com.koreait.mybatis.config.SqlMapConfig;
import com.oreilly.servlet.MultipartRequest;

public class FilesDAO {

	SqlSessionFactory sessionf = SqlMapConfig.getSqlMapInstance();
	SqlSession session;
	
	public FilesDAO() {
		session = sessionf.openSession(true);
	}
	
	//파일첨부
	public boolean insertFiles(int boardNum, MultipartRequest multi) {
		
		boolean check = true; 
		FilesVO vo = new FilesVO();
		
		//사용자가 업로드한 원본 파일명 
		Enumeration<String>files = multi.getFileNames();
		
		while(files.hasMoreElements()) {//이대로하면 무조건 반복문은 세번 돈다. form태그에 3개의 tr을 만들었으니까
			String data = files.nextElement();
			//사용자가 업로드한 파일명을 통해서 중복이 없도록 변경한 시스템 파일명을 가져온다. 
			String systemName = multi.getFilesystemName(data);//중복없는 이름을 가져오는 메소드
			if(systemName ==null) {continue;}	// 순서대로 파일을 첨부안하고 중간에 첨부할 수 있기때문에 continue로 한다.
			
			vo.setFileName(systemName);
			vo.setBoardNum(boardNum);
			
			if(session.insert("Files.insertFile",vo) !=1) {
				check = false;
				break;
			}
		} 
		
		
		return check;
		
	}
	
	public List<FilesVO> getFileList(int boardNum) {
		return session.selectList("Files.getFileList",boardNum);
	}
	public void deleteFile(int boardNum) {
		session.delete("Files.deleteFile",boardNum);
	}
}
