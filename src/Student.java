public class Student {
    private int sid;
    private String firstname;
    private String lastname;

    public Student(int sid, String firstname, String lastname) {
        this.sid = sid;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public int getSid() {return sid;}
    public String getFirstname() {return firstname;}
    public String getLastname() {return lastname;}

    @Override
    public String toString() {
        return "Student{" +
                "sid=" + sid +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}