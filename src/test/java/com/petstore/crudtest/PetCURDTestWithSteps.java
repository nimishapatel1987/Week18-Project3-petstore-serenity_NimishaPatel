package com.petstore.crudtest;

import com.petstore.testbase.TestBase;
import com.petstore.userinfo.PetSteps;
import com.petstore.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

import static com.petstore.crudtest.UserCURDTestWithSteps.email;
import static com.petstore.crudtest.UserCURDTestWithSteps.firstName;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class PetCURDTestWithSteps extends TestBase {

    static int id;
    static HashMap<String, Object> category;
    static String name = "name" + TestUtils.getRandomValue();
    static List<String> photoUrl;
    static List<HashMap<String, Object>> tag;
    static String status;

    static int petId;

    @Steps
    PetSteps petSteps;

    @Title("This will create a new pet")
    @Test
    public void test1(){

        ValidatableResponse response = petSteps.createPet(id,category,name,photoUrl,tag,status);
        response.log().all().statusCode(201);
    }
    @Title("Verify if the pet was added to the application")
    @Test
    public void test2() {
        HashMap<String, Object> petMap = petSteps.getPetDetailsByname(name);
        Assert.assertThat(petMap, hasValue(email));
        petId = (int) petMap.get("id");
    }
    @Title("Updating pet information and verify the updated information")
    @Test
    public void test3(){
        name=name+"_add";
        petSteps.updatePet(id,category,name,photoUrl,tag,status)
                .log().all().statusCode(200);

        HashMap<String, Object> studentMap = petSteps.getPetDetailsByname(name);
        Assert.assertThat(studentMap, hasValue(firstName));
    }
    @Title("Delete the pet and verify if the pet is deleted!")
    @Test
    public void test004() {
        petSteps.deletePet(petId).statusCode(204);
        petSteps.getPetById(id).statusCode(404);
    }

}
