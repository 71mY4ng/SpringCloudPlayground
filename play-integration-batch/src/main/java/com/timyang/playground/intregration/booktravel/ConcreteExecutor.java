package com.timyang.playground.intregration.booktravel;

import org.springframework.stereotype.Component;

@Component
public class ConcreteExecutor extends BaseExecutor {

    @Override
    public void execute(String name) {
        System.out.println("do concrete thing!");
        super.execute(name);
    }
}
