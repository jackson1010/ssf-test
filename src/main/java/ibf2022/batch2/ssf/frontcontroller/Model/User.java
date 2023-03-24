package ibf2022.batch2.ssf.frontcontroller.Model;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Random;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class User implements Serializable {

    @Size(min = 2, message = "Your username must be at least 2 characters in length")
    private String username;

    @Size(min = 2, message = "Your username must be at least 2 characters in length")
    private String password;

    private boolean authenticated = false;
    private int count = 0;
    private String captcha;
    private int answer;
    private int correctAnswer;
    private boolean timeout=false;
    

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("username", this.getUsername())
                .add("password", this.getPassword())
                .build();
    }

    public JsonObject ForUsernameToJSON() {
        return Json.createObjectBuilder()
                .add("username", this.getUsername())
                .build();
    }

    public void getRandomNumbers() {
        Random random = new Random();
        int min = 1;
        int max = 50;
        int result = 0;

        int num1 = random.nextInt(max - min + 1) + min;
        int num2 = random.nextInt(max - min + 1) + min;

        int operationIndex = random.nextInt(4);
        String operation = "";

        switch (operationIndex) {
            case 0:
                operation = "+"; // plus
                result = num1 + num2;
                break;
            case 1:
                operation = "-"; // minus
                result = num1 - num2;
                break;
            case 2:
                operation = "*"; // multiply
                result = num1 * num2;
                break;
            case 3:
                operation = "/"; // divide
                result = num1 / num2;
                break;
        }

        String expression = num1 + " " + operation + " " + num2 + "?";
        setCaptcha(expression);
        setCorrectAnswer(result);
    }

    public boolean isTimeout() {
        return timeout;
    }

    public void setTimeout(boolean timeout) {
        this.timeout = timeout;
    }

    // public static User createFromJson (String json) throws IOException{
    // User user = new User();
    // if(json != null){
    // StringReader sr = new StringReader(json);
    // JsonReader jsr = Json.createReader(sr);
    // JsonObject jso = jsr.readObject();

    // }

    // return null;

    // }

}
