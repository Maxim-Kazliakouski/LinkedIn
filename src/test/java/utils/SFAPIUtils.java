package utils;

import io.qameta.allure.Attachment;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import tests.base.BaseWithStepsTest;

import java.util.Date;

import static java.lang.String.format;

public class SFAPIUtils extends BaseWithStepsTest {

//    public static String accessToken() throws IOException, ParseException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = RequestBody.create(mediaType, "");
//        Request request = new Request.Builder()
//                .url("https://login.salesforce.com/services/oauth2/token?grant_type=password&" +
//                        "client_id=" + LoginPage.getClientID() +
//                        "&client_secret=" + LoginPage.getClientSecret() +
//                        "&password=M@xkaz1991" +
//                        "&username=chrome@oxa.sandbox")
//                .method("POST", body)
//                .addHeader("Cookie", "CookieConsentPolicy=0:0; LSKey-c$CookieConsentPolicy=0:0")
//                .build();
//        Response response = client.newCall(request).execute();
//        String s = response.body().string();
//        // read json
//        Object json = new JSONParser().parse(s);
//        // Customize json into JSONObject
//        JSONObject jsonObject = (JSONObject) json;
//        // Get accessToken
//        return (String) jsonObject.get("access_token");
//    }
//    @Step("deleting created user with id contact --> '{idContact}'")
//    public static void deleteContact(String idContact) throws IOException, ParseException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = RequestBody.create(mediaType, "");
//        Request request = new Request.Builder()
//                .url("https://oxagile-dev-ed.my.salesforce.com/services/data/v52.0/sobjects/Contact/" + idContact + "")
//                .method("DELETE", body)
//                .addHeader("Authorization", "Bearer " + SFAPIUtils.accessToken() + "")
//                .addHeader("Cookie", "BrowserId=k_j6AiyKEe20rYmjFe7Zzg; CookieConsentPolicy=0:1; LSKey-c$CookieConsentPolicy=0:1")
//                .build();
//        Response response = client.newCall(request).execute();
//        Assert.assertEquals(response.code(), 204, format("The contact with id '%s' hasn't been deleted or something went wrong!", idContact));
//    }

    public void setCookie(String sidValue) {
        String LOGIN_URL = "https://oxagile-dev-ed.my.salesforce.com/";
        browser.get(LOGIN_URL);
        Cookie cookie = new Cookie("sid", sidValue, "/", (new Date(System.currentTimeMillis() * 1000L)));
        browser.manage().addCookie(cookie);
    }

    @Attachment(value = "screenshot", type = "image/png")
    public static byte[] takeScreenshot(WebDriver browser) {
        return ((TakesScreenshot) browser).getScreenshotAs(OutputType.BYTES);
    }
}
