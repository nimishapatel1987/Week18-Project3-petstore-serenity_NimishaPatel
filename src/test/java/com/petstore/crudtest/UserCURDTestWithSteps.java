package com.petstore.crudtest;


import com.petstore.testbase.TestBase;
import com.petstore.userinfo.UserSteps;
import com.petstore.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UserCURDTestWithSteps extends TestBase {

    static Integer id;
    static String userName;
    static String firstName = "PrimeUser" + TestUtils.getRandomValue();
    static String lastName = "PrimeUser" + TestUtils.getRandomValue();
    static String email = TestUtils.getRandomValue() + "xyz@gmail.com";
    static String password;
    static String phone;
    static String userStatus;

    static int userid;

    @Steps
    UserSteps userSteps;

    @Title("This will create a new user")
    @Test
    public void test1(){
        List<String> courseList = new ArrayList<>();
        courseList.add("Scala");
        courseList.add("Java");

        ValidatableResponse response = userSteps.createUser(id,userName,firstName,lastName,email,password,phone,userStatus);
        response.log().all().statusCode(201);
    }
    @Title("Verify if the user was added to the application")
    @Test
    public void test2() {
        HashMap<String, Object> userMap = userSteps.getUserByEmail(email);
        Assert.assertThat(userMap, hasValue(email));
        userid = (int) userMap.get("id");
    }
    @Title("Updating user information and verify the updated information")
    @Test
    public void test3(){
        firstName=firstName+"_add";
        userSteps.updateUserById(userid,userName,firstName,lastName,email,password,phone,userStatus)
                .log().all().statusCode(200);

        HashMap<String, Object> studentMap = userSteps.getUserByEmail(email);
        Assert.assertThat(studentMap, hasValue(firstName));
    }
    @Title("Deleting user with id and verify that user is deleted")
    @Test
    public void test4(){
        userSteps.deleteUserById(userid).statusCode(204);
        userSteps.getUserById(userid).statusCode(404);
    }


}
