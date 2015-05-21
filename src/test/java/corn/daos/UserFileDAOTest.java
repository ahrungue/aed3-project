package corn.daos;

import corn.indexes.UserIndex;
import corn.models.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author rungue
 * @since 05/11/15
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserFileDAOTest {

    @Test
    public void test1Save() throws Exception {
        UserFileDAO userFileDAO = new UserFileDAO();
        User user = new User();
        user.setName( String.format("%s %s %s",
                RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(3,15)),
                RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(3,15)),
                RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(3,15))
        ));
        user.setPassword(RandomStringUtils.random(10, true, true));

        //Save the user
        userFileDAO.save(user);

        System.out.printf("User Saved -> %s%n", user.getId());
        List<User> userList = userFileDAO.findAll();

        assertNotNull("User list must not be null.", userList);
        assertTrue("User list must not be empty.", !userList.isEmpty());

    }//end testSave()

    @Test
    public void test2ById() throws Exception {
        UserFileDAO userFileDAO = new UserFileDAO();
        List<User> userList = userFileDAO.findAll();
        int listPosition = RandomUtils.nextInt(0, userList.size());
        String id = userList.get(listPosition).getId();

        User user = userFileDAO.byId(id);
        System.out.printf("%nBy id: %s%n", id);
        System.out.println(user);

        assertEquals("User.is must be equals to id. ", id, user.getId());

    }//end testById()

    @Test
    public void test3Update() throws Exception {
        UserFileDAO userFileDAO = new UserFileDAO();
        List<User> userList = userFileDAO.findAll();
        int listPosition = RandomUtils.nextInt(0, userList.size());

        //Get user to test
        User testUser = userList.get(listPosition);

        //Print the user before update to check on console
        System.out.printf("%nBefore User Update:%n%s%n", testUser);

        //Update the user attributes
        testUser.setName(String.format("%s %s %s",
                RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(3, 15)),
                RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(3, 15)),
                "Updated"
        ));
        testUser.setPassword(RandomStringUtils.random(10, true, true));

        //Save testUser to check the update function
        userFileDAO.update(testUser);

        //User after being updated
        User user = userFileDAO.byId(testUser.getId());

        //Print the user after update to check on console
        System.out.printf("%nAfter User Update:%n%s%n", user);

        assertEquals("TestUser must be equals user. ", testUser, user);

    }//end testById()

    @Test
    public void test4remove() throws Exception {
        UserFileDAO userFileDAO = new UserFileDAO();
        List<User> userList = userFileDAO.findAll();
        int listPosition = RandomUtils.nextInt(0, userList.size());

        //Get user.id to test
        String removeId = userList.get(listPosition).getId();

        //Remove the user
        userFileDAO.delete(removeId);

        User testUserRemoved = userFileDAO.byId(removeId);

        //Print the user after update to check on console
        System.out.printf("%nUser removed:%n%s%n", removeId);

        assertNull("TestUseRemoved must be NULL. ", testUserRemoved);

    }//end testById()

    @AfterClass
    public static void after(){
        UserFileDAO userFileDAO = new UserFileDAO();
        List<User> userList = userFileDAO.findAll();

        if (userList != null) {
            System.out.printf("%nFindAll Method:%n");
            userList.forEach(System.out::println);
            System.out.printf("%nArquivo de Index de Usuarios:%n");
            System.out.println(new UserIndex());
        }//end if

        System.out.println(new UserIndex());

    }//end after()

}//end class UserFileDAOTest