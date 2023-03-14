package org.apache.ibatis.examples;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Main
 *
 * @author zhuzhenjie
 * @since 3/13/2023
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 获取Mapper
        DemoBeanMapper mapper = sqlSession.getMapper(DemoBeanMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id", "1");
        log.info(mapper.selectById(map).toString());

        // sqlSession.commit();
        sqlSession.close();
    }
}
