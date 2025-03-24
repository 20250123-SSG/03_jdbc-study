package com.pch.dto;

import java.util.Date;

public class EmployeeDto {
    private int emp_id;
    private String emp_name;
    private String emp_no;
    private String email;
    private String phone;
    private String dept_code;
    private String job_code;
    private double salary;
    private double bonus;
    private String manager_id;
    private Date hire_date;
    private Date quit_date;
    private char quit_yn;

    public EmployeeDto() {
    }

    public EmployeeDto(int emp_id, String emp_name, String emp_no, String email, String phone, String dept_code, String job_code, double salary, double bonus, String manager_id, Date hire_date, Date quit_date, char quit_yn) {
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.emp_no = emp_no;
        this.email = email;
        this.phone = phone;
        this.dept_code = dept_code;
        this.job_code = job_code;
        this.salary = salary;
        this.bonus = bonus;
        this.manager_id = manager_id;
        this.hire_date = hire_date;
        this.quit_date = quit_date;
        this.quit_yn = quit_yn;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public String getEmp_no() {
        return emp_no;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDept_code() {
        return dept_code;
    }

    public String getJob_code() {
        return job_code;
    }

    public double getSalary() {
        return salary;
    }

    public double getBonus() {
        return bonus;
    }

    public String getManager_id() {
        return manager_id;
    }

    public Date getHire_date() {
        return hire_date;
    }

    public Date getQuit_date() {
        return quit_date;
    }

    public char getQuit_yn() {
        return quit_yn;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public void setEmp_no(String emp_no) {
        this.emp_no = emp_no;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }

    public void setJob_code(String job_code) {
        this.job_code = job_code;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public void setManager_id(String manager_id) {
        this.manager_id = manager_id;
    }

    public void setHire_date(Date hire_date) {
        this.hire_date = hire_date;
    }

    public void setQuit_date(Date quit_date) {
        this.quit_date = quit_date;
    }

    public void setQuit_yn(char quit_yn) {
        this.quit_yn = quit_yn;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "emp_id=" + emp_id +
                ", emp_name='" + emp_name + '\'' +
                ", emp_no='" + emp_no + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dept_code='" + dept_code + '\'' +
                ", job_code='" + job_code + '\'' +
                ", salary=" + salary +
                ", bonus=" + bonus +
                ", manager_id='" + manager_id + '\'' +
                ", hire_date=" + hire_date +
                ", quit_date=" + quit_date +
                ", quit_yn=" + quit_yn +
                '}';
    }
}
