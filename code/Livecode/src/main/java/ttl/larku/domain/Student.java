package ttl.larku.domain;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author whynot
 */
public class Student implements Comparable<Student>{

//    1. An integer id
//2. A name
//3. A dateOfBirth of type LocalDate
//1. You can initialize a LocalDate with the of method, e.g. LocalDate.of(1957, 10, 10)
//4. Zero or more phone numbers
//5. A status, which can have one of the following values:
//            • Privileged
//            • Normal
//            • Restricted

    public enum Status {
        FullTime,
        PartTime,
        Hibernating
    }

    private int id;
    private String name;
    private LocalDate dob;
    private List<String> phoneNumbers;
    private Status status;

    public Student() {}

    public Student(int id, String name, LocalDate dob, Status status, String... phoneNumbers) {
        this(id, name, dob, status, Arrays.asList(phoneNumbers));
//        this.id = id;
//        this.name = name;
//        this.dob = dob;
//        this.phoneNumbers = Arrays.asList(phoneNumbers);
//        this.status = status;
    }

    public Student(int id, String name, LocalDate dob, Status status, List<String> phoneNumbers) {
//        init(id, name, dob, status, phoneNumbers);
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phoneNumbers = phoneNumbers;
        this.status = status;
    }

    public void init(int id, String name, LocalDate dob, Status status, List<String> phoneNumbers) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phoneNumbers = phoneNumbers;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", phoneNumbers=" + phoneNumbers +
                ", status=" + status +
                '}';
    }

    @Override
    public int compareTo(Student other) {
        return Integer.compare(id, other.id);
//        if(this.id < other.id) {
//            return -1;
//        } else if(this.id == other.id) {
//            return 0;
//        }
//        return 1;
    }
}
