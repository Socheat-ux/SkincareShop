package com.skincareshop.model.staff;

public class ManagerStaff extends Staff{

    private float salary;
    
    public ManagerStaff(String staffId, String fullName, String phone,
                    String username, String password, float salary) {
        super(staffId, fullName, phone, username, password);
        setSalary(salary);
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        if(salary < 800)
        {
            throw new IllegalArgumentException("Salary must be at least 800!");
        }
        this.salary = salary;
        
    }

    @Override
    public String toString() {
        return super.toString()+"\nManagerStaff [\"Position: Manager salary=" + salary + "]";
    }

    @Override
    public boolean can(String action) {
        return true; // Manager can do everything
    }

    @Override
    public boolean equals(Object obj) {  
        if (this == obj) return true;
        if (!(obj instanceof ManagerStaff)) return false;

        ManagerStaff other = (ManagerStaff) obj;
        if (!super.equals(obj)) return false;
        return Float.compare(this.salary, other.salary) == 0;
    }

}
