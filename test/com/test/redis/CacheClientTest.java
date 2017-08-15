package com.test.redis;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.Test;

import com.cache.client.CacheManagerClient;
import com.cache.operation.IStringOperation;
import com.test.utils.TestBean;

public class CacheClientTest {

    static {
        CacheManagerClient.registerKey("uuxx", 2, "test");
    }

    @Test
    public void testString() {
        IStringOperation op = CacheManagerClient.getStringOperation("test");

        // -----添加数据----------
        op.set("name", "曹操");
        Assert.assertEquals("曹操", op.get("name"));

        Assert.assertEquals(null, op.get("name"));

    }

    @Test
    public void testObject() {
        TestBean tb = TestBean.randomBean();
        IStringOperation op = CacheManagerClient.getStringOperation("test");

        // -----添加数据----------
        op.setObject("obj", tb);
        TestBean rtb = op.getObject("obj", TestBean.class);
        boolean isEq = EqualsBuilder.reflectionEquals(tb, rtb);
        Assert.assertEquals(isEq, true);

    }
    //
    // @Test
    // public void testMap() {
    // CacheManagerClient.registerKeys("test", 2);
    //
    // // 正确的用法
    // IMapOperation operation = CacheManagerClient.getMapOperation("test");
    // operation.put("user", "name", "王峙权");
    // operation.put("user", "age", "23");
    // operation.put("user", "character", "帅气");
    // }
    //
    // /**
    // * redis操作Map
    // */
    // @Test
    // @SuppressWarnings({ "serial", "unchecked", "rawtypes" })
    // public void testMap() {
    // // -----添加数据----------
    // Map<String, String> map = new HashMap<String, String>();
    // map.put("name", "xinxin");
    // map.put("age", "22");
    // map.put("qq", "123456");
    // op.hmset("user", map);
    // List<String> rsmap = op.hmget("user", "name", "age", "qq");
    // assertEquals(Arrays.asList("xinxin", "22", "123456"), rsmap);
    // assertEquals(3, op.hlen("user").longValue());
    // // 删除map中的某个键值
    // op.hdel("user", "age");
    // assertEquals(null, op.hmget("user", "age").get(0));
    // assertEquals(2, op.hlen("user").longValue());
    // assertEquals(3, rsmap.size());
    // assertTrue(op.exists("user"));
    // assertEquals(new HashSet() {
    // {
    // this.addAll(Arrays.asList("name", "qq"));
    // }
    // }, op.hkeys("user"));
    // List<String> values = op.hvals("user");
    // Collections.sort(values);
    // assertEquals(Arrays.asList("123456", "xinxin"), values);
    // assertEquals("xinxin", op.hmget("user", "name").get(0));
    // assertEquals("123456", op.hmget("user", "qq").get(0));
    // }
    //
    // /**
    // * op操作List
    // */
    // @Test
    // public void testList() {
    // assertEquals(Collections.EMPTY_LIST, op.lrange("java framework", 0, -1));
    // op.lpush("java framework", "spring");
    // op.lpush("java framework", "struts");
    // op.lpush("java framework", "hibernate");
    // assertEquals(Arrays.asList("hibernate", "struts", "spring"),
    // op.lrange("java framework", 0, -1));
    // op.del("java framework");
    // op.rpush("java framework", "spring");
    // op.rpush("java framework", "struts");
    // op.rpush("java framework", "hibernate");
    // assertEquals(Arrays.asList("spring", "struts", "hibernate"),
    // op.lrange("java framework", 0, -1));
    // }
    //
    // /**
    // * op操作Set
    // */
    // @SuppressWarnings({ "rawtypes", "serial", "unchecked" })
    // @Test
    // public void testSet() {
    // op.sadd("user", "liuling");
    // op.sadd("user", "xinxin");
    // op.sadd("user", "ling");
    // op.sadd("user", "zhangxinxin");
    // op.sadd("user", "who");
    // assertEquals(5, op.scard("user").longValue());
    // assertEquals(true, op.sismember("user", "who"));
    // op.srem("user", "who");
    // assertEquals(false, op.sismember("user", "who"));
    // assertEquals(4, op.scard("user").longValue());
    // assertEquals(new HashSet() {
    // {
    // this.addAll(Arrays.asList("xinxin", "liuling", "ling", "zhangxinxin"));
    // }
    // }, op.smembers("user"));
    // assertEquals(false, op.sismember("user", "who"));
    // assertEquals(4, op.scard("user").longValue());
    // }
    //
    // @Test
    // public void test() throws InterruptedException {
    // op.rpush("a", "1");
    // op.lpush("a", "6");
    // op.rpush("a", "3");
    // op.lpush("a", "9");
    // assertEquals(Arrays.asList("9", "6", "1", "3"), op.lrange("a", 0, -1));
    // assertEquals(Arrays.asList("1", "3", "6", "9"), op.sort("a"));
    // assertEquals(Arrays.asList("9", "6", "1", "3"), op.lrange("a", 0, -1));
    // }
}
