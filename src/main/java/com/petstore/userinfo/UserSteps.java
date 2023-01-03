package com.petstore.userinfo;

import com.petstore.constants.EndPoints;
import com.petstore.model.PetPojo;
import com.petstore.model.UserPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;
import java.util.List;

public class UserSteps {
    @Step("Create User with UserId:{0},username:{1},firstname:{2},lastname{3},email:{4},password:{5},phone:{6},userstatus:{7}")
    public ValidatableResponse createUser(int id,String userName, String firstName, String lastname, String email, String password, String phone,
                                          String userStatus) {
        UserPojo userPojo = new UserPojo();
        userPojo.setId(id);
        userPojo.setUserName(userName);
        userPojo.setFirstName(firstName);
        userPojo.setLastname(lastname);
        userPojo.setEmail(email);
        userPojo.setPassword(password);
        userPojo.setPhone(phone);
        userPojo.setUserStatus(userStatus);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(userPojo)
                .when()
                .post()
                .then();
    }
    @Step("Getting the user information with firstName: {0}")
    public HashMap<String, Object> getUserByEmail(String email) {
        String p1 = "findAll{it.firstName == '";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_USER)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + email + p2);
    }
    @Step("Updating User with UserId:{0},username:{1},firstname:{2},lastname{3},email:{4},password:{5},phone:{6},userstatus:{7}")
    public ValidatableResponse updateUserById(int id,String userName, String firstName, String lastname, String email, String password, String phone,
                                          String userStatus) {
        UserPojo userPojo = new UserPojo();
        userPojo.setId(id);
        userPojo.setUserName(userName);
        userPojo.setFirstName(firstName);
        userPojo.setLastname(lastname);
        userPojo.setEmail(email);
        userPojo.setPassword(password);
        userPojo.setPhone(phone);
        userPojo.setUserStatus(userStatus);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("userID", id)
                .body(userPojo)
                .when()
                .put(EndPoints.UPDATE_USER_BY_ID)
                .then();
    }
    @Step("Deleting user information with userId: {0}")
    public ValidatableResponse deleteUserById(int userId) {
        return SerenityRest.given().log().all()
                .pathParam("userID", userId)
                .when()
                .delete(EndPoints.DELETE_USER_BY_ID)
                .then();
    }
    @Step("Getting user information with userId: {0}")
    public ValidatableResponse getUserById(int userId){
        return SerenityRest.given().log().all()
                .pathParam("userID", userId)
                .when()
                .get(EndPoints.GET_SINGLE_USER_BY_ID)
                .then();
    }
}
