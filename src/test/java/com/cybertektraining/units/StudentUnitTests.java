package com.cybertektraining.units;

import com.cybertektraining.api.StudentApi;
import com.cybertektraining.pojos.Address;
import com.cybertektraining.pojos.Company;
import com.cybertektraining.pojos.Contact;
import com.cybertektraining.pojos.Student;
import com.cybertektraining.utilities.FileUtilities;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class StudentUnitTests {

    @Test
    public void test1(){

        try {
            String path = System.getProperty("user.dir")+"/student.json";
            Gson gson = new Gson();
            Student student = new Student();

            FileReader fileReader = new FileReader(path);

            Student student_from_json = gson.fromJson(fileReader,Student.class);

            System.out.println(student_from_json);

            Assert.assertEquals("01/01/1890",student_from_json.getBirthDate());
            Assert.assertEquals("Cybertek",student_from_json.getCompany().getCompanyName());
            Assert.assertEquals("Virginia",student_from_json.getCompany().getAddress().getState());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test2(){
        Address address = new Address("McLean", "Virginia", "7925 Jones Branch Dr #3300", 7925);
        Company company = new Company(address, "02/02/2020", "Cybertek", "SDET");
        Contact contact = new Contact("sdet@email.com", "454-323-2341", "7925 Jones Branch Dr #3300");
        Student student = new Student("123456", 11, "01/06/1990", company, contact,
                "Robert", "Male", "11/26/2017",
                "Dzagoev", "MBA","AP-45", "1111", "Math");

        Response response = StudentApi.addStudent(student);

        Assert.assertEquals(200, response.getStatusCode());

        response.prettyPrint();
    }

    @Test
    public void test3() {
        //path to the student JSON file
        String path = System.getProperty("user.dir") + "/student.json";
        //deserialize JSON object into student POJO
        Student student = FileUtilities.getUserFromJSON(path);
        //POST new student
        Response response = StudentApi.addStudent(student);
        //assert status code
        Assert.assertEquals(200, response.getStatusCode());
    }

}
