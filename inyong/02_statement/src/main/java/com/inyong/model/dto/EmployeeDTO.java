package com.inyong.model.dto;

import java.util.Date;

/*
DTO

1. data transfer object
2. 여러 계층간 데잍러 전송을 위해 다양한 타입의 데이터를 하나로 묶어 전송하는 쓰임새의 클래스
3. 유사어 : VO, Bean, Entity 등이 있음

DTO 클래스 작성
1. 모든필드는 private
2. 기본 생성자와 모든 필드를 초기화하는 생성자
3. 모든 필드에 대한 getter/setter 메소드
4. toString 메소드 오버라이딩 => 모든 필드값을 하나의 문자열로 반환
5. 직렬화 처리 (선택)
 */
public class EmployeeDTO {
    private String empId;
    private String empName;
    private String empNo;
    private String email;
    private String phone;
    private String deptCode;
    private String jobCode;
    private String salLevel;
    private int salary;
    private double bonus;
    private String mangerId;
    private Date hireDate;
    private Date quitDate;
    private char quitYn;

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", empNo='" + empNo + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", deptCode='" + deptCode + '\'' +
                ", jobCode='" + jobCode + '\'' +
                ", salLevel='" + salLevel + '\'' +
                ", salary=" + salary +
                ", bonus=" + bonus +
                ", mangerId='" + mangerId + '\'' +
                ", hireDate=" + hireDate +
                ", quitDate=" + quitDate +
                ", quitYn=" + quitYn +
                '}';
    }

    public String getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

    public String getEmpNo() {
        return empNo;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public String getJobCode() {
        return jobCode;
    }

    public String getSalLevel() {
        return salLevel;
    }

    public int getSalary() {
        return salary;
    }

    public double getBonus() {
        return bonus;
    }

    public String getMangerId() {
        return mangerId;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public Date getQuitDate() {
        return quitDate;
    }

    public char getQuitYn() {
        return quitYn;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public void setSalLevel(String salLevel) {
        this.salLevel = salLevel;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public void setMangerId(String mangerId) {
        this.mangerId = mangerId;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public void setQuitDate(Date quitDate) {
        this.quitDate = quitDate;
    }

    public void setQuitYn(char quitYn) {
        this.quitYn = quitYn;
    }

    public EmployeeDTO(){}

    public EmployeeDTO(String empId, String empName, String empNo, String email, String phone, String deptCode, String jobCode, String salLevel, int salary, double bonus, String mangerId, Date hireDate, Date quitDate, char quitYn) {
        this.empId = empId;
        this.empName = empName;
        this.empNo = empNo;
        this.email = email;
        this.phone = phone;
        this.deptCode = deptCode;
        this.jobCode = jobCode;
        this.salLevel = salLevel;
        this.salary = salary;
        this.bonus = bonus;
        this.mangerId = mangerId;
        this.hireDate = hireDate;
        this.quitDate = quitDate;
        this.quitYn = quitYn;
    }
}
