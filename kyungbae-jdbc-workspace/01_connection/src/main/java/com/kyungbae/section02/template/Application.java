package com.kyungbae.section02.template;

import java.sql.Connection;

import static com.kyungbae.section02.template.JDBCTemplate.close;
import static com.kyungbae.section02.template.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {

        // Connection conn = JDBCTemplate.getConnection(); // db연결
        Connection conn = getConnection(); // method 선언 가능
        // => JDBCTemplate class에 있는 getConnection method 호출
        System.out.println(conn);
        System.out.println("sql문 실행완료");

        close(conn);
        // => JDBCTemplate class에 있는 close method를 호출하여 Connection 객체 반납
    }
}
