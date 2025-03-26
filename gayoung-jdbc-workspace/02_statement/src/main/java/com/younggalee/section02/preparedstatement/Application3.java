package com.younggalee.section02.preparedstatement;

import com.younggalee.model.dto.EmployeeDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Target;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import static com.younggalee.common.JDBCTemplate.close;
import static com.younggalee.common.JDBCTemplate.getConnection;

public class Application3 {
    public static void main(String[] args) {
        // 조회할 사원의 성을 입력받아 해당 성씨를 가진 사원정보들을 모두 출력
        // 여러행 조회 가능 >> ResultSet >> List<employeeDTO>

        List<EmployeeDTO> list = new ArrayList<>();

        Properties prop = new Properties(); //의 load관련 매소드로 읽어들이기
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("조회할 사원의 성 입력 : ");
        String familyName = sc.nextLine();

        // 쿼리문을 외부파일로 관리하도록 (employee-query.xml) properties 객체 필요함.
        try {
            prop.loadFromXML(new FileInputStream("com/younggalee/mapper/employee-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String query = prop.getProperty("selectEmpByFamilyName");
        System.out.println("query: " + query);

        try {
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, familyName);

            rset = pstmt.executeQuery();

            while(rset.next()){
                list.add(new EmployeeDTO(
                        rset.getString("emp_id"),
                        rset.getString("emp_name"),
                        rset.getString("emp_no"),
                        rset.getString("email"),
                        rset.getString("phone"),
                        rset.getString("dept_Code"),
                        rset.getString("job_code"),
                        rset.getString("sal_level"),
                        rset.getInt("salary"),
                        rset.getDouble("bonus"),
                        rset.getString("manager_id"),
                        rset.getDate("hire_date"),
                        rset.getDate("quit_date"),
                        rset.getString("quit_yn")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            close(rset);
            close(pstmt);
            close(conn);
        }

        if (list.isEmpty()){
            System.out.println("조회된 사원이 없습니다.");
        } else {
            System.out.println("조회된 사원이 있습니다.");
            list.forEach(System.out::println);
            }

    }
}
