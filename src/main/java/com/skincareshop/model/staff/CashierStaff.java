package com.skincareshop.model.staff;

import com.skincareshop.model.SkincareShop;

public class CashierStaff extends Staff {
 
    private float salary;

    public CashierStaff(String staffId, String fullName, String phone,
                    String username, String password, float salary) {
        super(staffId, fullName, phone, username, password);
        setSalary(salary);
    }

    @Override
    public boolean can(String action) {
        if (action.equals(SkincareShop.CREATE_ORDER) || action.equals(SkincareShop.CREATE_CUSTOMER)
        || action.equals(SkincareShop.VIEW_ORDER) || action.equals(SkincareShop.VIEW_CUSTOMER)){
            return true;
        }
        return false; 
    }

    public float getSalary() { return salary; }

    public void setSalary(float salary) {
        if (salary < 400) {
            throw new IllegalArgumentException("ERROR! Salary must be more than 400$!");
        }
        this.salary = salary;
        
    }

    // Overloaded — takes base salary + bonus
    public void setSalary(float baseSalary, float bonus) {
        setSalary(baseSalary + bonus);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CashierStaff)) return false;

        CashierStaff other = (CashierStaff) obj;
        if (!super.equals(obj)) return false;
        return Float.compare(this.salary, other.salary) == 0;
    }


    @Override
    public String toString() {
        return super.toString() + 
                ", salary=" + salary +
                '}';
    }




}
