package com.example.skillshub;

import java.util.List;

public class User {
    public String uid;
    public String name;
    public String email;
    public String phone;
    public String experience;
    public String portfolio;
    public List<String> skillsYouKnow;
    public List<String> skillsWantToLearn;
    public int weeklyTimeInvestment;
    public boolean onboardingCompleted;

    // Empty constructor required by Firebase
    public User() {}

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.onboardingCompleted = false;
    }

    public User(String uid, String name, String email, List<String> skillsYouKnow, 
                List<String> skillsWantToLearn, int weeklyTimeInvestment) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.skillsYouKnow = skillsYouKnow;
        this.skillsWantToLearn = skillsWantToLearn;
        this.weeklyTimeInvestment = weeklyTimeInvestment;
        this.onboardingCompleted = true;
    }
}
