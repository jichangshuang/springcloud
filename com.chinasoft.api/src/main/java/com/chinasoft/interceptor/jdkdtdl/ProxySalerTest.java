package com.chinasoft.interceptor.jdkdtdl;

import com.chinasoft.interceptor.Person;
import com.chinasoft.interceptor.XiaoQiang;

public class ProxySalerTest {

    public static void main(String[] args) {

        ProxySaler ps = new ProxySaler();
        Person person = (Person) ps.newInstance(new XiaoQiang("jcs", "西安"));
        person.buy();
        person.buy1();

    }
}
