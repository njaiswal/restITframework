package com.clearqa.test.utils;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;

public class TestScenarios {
    public String baseurl;
    public List<String> services;

    @Override
    public String toString() {
        return "TestScenarios [baseurl=" + baseurl + ", services=" + services
                + "]";
    }

    public Object[][] getDataProviderFormatedTestUrls() {

        String baseURL = System.getProperty("base_url", this.baseurl);

        Assert.assertTrue(this.services.size() != 0, "There are some test urls");

        List<String> testUrls = new ArrayList<String>();;
        for(String u: this.services) {
            testUrls.add(baseURL + u);
        }

        Object[][] result=new Object[testUrls.size()][];
        int i=0;
        for(String s:testUrls){
            result[i]=new Object[]{new String(s)};
            i++;
        }
        return result;
    }

    public List<String> getTestUrls() {
        List<String> testUrls = new ArrayList<String>();

        String baseURL = System.getProperty("base_url", this.baseurl);
        for(String u: this.services) {
            testUrls.add(baseURL + u);
        }
        return testUrls;
    }
}
