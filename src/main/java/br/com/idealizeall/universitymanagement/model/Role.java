package br.com.idealizeall.universitymanagement.model;

public enum Role {

    ADMIN(1,"ADMIN"),
    TEACHER(2,"TEACHER"),
    STUDENT(3,"STUDENT");

    private final int roleID;
    private final String roleName;

    Role(int roleID, String roleName) {
        this.roleID = roleID;
        this.roleName = roleName;
    }

    public static Role getRoleById(int roleID){
        for (Role role : Role.values()){
            if(role.getRoleID() == roleID){
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid roleId: " + roleID);
    }


    public int getRoleID() {
        return roleID;
    }

    public String getRoleName() {
        return roleName;
    }
}
