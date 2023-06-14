package com.bailizhang.website.controller;

import com.bailizhang.lynxdb.client.LynxDbClient;
import com.bailizhang.lynxdb.client.connection.LynxDbConnection;
import com.bailizhang.lynxdb.core.common.G;
import com.bailizhang.lynxdb.springboot.starter.LynxDbProperties;
import com.bailizhang.website.param.BenchmarkParam;
import com.bailizhang.website.result.BenchmarkResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("benchmark")
public class BenchmarkController {
    private final LynxDbClient lynxDbClient;
    private final LynxDbProperties lynxDbProperties;

    private final HashMap<String, String> map = new HashMap<>();

    public BenchmarkController(LynxDbClient client, LynxDbProperties properties) {
        lynxDbClient = client;
        lynxDbProperties = properties;
    }

    @PostMapping("insert")
    private BenchmarkResult doInsertTest(@RequestBody BenchmarkParam param) throws ConnectException, InterruptedException {
        String host = lynxDbProperties.getHost();
        int port = lynxDbProperties.getPort();

        int connectionSize = param.getConnectionSize();
        int threadSize = param.getThreadSize();
        int keySize = param.getKeySize();
        int keyLength = param.getKeyLength();
        int valueLength = param.getValueLength();
        String columnFamily = param.getColumnFamily();
        String column = param.getColumn();

        LynxDbConnection[] connections = new LynxDbConnection[connectionSize];

        for(int i = 0; i < connectionSize; i ++) {
            connections[i] = lynxDbClient.createConnection(host, port);
        }

        ExecutorService executor = Executors.newFixedThreadPool(threadSize);
        CountDownLatch latch = new CountDownLatch(keySize);

        long begin = System.currentTimeMillis();

        for(int i = 0; i < keySize; i ++) {
            executor.submit(() -> {
                Random random = new Random();
                int conn = random.nextInt(0, connectionSize);

                LynxDbConnection connection = connections[conn];

                String key = randomStr(keyLength);
                String value = randomStr(valueLength);

                map.put(key, value);

                try {
                    connection.insert(
                            G.I.toBytes(key),
                            columnFamily,
                            column,
                            G.I.toBytes(value)
                    );
                } catch (ConnectException e) {
                    System.out.println(e.getMessage());
                }

                latch.countDown();
            });
        }

        latch.await();
        long end = System.currentTimeMillis();

        long totalTime = end - begin;
        double timePerRequest = (double) totalTime / keySize;

        BenchmarkResult result = new BenchmarkResult();
        result.setTotalTime(totalTime);
        result.setTimePerRequest(timePerRequest);

        AtomicInteger matchKeyCount = new AtomicInteger(0);

        CountDownLatch matchLatch = new CountDownLatch(keySize);

        map.forEach((key, value) -> executor.submit(() -> {
            Random random = new Random();
            int conn = random.nextInt(0, connectionSize);

            LynxDbConnection connection = connections[conn];

            try {
                byte[] findValue = connection.find(
                        G.I.toBytes(key),
                        columnFamily,
                        column
                );

                if(value.equals(G.I.toString(findValue))) {
                    matchKeyCount.incrementAndGet();
                }

            } catch (ConnectException e) {
                System.out.println(e.getMessage());
            }

            matchLatch.countDown();
        }));

        matchLatch.await();

        map.clear();

        result.setMatchKeyCount(matchKeyCount.get());

        return result;
    }

    private String randomStr(int length) {
        Random random = new Random();
        char[] charArray = new char[length];

        for (int i = 0; i < length; i ++) {
            int tempInt = 32 + (int) (94 * random.nextDouble());
            charArray[i] = (char) (tempInt);
        }

        return new String(charArray);
    }
}
