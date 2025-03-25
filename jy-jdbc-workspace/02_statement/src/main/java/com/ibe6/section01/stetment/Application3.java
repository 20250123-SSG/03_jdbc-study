/*
package com.ibe6.section01.stetment;

import com.ibe6.model.dto.EmployeeDTO;

public class Application3 {


        // 최종 결과를 담을 변수
        EmployeeDTO selectedEmp = null;

        // 4. 결과 로직 처리
        if (rset.next()) {
            // System.out.println

            selectedEmp = new EmployeeDTO(); // 조회결과가 있을 경우 생성이 진행

            // 조회된 한 행의 모든 컬럼값들을 해당 dto 객체의 각 필드에 담기
            selectedEmp.setEmpId(rset.getString("emp_id"));
            selectedEmp.setEmpName(rset.getString("emp_name"));
            selectedEmp.setEmpNo(rset.getString("emp_no"));
            selectedEmp.setEmail(rset.getString("emp_email"));
            // ...
            selectedEmp.setSalary(rset.getInt("salary"));
            selectedEmp.setBonus(rset.getDouble("bonus"));
            selectedEmp.setHireDate(rset.getDate("hire_date"));
            // ...


        }

    }


 */