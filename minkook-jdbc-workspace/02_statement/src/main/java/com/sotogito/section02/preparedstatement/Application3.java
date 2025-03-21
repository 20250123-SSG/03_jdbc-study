package com.sotogito.section02.preparedstatement;

import com.mysql.cj.jdbc.JdbcConnection;
import com.sotogito.common.JDBCTemplate;
import com.sotogito.model.dto.EmployeeDTO;

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

public class Application3 {
    public static void main(String[] args) {

        List<EmployeeDTO> reuslts = new ArrayList<EmployeeDTO>();

        Connection conn = JDBCTemplate.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("성");
        String input = sc.nextLine();


      //  String query = "SELECT * FROM employee WHERE emp_name LIKE CONCAT(?,'%')";
//        String query = "SELECT * FROM employee WHERE emp_name LIKE ?%";?는 단독으로 사용해야 하며, 와일드카드(%)는 값에 포함해야 함

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/sotogito/mapper/employee-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            ps = conn.prepareStatement(prop.getProperty("selectEmpByFamilyName"));
            ps.setString(1,  input);
            rs = ps.executeQuery();

            while (rs.next()) {
                reuslts.add(new EmployeeDTO(
                        rs.getString("emp_id")
                        ,rs.getString("emp_name")
                        ,rs.getString("emp_no")
                        ,rs.getString("email")
                        ,rs.getString("phone")
                        ,rs.getString("dept_code")
                        ,rs.getString("job_code")
                        ,rs.getString("sal_level")
                        ,rs.getInt("salary")
                        ,rs.getDouble("bonus")
                        ,rs.getString("manager_id")
                        ,rs.getDate("hire_date")
                        ,rs.getDate("quit_date")
                        ,rs.getString("quit_yn")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCTemplate.close(rs);
            JDBCTemplate.close(ps);
            JDBCTemplate.close(conn);
        }

        if(reuslts.isEmpty()) {
            System.out.println("조회된 사람이 없음");
            return;
        }
        reuslts.forEach(System.out::println);
    }
}
