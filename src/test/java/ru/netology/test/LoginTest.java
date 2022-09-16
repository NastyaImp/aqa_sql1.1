package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import ru.netology.sql.SqlHelper;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.sql.SqlHelper.cleanDataBase;

public class LoginTest {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    public static void cleanDb() {
        SqlHelper.cleanDataBase();
    }

    @Test
    public void shouldAuthorization() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validData(authInfo);
        verificationPage.validVerify(SqlHelper.getCode());
    }

    @Test
    public void shouldGetErrorIfInvalidPassword() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getInvalidPasswordAuthInfo();
        loginPage.invalidData(authInfo);
        loginPage.getErrorIfInvalidData();
    }

    @Test
    public void shouldGetErrorIfInvalidLogin() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getInvalidLoginAuthInfo();
        loginPage.invalidData(authInfo);
        loginPage.getErrorIfInvalidData();
    }

    @Test
    public void shouldGetErrorIfWrongCode() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validData(authInfo);
        val code = DataHelper.getWrongVerificationCode();
        verificationPage.invalidVerify(code);
        verificationPage.getErrorIfInvalidSmsCode();
    }

}