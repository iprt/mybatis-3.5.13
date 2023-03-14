package org.apache.ibatis.examples;

import java.util.Map;

/**
 * DemoMapper
 *
 * @author zhuzhenjie
 * @since 3/13/2023
 */
public interface DemoMapper {

    /**
     * Select all map.
     *
     * @param map the map
     * @return the map
     */
    Map<String, Object> selectAll(Map<String, Object> map);
}
