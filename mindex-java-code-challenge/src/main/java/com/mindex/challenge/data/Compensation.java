package com.mindex.challenge.data;


import java.time.LocalDate;

public class Compensation {
    private static Employee employee;

    private Double salary;

    private static LocalDate effectiveDate;

    public Compensation(){
    }

    public Employee getEmployee(){ return employee; }

    public void setEmployee(Employee employee){ Compensation.employee = employee; }

    public Double getSalary(){ return salary; }

    public void setSalary(double salary){ this.salary = salary; }

    public LocalDate getEffectiveDate(){ return effectiveDate; }

    public void setEffectiveDate(LocalDate effectiveDate){ Compensation.effectiveDate = effectiveDate; }
}
