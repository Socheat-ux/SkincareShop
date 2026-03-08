package com.skincareshop.model.staff;

import com.skincareshop.model.SkincareShop;

public class CashierStaff extends Staff {
 
    private float salary;

    public CashierStaff(Staff S1, float salary) {
        super(S1.getStaffId(), S1.getFullName(), S1.getUsername(), 
        S1.getPhone(), S1.getPassword());
        this.setSalary(salary);
    }

    @Override
    public boolean can(String action) {
        if (action.equals(SkincareShop.CREATE_ORDER) || action.equals(SkincareShop.CREATE_CUSTOMER)
        || action.equals(SkincareShop.VIEW_CUSTOMER) || action.equals(SkincareShop.VIEW_CUSTOMER)){
            return true;
        }
        return false; 
    }

    public float getSalary() { return salary; }

    public void setSalary(float salary) {
        if (salary < 800) {
            throw new IllegalArgumentException("ERROR!");
        }
        else {
            this.salary = salary;
        }
    }

    @Override
    public boolean equals(Object obj) {
        CashierStaff other = (CashierStaff) obj;

        if(!super.equals(obj)) {
            return false;
        }
        else {
            if (Float.floatToIntBits(salary) != Float.floatToIntBits(other.salary))
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + 
                ", salary=" + salary +
                '}';
    }
   

}
