package com.koreait.mybatis.config;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//응용 프로그램 시작하자마자 할일 구현하기
public class SqlMapConfig {
	private static SqlSessionFactory sqlsession_f;
	
	//static초기화 블럭-->프로그램 실행 시 단 한번만 실행되도록 
	static {
		//자바에서 config파일 가져오기
		try {
			//SqlSessionFactoryBuilder가 참조할 수 있게 Maybatis 구성 파일(.xml)을 객체화
			String resource = "./com/koreait/mybatis/config/config.xml";//그 파일의 경로를 작성
			Reader reader; //캐릭터 단위로 읽는 Reader타입의 변수 
			reader = Resources.getResourceAsReader(resource);//reader가 config파일이 된다.(읽어온다) io니까 ioException
			
			sqlsession_f = new SqlSessionFactoryBuilder().build(reader); //전달한 config.xml객체를 토대로 Factory빌드를 한다.
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("초기화 문제 발생, SqlMapConfig.java");
		} 
	}
	
	
	public static SqlSessionFactory getSqlMapInstance() { //외부에서 쓸 수 있게 메소드 선언 
		return sqlsession_f; //빌드된(객체화된?할당된?) SqlSessionFactory
	}
}
