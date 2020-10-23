package com.timyang.playground.transaction.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

@Service
public class IdGenService {

    public static final DateTimeFormatter FLAT_TS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");

    private static final AtomicInteger LOCAL_SEQUENCE = new AtomicInteger(0);
    private static final int LOCAL_SEQUENCE_MAX = 1000;
    private static final IntUnaryOperator INCR_OP = i -> i + 1 % LOCAL_SEQUENCE_MAX;

    public String getId() {
        return getId(LocalDateTime.now());
    }

    public String getId(LocalDateTime createTime) {
        return createTime.format(FLAT_TS) + RandomStringUtils.randomAlphanumeric(1)
                + StringUtils.leftPad(String.valueOf(incrAndGet()), 3, "0");
    }

    private int incrAndGet() {
        return LOCAL_SEQUENCE.updateAndGet(INCR_OP);
    }

    public static void main(String[] args) throws InterruptedException {
        IdGenService sgs = new IdGenService();
        Set<String> ordrNoSet = new HashSet<>();
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1,
                5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

        try {
            for (int i = 0; i < 1000; i++) {
                try {
                    Thread.sleep(30);

                    e.submit(() -> {
                        String orderNo = sgs.getId(LocalDateTime.now());
                        System.out.println(orderNo);

                        ordrNoSet.add(orderNo);
                    });
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        } finally {
            e.shutdown();
            e.awaitTermination(1L, TimeUnit.MINUTES);
        }
    }

}
