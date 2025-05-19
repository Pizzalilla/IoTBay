import com.mycompany.labs.dao.UserDao;
import com.mycompany.labs.model.User;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDaoTest {

    private UserDao userDao;
    private int testUserId;

    @BeforeAll
    public void setup() throws Exception {
        userDao = new UserDao();
    }

    @Test
    @Order(1)
    public void testAddUser() throws SQLException {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("testuser@example.com");
        user.setPassword("123456");
        user.setGender("Male");
        user.setMobile("1234567890");
        user.setAddress("123 Test Street");
        user.setType("Customer");
        user.setPosition("Member");

        userDao.addUser(user);

        User inserted = userDao.getUserByEmail("testuser@example.com");
        assertNotNull(inserted);
        testUserId = inserted.getUserID();
    }

    @Test
    @Order(2)
    public void testUpdateUser() throws SQLException, ClassNotFoundException {
        User user = userDao.getUserByEmail("testuser@example.com");
        assertNotNull(user);

        user.setFirstName("Updated");
        user.setPassword("654321");
        userDao.updateUser(user);

        User updated = userDao.getUserById(user.getUserID());
        assertEquals("Updated", updated.getFirstName());
        assertEquals("654321", updated.getPassword());
    }

    @Test
    @Order(3)
    public void testDeleteUser() throws SQLException {
        User user = userDao.getUserByEmail("testuser@example.com");
        assertNotNull(user);

        userDao.deleteUser(user.getUserID());

        User deleted = userDao.getUserByEmail("testuser@example.com");
        assertNull(deleted);
    }
}
