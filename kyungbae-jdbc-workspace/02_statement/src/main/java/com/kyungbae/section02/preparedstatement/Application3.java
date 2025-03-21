package com.kyungbae.section02.preparedstatement;

import com.kyungbae.model.dto.EmployeeDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import static com.kyungbae.common.JDBCTemplate.close;
import static com.kyungbae.common.JDBCTemplate.getConnection;

public class Application3 {
    /*
        PreparedStatement 로
        조회할 사원의 성을 입력받아 해당 성씨를 가진
        사원정보들을 모두 출력
     */
    public static void main(String[] args) {

        Connection conn = getConnection();

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<EmployeeDTO> listEmp = new ArrayList<>();

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/kyungbae/mapper/employee-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("검색할 사원의 성 입력: ");
//        String lastName = sc.nextLine() + '%';
        String lastName = sc.nextLine();

//        String query = "SELECT * FROM employee WHERE emp_name LIKE ?";
        String query = prop.getProperty("selectEmpByLastName");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, lastName);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                EmployeeDTO rowEmp = new EmployeeDTO(
                        rset.getString("emp_id"),
                        rset.getString("emp_name"),
                        rset.getString("emp_no"),
                        rset.getString("email"),
                        rset.getString("phone"),
                        rset.getString("dept_code"),
                        rset.getString("job_code"),
                        rset.getString("sal_level"),
                        rset.getInt("salary"),
                        rset.getDouble("bonus"),
                        rset.getString("manager_id"),
                        rset.getDate("hire_date"),
                        rset.getDate("quit_date"),
                        rset.getString("quit_yn")
                );
                listEmp.add(rowEmp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
            close(conn);
        }
        if (listEmp.isEmpty()) {
            System.out.println("조회 결과가 없습니다.");
        } else {
            listEmp.forEach(System.out::println);
        }
    }
}
