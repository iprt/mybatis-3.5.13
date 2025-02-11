/**
 * Copyright 2009-2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.binding;

import org.apache.ibatis.session.SqlSession;
import org.iproute.logfunc.SrcLog;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lasse Voss
 */
public class MapperProxyFactory<T> {

    private final Class<T> mapperInterface;
    private final Map<Method, MapperMethod> methodCache = new ConcurrentHashMap<>();

    public MapperProxyFactory(Class<T> mapperInterface) {
        SrcLog.get().addNode(this, "MapperProxyFactory", "Class<T>")
                .logMsg("MapperProxyFactory理解：一个 Mapper接口就有一个 MapperProxyFactory，MapperProxyFactory 用于创建 Mapper接口的代理对象");
        this.mapperInterface = mapperInterface;
    }

    public Class<T> getMapperInterface() {
        return mapperInterface;
    }

    public Map<Method, MapperMethod> getMethodCache() {
        return methodCache;
    }

    @SuppressWarnings("unchecked")
    protected T newInstance(MapperProxy<T> mapperProxy) {
        SrcLog.get().addNode(this, "newInstance", "MapperProxy<T>")
                .logMsg("通过MapperProxyFactory 创建 Mapper接口的 代理对象|mapperInterface={}", mapperInterface.getName());

        SrcLog.get().addNode(this, "newInstance", "MapperProxy<T>")
                .tips("Mapper接口封装再 MapperProxy<T> 中，通过动态代理创建 Mapper 接口的代理对象");

        SrcLog.get().addNode(this, "newInstance", "MapperProxy<T>")
                .tips("MapperProxy 继承了 InvocationHandler 接口，invoke执行SQL的逻辑本质上是【Dispatch】，每个Mapper接口的方法，都会对应一个 MapperMethod 存储在 HashMap中");

        SrcLog.get().addNode(this, "newInstance", "MapperProxy<T>")
                .tips("获取到的Mapper接口的代理对象是Proxy.newProxyInstance创建的, 但是执行逻辑是 MapperProxy控制的。【关键理解：Proxy.newProxyInstance 代理的接口信息，MapperProxy代理执行了真正的CURD】");

        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }

    public T newInstance(SqlSession sqlSession) {
        SrcLog.get().addNode(this, "getMapper")
                .tips("获取Mapper代理对象对象的本质: step2 Proxy.newProxyInstance 反射创建Mapper接口代理对象");
        final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface, methodCache);
        return newInstance(mapperProxy);
    }

}
