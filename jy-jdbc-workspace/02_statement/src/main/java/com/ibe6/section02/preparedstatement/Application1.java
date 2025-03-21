
package com.ibe6.section02.preparedstatement;

//public class Application1 {
    /*
        ## java.sql.PreparedStatement ##
        1. Statement의 하위 인터페이스
        2. 미리 sql문을 담은 상태로 생성됨
        3. 플레이스홀더(?)를 사용해 런타임 중에 매개변수에 값을 전달시킬 수 있음
            => 동적 쿼리(사용자의 입력값에 따라 매번 달라지는 쿼리) 생성에 유용
     */
/*
    public static void main(String[] args) {
        // 사번으로 사원 조회 => 한 행 조회 => ResultSet => EmployeeDTO

        EmployeeDTO emp = null; // 최종 결과를 담아낼 변수

        Connection conn = getConnection(); // 1) db 연결

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("조회하고자 하는 사번 입력: ");
        String empId = sc.nextLine();

        String query = "SELECT * FROM employee WHERE emp_id = ?";

        try {
            pstmt = conn.prepareStatement(query);

            // 2) sql문을 담은 채로 PrepareStatement 생성

            // 현재 담겨있는 미완성된 sql문을 완성형으로 만들기 (사용자가 입력한 값을 ?에 채우기)
            // prtmt.setXXX(홀더순번, 대체할값)
            pstmt.setString(1, empId); // ? => '200'

            rset = pstmt.executeQuery(); // 3) sql문 실행 후 결과 받기

            // 4) 결과 처리
            if (rset.next()) {
                emp = new EmployeeDTO();
                emp.setEmpId(rset.getString("emp_id"));
                emp.setEmpName(rset.getString("emp_name"));
                emp.setEmail(rset.getString("emp_email"));
            }

        } finally {
            close(reset);
            close(pstmt);
            close(conn);
        }

        if (emp == null) {
            System.out.println("조회된 사원이 없습니다.");
        } else {
            System.out.println(emp);

        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}

 */