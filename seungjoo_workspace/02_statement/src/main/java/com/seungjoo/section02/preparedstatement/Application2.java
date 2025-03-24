package com.seungjoo.section02.preparedstatement;

import com.seungjoo.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.seungjoo.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {

        //전체 사원 조회 -> ResultSet -> List<EmployeeDTO

        List<EmployeeDTO> empList = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        String query = "select * from emp";

        try {
            pstmt = conn.prepareStatement(query); //완성형태의 sql문을 담은채로 생성
            rset = pstmt.executeQuery();
                EmployeeDTO rowEmp = new EmployeeDTO(
                    rset.getString("emp_id")
                                                                    //모든 필드를 순서에 맞게 넣으면 됨






                );
                empList.add(rowEmp);

            while(rset.next()) {
                // 현재 참조하는 행 -> 한 사원 -> employeeDTO에 담기
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }


    }

}
