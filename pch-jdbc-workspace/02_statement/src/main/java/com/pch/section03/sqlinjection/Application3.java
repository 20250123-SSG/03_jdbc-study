package com.pch.section03.sqlinjection;

import com.pch.dto.EmployeeDto;

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

import static com.pch.common.JDBCTemplate.close;
import static com.pch.common.JDBCTemplate.getConnection;

public class Application3 {
    public static void main(String[] args) {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        ResultSet rset = null;
        List<EmployeeDto> empDtoList = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        System.out.println("조회할 사원의 성 입력");
        String emp_id = sc.nextLine();

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/pch/mapper/employee-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String query = prop.getProperty("selectEmpByFamilyName");


        try{
            psmt = conn.prepareStatement(query);
            psmt.setString(1, emp_id);
            rset = psmt.executeQuery();

            while (rset.next()) {
                empDtoList.add(new EmployeeDto());
                empDtoList.get(empDtoList.size()-1).setEmp_id(rset.getInt("emp_id"));
                empDtoList.get(empDtoList.size()-1).setEmp_name(rset.getString("emp_name"));
                empDtoList.get(empDtoList.size()-1).setEmp_no(rset.getString("emp_no"));
                empDtoList.get(empDtoList.size()-1).setEmail(rset.getString("email"));
                empDtoList.get(empDtoList.size()-1).setPhone(rset.getString("phone"));
                empDtoList.get(empDtoList.size()-1).setDept_code(rset.getString("dept_code"));
                empDtoList.get(empDtoList.size()-1).setJob_code(rset.getString("job_code"));
                empDtoList.get(empDtoList.size()-1).setSalary(rset.getDouble("salary"));
                empDtoList.get(empDtoList.size()-1).setBonus(rset.getDouble("bonus"));
                empDtoList.get(empDtoList.size()-1).setManager_id(rset.getString("manager_id"));
                empDtoList.get(empDtoList.size()-1).setHire_date(rset.getDate("hire_date"));
                empDtoList.get(empDtoList.size()-1).setQuit_date(rset.getDate("quit_date"));
                empDtoList.get(empDtoList.size()-1).setQuit_yn(rset.getString("quit_yn").charAt(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(psmt);
            close(conn);
        }

        for(EmployeeDto empDto : empDtoList){
            if(empDto != null) {
                System.out.println(empDto.toString());
            } else {
                System.out.println("조회된 사원이 없습니다.");
            }
        }
    }
}
