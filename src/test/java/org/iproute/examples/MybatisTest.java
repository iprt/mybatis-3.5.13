package org.iproute.examples;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.iproute.examples.demo.DemoBeanMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * MybatisTest
 *
 * @author tech@intellij.io
 * @since 2025-01-15
 */
public class MybatisTest {

    @Test
    public void testDemo() throws IOException {

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 获取Mapper
        DemoBeanMapper mapper = sqlSession.getMapper(DemoBeanMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id", "1");

        System.out.println(mapper.selectById(map).toString());

        // sqlSession.commit();
        sqlSession.close();

    }

}
