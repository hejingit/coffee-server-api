/**
 * Project Name:coffee-server-api
 * File Name:RedisResourceDao.java
 * Package Name:com.coffee.api.core.dao
 * Date:2018年2月7日下午2:50:57
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.core.dao;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.util.SafeEncoder;

/**
 * ClassName:RedisResourceDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月7日 下午2:50:57 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
public class RedisResourceDao {
	private static JedisPool jedisPool = null;

	static {
		ResourceBundle bundle = ResourceBundle.getBundle("application");
		init(bundle.getString("metadata.redis.url"), Integer.parseInt(bundle.getString("metadata.redis.port")));
	}

	public static void init(String url, int port) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(50);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(1000 * 100);
		config.setTestOnBorrow(true);
		jedisPool = new JedisPool(config, url, port);
	}

	public static class Hash {

		/**
		 * 从hash中删除指定的存储
		 * @param String key
		 * @param String  fieid 存储的名字
		 * @return 状态码，1成功，0失败
		 * */
		public static long hdel(String key, String fieid) {
			try (Jedis jedis = jedisPool.getResource()) {
				long s = jedis.hdel(key, fieid);
				return s;
			}
		}

		public static long hdel(String key) {
			try (Jedis jedis = jedisPool.getResource()) {
				long s = jedis.del(key);
				return s;
			}
		}

		/**
		 * 测试hash中指定的存储是否存在
		 * @param String key
		 * @param String  fieid 存储的名字
		 * @return 1存在，0不存在
		 * */
		public static boolean hexists(String key, String fieid) {
			try (Jedis jedis = jedisPool.getResource()) {
				boolean s = jedis.hexists(key, fieid);
				return s;
			}
		}

		/**
		 * 返回hash中指定存储位置的值
		 * 
		 * @param String key
		 * @param String fieid 存储的名字
		 * @return 存储对应的值
		 * */
		public static String hget(String key, String fieid) {
			try (Jedis jedis = jedisPool.getResource()) {
				String s = jedis.hget(key, fieid);
				return s;
			}
		}

		public static byte[] hget(byte[] key, byte[] fieid) {
			try (Jedis jedis = jedisPool.getResource()) {
				byte[] s = jedis.hget(key, fieid);
				return s;
			}
		}

		/**
		 * 以Map的形式返回hash中的存储和值
		 * @param String    key
		 * @return Map<Strinig,String>
		 * */
		public static Map<String, String> hgetAll(String key) {
			try (Jedis jedis = jedisPool.getResource()) {
				Map<String, String> map = jedis.hgetAll(key);
				return map;
			}
		}

		/**
		 * 添加一个对应关系
		 * @param String  key
		 * @param String fieid
		 * @param String value
		 * @return 状态码 1成功，0失败，fieid已存在将更新，也返回0
		 * **/
		public static long hset(String key, String fieid, String value) {
			try (Jedis jedis = jedisPool.getResource()) {
				long s = jedis.hset(key, fieid, value);
				return s;
			}
		}

		public static long hset(String key, String fieid, byte[] value) {
			try (Jedis jedis = jedisPool.getResource()) {
				long s = jedis.hset(key.getBytes(), fieid.getBytes(), value);
				return s;
			}
		}

		/**
		 * 添加对应关系，只有在fieid不存在时才执行
		 * @param String key
		 * @param String fieid
		 * @param String value
		 * @return 状态码 1成功，0失败fieid已存
		 * **/
		public static long hsetnx(String key, String fieid, String value) {
			try (Jedis jedis = jedisPool.getResource()) {
				long s = jedis.hsetnx(key, fieid, value);
				return s;
			}
		}

		/**
		 * 获取hash中value的集合
		 * 
		 * @param String
		 *            key
		 * @return List<String>
		 * */
		public static List<String> hvals(String key) {
			try (Jedis jedis = jedisPool.getResource()) {
				List<String> list = jedis.hvals(key);
				return list;
			}
		}

		/**
		 * 在指定的存储位置加上指定的数字，存储位置的值必须可转为数字类型
		 * @param String  key
		 * @param String  fieid 存储位置
		 * @param String long value 要增加的值,可以是负数
		 * @return 增加指定数字后，存储位置的值
		 * */
		public static long hincrby(String key, String fieid, long value) {
			try (Jedis jedis = jedisPool.getResource()) {
				long s = jedis.hincrBy(key, fieid, value);
				return s;
			}
		}

		/**
		 * 返回指定hash中的所有存储名字,类似Map中的keySet方法
		 * @param String key
		 * @return Set<String> 存储名称的集合
		 * */
		public static Set<String> hkeys(String key) {
			try (Jedis jedis = jedisPool.getResource()) {
				Set<String> set = jedis.hkeys(key);
				return set;
			}
		}

		/**
		 * 获取hash中存储的个数，类似Map中size方法
		 * @param String  key
		 * @return long 存储的个数
		 * */
		public static long hlen(String key) {
			try (Jedis jedis = jedisPool.getResource()) {
				long len = jedis.hlen(key);
				return len;
			}
		}

		/**
		 * 根据多个key，获取对应的value，返回List,如果指定的key不存在,List对应位置为null
		 * @param String  key
		 * @param String ... fieids 存储位置
		 * @return List<String>
		 * */
		public static List<String> hmget(String key, String... fieids) {
			try (Jedis jedis = jedisPool.getResource()) {
				List<String> list = jedis.hmget(key, fieids);
				return list;
			}
		}

		public static List<byte[]> hmget(byte[] key, byte[]... fieids) {
			try (Jedis jedis = jedisPool.getResource()) {
				List<byte[]> list = jedis.hmget(key, fieids);
				return list;
			}
		}

		/**
		 * 添加对应关系，如果对应关系已存在，则覆盖
		 * @param Strin   key
		 * @param Map <String,String> 对应关系
		 * @return 状态，成功返回OK
		 * */
		public static String hmset(String key, Map<String, String> map) {
			try (Jedis jedis = jedisPool.getResource()) {
				String s = jedis.hmset(key, map);
				return s;
			}
		}

		/**
		 * 添加对应关系，如果对应关系已存在，则覆盖
		 * @param Strin key
		 * @param Map <String,String> 对应关系
		 * @return 状态，成功返回OK
		 * */
		public static String hmset(byte[] key, Map<byte[], byte[]> map) {
			try (Jedis jedis = jedisPool.getResource()) {
				String s = jedis.hmset(key, map);
				return s;
			}
		}

	}

	public static class Keys {

		/**
		 * 清空所有key
		 */
		public static String flushAll() {
			try (Jedis jedis = jedisPool.getResource()) {
				String stata = jedis.flushAll();
				return stata;
			}
		}

		/**
		 * 更改key
		 * @param String oldkey
		 * @param String  newkey
		 * @return 状态码
		 * */
		public static String rename(String oldkey, String newkey) {
			return rename(SafeEncoder.encode(oldkey), SafeEncoder.encode(newkey));
		}

		/**
		 * 更改key,仅当新key不存在时才执行
		 * @param String oldkey
		 * @param String newkey 
		 * @return 状态码
		 * */
		public static long renamenx(String oldkey, String newkey) {
			try (Jedis jedis = jedisPool.getResource()) {
				long status = jedis.renamenx(oldkey, newkey);
				return status;
			}
		}

		/**
		 * 更改key
		 * @param String oldkey
		 * @param String newkey
		 * @return 状态码
		 * */
		public static String rename(byte[] oldkey, byte[] newkey) {
			try (Jedis jedis = jedisPool.getResource()) {
				String status = jedis.rename(oldkey, newkey);
				return status;
			}
		}

		/**
		 * 设置key的过期时间，以秒为单位
		 * @param String key
		 * @param 时间,已秒为单位
		 * @return 影响的记录数
		 * */
		public static long expired(String key, int seconds) {
			try (Jedis jedis = jedisPool.getResource()) {
				long count = jedis.expire(key, seconds);
				return count;
			}
		}

		/**
		 * 设置key的过期时间,它是距历元（即格林威治标准时间 1970 年 1 月 1 日的 00:00:00，格里高利历）的偏移量。
		 * @param String key
		 * @param 时间,已秒为单位
		 * @return 影响的记录数
		 * */
		public static long expireAt(String key, long timestamp) {
			try (Jedis jedis = jedisPool.getResource()) {
				long count = jedis.expireAt(key, timestamp);
				return count;
			}
		}

		/**
		 * 查询key的过期时间
		 * @param String key
		 * @return 以秒为单位的时间表示
		 * */
		public static long ttl(String key) {
			try (Jedis jedis = jedisPool.getResource()) {
				long len = jedis.ttl(key);
				return len;
			}
		}

		/**
		 * 取消对key过期时间的设置
		 * @param key
		 * @return 影响的记录数
		 * */
		public static long persist(String key) {
			try (Jedis jedis = jedisPool.getResource()) {
				long count = jedis.persist(key);
				return count;
			}
		}

		/**
		 * 删除keys对应的记录,可以是多个key
		 * @param String  ... keys
		 * @return 删除的记录数
		 * */
		public static long del(String... keys) {
			try (Jedis jedis = jedisPool.getResource()) {
				long count = jedis.del(keys);
				return count;
			}
		}

		/**
		 * 删除keys对应的记录,可以是多个key
		 * @param String .. keys
		 * @return 删除的记录数
		 * */
		public static long del(byte[]... keys) {
			try (Jedis jedis = jedisPool.getResource()) {
				long count = jedis.del(keys);
				return count;
			}
		}

		/**
		 * 判断key是否存在
		 * @param String key
		 * @return boolean
		 * */
		public static boolean exists(String key) {
			try (Jedis jedis = jedisPool.getResource()) {
				boolean exis = jedis.exists(key);
				return exis;
			}
		}

		/**
		 * 对List,Set,SortSet进行排序,如果集合数据较大应避免使用这个方法
		 * @param String key
		 * @return List<String> 集合的全部记录
		 * **/
		public static List<String> sort(String key) {
			try (Jedis jedis = jedisPool.getResource()) {
				List<String> list = jedis.sort(key);
				return list;
			}
		}

		/**
		 * 对List,Set,SortSet进行排序或limit
		 * @param String key
		 * @param SortingParams parame 定义排序类型或limit的起止位置.
		 * @return List<String> 全部或部分记录
		 * **/
		public static List<String> sort(String key, SortingParams parame) {
			try (Jedis jedis = jedisPool.getResource()) {
				List<String> list = jedis.sort(key, parame);
				return list;
			}
		}

		/**
		 * 返回指定key存储的类型
		 * @param String key
		 * @return String string|list|set|zset|hash
		 * **/
		public static String type(String key) {
			try (Jedis jedis = jedisPool.getResource()) {
				String type = jedis.type(key);
				return type;
			}
		}

		/**
		 * 查找所有匹配给定的模式的键
		 * @param String  key的表达式,*表示多个，？表示一个
		 * */
		public static Set<String> keys(String pattern) {
			try (Jedis jedis = jedisPool.getResource()) {
				Set<String> set = jedis.keys(pattern);
				return set;
			}
		}

		/**
		 * 将key对应的value减去指定的值，只有value可以转为数字时该方法才可用
		 * @param String key
		 * @param long number 要减去的值
		 * @return long 减指定值后的值
		 * */
		public static long decrBy(String key, long number) {
			try (Jedis jedis = jedisPool.getResource()) {
				long len = jedis.decrBy(key, number);
				return len;
			}
		}

		/**
		 * <b>可以作为获取唯一id的方法</b><br/>
		 * 将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
		 * @param String  key
		 * @param long number 要减去的值
		 * @return long 相加后的值
		 * */
		public static long incrBy(String key, long number) {
			try (Jedis jedis = jedisPool.getResource()) {
				long len = jedis.incrBy(key, number);
				return len;
			}
		}
	}

	public static class Lists {
		/**
		 * List长度
		 * @param String key
		 * @return 长度
		 * */
		public static long llen(String key) {
			return llen(SafeEncoder.encode(key));
		}

		/**
		 * List长度
		 * @param byte[] key
		 * @return 长度
		 * */
		public static long llen(byte[] key) {
			try (Jedis jedis = jedisPool.getResource()) {
				long count = jedis.llen(key);
				return count;
			}
		}

		/**
		 * 覆盖操作,将覆盖List中指定位置的值
		 * @param byte[] key
		 * @param int index 位置
		 * @param byte[] value 值
		 * @return 状态码
		 * */
		public static String lset(byte[] key, int index, byte[] value) {
			try (Jedis jedis = jedisPool.getResource()) {
				String status = jedis.lset(key, index, value);
				return status;
			}
		}

		/**
		 * 覆盖操作,将覆盖List中指定位置的值
		 * @param key
		 * @param int index 位置
		 * @param String  value 值
		 * @return 状态码
		 * */
		public static String lset(String key, int index, String value) {
			return lset(SafeEncoder.encode(key), index, SafeEncoder.encode(value));
		}

		/**
		 * 在value的相对位置插入记录
		 * @param key
		 * @param LIST_POSITION   前面插入或后面插入
		 * @param String pivot 相对位置的内容
		 * @param String value 插入的内容
		 * @return 记录总数
		 * */
		public static long linsert(String key, LIST_POSITION where, String pivot, String value) {
			return linsert(SafeEncoder.encode(key), where, SafeEncoder.encode(pivot), SafeEncoder.encode(value));
		}

		/**
		 * 在指定位置插入记录
		 * @param String key
		 * @param LIST_POSITION 前面插入或后面插入
		 * @param byte[] pivot 相对位置的内容
		 * @param byte[] value 插入的内容
		 * @return 记录总数
		 * */
		public static long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
			try (Jedis jedis = jedisPool.getResource()) {
				long count = jedis.linsert(key, where, pivot, value);
				return count;
			}
		}

		/**
		 * 获取List中指定位置的值
		 * @param String  key
		 * @param int index 位置 
		 * @return 值
		 * **/
		public static String lindex(String key, int index) {
			return SafeEncoder.encode(lindex(SafeEncoder.encode(key), index));
		}

		/**
		 * 获取List中指定位置的值 
		 * @param byte[] key
		 * @param int index 位置
		 * @return 值
		 * **/
		public static byte[] lindex(byte[] key, int index) {
			try (Jedis jedis = jedisPool.getResource()) {
				byte[] value = jedis.lindex(key, index);
				return value;
			}
		}

		/**
		 * 将List中的第一条记录移出List
		 * @param String key
		 * @return 移出的记录 
		 * */
		public static String lpop(String key) {
			byte[] ret = lpop(SafeEncoder.encode(key));
			if (ret != null)
				return SafeEncoder.encode(ret);
			return null;
		}

		/**
		 * 将List中的第一条记录移出List
		 * @param byte[] key
		 * @return 移出的记录
		 * */
		public static byte[] lpop(byte[] key) {
			try (Jedis jedis = jedisPool.getResource()) {
				byte[] value = jedis.lpop(key);
				return value;
			}
		}

		/**
		 * 将List中最后第一条记录移出List
		 * 
		 * @param byte[] key
		 * @return 移出的记录
		 * */
		public static String rpop(String key) {
			try (Jedis jedis = jedisPool.getResource()) {
				String value = jedis.rpop(key);
				return value;
			}
		}

		/**
		 * 向List尾部追加记录
		 * @param String key
		 * @param String value
		 * @return 记录总数
		 * */
		public static long lpush(String key, String value) {
			return lpush(SafeEncoder.encode(key), SafeEncoder.encode(value));
		}

		/**
		 * 向List头部追加记录
		 * @param String  key
		 * @param String  value
		 * @return 记录总数
		 * */
		public static long rpush(String key, String value) {
			try (Jedis jedis = jedisPool.getResource()) {
				long count = jedis.rpush(key, value);
				return count;
			}
		}

		/**
		 * 向List头部追加记录
		 * @param String key
		 * @param String value
		 * @return 记录总数
		 * */
		public static long rpush(byte[] key, byte[] value) {
			try (Jedis jedis = jedisPool.getResource()) {
				long count = jedis.rpush(key, value);
				return count;
			}
		}

		/**
		 * 向List中追加记录
		 * @param byte[] key
		 * @param byte[] value
		 * @return 记录总数
		 * */
		public static long lpush(byte[] key, byte[] value) {
			try (Jedis jedis = jedisPool.getResource()) {
				long count = jedis.lpush(key, value);
				return count;
			}
		}

		/**
		 * 获取指定范围的记录，可以做为分页使用
		 * @param String key
		 * @param long start
		 * @param long end
		 * @return List
		 * */
		public static List<String> lrange(String key, long start, long end) {
			// ShardedJedis sjedis = getShardedJedis();
			try (Jedis jedis = jedisPool.getResource()) {
				List<String> list = jedis.lrange(key, start, end);
				return list;
			}
		}

		/**
		 * 获取指定范围的记录，可以做为分页使用
		 * @param byte[] key
		 * @param int start
		 * @param int end 如果为负数，则尾部开始计算
		 * @return List
		 * */
		public static List<byte[]> lrange(byte[] key, int start, int end) {
			try (Jedis jedis = jedisPool.getResource()) {
				List<byte[]> list = jedis.lrange(key, start, end);
				return list;
			}
		}

		/**
		 * 删除List中c条记录，被删除的记录值为value
		 * @param byte[] key
		 * @param int c 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
		 * @param byte[] value 要匹配的值
		 * @return 删除后的List中的记录数
		 * */
		public static long lrem(byte[] key, int c, byte[] value) {
			try (Jedis jedis = jedisPool.getResource()) {
				long count = jedis.lrem(key, c, value);
				return count;
			}
		}

		/**
		 * 删除List中c条记录，被删除的记录值为value
		 * @param String key
		 * @param int c 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
		 * @param String value 要匹配的值
		 * @return 删除后的List中的记录数
		 * */
		public static long lrem(String key, int c, String value) {
			return lrem(SafeEncoder.encode(key), c, SafeEncoder.encode(value));
		}

		/**
		 * 算是删除吧，只保留start与end之间的记录
		 * @param byte[] key
		 * @param int start 记录的开始位置(0表示第一条记录)
		 * @param int end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
		 * @return 执行状态码
		 * */
		public static String ltrim(byte[] key, int start, int end) {
			try (Jedis jedis = jedisPool.getResource()) {
				String str = jedis.ltrim(key, start, end);
				return str;
			}
		}

		/** 
		 * 算是删除吧，只保留start与end之间的记录
		 * @param String key 
		 * @param int start 记录的开始位置(0表示第一条记录)
		 * @param int end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
		 * @return 执行状态码
		 * */
		public static String ltrim(String key, int start, int end) {
			return ltrim(SafeEncoder.encode(key), start, end);
		}
	}

	public static class Strings {
		/**
		 * 根据key获取记录
		 * @param String  key
		 * @return 值
		 * */
		public static String get(String key) {
			try (Jedis jedis = jedisPool.getResource()) {
				String value = jedis.get(key);
				return value;
			}
		}

		/**
		 * 根据key获取记录
		 * @param byte[] key
		 * @return 值
		 * */
		public static byte[] get(byte[] key) {
			try (Jedis jedis = jedisPool.getResource()) {
				byte[] value = jedis.get(key);
				return value;
			}
		}

		/**
		 * 添加有过期时间的记录
		 * 
		 * @param String  key
		 * @param int seconds 过期时间，以秒为单位
		 * @param String value
		 * @return String 操作状态
		 * */
		public static String setEx(String key, int seconds, String value) {
			try (Jedis jedis = jedisPool.getResource()) {
				String str = jedis.setex(key, seconds, value);
				return str;
			}
		}

		/**
		 * 添加有过期时间的记录
		 * 
		 * @param String key
		 * @param int seconds 过期时间，以秒为单位
		 * @param String  value
		 * @return String 操作状态
		 * */
		public static String setEx(byte[] key, int seconds, byte[] value) {
			try (Jedis jedis = jedisPool.getResource()) {
				String str = jedis.setex(key, seconds, value);
				return str;
			}
		}

		/**
		 * 添加一条记录，仅当给定的key不存在时才插入
		 * @param String key
		 * @param String value
		 * @return long 状态码，1插入成功且key不存在，0未插入，key存在
		 * */
		public static long setnx(String key, String value) {
			try (Jedis jedis = jedisPool.getResource()) {
				long str = jedis.setnx(key, value);
				return str;
			}
		}

		/**
		 * 添加记录,如果记录已存在将覆盖原有的value
		 * @param String key
		 * @param String value
		 * @return 状态码
		 * */
		public static String set(String key, String value) {
			return set(SafeEncoder.encode(key), SafeEncoder.encode(value));
		}

		/**
		 * 添加记录,如果记录已存在将覆盖原有的value
		 * @param String  key
		 * @param String value
		 * @return 状态码
		 * */
		public static String set(String key, byte[] value) {
			return set(SafeEncoder.encode(key), value);
		}

		/**
		 * 添加记录,如果记录已存在将覆盖原有的value
		 * @param byte[] key
		 * @param byte[] value
		 * @return 状态码
		 * */
		public static String set(byte[] key, byte[] value) {
			try (Jedis jedis = jedisPool.getResource()) {
				String status = jedis.set(key, value);
				return status;
			}
		}

		/**
		 * 从指定位置开始插入数据，插入的数据会覆盖指定位置以后的数据<br/>
		 * 例:String str1="123456789";<br/>
		 * 对str1操作后setRange(key,4,0000)，str1="123400009";
		 * @param String  key
		 * @param long offset
		 * @param String  value
		 * @return long value的长度
		 * */
		public static long setRange(String key, long offset, String value) {
			try (Jedis jedis = jedisPool.getResource()) {
				long len = jedis.setrange(key, offset, value);
				return len;
			}
		}

		/**
		 * 在指定的key中追加value
		 * @param String  key
		 * @param String value
		 * @return long 追加后value的长度
		 * **/
		public static long append(String key, String value) {
			try (Jedis jedis = jedisPool.getResource()) {
				long len = jedis.append(key, value);
				return len;
			}
		}

		/**
		 * 将key对应的value减去指定的值，只有value可以转为数字时该方法才可用
		 * @param String key
		 * @param long number 要减去的值
		 * @return long 减指定值后的值
		 * */
		public static long decrBy(String key, long number) {
			try (Jedis jedis = jedisPool.getResource()) {
				long len = jedis.decrBy(key, number);
				return len;
			}
		}

		/**
		 * <b>可以作为获取唯一id的方法</b><br/>
		 * 将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
		 * @param String  key
		 * @param long number 要减去的值
		 * @return long 相加后的值
		 * */
		public static long incrBy(String key, long number) {
			try (Jedis jedis = jedisPool.getResource()) {
				long len = jedis.incrBy(key, number);
				return len;
			}
		}

		/**
		 * 对指定key对应的value进行截取 
		 * @param String   key
		 * @param long startOffset 开始位置(包含)
		 * @param long endOffset 结束位置(包含)
		 * @return String 截取的值
		 * */
		public static String getrange(String key, long startOffset, long endOffset) {
			try (Jedis jedis = jedisPool.getResource()) {
				String value = jedis.getrange(key, startOffset, endOffset);
				return value;
			}
		}

		/**
		 * 获取并设置指定key对应的value<br/>
		 * 如果key存在返回之前的value,否则返回null
		 * @param String  key
		 * @param String value
		 * @return String 原始value或null
		 * */
		public static String getSet(String key, String value) {
			try (Jedis jedis = jedisPool.getResource()) {
				String str = jedis.getSet(key, value);
				return str;
			}
		}

		/**
		 * 批量获取记录,如果指定的key不存在返回List的对应位置将是null
		 * @param String keys
		 * @return List<String> 值得集合
		 * */
		public static List<String> mget(String... keys) {
			try (Jedis jedis = jedisPool.getResource()) {
				List<String> str = jedis.mget(keys);
				return str;
			}
		}

		/**
		 * 批量存储记录
		 * @param String keysvalues 例:keysvalues="key1","value1","key2","value2";
		 * @return String 状态码 
		 * */
		public static String mset(String... keysvalues) {
			try (Jedis jedis = jedisPool.getResource()) {
				String str = jedis.mset(keysvalues);
				return str;
			}
		}

		/**
		 * 获取key对应的值的长度
		 * @param String key
		 * @return value值得长度
		 * */
		public static long strlen(String key) {
			try (Jedis jedis = jedisPool.getResource()) {
				long len = jedis.strlen(key);
				return len;
			}
		}
	}
}

