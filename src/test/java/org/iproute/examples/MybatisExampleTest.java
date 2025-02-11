package org.iproute.examples;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.iproute.examples.demo.DemoBeanMapper;
import org.iproute.logfunc.SrcLog;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * MybatisExampleTest
 *
 * @author tech@intellij.io
 * @since 2025-01-15
 */
public class MybatisExampleTest {

    @Test
    public void testDemo() throws IOException {
        SrcLog.get().addGroup("create sqlSessionFactory");

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        this.printMybatisConfiguration(sqlSessionFactory.getConfiguration());

        SrcLog.get().addGroup("sqlSessionFactory.openSession()");
        SqlSession sqlSession = sqlSessionFactory.openSession();

        SrcLog.get().addGroup("sqlSession.getMapper()");
        // 获取Mapper
        DemoBeanMapper mapper = sqlSession.getMapper(DemoBeanMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id", "1");

        System.out.println(mapper.selectById(map).toString());

        // sqlSession.commit();
        sqlSession.close();

        SrcLog.get().printLog();
    }


    private void printMybatisConfiguration(Configuration configuration) {
        System.out.println("========== print mybatis configuration start ==========");

        Environment environment = configuration.getEnvironment();

        System.out.println("======== 打印 environment 开始 ========");

        String envId = environment.getId();
        System.out.println("environment.id | envId = " + envId);

        DataSource dataSource = environment.getDataSource();

        System.out.println("environment.dataSource | dataSource.class = " + dataSource.getClass());

        TransactionFactory transactionFactory = environment.getTransactionFactory();

        System.out.println("environment.transactionFactory | transactionFactory.class = " + transactionFactory.getClass());

        System.out.println("======== 打印 environment 结束 ========\r\n");

        System.out.println("======== 打印 boolean 配置 开始 ========");

        boolean safeRowBoundsEnabled = configuration.isSafeRowBoundsEnabled();
        boolean safeResultHandlerEnabled = configuration.isSafeResultHandlerEnabled();
        boolean mapUnderscoreToCamelCase = configuration.isMapUnderscoreToCamelCase();
        boolean aggressiveLazyLoading = configuration.isAggressiveLazyLoading();
        boolean multipleResultSetsEnable = configuration.isMultipleResultSetsEnabled();
        boolean useGeneratedKeys = configuration.isUseGeneratedKeys();
        boolean useColumnLabel = configuration.isUseColumnLabel();
        boolean cacheEnabled = configuration.isCacheEnabled();
        boolean callSettersOnNulls = configuration.isCallSettersOnNulls();
        boolean useActualParamName = configuration.isUseActualParamName();
        boolean returnInstanceForEmptyRow = configuration.isReturnInstanceForEmptyRow();

        System.out.println("是否启用安全的行边界 | safeRowBoundsEnabled = " + safeRowBoundsEnabled);
        System.out.println("是否启用安全的结果处理器 | safeResultHandlerEnabled = " + safeResultHandlerEnabled);
        System.out.println("是否将下划线映射为驼峰命名法 | mapUnderscoreToCamelCase = " + mapUnderscoreToCamelCase);
        System.out.println("是否启用激进的懒加载 | aggressiveLazyLoading = " + aggressiveLazyLoading);
        System.out.println("是否启用多结果集 | multipleResultSetsEnable = " + multipleResultSetsEnable);
        System.out.println("是否使用生成的键 | useGeneratedKeys = " + useGeneratedKeys);
        System.out.println("是否使用列标签 | useColumnLabel = " + useColumnLabel);
        System.out.println("是否启用缓存 | cacheEnabled = " + cacheEnabled);
        System.out.println("是否在值为 null 时调用 setter 方法 | callSettersOnNulls = " + callSettersOnNulls);
        System.out.println("是否使用实际的参数名称 | useActualParamName = " + useActualParamName);
        System.out.println("是否在空行时返回实例 | returnInstanceForEmptyRow = " + returnInstanceForEmptyRow);

        System.out.println("======== 打印 boolean 配置 结束 ========\r\n");

        System.out.println("========== print mybatis configuration end    ==========");

    }

}
