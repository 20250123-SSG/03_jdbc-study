package com.johnth.section03.sqlinjection;

/*
    Statement VS PreparedStatement
    1. 파라미터 바인딩
        Statement: 파라미터 바인딩 불가, 쿼리 문자열대로 실행
        PreparedStatement : <T>홀더를 통해 파라미터 바인딩 가능 => 동적 쿼리작업시 유용
    2. SQL Injection
        Statement : SQL Injection에 취약
        PreparedStatement : 파라미터 바인딩 방식으로 SQL Injection 방지
    3. 컴파일 방식
        Statement           : SQL문이 실행될때마다 DB엔진에서 매번 새롭게 컴파일
        PreparedStatement   :
 */
public class Application2 {
    public static void main(String[] args) {

    }
}
