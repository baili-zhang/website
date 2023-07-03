package com.bailizhang.website.rest;

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
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("benchmark")
public class BenchmarkController {
    private final LynxDbClient lynxDbClient;
    private final LynxDbProperties lynxDbProperties;
    private final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    private ExecutorService executor;
    private LynxDbConnection[] connections;
    private String columnFamily;
    private String column;

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

        columnFamily = param.getColumnFamily();
        column = param.getColumn();

        connections = new LynxDbConnection[connectionSize];

        for(int i = 0; i < connectionSize; i ++) {
            connections[i] = lynxDbClient.createConnection(host, port);
        }

        executor = Executors.newFixedThreadPool(threadSize);
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

        return result;
    }

    @PostMapping("find")
    private void doFindTest() throws InterruptedException {
        AtomicInteger matchKeyCount = new AtomicInteger(0);

        CountDownLatch matchLatch = new CountDownLatch(map.size());

        map.forEach((key, value) -> executor.submit(() -> {
            Random random = new Random();
            int conn = random.nextInt(0, connections.length);

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

        System.out.println(matchKeyCount.get());
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
