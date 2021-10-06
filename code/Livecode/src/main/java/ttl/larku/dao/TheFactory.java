package ttl.larku.dao;

import ttl.larku.service.StudentService;

import java.util.ResourceBundle;

/**
 * @author whynot
 */
public class TheFactory {

    public static StudentDao studentDao() {
        ResourceBundle bundle = ResourceBundle.getBundle("myapp");
        String profile = bundle.getString("larku.profile");
        switch (profile) {
            case "development":
                return new InMemoryStudentDao();
            case "production":
                return new MysqlStudentDao();
            default:
                throw new RuntimeException("Unknow profile: " + profile);
        }
    }

    public static StudentService studentService() {
        StudentDao dao = studentDao();
        StudentService service = new StudentService(dao);
        return service;
    }
}
