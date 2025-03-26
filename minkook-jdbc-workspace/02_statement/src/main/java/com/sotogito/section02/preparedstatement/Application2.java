package com.sotogito.section02.preparedstatement;

import com.sotogito.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.sotogito.common.JDBCTemplate.close;
import static com.sotogito.common.JDBCTemplate.getConnection;

public class Application2 {

    public static void main(String[] args) {
        /// 전체 사원 조회 -> ResultSet -> List<EmployeeDTO>

        List<EmployeeDTO> empList = new ArrayList<>(); //최종결과들을 담을 변수

        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM employee ORDER BY salary DESC";

        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                EmployeeDTO emp = new EmployeeDTO(
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
                );

                empList.add(emp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            close(rs);
            close(ps);
            close(conn);
        }

        if(empList.isEmpty()){
            System.out.println("없음");
            return;
        }
        empList.forEach(System.out::println);
        System.out.println(empList.size());
    }
}
